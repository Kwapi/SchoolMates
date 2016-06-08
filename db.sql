DROP TABLE student CASCADE;
DROP TABLE school  CASCADE;
DROP TABLE school_student CASCADE;
DROP TABLE message CASCADE;

CREATE TABLE school(
	id SERIAL,
	school_type VARCHAR (100),
	name VARCHAR(100),
	street VARCHAR (100),
	city VARCHAR (100),
	post_code VARCHAR (100),
	website VARCHAR (100),
		
	PRIMARY KEY (id)
);
CREATE TABLE student
(
	id SERIAL,
	email VARCHAR(100),
	password VARCHAR(20),
	name VARCHAR (100),
	surname VARCHAR (100),
	school_surname VARCHAR (100),
	about_me VARCHAR (500),
	PRIMARY KEY (id)
	
);

CREATE TABLE school_student
(
	school_id INT REFERENCES school(id),
	student_id INT REFERENCES student(id),
	start_year INT,
	end_year INT
);


CREATE TABLE message
(
		id SERIAL,
		sender_id INT REFERENCES student(id),
		receiver_id INT  REFERENCES student(id),
		message VARCHAR (250),
		title VARCHAR (100),
		time_sent VARCHAR(100),  
		message_read INT,
		
		PRIMARY KEY (sender_id, id)
);
---------------------------------------------
----POPOULATING DATABASE FOR TESTING PURPOSES

---SCHOOLS---
INSERT INTO school (school_type, name, street, city, post_code, website)
VALUES ('primary', 'Cool School', 'Cool Street', 'Nice City', 'NR4-7TJ', 'awesomesauce.co.uk');
INSERT INTO school (school_type, name, street, city, post_code, website)
VALUES ('primary', 'Cool School 2', 'Cool Street 2', 'Nice City 2', 'NR4-7TJ', 'awesomesauce2.co.uk');
INSERT INTO school (school_type, name, street, city, post_code, website)
VALUES ('primary', 'Cool School 3', 'Cool Street 3', 'Nice City 3', 'NR4-7TJ', 'awesomesauce3.co.uk');
INSERT INTO school (school_type, name, street, city, post_code, website)
VALUES ('secondary', 'Awesome School', 'Cool Street', 'Nice City', 'NR4-7TJ', 'awesomecoke.co.uk');
INSERT INTO school (school_type, name, street, city, post_code, website)
VALUES ('secondary', 'Awesome School 2', 'Cool Street 2', 'Nice City', 'NR4-7TJ', 'awesomecoke2.co.uk');
INSERT INTO school (school_type, name, street, city, post_code, website)
VALUES ('secondary', 'Awesome School 3', 'Cool Street 3', 'Nice City', 'NR4-7TJ', 'awesomecoke3.co.uk');
INSERT INTO school (school_type, name, street, city, post_code, website)
VALUES ('sixth form', 'Rad School', 'Cool Street', 'Nice City', 'NR4-7TJ', 'radbitches.co.uk');
INSERT INTO school (school_type, name, street, city, post_code, website)
VALUES ('sixth form', 'Rad School 2', 'Cool Street ', 'Nice City', 'NR4-7TJ', 'radbitches2.co.uk');
INSERT INTO school (school_type, name, street, city, post_code, website)
VALUES ('sixth form', 'Rad School 3', 'Cool Street', 'Nice City', 'NR4-7TJ', 'radbitches3.co.uk');


---STUDENTS---

INSERT INTO student(email, password, name, surname, school_surname, about_me)
VALUES ('a', 'a', 'Alfred', 'Alfred', 'Alfred', 'Alfred');
INSERT INTO student(email, password, name, surname, school_surname, about_me)
VALUES ('b', 'b', 'John', 'John', 'John', 'John');
INSERT INTO student(email, password, name, surname, school_surname, about_me)
VALUES ('c', 'c', 'Emily', 'Emily', 'Emily', 'Emily');
INSERT INTO student(email, password, name, surname, school_surname, about_me)
VALUES ('d', 'd', 'Samantha', 'Samantha', 'Samantha', 'Samantha');


INSERT INTO school_student (school_id, student_id, start_year, end_year)
VALUES (1, 1, 2013, 2016);

INSERT INTO school_student (school_id, student_id, start_year, end_year)
VALUES (2, 1, 2016, 2018);

INSERT INTO school_student (school_id, student_id, start_year, end_year)
VALUES (1, 2, 2012, 2014);

INSERT INTO school_student (school_id, student_id, start_year, end_year)
VALUES (1, 3, 2010, 2012);

INSERT INTO school_student (school_id, student_id, start_year, end_year)
VALUES (2, 4, 2016, 2018);





-----gives attendance based on id
SELECT  DISTINCT school_id, start_year, end_year  FROM school_student AS my_attendance WHERE student_id = 1; 

----finds other people with the same attendace (including the main person)
SELECT DISTINCT student_ID FROM school_student, my_attendance 
WHERE (school_student.school_ID = (SELECT school_id from my_attendance WHERE school_id =1)
	AND school_student.start_year <=2016 
	AND school_student.end_year>=2013 ) 
OR (school_student.school_ID = 2 
	AND school_student.start_year<=2018 
	AND school_student.end_year >= 2016);
	

	
	SELECT student.id, student.name, student.surname, student.school_surname FROM student, school_student
	WHERE student.id = school_student.student_id AND school_student.school_id = 2;



