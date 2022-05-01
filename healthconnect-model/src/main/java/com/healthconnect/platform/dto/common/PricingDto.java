package com.healthconnect.platform.dto.common;

import com.healthconnect.platform.enums.PricingUnit;

public class PricingDto {

	private PricingUnit unit;
	
	private double price;
	
	public PricingDto(){
		
	}
	public PricingDto(PricingUnit unit, double price) {
		this.unit = unit;
		this.price = price;
	}

	public PricingUnit getUnit() {
		return unit;
	}

	public void setUnit(PricingUnit unit) {
		this.unit = unit;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
}
