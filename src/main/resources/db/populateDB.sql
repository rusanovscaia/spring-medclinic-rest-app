INSERT INTO doctors VALUES (1, 'James', 'Carter') ON CONFLICT DO NOTHING;
INSERT INTO doctors VALUES (2, 'Helen', 'Leary') ON CONFLICT DO NOTHING;
INSERT INTO doctors VALUES (3, 'Linda', 'Douglas') ON CONFLICT DO NOTHING;
INSERT INTO doctors VALUES (4, 'Rafael', 'Ortega') ON CONFLICT DO NOTHING;
INSERT INTO doctors VALUES (5, 'Henry', 'Stevens') ON CONFLICT DO NOTHING;
INSERT INTO doctors VALUES (6, 'Arissa', 'Hernandez') ON CONFLICT DO NOTHING;
INSERT INTO doctors VALUES (7, 'Rhiannon', 'Stein') ON CONFLICT DO NOTHING;
INSERT INTO doctors VALUES (8, 'Lucy', 'Farmer') ON CONFLICT DO NOTHING;

INSERT INTO specialties VALUES (1, 'radiology') ON CONFLICT DO NOTHING;
INSERT INTO specialties VALUES (2, 'surgery') ON CONFLICT DO NOTHING;
INSERT INTO specialties VALUES (3, 'mamology') ON CONFLICT DO NOTHING;
INSERT INTO specialties VALUES (4, 'dermatology') ON CONFLICT DO NOTHING;
INSERT INTO specialties VALUES (5, 'oncology') ON CONFLICT DO NOTHING;
INSERT INTO specialties VALUES (6, 'rheumatology') ON CONFLICT DO NOTHING;
INSERT INTO specialties VALUES (7, 'gastroenterology') ON CONFLICT DO NOTHING;
INSERT INTO specialties VALUES (8, 'anesthesiology') ON CONFLICT DO NOTHING;

INSERT INTO doc_specialties VALUES (2, 1) ON CONFLICT DO NOTHING;
INSERT INTO doc_specialties VALUES (3, 2) ON CONFLICT DO NOTHING;
INSERT INTO doc_specialties VALUES (4, 7) ON CONFLICT DO NOTHING;
INSERT INTO doc_specialties VALUES (5, 5) ON CONFLICT DO NOTHING;
INSERT INTO doc_specialties VALUES (6, 8) ON CONFLICT DO NOTHING;
INSERT INTO doc_specialties VALUES (1, 3) ON CONFLICT DO NOTHING;
INSERT INTO doc_specialties VALUES (7, 4) ON CONFLICT DO NOTHING;
INSERT INTO doc_specialties VALUES (8, 6) ON CONFLICT DO NOTHING;


INSERT INTO patients  VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023', '1990-09-07') ON CONFLICT DO NOTHING;
INSERT INTO patients  VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749', '1988-01-12') ON CONFLICT DO NOTHING;
INSERT INTO patients  VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763', '1988-02-12') ON CONFLICT DO NOTHING;
INSERT INTO patients  VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198', '1999-5-05') ON CONFLICT DO NOTHING;
INSERT INTO patients  VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765', '1988-05-11') ON CONFLICT DO NOTHING;
INSERT INTO patients  VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654', '1998-06-12') ON CONFLICT DO NOTHING;
INSERT INTO patients  VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387', '1988-01-10') ON CONFLICT DO NOTHING;
INSERT INTO patients  VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683', '2010-01-12') ON CONFLICT DO NOTHING;
INSERT INTO patients  VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435', '1997-02-02') ON CONFLICT DO NOTHING;
INSERT INTO patients  VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487', '1966-02-11') ON CONFLICT DO NOTHING;




INSERT INTO visits VALUES (1, '2020-03-04', 1, 1, 'cancer') ON CONFLICT DO NOTHING;
INSERT INTO visits VALUES (2, '2020-03-04', 10, 2, 'cold') ON CONFLICT DO NOTHING;
INSERT INTO visits VALUES (3, '2018-06-04', 6, 8, 'cough') ON CONFLICT DO NOTHING;
INSERT INTO visits VALUES (4, '2019-09-04', 3, 6, 'leg pain') ON CONFLICT DO NOTHING;
INSERT INTO visits VALUES (5, '2020-09-04', 5, 8, 'heart murmur') ON CONFLICT DO NOTHING;
INSERT INTO visits VALUES (6, '2020-09-04', 1, 3, 'cancer') ON CONFLICT DO NOTHING;
INSERT INTO visits VALUES (7, '2018-09-04', 6, 8, 'scabies mite') ON CONFLICT DO NOTHING;

INSERT INTO users(username,password,enabled) VALUES ('admin','{noop}admin', true) ON CONFLICT DO NOTHING;

INSERT INTO roles (username, role) VALUES ('admin', 'ROLE_PATIENT_ADMIN') ON CONFLICT DO NOTHING;
INSERT INTO roles (username, role) VALUES ('admin', 'ROLE_DOC_ADMIN') ON CONFLICT DO NOTHING;
INSERT INTO roles (username, role) VALUES ('admin', 'ROLE_ADMIN') ON CONFLICT DO NOTHING;
