package com.healthconnect.platform.entity.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.healthconnect.platform.entity.core.BaseEntity;

@Entity
@Table(name ="CollegeMaster")
public class MedicalCollegeMaster extends BaseEntity {
	
	@Column(name ="Name", unique = true)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

