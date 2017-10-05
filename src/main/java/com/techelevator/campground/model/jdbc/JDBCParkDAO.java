package com.techelevator.campground.model.jdbc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.models.Park;
import com.techelevator.campground.models.ParkDAO;

public class JDBCParkDAO implements ParkDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public JDBCParkDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Park> getAllParks() {
		List<Park> parkList = new ArrayList<>();
		String sqlParkNames = "SELECT * FROM park";
		SqlRowSet returnParks = jdbcTemplate.queryForRowSet(sqlParkNames);
			while(returnParks.next()) {
				Park nationalPark = mapRowToParks(returnParks);
				parkList.add(nationalPark);
			}
		return parkList;
	}

	@Override
	public Park addPark(Long parkId, String parkName, String parkLocation, LocalDate dateEstablished, int parkArea, int annualVisitors, String parkDescription) {
		String sqlNewPark = "INSERT INTO park (name, location, establish_date, area, visitors, description) VALUES (?, ?, ?, ?, ?, ?) RETURNING park_id";
		Long newPark = jdbcTemplate.queryForObject(sqlNewPark, Long.class, parkName, parkLocation, dateEstablished, parkArea, annualVisitors, parkDescription);
		return getParkById(newPark);
	}

	@Override
	public void removePark(Long park_id, String parkName, String parkLocation, LocalDate dateEstablished, int parkArea, int annualVisitors, String parkDescription) {
		String sqlRemovePark = "DELETE * FROM park WHERE park_id = ?";
		
		jdbcTemplate.update(sqlRemovePark, Long.class, parkName, parkLocation, dateEstablished, parkArea, annualVisitors, parkDescription);
	}
	
	@Override
	public Park getParkById(Long parkId) {
		Park thePark = null;
		String sqlParkById = "SELECT * " +
							   "FROM park "+
							   "WHERE park_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlParkById, parkId);
		if(results.next()) {
			thePark = mapRowToParks(results);
		}
		return thePark;
		}
	
	private Park mapRowToParks (SqlRowSet results) {
		Park thePark;
		thePark = new Park();
		thePark.setParkId(results.getLong("park_id"));
		thePark.setName(results.getString("name"));
		thePark.setLocation(results.getString("location"));
		thePark.setEstablishDate(results.getDate("establish_date").toLocalDate());
		thePark.setArea(results.getInt("area"));
		thePark.setVisitors(results.getInt("visitors"));
		thePark.setDescription(results.getString("description"));
		return thePark;
	}
}
