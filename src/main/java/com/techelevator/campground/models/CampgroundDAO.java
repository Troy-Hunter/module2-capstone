package com.techelevator.campground.models;

import java.math.BigDecimal;
import java.util.List;

public interface CampgroundDAO {

	public List<Campground> getAllCampgrounds ();
	
	public List<Campground> getCampgroundByPark (Long parkId);
	
	public List<Campground> getCampgroundByName (String name);
	
	public List<Campground> getCampgroundById (Long campgroundId);
	
	public List<Campground> getCampgroundByDailyFee (BigDecimal dailyFee);
	
}
