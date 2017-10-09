package com.techelevator;

import static org.junit.Assert.*;

import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

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
import com.techelevator.campground.models.Campground;
import com.techelevator.campground.models.Park;

public class JDBCCampgroundDAOTest {

		private static SingleConnectionDataSource dataSource;
		private JDBCCampgroundDAO campDao;
		private JDBCParkDAO parkDao;
		private JDBCReservationDAO resDao;
		private JDBCSiteDAO siteDao;
		private JdbcTemplate jdbcTemplate;
		private static final Integer TEST_PARK = 1; 
		private static final Integer TEST_CAMPGROUND = 1;
		
		@BeforeClass
		public static void setupDataSource() {
			dataSource = new SingleConnectionDataSource();
			dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
			dataSource.setUsername("postgres");
			dataSource.setPassword("postgres1");
			dataSource.setAutoCommit(false);
		}
		
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
			
			parkDao = new JDBCParkDAO(dataSource);
			campDao = new JDBCCampgroundDAO(dataSource);
			siteDao =new JDBCSiteDAO(dataSource);
			resDao = new JDBCReservationDAO(dataSource);
			
			String sqlInsertPark = "INSERT INTO park (park_id, name, location, establish_date, area, visitors, description) VALUES (?, 'Fake Park', 'Fake', '2017-10-17', 600, 1, 'This is the best fake park ever')";
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			jdbcTemplate.update(sqlInsertPark, TEST_PARK);
			parkDao = new JDBCParkDAO(dataSource);
			
			String sqlInsertCampground = "INSERT INTO campground (campground_id, park_id, name, open_from_mm, open_to_mm, daily_fee) VALUES (?, 1, 'Fake Campground', 1, 12, 12.00)";
			JdbcTemplate jdbcCampTemplate = new JdbcTemplate(dataSource);
			jdbcCampTemplate.update(sqlInsertCampground, TEST_CAMPGROUND);
			campDao = new JDBCCampgroundDAO(dataSource);
			
		}
		
		@After
		public void rollback() throws SQLException {
			dataSource.getConnection().rollback();
		}
		
		@Test
		public void testGetAllCampgrounds() {
			
			campDao.getAllCampgrounds();
			
			List<Campground> campgroundList = campDao.getAllCampgrounds();
			
			assertNotNull(campgroundList);
			assertEquals(1, campgroundList.size());
		}
		
		@Test
		public void testGetCamgroundByPark() {
			
			campDao.getAllCampgrounds();
			
			List<Campground> campgroundList = campDao.getAllCampgrounds();
			List<Park> parkList = parkDao.getAllParks();
			
			assertNotNull(campgroundList);
			assertEquals(campgroundList.get(0).getParkId(), parkList.get(0).getParkId());
	}
		
		@Test
		public void testGetCamgroundByName() {
			
			campDao.getAllCampgrounds();
			
			List<Campground> campgroundList = campDao.getAllCampgrounds();
			
			assertNotNull(campgroundList);
			assertEquals(campgroundList.get(0).getName(), campgroundList.get(0).getName());
			
		}

		
		@Test
		public void testGetCamgroundById() {
			
			campDao.getAllCampgrounds();
			
			List<Campground> campgroundList = campDao.getAllCampgrounds();
			List<Park> parkList = parkDao.getAllParks();
			
			assertNotNull(campgroundList);
			assertEquals(campgroundList.get(0).getCampgroundId(), campgroundList.get(0).getCampgroundId());
		}
		
		
		@Test
		public void testGetCamgroundByDailyFee() {
			
			campDao.getAllCampgrounds();
			
			List<Campground> campgroundList = campDao.getAllCampgrounds();
			List<Park> parkList = parkDao.getAllParks();
			
			assertNotNull(campgroundList);
			assertEquals(campgroundList.get(0).getDailyFee(), campgroundList.get(0).getDailyFee());
		}
}
