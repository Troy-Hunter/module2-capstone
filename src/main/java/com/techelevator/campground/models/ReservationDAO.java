package com.techelevator.campground.models;

import java.time.LocalDate;
import java.util.List;

public interface ReservationDAO {
	
	public List<Reservation> getAllReservations();
	
	public Reservation addReservation(Long reservationId, Long siteId, String reservationName, LocalDate resStartDate, LocalDate resEndDate, LocalDate resCreate);
	
	public void removeReservation(Long reservationId, Long siteId, String reservationName, LocalDate resStartDate, LocalDate resEndDate, LocalDate resCreate);

	public Reservation getReservationBySiteNumber(int siteNumber);
	
	public Reservation getReservationByResId(Long reservationId);
	
	public Reservation getReservationByResName(String reservationName);
}
