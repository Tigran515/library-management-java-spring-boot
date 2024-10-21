CREATE TABLE author(
id INT NOT NULL AUTO_INCREMENT,
name VARCHAR(180) NOT NULL,
lname VARCHAR (180), -- update in the ER diagram  --
sname VARCHAR (180),  -- update in the ER diagram  --
born SMALLINT ,  -- update in the ER diagram  --

PRIMARY KEY (id)
);
CREATE TABLE book(
id INT NOT NULL AUTO_INCREMENT,
title VARCHAR(180) NOT NULL,
published INT NOT NULL,
ISBN CHAR(17) NOT NULL,
PRIMARY KEY (id)
);
CREATE TABLE book_author(
book_id INT NOT NULL,
author_id INT NOT NULL,
Constraint PK_Book_Author PRIMARY KEY (book_id,author_id),
FOREIGN KEY (book_id) REFERENCES book(id),
FOREIGN KEY (author_id) REFERENCES author(id)
);
CREATE TABLE user (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
username VARCHAR(320) NOT NULL,
password VARCHAR(255) NOT NULL,
name VARCHAR(180) NOT NULL,
lname VARCHAR(180) NOT NULL,
sname VARCHAR(180) NOT NULL,
role ENUM('admin', 'user') DEFAULT 'user' ,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
last_connection TIMESTAMP,
active BOOLEAN DEFAULT true
)
