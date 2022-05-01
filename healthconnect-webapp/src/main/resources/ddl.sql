INSERT INTO Users (Title,ShortTitleDescription,CreatedOn,Email,EmailVerified,FirstName,Gender,LastName,MobileNumber,Password,UserId,Username,ProfileImageUrl) VALUES
('Dr.','MBBS, MD(Medicine)',NOW(),'akswain284@gmail.com', true, 'Admin', 'MALE','Admin','9738228070','$2a$04$2itbbm3M.hpP3oGZp9SaueeF4AcA/zN5Dt2CmAnTaonNV/NPvj/36','MCT1AD','Admin','https://i.imgur.com/eB46hvd.png');
INSERT INTO Roles (Description, Name, CreatedBy) VALUES ('SuperAdmin', 'SUADMIN', 1);
INSERT INTO Roles (Description, Name, CreatedBy) VALUES ('Admin', 'ADMIN', 1);
INSERT INTO Roles (Description, Name, CreatedBy) VALUES ('Doctor', 'DOCTOR', 1);
INSERT INTO Roles (Description, Name, CreatedBy) VALUES ('User', 'USER', 1);
INSERT INTO Roles (Description, Name, CreatedBy) VALUES ('Patient', 'PATIENT', 1);
INSERT INTO UserUserRoles(UserId, RoleId) VALUES (1, 1);
INSERT INTO UserUserRoles(UserId, RoleId) VALUES (1, 2);

ALTER TABLE healthconnectjob MODIFY Content LONGTEXT;
ALTER TABLE healthconnectjob MODIFY ExtraData LONGTEXT;

INSERT INTO specialitymaster (`CreatedOn`,`DeleteFlag`,`LastModifiedOn`,`Description`,`Name`,`CreatedBy`,`LastModifiedBy`) 
VALUES
  (NULL,0,NULL,'Diagnose diseases such as diabetes, menopause, hypertension, thyroid diseases, cholesterol diseases','Endocrinology',1,NULL),
  (NULL,0,NULL,'Medicine and also diagnose common childhood diseases, such as asthma, allergies, and croup','Pediatrics',1,NULL),
  (NULL,0,NULL,'Medicine dedicated to pain relief for patients before, during, and after surgery','Anesthesiology',1,NULL),
  (NULL,0,NULL,'Diagnosis to mental health and its associated mental and physical ramifications','Psychiatry',1,NULL),
  (NULL,0,NULL,'Diagnosis related to skin, hair, nails, and adjacent mucous membranes','Dermatology',1,NULL);

INSERT INTO subspecialitymaster (`CreatedOn`,`DeleteFlag`,`LastModifiedOn`,`Description`,`Name`,`CreatedBy`,`LastModifiedBy`,`specialityMaster_RecordId`) 
VALUES
  (NULL,0,NULL,'Sub Speciality','Sub Speciality',1,NULL,NULL),
  (NULL,0,NULL,'Sub Speciality','Pediatric dermatology',1,NULL,5),
  (NULL,0,NULL,'Sub Speciality','Procedural dermatology',1,NULL,5),
  (NULL,0,NULL,'Sub Speciality','Hospice and palliative care',1,NULL,3),
  (NULL,0,NULL,'Sub Speciality','Pediatric anesthesiology',1,NULL,3),
  (NULL,0,NULL,'Sub Speciality','Sleep medicine',1,NULL,3),
  (NULL,0,NULL,'Sub Speciality','Pediatric gastroenterology',1,NULL,2),
  (NULL,0,NULL,'Sub Speciality','Pediatric endocrinology',1,NULL,2),
  (NULL,0,NULL,'Sub Speciality','Addiction psychiatry',1,NULL,4),
  (NULL,0,NULL,'Sub Speciality','Administrative psychiatry',1,NULL,4),
  (NULL,0,NULL,'Sub Speciality','Child and adolescent psychiatry',1,NULL,4),
  (NULL,0,NULL,'Sub Speciality','Community psychiatry',1,NULL,4),
  (NULL,0,NULL,'Sub Speciality','Diabetes and metabolism',1,NULL,1),
  (NULL,0,NULL,'Sub Speciality','Endocrinology nuclear medicine',1,NULL,1),
  (NULL,0,NULL,'Sub Speciality','Thyroid disease',1,NULL,1);
  
INSERT INTO MedicalCouncilMaster (Name,CreatedBy) VALUES('BIHAR Medical Council', 1);
INSERT INTO MedicalCouncilMaster (Name,CreatedBy) VALUES('Gujarat Medical Council', 1);

INSERT INTO DegreeMaster (Name,Abbrv,CreatedBy) VALUES('MBBS', 'MBBS', 1);
INSERT INTO DegreeMaster (Name,Abbrv,CreatedBy) VALUES('PG','PG', 1);
