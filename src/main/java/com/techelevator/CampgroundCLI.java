package com.techelevator;
import com.techelevator.campground.model.jdbc.*;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.models.Campground;
import com.techelevator.campground.models.CampgroundDAO;
import com.techelevator.campground.models.Park;
import com.techelevator.campground.models.ParkDAO;
import com.techelevator.campground.models.Reservation;
import com.techelevator.campground.models.ReservationDAO;
import com.techelevator.campground.models.Site;
import com.techelevator.campground.models.SiteDAO;
import com.techelevator.campground.view.Menu;

public class CampgroundCLI {
	
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = new String[] { MAIN_MENU_OPTION_EXIT };
	
	
	private static final String PARK_MENU_OPTION_SHOW_CAMPGROUNDS = "Display Campgrounds";
	private static final String PARK_MENU_OPTION_SEARCH_FOR_RESERVATION = "Search for Reservation";
	private static final String PARK_MENU_OPTION_RETURN_TO_MAIN = "Return to Previous Screen";
	private static final String[] PARK_MENU_OPTIONS = new String[] { PARK_MENU_OPTION_SHOW_CAMPGROUNDS,
																		   PARK_MENU_OPTION_SEARCH_FOR_RESERVATION,
																		   PARK_MENU_OPTION_RETURN_TO_MAIN};
	
	private static final String CG_MENU_OPTION_SEARCH_FOR_RESERVATION = "Search for Available Reservations";
	private static final String CG_MENU_OPTION_RETURN = "Return To Previous Screen";
	private static final String[]	CG_MENU_OPTIONS = new String[] { CG_MENU_OPTION_SEARCH_FOR_RESERVATION,
																	 CG_MENU_OPTION_RETURN};
	
	
	private ParkDAO parkDao;
	private CampgroundDAO campDao;
	private SiteDAO siteDao;
	private ReservationDAO resDao;
	private Menu menu;

	public static void main(String[] args) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		
		CampgroundCLI application = new CampgroundCLI(dataSource);
		application.run();
	}

	public CampgroundCLI(DataSource datasource) {
		parkDao = new JDBCParkDAO(datasource);
		campDao = new JDBCCampgroundDAO(datasource);
		siteDao = new JDBCSiteDAO(datasource);
		resDao = new JDBCReservationDAO(datasource);
		menu = new Menu(System.in, System.out);
	}
	
	public void run() {	
		while(true) {
			printHeading("Main Menu");
			List<Park> parkList = parkDao.getAllParks();
			Park choice = (Park)menu.getChoiceFromOptions(parkList.toArray());
			
			handlePark(choice);

		}
	}
	
	private void handlePark(Park selectedPark) {
		while(true) {
			printHeading("Welcome to " + selectedPark + "!");
			List<Campground> campList = campDao.getCampgroundByPark(selectedPark.getParkId());
			Campground choice = (Campground)menu.getChoiceFromOptions(campList.toArray());
			
			handleCamp(choice);
		}
	}
	
	private void handleCamp(Campground selectedCampground) {
		while(true) {	
			printHeading("Welcome to " + selectedCampground + "!");
			List<Site> siteList = siteDao.getSiteByCampground(selectedCampground.getCampgroundId());
			Site choice = (Site)menu.getChoiceFromOptions(siteList.toArray());
			
			handleSite(choice);
		}
	}
	
	private void handleSite (Site selectedSite) {
		while(true) {
			printHeading("Your Camp Site Number is " + selectedSite);
			List<Reservation> reservationList = resDao.get);
			Reservation choice = (Reservation)menu.getChoiceFromOptions(reservationList.toArray());
			
			handleReservation(choice);
		}
	}
		/*
		
		private void handleListAcadiaCg() {
			printHeading("Acadia National Park Campgrounds");
			List<Campground> acadiaCampgrounds = campgroundDAO.getAllCampgrounds();
			listCampgrounds(acadiaCampgrounds);
		}
		
		private void handleListArchesCg() {
			printHeading("Acadia National Park Campgrounds");
			List<Campground> archesCampgrounds = campgroundDAO.getAllCampgrounds();
			listCampgrounds(archesCampgrounds);
		}
		
		private void handleListCuyahogaCg() {
			printHeading("Acadia National Park Campgrounds");
			List<Campground> cuyahogaCampgrounds = campgroundDAO.getAllCampgrounds();
			listCampgrounds(cuyahogaCampgrounds);
		}
		*/
		private void printHeading(String headingText) {
			System.out.println("\n"+headingText);
			for(int i = 0; i < headingText.length(); i++) {
				System.out.print("-");
			}
			System.out.println();
		}
		
}
