package com.techelevator.campground.models;


import java.time.LocalDate;
import java.util.List;

public interface SiteDAO {
	
	public List<Site> getAllSites();
	
	public List<Site> getSiteByAvailability (Long campgroundId, LocalDate fromDate, LocalDate toDate);
	
	public List<Site> getSiteByCampground (Long campgroundId);
		
	public List<Site> getSiteById (Long siteId);
	
	public List<Site> getSiteBySiteNumber (int siteNumber);
	
	public List<Site> getSiteByMaxOccupancy (int maxOccupancy);
	
	public List<Site> getSiteByAccessible (boolean accessible);
	
	public List<Site> getSiteByUtilities (boolean utilities);
}
