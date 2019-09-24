-- Create Table Space
CREATE TABLESPACE RUCHI_OWNER_PERM_01
DATAFILE 'RUCHI_OWNER_PERM_01.dat' 
SIZE 50M
ONLINE;

-- Create User 
CREATE USER RUCHI_OWNER
IDENTIFIED BY RUCHI_OWNER
DEFAULT TABLESPACE RUCHI_OWNER_PERM_01
QUOTA 50M on RUCHI_OWNER_PERM_01;

-- All Grants
GRANT create session TO RUCHI_OWNER;
GRANT create table TO RUCHI_OWNER;
GRANT create view TO RUCHI_OWNER;
GRANT create any trigger TO RUCHI_OWNER;
GRANT create any procedure TO RUCHI_OWNER;
GRANT create sequence TO RUCHI_OWNER;
GRANT create synonym TO RUCHI_OWNER;

-- Create table
DROP TABLE RUCHI_OWNER.TEMP_TABLE1;
CREATE TABLE RUCHI_OWNER.TEMP_TABLE1 (
	TEMP_ID CHAR(6) NOT NULL, 
	SEGMENT_NUMBER INT NOT NULL,
	TEMP_DATE DATE NOT NULL, 
	TEMP_IS_TAKEN INT,
	TEMP_NAME VARCHAR2(30), 
	TEMP_ADDRESS VARCHAR2(100), 
	CONSTRAINT TEMP_TABLE1_PK
	PRIMARY KEY (TEMP_ID, TEMP_NAME));

DROP TABLE RUCHI_OWNER.TEMP_TABLE2;
CREATE TABLE RUCHI_OWNER.TEMP_TABLE2 (
	TEMP_ID CHAR(6) NOT NULL, 
	SEGMENT_NUMBER INT NOT NULL,
	TEMP_DATE DATE NOT NULL, 
	TEMP_NAME VARCHAR2(30),
	CONSTRAINT TEMP_TABLE2_PK
	PRIMARY KEY (TEMP_ID, TEMP_NAME));
