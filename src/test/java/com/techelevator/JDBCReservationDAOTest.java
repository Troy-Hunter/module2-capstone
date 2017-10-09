package com.techelevator;

import static org.junit.Assert.*;

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
import com.techelevator.campground.models.Reservation;
import com.techelevator.campground.models.Site;

public class JDBCReservationDAOTest {

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
	public void testGetAllReservations() {
		List<Reservation> newRes = resDao.addReservation(1L, 1L, "Bobdob", LocalDate.parse("2017-10-10"), LocalDate.parse("2017-10-15"), LocalDate.now());
		List<Reservation> newRes2 = resDao.addReservation(2L, 2L, "Ellain", LocalDate.parse("2017-05-10"), LocalDate.parse("2017-05-15"), LocalDate.now());
		
		List<Reservation> resList = resDao.getAllReservations();
		
		assertNotNull(resList);
		assertEquals(1, resList.size());
		assertEquals(newRes.get(0).getReservationId(), resList.get(0).getReservationId());
		assertEquals(newRes2.get(0).getReservationId(), resList.get(1).getReservationId());
	}

	@Test
	public void testAddReservation() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetReservationBySiteNumber() {
		List<Reservation> newRes = resDao.addReservation(1L, 1L, "Bobdob", LocalDate.parse("2017-10-10"), LocalDate.parse("2017-10-15"), LocalDate.now());
		
		List<Reservation> resList = resDao.getAllReservations();
		
		assertNotNull(resList);
		assertEquals(1, resList.size());
		assertEquals(newRes.get(0).getSiteId(), resList.get(0).getSiteId());
	}

	@Test
	public void testGetReservationByResId() {
		List<Reservation> newRes = resDao.addReservation(1L, 1L, "Bobdob", LocalDate.parse("2017-10-10"), LocalDate.parse("2017-10-15"), LocalDate.now());
		
		List<Reservation> resList = resDao.getAllReservations();
		
		assertNotNull(resList);
		assertEquals(1, resList.size());
		assertEquals(newRes.get(0).getReservationId(), resList.get(0).getReservationId());
	}

	@Test
	public void testGetReservationByResName() {
		List<Reservation> newRes = resDao.addReservation(1L, 1L, "Bobdob", LocalDate.parse("2017-10-10"), LocalDate.parse("2017-10-15"), LocalDate.now());
		
		List<Reservation> resList = resDao.getAllReservations();
		
		assertNotNull(resList);
		assertEquals(1, resList.size());
		assertEquals(newRes.get(0).getName(), resList.get(0).getName());
	}

}
