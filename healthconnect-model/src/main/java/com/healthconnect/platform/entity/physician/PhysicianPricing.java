package com.healthconnect.platform.entity.physician;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.healthconnect.platform.entity.common.BasePricing;
import com.healthconnect.platform.entity.common.SpecialityMaster;

@Entity
@Table(name ="PhysicianPricing" ,uniqueConstraints= @UniqueConstraint(columnNames={"PhysicianId", "SpecialtyId"}))
public class PhysicianPricing  extends BasePricing {
	
	@ManyToOne
	@JoinColumn(name ="PhysicianId")
	private Physician physician;
	
	@ManyToOne
	@JoinColumn(name ="SpecialtyId")
	private SpecialityMaster speciality;
	
	//Validity in Weeks
	 @Column(name ="Validity")
	 private String validity;

	public Physician getPhysician() {
		return physician;
	}

	public void setPhysician(Physician physician) {
		this.physician = physician;
	}

	public SpecialityMaster getSpeciality() {
		return speciality;
	}

	public void setSpeciality(SpecialityMaster speciality) {
		this.speciality = speciality;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}
	
	

}

