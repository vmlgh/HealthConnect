package com.healthconnect.platform.entity.patient;

import com.healthconnect.platform.entity.core.UserDocument;
import com.healthconnect.platform.enums.PatientMedicalRecordFileType;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Table(name = "PatientDocument")
public class PatientDocument extends UserDocument {

    @Enumerated(EnumType.STRING)
    @Column(name = "RecordFileType")
    private PatientMedicalRecordFileType fileType;

    @ManyToOne
    @JoinColumn(name ="PatientMedicalRecordId", referencedColumnName = "RecordId")
    private PatientMedicalRecord patientMedicalRecord;

    @Transient
    private MultipartFile file;

    public PatientMedicalRecordFileType getFileType() {
        return fileType;
    }

    public void setFileType(PatientMedicalRecordFileType fileType) {
        this.fileType = fileType;
    }

    public PatientMedicalRecord getPatientMedicalRecord() {
        return patientMedicalRecord;
    }

    public void setPatientMedicalRecord(PatientMedicalRecord patientMedicalRecord) {
        this.patientMedicalRecord = patientMedicalRecord;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}

