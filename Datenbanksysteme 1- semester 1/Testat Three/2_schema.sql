--Kailing Peng
--Alessia Vannini

/*
* Type erzeugen
*/
 
CREATE TYPE gender AS ENUM('male', 'female', 'x');
CREATE TYPE method AS ENUM('cash', 'check', 'bank_transfer');
CREATE TYPE subject AS ENUM('child', 'project');

 /*
 * Tabellen erzeugen
 */

CREATE TABLE team (
  name VARCHAR (30) PRIMARY KEY,
  role VARCHAR (20) NOT NULL,
  location VARCHAR (40) NOT NULL
);

CREATE TABLE project(
  id SERIAL PRIMARY KEY,
  full_name VARCHAR (30) NOT NULL,
  purpose VARCHAR (30),
  duration INTEGER,
  budget FLOAT NOT NULL,
  staff VARCHAR (30)
);

CREATE TABLE sponsor (
  id SERIAL PRIMARY KEY,
  name VARCHAR(30),
  anonymous BOOLEAN NOT NULL,
  contact VARCHAR(100)
);

CREATE TABLE child (
  id SERIAL PRIMARY KEY,
  name VARCHAR(30) NOT NULL,
  age INTEGER NOT NULL,
  gender gender NOT NULL,
  guardian VARCHAR(30) NOT NULL,
  contact VARCHAR(20) NOT NULL
);

CREATE TABLE child_project (
  child_id INTEGER NOT NULL,
  project_id INTEGER NOT NULL,
  PRIMARY KEY (child_id, project_id)
);

CREATE TABLE donation (
  id SERIAL PRIMARY KEY,
  method method NOT NULL,
  amount FLOAT NOT NULL,
  sponsor INTEGER NOT NULL,
  donation_subject subject NOT NULL,
  to_child INTEGER,
  to_project INTEGER
);


