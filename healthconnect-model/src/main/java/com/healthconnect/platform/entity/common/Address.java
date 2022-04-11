package com.healthconnect.platform.entity.common;

import javax.persistence.*;

import com.healthconnect.platform.dto.common.AddressDto;
import com.healthconnect.platform.entity.core.BaseEntity;
//import com.healthconnect.platform.entity.hospital.Hospital;

import java.util.List;

@Entity
@Table(name ="Address")
public class Address extends BaseEntity {

	@Column(name="Country")
	private String country;
	
	@Column(name ="State")
	private String state;
	
	@Column(name ="District")
	private String district;
	
	@Column(name="City")
	private String city;
	
	@OneToOne
	@JoinColumn(name ="CityId")
	private CityMaster cityMaster;
	
	@Column(name ="FullAddress")
	private String fullAddress;
	
	@Column(name ="Pin")
	private String  pinCode;
	
	@Column(name ="Lat")
	private Double lat;
	
	@Column(name ="Lng")
	private Double lng;
	
	@Column(name ="Locality")
	private String locality;

	/*
	 * @OneToOne private Hospital hospital;
	 */
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public CityMaster getCityMaster() {
		return cityMaster;
	}

	public void setCityMaster(CityMaster cityMaster) {
		this.cityMaster = cityMaster;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	/*
	 * public Hospital getHospital() { return hospital; }
	 * 
	 * public void setHospital(Hospital hospital) { this.hospital = hospital; }
	 */

	public String getFullAddress() {
		return fullAddress;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}

	public AddressDto toAddressDto() {
		AddressDto addressDto  = new AddressDto(this.locality,this.city,this.pinCode);
		addressDto.setCountry(this.country);
		addressDto.setFullAddress(this.fullAddress);
		addressDto.setLng(this.lng);
		addressDto.setLat(this.lat);
		return addressDto;
	}
}

