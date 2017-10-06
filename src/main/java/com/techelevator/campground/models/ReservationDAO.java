package com.techelevator.campground.models;

import java.time.LocalDate;
import java.util.List;

public interface ReservationDAO {
	
	public List<Reservation> getAllReservations();
	
	public List<Reservation> addReservation(Long reservationId, Long siteId, String reservationName, LocalDate resStartDate, LocalDate resEndDate, LocalDate resCreate);
	
	public void removeReservation(Long reservationId, Long siteId, String reservationName, LocalDate resStartDate, LocalDate resEndDate, LocalDate resCreate);

	public List<Reservation> getReservationBySiteNumber(int siteNumber);
	
	public List<Reservation> getReservationByResId(Long reservationId);
	
	public List<Reservation> getReservationByResName(String reservationName);
}
