--Kailing Peng
--Alessia Vannini

/*
 * Testdaten einfüllen
 */

 -- TABLE  team
INSERT INTO team (name, role, location) VALUES
('Marxer, Markus', 'Projektleiter', 'Zürich'),
('Widmer, Anna', 'Admin', 'Bern'),
('Steiner, Hans', 'Admin', 'Lausanne'),
('Affolter, Vreni', 'Projektleiter', 'Genf'),
('Widmer, Karl', 'Projektleiter', 'Zürich'),
('Meier, Franz', 'Admin', 'Bern'),
('Hassler, Hans', 'Projektleiter', 'Lausanne');
 
-- TABLE  project
INSERT INTO project (full_name, purpose, duration, budget, staff) VALUES
('health', 'health care for children', '3', '11111', 'Marxer, Markus'), --1
('food', 'food care for children', '4', '22222', 'Affolter, Vreni'), --2
('water', 'water care for children', '5', '33333', 'Hassler, Hans'), --3
('mental care', 'mental care for children', '6', '44444', 'Meier, Franz'), --4
('education', 'education care for children', '7', '55555', 'Affolter, Vreni'), --5
('home', 'home care for children', '8', '66666', 'Marxer, Markus'), --6
('parents', 'parents care for children', '9', '77777', 'Marxer, Markus'); --7
 
-- TABLE  sponsor 
INSERT INTO sponsor (name, anonymous, contact) VALUES
(NULL, 'true', NULL), --1
('Müller Max', 'false', 'Hinterweg, 9876 Pfäffikon ZH'), --2
(NULL, 'true', NULL), --3
('Weissert Madlaina', 'false', 'Dorfstrasse, Wetzikon'), --4
('Rusterholz Otto', 'false', 'Rosenbaum, 2468 Musterwald'), --5
('Zimmermann Hansueli', 'false', 'Glücksklee, 5432 Glücksdorf'), --6
('Boller Sepp', 'false', 'Tunnelstrasse, 2638 Mandelschmand'), --7
(NULL, 'true', NULL); --8

--TABLE  child 
INSERT INTO child (name, age, gender, guardian, contact) VALUES
('Sennett, Kirsten', '7', 'female', 'Helen', 'email'), --1
('Frauenfelder, Lukas', '8', 'male', 'Ursi', 'phone'), 
('Frauenfelder, Reto', '9', 'male', 'Ursi', 'phone'),
('Gertsch, Ella', '11', 'female', 'Anita', 'letter'),
('Leu, Nadine', '12', 'female', 'Sarah', 'email'),

('Aebi, Simone', '15', 'male', 'Rita', 'phone'), --5
('Aebi, Markus', '9', 'x', 'Rita', 'phone'),
('Hofstetter, Céline', '7', 'female', 'Marlies', 'email'),
('Huber, Stephanie', '8', 'female', 'Ursula', 'phone'),
('Papantoniou, Alexia', '9', 'female', 'Karin', 'letter'),

('Hauser, Anja', '11', 'female', 'Rahel', 'letter'), --10
('Kaynak, Ayla', '12', 'female', 'Vedi', 'email'),
('Wilde Céline', '15', 'female', 'Otto', 'phone'),
('Rawyler, Enya', '9', 'x', 'Andrea', 'phone'),
('Habsburger, Beat', '9', 'male', 'Sandra', 'phone'),

('Gruber, Jill', '7', 'female', 'Anna', 'email'), --15
('Licci, Noemi', '8', 'female', 'Karin', 'phone'),
('Aeschlimann, Leila', '9', 'female', 'Sabina', 'letter'),
('Linsbichler, Leonie', '11', 'female', 'Luise', 'letter'),
('Hofer, Mara', '12', 'female', 'Andrea', 'email');

--TABLE child_project
INSERT INTO child_project (child_id, project_id) VALUES 
('1', '1'),
('2', '1'),
('3', '1'),
('4', '1'),
('5', '1'),

('6', '2'),
('7', '2'),
('8', '2'),
('4', '2'),
('5', '2'),

