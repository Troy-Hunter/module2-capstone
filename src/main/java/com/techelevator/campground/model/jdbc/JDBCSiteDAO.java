package com.techelevator.campground.model.jdbc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.models.Site;
import com.techelevator.campground.models.SiteDAO;

public class JDBCSiteDAO implements SiteDAO {

	private JdbcTemplate jdbcTemplate;
	
	public JDBCSiteDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Site> getAllSites() {
		List<Site> siteList = new ArrayList<>();
		String sqlSiteNumbers = "SELECT * FROM site";
		SqlRowSet returnSites = jdbcTemplate.queryForRowSet(sqlSiteNumbers);
			while(returnSites.next()) {
				Site campSite = mapRowToSite(returnSites);
				siteList.add(campSite);
			}
		return siteList;
	}

	@Override
	public List<Site> getSiteByCampground(Long campgroundId) {
			List<Site> siteList = new ArrayList<>();
			String sqlSiteNumbers = "SELECT * FROM site WHERE campground_id = ?";
			SqlRowSet returnSites = jdbcTemplate.queryForRowSet(sqlSiteNumbers, campgroundId);
				while(returnSites.next()) {
					Site campSite = mapRowToSite(returnSites);
					siteList.add(campSite);
				}
			return siteList;
	}

	@Override
	public List<Site> getSiteById(Long siteId) {
		List<Site> siteList = new ArrayList<>();
		String sqlSiteNumbers = "SELECT * FROM site WHERE site_id = ?";
		SqlRowSet returnSites = jdbcTemplate.queryForRowSet(sqlSiteNumbers);
			while(returnSites.next()) {
				Site campSite = mapRowToSite(returnSites);
				siteList.add(campSite);
			}
		return siteList;
		
	}

	@Override
	public List<Site> getSiteBySiteNumber(int siteNumber) {
		List<Site> siteList = new ArrayList<>();
		String sqlSiteNumbers = "SELECT * FROM site WHERE site_number = ?";
		SqlRowSet returnSites = jdbcTemplate.queryForRowSet(sqlSiteNumbers);
			while(returnSites.next()) {
				Site campSite = mapRowToSite(returnSites);
				siteList.add(campSite);
			}
		return siteList;
	}

	@Override
	public List<Site> getSiteByMaxOccupancy(int maxOccupancy) {
		List<Site> siteList = new ArrayList<>();
		String sqlSiteNumbers = "SELECT * FROM site WHERE max_occupancy = ?";
		SqlRowSet returnSites = jdbcTemplate.queryForRowSet(sqlSiteNumbers);
			while(returnSites.next()) {
				Site campSite = mapRowToSite(returnSites);
				siteList.add(campSite);
			}
		return siteList;
	}

	@Override
	public List<Site> getSiteByAccessible(boolean accessible) {
		List<Site> siteList = new ArrayList<>();
		String sqlSiteNumbers = "SELECT * FROM site WHERE site_accessible = ?";
		SqlRowSet returnSites = jdbcTemplate.queryForRowSet(sqlSiteNumbers);
			while(returnSites.next()) {
				Site campSite = mapRowToSite(returnSites);
				siteList.add(campSite);
			}
		return siteList;
	}

	@Override
	public List<Site> getSiteByUtilities(boolean utilities) {
		List<Site> siteList = new ArrayList<>();
		String sqlSiteNumbers = "SELECT * FROM site WHERE utilities = ?";
		SqlRowSet returnSites = jdbcTemplate.queryForRowSet(sqlSiteNumbers);
			while(returnSites.next()) {
				Site campSite = mapRowToSite(returnSites);
				siteList.add(campSite);
			}
		return siteList;
	}
	
	@Override
	public List<Site> getSiteByAvailability(Long campgroundId, LocalDate fromDate, LocalDate toDate) {
		List<Site> siteList = new ArrayList<>();
		String sqlSiteNumbers = "SELECT * from site s WHERE campground_id = ? "
								+ "AND s.site_id NOT IN (SELECT s.site_id FROM site s "
								+ "JOIN reservation res ON res.site_id = s.site_id "
								+ "WHERE s.campground_id = ? AND ("
								+ "res.from_date BETWEEN ? AND ? OR "
								+ "res.to_date BETWEEN ? AND ? OR "
								+ "(? BETWEEN res.from_date AND res.to_date AND "
								+ "? BETWEEN res.from_date AND res.to_date) "
								+ ")) LIMIT 5";
		SqlRowSet returnSites = jdbcTemplate.queryForRowSet(sqlSiteNumbers, campgroundId, campgroundId, fromDate, toDate, fromDate, toDate, fromDate, toDate);
			while(returnSites.next()) {
				Site campSite = mapRowToSite(returnSites);
				siteList.add(campSite);
			}
		return siteList;	
	}
	
	private Site mapRowToSite (SqlRowSet results) {
		Site theSite;
		theSite = new Site();
		theSite.setSiteId(results.getLong("site_id"));
		theSite.setCampgroundId(results.getLong("campground_id"));
		theSite.setSiteNumber(results.getInt("site_number"));
		theSite.setMaxOccupancy(results.getInt("max_occupancy"));
		theSite.setAccessible(results.getBoolean("accessible"));
		theSite.setMaxRvLength(results.getInt("max_rv_length"));
		theSite.setUtilities(results.getBoolean("utilities"));
		return theSite;
	}


}
