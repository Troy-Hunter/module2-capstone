package com.techelevator.campground.model.jdbc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.models.Campground;
import com.techelevator.campground.models.CampgroundDAO;

public class JDBCCampgroundDAO implements CampgroundDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public JDBCCampgroundDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Campground> getAllCampgrounds() {
		List<Campground> campgroundList = new ArrayList<>();
		String sqlCampgroundNames = "SELECT * FROM campground";
		SqlRowSet returnCampgrounds = jdbcTemplate.queryForRowSet(sqlCampgroundNames);
			while(returnCampgrounds.next()) {
				Campground campName = mapRowToCampground(returnCampgrounds);
				campgroundList.add(campName);
			}
		return campgroundList;
	}

	@Override
	public List<Campground> getCampgroundByPark(Long parkId) {
		List<Campground> campgroundList = new ArrayList<>();
		String sqlCampgroundNames = "SELECT * FROM campground WHERE park_id = ?";
		SqlRowSet returnCampgrounds = jdbcTemplate.queryForRowSet(sqlCampgroundNames, parkId);
			while(returnCampgrounds.next()) {
				Campground nationalPark = mapRowToCampground(returnCampgrounds);
				campgroundList.add(nationalPark);
			}
		return campgroundList;
	}

	@Override
	public List<Campground> getCampgroundByName(String name) {
		List<Campground> campgroundList = new ArrayList<>();
		String sqlCampgroundNames = "SELECT * FROM campground WHERE name = ?";
		SqlRowSet returnCampgrounds = jdbcTemplate.queryForRowSet(sqlCampgroundNames, name);
			while(returnCampgrounds.next()) {
				Campground nationalPark = mapRowToCampground(returnCampgrounds);
				campgroundList.add(nationalPark);
			}
		return campgroundList;
	}

	@Override
	public List<Campground> getCampgroundById(Long campgroundId) {
		List<Campground> campgroundList = new ArrayList<>();
		String sqlCampgroundNames = "SELECT * FROM campground WHERE campground_id = ?";
		SqlRowSet returnCampgrounds = jdbcTemplate.queryForRowSet(sqlCampgroundNames, campgroundId);
			while(returnCampgrounds.next()) {
				Campground nationalPark = mapRowToCampground(returnCampgrounds);
				campgroundList.add(nationalPark);
			}
		return campgroundList;
	}

	@Override
	public List<Campground> getCampgroundByDailyFee(BigDecimal dailyFee) {
		List<Campground> campgroundList = new ArrayList<>();
		String sqlCampgroundNames = "SELECT * FROM campground WHERE daily_fee LIKE ?";
		SqlRowSet returnCampgrounds = jdbcTemplate.queryForRowSet(sqlCampgroundNames, "%" + dailyFee + "%");
			while(returnCampgrounds.next()) {
				Campground nationalPark = mapRowToCampground(returnCampgrounds);
				campgroundList.add(nationalPark);
			}
		return campgroundList;
	}

	private Campground mapRowToCampground (SqlRowSet results) {
		Campground theCampground;
		theCampground = new Campground();
		theCampground.setCampgroundId(results.getLong("campground_id"));
		theCampground.setParkId(results.getLong("park_id"));
		theCampground.setName(results.getString("name"));
		theCampground.setOpeningMonth(results.getString("open_from_mm"));
		theCampground.setClosingMonth(results.getString("open_to_mm"));
		theCampground.setDailyFee(results.getBigDecimal("daily_fee"));
		return theCampground;
	}
}