('9', '3'),
('10', '3'),
('11', '3'),
('12', '3'),
('4', '3'),
('5', '3'),

('13', '4'),
('14', '4'),
('15', '4'),

('16', '5'),
('17', '5'),
('18', '5'),
('19', '5'),
('14', '5'),
('15', '5'),
('3', '5'),
('4', '5'),
('5', '5'),

('1', '6'),
('2', '6'),
('3', '6'),
('4', '6'),
('5', '6'),
('9', '6'),
('10', '6'),

('6', '7'),
('7', '7'),
('8', '7'),
('9', '7'),
('10', '7'),
('13', '7'),
('14', '7'),
('15', '7');
 
-- TABLE  donation
INSERT INTO donation (method, amount, sponsor, donation_subject, to_project, to_child) VALUES

--to child
('bank_transfer', '3000', '1', 'child', NULL, '1'),
('check', '3000', '2', 'child', NULL, '2'),
('cash', '7000', '3', 'child', NULL, '3'),
('check', '3000', '1', 'child', NULL, '4'),

('check', '5000', '8', 'child', NULL, '5'),
('cash', '4000', '7', 'child', NULL, '6'),
('bank_transfer', '5000', '6', 'child', NULL, '7'),
('bank_transfer', '2000', '5', 'child', NULL, '8'),

('cash', '6000', '4', 'child', NULL, '9'),
('cash', '9000', '3', 'child', NULL, '11'),

('bank_transfer', '4000', '2', 'child', NULL, '11'),
('bank_transfer', '2000', '1', 'child', NULL, '12'),
('check', '1000', '1', 'child', NULL, '13'),
('cash', '3000', '2', 'child', NULL, '14'),
('bank_transfer', '1000', '2', 'child', NULL, '15'),
('bank_transfer', '6000', '3', 'child', NULL, '16'),

('check', '2000', '4', 'child', NULL, '17'),
('cash', '5000', '5', 'child', NULL, '18'),
('bank_transfer', '2000', '6', 'child', NULL, '19'),
('check', '1000', '8', 'child', NULL, '11'),
('cash', '3000', '8', 'child', NULL, '1'),

('cash', '1000', '4', 'child', NULL, '1'),
('check', '7000', '7', 'child', NULL, '5'),

('bank_transfer', '3000', '3', 'child', NULL, '7'),
('bank_transfer', '3000', '2', 'child', NULL, '13'),
('bank_transfer', '3000', '1', 'child', NULL, '1'),

--to project
('bank_transfer', '3000', '1', 'project', '1', NULL),
('check', '3000', '2', 'project', '1', NULL),
('cash', '7000', '3', 'project', '1', NULL),
('check', '3000', '1', 'project', '1', NULL),

('check', '5000', '8', 'project', '2', NULL),
('cash', '4000', '7', 'project', '2', NULL),
('bank_transfer', '5000', '6', 'project', '2', NULL),
('bank_transfer', '2000', '5', 'project', '2', NULL),

('cash', '6000', '4', 'project', '3', NULL),
('cash', '9000', '3', 'project', '3', NULL),

('bank_transfer', '4000', '2', 'project', '4', NULL),
('bank_transfer', '2000', '1', 'project', '4', NULL),
('check', '1000', '1', 'project','4', NULL),
('cash', '3000', '2', 'project', '4', NULL),
('bank_transfer', '1000', '2', 'project', '4', NULL),
('bank_transfer', '6000', '3', 'project', '4', NULL),

('check', '2000', '4', 'project', '5', NULL),
('cash', '5000', '5', 'project', '5', NULL),
('bank_transfer', '2000', '6', 'project', '5', NULL),
('check', '1000', '8', 'project', '5', NULL),
('cash', '3000', '8', 'project', '5', NULL),

('cash', '1000', '4', 'project', '6', NULL),
('check', '7000', '7', 'project', '6', NULL),

('bank_transfer', '3000', '3', 'project', '7', NULL),
('bank_transfer', '3000', '2', 'project', '7', NULL),
('bank_transfer', '3000', '1', 'project', '7', NULL);