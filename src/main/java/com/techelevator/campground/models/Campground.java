package com.techelevator.campground.models;

import java.math.BigDecimal;

public class Campground {
	private Long campgroundId;
	private Long parkId;
	private String campgroundName;
	private String openingMonth;
	private String closingMonth;
	private BigDecimal dailyFee;
	public Long getCampgroundId() {
		return campgroundId;
	}
	public void setCampgroundId(Long campgroundId) {
		this.campgroundId = campgroundId;
	}
	public Long getParkId() {
		return parkId;
	}
	public void setParkId(Long parkId) {
		this.parkId = parkId;
	}
	public String getName() {
		return campgroundName;
	}
	public void setName(String campgroundName) {
		this.campgroundName = campgroundName;
	}
	public String getOpeningMonth() {
		return openingMonth;
	}
	public void setOpeningMonth(String openingMonth) {
		this.openingMonth = openingMonth;
	}
	public String getClosingMonth() {
		return closingMonth;
	}
	public void setClosingMonth(String closingMonth) {
		this.closingMonth = closingMonth;
	}
	public BigDecimal getDailyFee() {
		return dailyFee;
	}
	public void setDailyFee(BigDecimal dailyFee) {
		this.dailyFee = dailyFee;
	}
	@Override
	public String toString() {
		return campgroundName + " Campgrounds";
	}	
}
