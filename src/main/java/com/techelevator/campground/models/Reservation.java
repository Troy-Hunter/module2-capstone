package com.techelevator.campground.models;

import java.time.LocalDate;

public class Reservation {
	private Long reservationId;
	private Long siteId;
	private String reservationName;
	private LocalDate resStartDate;
	private LocalDate resEndDate;
	private LocalDate createDate;
	public Long getReservationId() {
		return reservationId;
	}
	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}
	public Long getSiteId() {
		return siteId;
	}
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	public String getName() {
		return reservationName;
	}
	public void setName(String reservationName) {
		this.reservationName = reservationName;
	}
	public LocalDate getResStartDate() {
		return resStartDate;
	}
	public void setResStartDate(LocalDate resStartDate) {
		this.resStartDate = resStartDate;
	}
	public LocalDate getResEndDate() {
		return resEndDate;
	}
	public void setResEndDate(LocalDate resEndDate) {
		this.resEndDate = resEndDate;
	}
	public LocalDate getCreateDate() {
		return createDate;
	}
	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}
	@Override
	public String toString() {
		return reservationName + ".";
	}	
}
