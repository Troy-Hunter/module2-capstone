package com.techelevator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.campground.model.jdbc.JDBCCampgroundDAO;
import com.techelevator.campground.model.jdbc.JDBCParkDAO;
import com.techelevator.campground.model.jdbc.JDBCReservationDAO;
import com.techelevator.campground.model.jdbc.JDBCSiteDAO;
import com.techelevator.campground.models.Site;



public class JDBCSiteDAOTest {
	
private static SingleConnectionDataSource dataSource;
private JdbcTemplate jdbcTemplate;
private JDBCReservationDAO resDao;
private JDBCSiteDAO siteDao;
private JDBCCampgroundDAO campDao;
private JDBCParkDAO parkDao;

	
	/* Before any tests are run, this method initializes the datasource for testing. */
	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		/* The following line disables autocommit for connections 
		 * returned by this DataSource. This allows us to rollback
		 * any changes after each test */
		dataSource.setAutoCommit(false);
	}
	
	/* After all tests have finished running, this method will close the DataSource */
	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}

	@Before
	public void setup() {
		jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update("DELETE FROM reservation");
		jdbcTemplate.update("DELETE FROM site");
		jdbcTemplate.update("DELETE FROM campground");
		jdbcTemplate.update("DELETE FROM park");
		resDao = new JDBCReservationDAO(dataSource);
		siteDao = new JDBCSiteDAO(dataSource);
		campDao = new JDBCCampgroundDAO(dataSource);
		parkDao = new JDBCParkDAO(dataSource);
	}
	/* After each test, we rollback any changes that were made to the database so that
	 * everything is clean for the next test */
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	/* This method provides access to the DataSource for subclasses so that 
	 * they can instantiate a DAO for testing */
	protected DataSource getDataSource() {
		return dataSource;
	}

	@Test
	public void testGetAllSites() {
		Site newSite = siteDao.createSite(1L, 1L, 1, 5, true, 20, true);
		Site newSite2 = siteDao.createSite(2L, 2L, 2, 6, true, 35, true);
		
		List<Site> siteList = siteDao.getAllSites();
		
		assertNotNull(siteList);
		assertEquals(1, siteList.size());
		assertEquals(newSite.getSiteId(), siteList.get(0).getSiteId());
		assertEquals(newSite2.getSiteId(), siteList.get(1).getSiteId());
	}

	@Test
	public void testGetSiteById() {
		Site newSite = siteDao.createSite(1L, 1L, 1, 5, true, 20, true);
		
		List<Site> siteList = siteDao.getSiteById(1L);
		
		assertNotNull(siteList);
		assertEquals(1, siteList.size());
		assertEquals(newSite.getSiteId(), siteList.get(0).getSiteId());
	}

	@Test
	public void testGetSiteBySiteNumber() {
		Site newSite = siteDao.createSite(1L, 1L, 1, 5, true, 20, true);
		
		List<Site> siteList = siteDao.getSiteById(1L);
		
		assertNotNull(siteList);
		assertEquals(1, siteList.size());
		assertEquals(newSite.getSiteNumber(), siteList.get(0).getSiteNumber());
	}

	@Test
	public void testGetSiteByMaxOccupancy() {
		Site newSite = siteDao.createSite(1L, 1L, 1, 5, true, 20, true);
		
		List<Site> siteList = siteDao.getSiteById(1L);
		
		assertNotNull(siteList);
		assertEquals(1, siteList.size());
		assertEquals(newSite.getMaxOccupancy(), siteList.get(0).getMaxOccupancy());
	}
}
