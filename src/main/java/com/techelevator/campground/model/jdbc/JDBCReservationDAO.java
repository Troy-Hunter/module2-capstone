package com.techelevator.campground.model.jdbc;

import java.time.LocalDate;
import java.util.List;

import com.techelevator.campground.models.Reservation;
import com.techelevator.campground.models.ReservationDAO;

public class JDBCReservationDAO implements ReservationDAO {

	@Override
	public List<Reservation> getAllReservations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reservation addReservation(Long reservationId, Long siteId, String reservationName, LocalDate resStartDate,
			LocalDate resEndDate, LocalDate resCreate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeReservation(Long reservationId, Long siteId, String reservationName, LocalDate resStartDate,
			LocalDate resEndDate, LocalDate resCreate) {
		// TODO Auto-generated method stub

	}

	@Override
	public Reservation getReservationBySiteNumber(int siteNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reservation getReservationByResId(Long reservationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reservation getReservationByResName(String reservationName) {
		// TODO Auto-generated method stub
		return null;
	}

}
