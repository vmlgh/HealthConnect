package com.healthconnect.platform.entity.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.healthconnect.platform.entity.core.BaseEntity;

@Entity
@Table(name ="DegreeMaster")
public class DegreeMaster extends BaseEntity {
	
	@Column(name ="Name", unique = true)
	private String name;

	@Column(name = "Abbrv")
	private String abbrv;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbbrv() {
		return abbrv;
	}

	public void setAbbrv(String abbrv) {
		this.abbrv = abbrv;
	}
}
