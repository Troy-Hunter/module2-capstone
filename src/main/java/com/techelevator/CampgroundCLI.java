package com.techelevator;
import com.techelevator.campground.model.jdbc.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
			printHeading("Main Menu - Select a Park for Further Details");
			List<Park> parkList = parkDao.getAllParks();
			Park choice = (Park)menu.getChoiceFromOptions(parkList.toArray());
			
			handlePark(choice);

		}
	}
	
	private void handlePark(Park selectedPark) {
		while(true) {
			printHeading("Welcome to " + selectedPark + "!" + "\nLocation: " + selectedPark.getLocation()
					+ "\nEstablished: " + selectedPark.getEstablishDate()
					+ "\nArea: " + selectedPark.getArea()
					+ "\nAnnual Visitors: " + selectedPark.getVisitors()
					+ "\n" + selectedPark.getDescription());
			printHeading("Select a Command");
			String choice = (String)menu.getChoiceFromOptions(PARK_MENU_OPTIONS);
			if(choice.equals(PARK_MENU_OPTION_SHOW_CAMPGROUNDS)) {
				List<Campground> campList = campDao.getCampgroundByPark(selectedPark.getParkId());
					for(int i = 0; i < campList.size(); i++) {
						System.out.println("#" + campList.get(i).getCampgroundId() + " " + campList.get(i).getName() + " Opening Month: " + campList.get(i).getOpeningMonth() + " Closing Month: " + campList.get(i).getClosingMonth() + " Daily Fee: $" + campList.get(i).getDailyFee());
					}
					printHeading("Please select an option.");
					String choice2 = (String)menu.getChoiceFromOptions(CG_MENU_OPTIONS);
					if(choice2.equals(CG_MENU_OPTION_SEARCH_FOR_RESERVATION )) {
						for(int i = 0; i < campList.size(); i++) {
							System.out.println("#" + campList.get(i).getCampgroundId() + " " + campList.get(i).getName() + " Opening Month: " + campList.get(i).getOpeningMonth() + " Closing Month: " + campList.get(i).getClosingMonth() + " Daily Fee: $" + campList.get(i).getDailyFee());
						}
						System.out.println("\nWhich campground (enter 0 to exit)? ");
						Scanner input = new Scanner(System.in);
						String newLine = input.nextLine();
						int campIndex = Integer.parseInt(newLine);
						
						if(!(campIndex == 0 || campIndex > campList.size())) {
							Campground selectedCampground = campList.get(campIndex - 1);
							System.out.println("\nWhat is the arrival date (yyyy-mm-dd)?");
						
							newLine = input.nextLine();
							LocalDate arrivalDate = LocalDate.parse(newLine);
							
							System.out.println("\nWhat is the departure date (yyyy-mm-dd)?");
							String newLine2 = input.nextLine();
							LocalDate departureDate = LocalDate.parse(newLine2);
							List<Site> availableSiteList = siteDao.getSiteByAvailability(campList.get(campIndex - 1).getCampgroundId(), arrivalDate, departureDate);
								if(availableSiteList.size() == 0) {
									System.out.println("Your selected site is unavailable during this time. Sorry.");
								} else {
									System.out.println("Here are the available sites: ");
									int count = 0;
									for(Site sites : availableSiteList) {
										count ++;
										System.out.println("#"+ count + " Campsite " + sites);
									}	
								}
								
							System.out.println("\nWhich site would you like to reserve (enter a number 1-5)? ");
							newLine = input.nextLine();
							int siteIndex = Integer.parseInt(newLine);
							if(!(siteIndex == 0 || siteIndex > availableSiteList.size())) {
								Site selectedSite = availableSiteList.get(siteIndex - 1);
								System.out.println("\nWhat name should the reservation be made under? ");
								newLine2 = input.nextLine();
								String resName = newLine2;
								Reservation resMade = new Reservation();
								resDao.addReservation(resMade.getReservationId(), selectedSite.getSiteId(), resName, arrivalDate, departureDate, LocalDate.now());
								
								System.out.println("The reservation has been made and the confirmation id is " + resMade.getReservationId());
							}
					
			} else if(choice.equals(PARK_MENU_OPTION_SEARCH_FOR_RESERVATION)) {
				for(int i = 0; i < campList.size(); i++) {
					System.out.println("#" + campList.get(i).getCampgroundId() + " " + campList.get(i).getName() + " Opening Month: " + campList.get(i).getOpeningMonth() + " Closing Month: " + campList.get(i).getClosingMonth() + " Daily Fee: $" + campList.get(i).getDailyFee());
				}			
			}
		
					}}}
		}
	//		handleCamp(choice);


/*
	private void handleCamp(String something) {
		while(true) {	
			printHeading("Select a Command");
			Site choice = (Site)menu.getChoiceFromOptions(PARK_MENU_OPTIONS);
			if(choice.equals(PARK_MENU_OPTION_SHOW_CAMPGROUNDS)) {
				List<Site> siteList = siteDao.getSiteByCampground(selectedCampground.getCampgroundId());
			}
			
			handleSite(choice);
		}
	}
	
	private void handleSite (Site selectedSite) {
		while(true) {
			printHeading("Your Camp Site Number is " + selectedSite);
			List<Reservation> reservationList = resDao.getReservationBySiteNumber(selectedSite.getSiteNumber());
			Reservation choice = (Reservation)menu.getChoiceFromOptions(reservationList.toArray());
		}
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
