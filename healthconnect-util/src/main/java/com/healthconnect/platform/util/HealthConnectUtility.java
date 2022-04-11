package com.healthconnect.platform.util;

public class HealthConnectUtility {

	public static final String HEALTHCONNECT_ABBRV = "HCT";
	
	public static final String FIRST_NAME = "firstName";
  	public static final String LAST_NAME = "lastName";
	
	public static String generateUserId(long id, String serviceTypeChar){
        StringBuilder userId = new StringBuilder(HEALTHCONNECT_ABBRV);
        return userId.append(id).append(serviceTypeChar).toString();
    }

	
	  public static long extractRecordIdFromUserId(String userId) { return
	  Long.parseLong(userId.replaceAll("[^0-9]", "")); }
	  
	/*
	 * public static Map<String,String> parseFirstNameAndLastName(String fullName){
	 * Map<String,String> names = new HashMap<>();
	 * if(!StringUtils.isEmpty(fullName)) {
	 * org.apache.commons.lang3.StringUtils.normalizeSpace(fullName); String []
	 * nameFragments= fullName.split(" "); names.put(FIRST_NAME, nameFragments[0]);
	 * if(nameFragments.length > 1)
	 * names.put(LAST_NAME,nameFragments[nameFragments.length-1]); } return names; }
	 * 
	 * public static String generateFileName(User user, FileType fileType,
	 * MultipartFile file) { StringBuilder fileName = new StringBuilder(); String
	 * extension = file.getOriginalFilename().split("\\.")[1];
	 * fileName.append(user.getUserId()+MedicoCyteConstants.FILE_NAME_SEPARATOR+
	 * fileType.name()+"."+extension); return fileName.toString(); }
	 */
	 
}

