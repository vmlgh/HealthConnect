package com.healthconnect.platform.dto.common;

import com.healthconnect.platform.enums.ServiceCategoryType;

public class ServiceSearchDto extends CommonDto {
	
	private ServiceCategoryType category;
	
	public ServiceSearchDto(String key,String value,ServiceCategoryType category) {
		super();
		setKey(key);
		setValue(value);
		setCategory(category);
	}

	public ServiceCategoryType getCategory() {
		return category;
	}

	public void setCategory(ServiceCategoryType category) {
		this.category = category;
	}

}
