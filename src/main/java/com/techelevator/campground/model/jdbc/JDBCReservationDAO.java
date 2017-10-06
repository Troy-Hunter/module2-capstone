package com.techelevator.campground.model.jdbc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.models.Reservation;
import com.techelevator.campground.models.ReservationDAO;
import com.techelevator.campground.models.Site;

public class JDBCReservationDAO implements ReservationDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public JDBCReservationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Reservation> getAllReservations() {
		List<Reservation> reservationList = new ArrayList<>();
		String sqlReservationNumbers = "SELECT * FROM site";
		SqlRowSet returnReservations = jdbcTemplate.queryForRowSet(sqlReservationNumbers);
			while(returnReservations.next()) {
				Reservation res = mapRowToReservation(returnReservations);
				reservationList.add(res);
			}
		return reservationList;
	}

	@Override
	public List<Reservation> addReservation(Long reservationId, Long siteId, String reservationName, LocalDate resStartDate,
			LocalDate resEndDate, LocalDate resCreate) {
		String sqlNewReservation = "INSERT INTO reservation (site_id, name, from_date, to_date, create_date) VALUES (?, ?, ?, ?, ?) RETURNING reservation_id";
		Long newReservation = jdbcTemplate.queryForObject(sqlNewReservation, Long.class, siteId, reservationName, resStartDate, resEndDate, LocalDate.now());
		return getReservationByResId(newReservation);
	}

	@Override
	public void removeReservation(Long reservationId, Long siteId, String reservationName, LocalDate resStartDate,
			LocalDate resEndDate, LocalDate resCreate) {
		String sqlRemoveReservation = "DELETE * FROM reservation WHERE reservation_id = ?";
		
		jdbcTemplate.update(sqlRemoveReservation, Long.class, reservationName, resStartDate, resEndDate, resCreate);

	}

	@Override
	public List<Reservation> getReservationBySiteNumber(int siteNumber) {
		List<Reservation> reservationList = new ArrayList<>();
		String sqlReservationNumbers = "SELECT * FROM site WHERE site_number = ?";
		SqlRowSet returnReservations = jdbcTemplate.queryForRowSet(sqlReservationNumbers);
			while(returnReservations.next()) {
				Reservation campSite = mapRowToReservation(returnReservations);
				reservationList.add(campSite);
			}
		return reservationList;
	}

	@Override
	public List<Reservation> getReservationByResId(Long reservationId) {
		List<Reservation> reservationList = new ArrayList<>();
		String sqlReservationNumbers = "SELECT s.* FROM site s JOIN reservation res ON res.site_id = s.site_id WHERE reservation_id = ?";
		SqlRowSet returnReservations = jdbcTemplate.queryForRowSet(sqlReservationNumbers, reservationId);
			while(returnReservations.next()) {
				Reservation campSite = mapRowToReservation(returnReservations);
				reservationList.add(campSite);
			}
		return reservationList;
	}

	@Override
	public List<Reservation> getReservationByResName(String reservationName) {
		List<Reservation> reservationList = new ArrayList<>();
		String sqlReservationNumbers = "SELECT s.* FROM site s JOIN reservation res ON res.site_id = s.site_id WHERE s.name = ?";
		SqlRowSet returnReservations = jdbcTemplate.queryForRowSet(sqlReservationNumbers);
			while(returnReservations.next()) {
				Reservation campSite = mapRowToReservation(returnReservations);
				reservationList.add(campSite);
			}
		return reservationList;
	}
	
	private Reservation mapRowToReservation (SqlRowSet results) {
		Reservation theReservation;
		theReservation = new Reservation();
		theReservation.getReservationId();
		theReservation.getSiteId();
		theReservation.getName();
		theReservation.getResStartDate();
		theReservation.getResEndDate();
		theReservation.getCreateDate();
		return theReservation;
	}

}
