CREATE DATABASE clientdb;
	CREATE TABLE tblclient(id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
						firstName VARCHAR(40),
						lastname VARCHAR(40),
						message VARCHAR(40));

	CREATE TABLE tbluser(id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
						ustype VARCHAR(2),
						lastname VARCHAR(40),
						contactemail VARCHAR(40),
						contactpostal VARCHAR(40));

	CREATE TABLE tblorder(id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
						client_id INT,
						user_id INT,
						message VARCHAR(40),
						delivery VARCHAR(2));

	ALTER TABLE tblorder ADD CONSTRAINT FK_order_client FOREIGN KEY client_id REFERENCES tblclient(id);

	ALTER TABLE tblorder ADD CONSTRAINT FK_order_user FOREIGN KEY user_id REFERENCES tbluser(id);

	
