CREATE TABLE IF NOT EXISTS doctors (
  id SERIAL,
  first_name VARCHAR(30),
  last_name VARCHAR(30),
  CONSTRAINT pk_doctors PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS idx_doctors_last_name ON doctors (last_name);

ALTER SEQUENCE doctors_id_seq RESTART WITH 100;


CREATE TABLE IF NOT EXISTS specialties (
  id SERIAL,
  name VARCHAR(80),
  CONSTRAINT pk_specialties PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS idx_specialties_name ON specialties (name);

ALTER SEQUENCE specialties_id_seq RESTART WITH 100;


CREATE TABLE IF NOT EXISTS doc_specialties (
  doc_id INT NOT NULL,
  specialty_id INT NOT NULL,
  FOREIGN KEY (doc_id) REFERENCES doctors(id),
  FOREIGN KEY (specialty_id) REFERENCES specialties(id),
  CONSTRAINT unique_ids UNIQUE (doc_id,specialty_id)
);



CREATE TABLE IF NOT EXISTS patients (
  id SERIAL,
  first_name VARCHAR(30),
  last_name VARCHAR(30),
  address VARCHAR(255),
  city VARCHAR(80),
  telephone VARCHAR(20),
  birth_date DATE,
  CONSTRAINT pk_patients PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS idx_patients_last_name ON patients (last_name);

ALTER SEQUENCE patients_id_seq RESTART WITH 100;


CREATE TABLE IF NOT EXISTS visits (
  id SERIAL,
  visit_date DATE,
  patient_id INT NOT NULL,
  doc_id INT NOT NULL,
  description VARCHAR(255),
  FOREIGN KEY (patient_id) REFERENCES patients(id),
  FOREIGN KEY (doc_id) REFERENCES doctors(id),
  CONSTRAINT pk_visits PRIMARY KEY (id)
);

ALTER SEQUENCE visits_id_seq RESTART WITH 100;

CREATE TABLE IF NOT EXISTS users (
  username VARCHAR(20) NOT NULL ,
  password VARCHAR(20) NOT NULL ,
  enabled boolean NOT NULL DEFAULT true ,
  CONSTRAINT pk_users PRIMARY KEY (username)
);

CREATE TABLE IF NOT EXISTS roles (
  id SERIAL,
  username varchar(20) NOT NULL,
  role varchar(20) NOT NULL,
  CONSTRAINT pk_roles PRIMARY KEY (id),
  FOREIGN KEY (username) REFERENCES users (username)
);

ALTER TABLE roles ADD CONSTRAINT uni_username_role UNIQUE (role,username);
ALTER SEQUENCE roles_id_seq RESTART WITH 100;
