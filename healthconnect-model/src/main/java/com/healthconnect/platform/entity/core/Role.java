package com.healthconnect.platform.entity.core;

import com.healthconnect.platform.enums.RoleType;

import javax.persistence.*;


/**
 * The Class Role.
 */
@Entity
@Table(name ="Roles")
public class Role extends BaseEntity {

    @Enumerated(EnumType.STRING)
	@Column(name ="Name", unique = true)
	private RoleType name;
	
	@Column(name ="Description")
	private String description;

    public RoleType getName() {
        return name;
    }

    public void setName(RoleType name) {
        this.name = name;
    }

    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

