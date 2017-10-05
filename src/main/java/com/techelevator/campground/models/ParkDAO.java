package com.techelevator.campground.models;

import java.time.LocalDate;
import java.util.List;

public interface ParkDAO {

	public List<Park> getAllParks();
	
	public Park addPark(Long park_id, String name, String location, LocalDate established, int area, int annualVisitors, String description);
	
	public void removePark(Long park_id, String name, String location, LocalDate established, int area, int annualVisitors, String description);

	public Park getParkById(Long parkId);
}
