package com.healthconnect.platform.dto.patient;

import com.healthconnect.platform.enums.PatientMedicalRecordFileType;
import org.springframework.web.multipart.MultipartFile;

public class PatientMedicalRecordFileDto {

    private PatientMedicalRecordFileType fileType;
    private MultipartFile file;
    private String publicImageUrl;

    public PatientMedicalRecordFileDto() {

    }

    public PatientMedicalRecordFileDto(PatientMedicalRecordFileType fileType) {
        this.fileType = fileType;
    }

    public PatientMedicalRecordFileDto(PatientMedicalRecordFileType fileType, String publicImageUrl) {
        this.fileType = fileType;
        this.publicImageUrl = publicImageUrl;
    }

    public PatientMedicalRecordFileType getFileType() {
        return fileType;
    }

    public void setFileType(PatientMedicalRecordFileType fileType) {
        this.fileType = fileType;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getPublicImageUrl() {
        return publicImageUrl;
    }

    public void setPublicImageUrl(String publicImageUrl) {
        this.publicImageUrl = publicImageUrl;
    }
}

