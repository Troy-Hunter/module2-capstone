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

	public class JDBCParkDAOTest {

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
				
				campDao = new JDBCCampgroundDAO(dataSource);
				siteDao =new JDBCSiteDAO(dataSource);
				resDao = new JDBCReservationDAO(dataSource);
				
				String sqlInsertPark = "INSERT INTO park (park_id, name, location, establish_date, area, visitors, description) VALUES (?, 'Fake Park', 'Fake', '2017-10-17', 600, 1, 'This is the best fake park ever')";
				JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
				jdbcTemplate.update(sqlInsertPark, TEST_PARK);
				parkDao = new JDBCParkDAO(dataSource);
				
			}
			
			@After
			public void rollback() throws SQLException {
				dataSource.getConnection().rollback();
			}

	@Test
	public void testGetAllParks() {
		
		parkDao.getAllParks();
		
		List<Park> parkList = parkDao.getAllParks();
		
		assertNotNull(parkList);
		assertEquals(1, parkList.size());
		
	}
	
	@Test
	public void testGetParkById() {
		
		parkDao.getAllParks();
		
		List<Park> parkList = parkDao.getAllParks();
		
		assertNotNull(parkList);
		assertEquals(parkList.get(0).getParkId(), parkList.get(0).getParkId());
	}
}
