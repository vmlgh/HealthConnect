package com.healthconnect.platform.entity.hospital;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import com.healthconnect.platform.entity.common.Address;
import com.healthconnect.platform.entity.core.BaseEntity;
import com.healthconnect.platform.entity.core.User;
import com.healthconnect.platform.entity.physician.Physician;
import com.healthconnect.platform.enums.HospitalType;

@Entity
@Table(name="Hospital")
public class Hospital extends BaseEntity {
	
	@Column(name="Name")
	private String name;
	
	@Column(name ="DialingCode")
	private String dialingCode;
	
	@Column(name="PhoneNumber")
	private String phoneNumber;
	
	@Pattern(regexp ="[A-Za-z0-9-_.]+@[A-Za-z0-9-_]+(?:\\.[A-Za-z0-9]+)+")
	@Column(name="Email")
	private String email;
	
	@Column(name ="Website")
	private String website;

    @Enumerated(EnumType.STRING)
    @Column(name = "Type")
	private HospitalType hospitalType;
    
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "hospitals")
	private List<Physician> physicians;
	
    @Column(name ="AddressName")
	private String addressName;
    
	@OneToOne
	@JoinColumn(name ="AddressId")
	private Address address;

	
	@OneToOne
	@JoinColumn(name ="UserId", referencedColumnName = "userId")
	private User user;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
    public List<Physician> getPhysicians() {
        return physicians;
    }

    public void setPhysicians(List<Physician> physicians) {
        this.physicians = physicians;
    }

    public String getDialingCode() {
		return dialingCode;
	}

	public void setDialingCode(String dialingCode) {
		this.dialingCode = dialingCode;
	}

    public HospitalType getHospitalType() {
        return hospitalType;
    }
    
    public void setHospitalType(HospitalType hospitalType) {
        this.hospitalType = hospitalType;
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
}

