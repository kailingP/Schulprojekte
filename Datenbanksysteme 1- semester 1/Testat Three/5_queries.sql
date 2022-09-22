--Kailing Peng
--Alessia Vannini


-- 1.1 list of all active sponsor IDs and the total amount they have donated, ordered by IDs
SELECT DISTINCT	sponsor AS "sponsor",
				sum(amount) AS "total amount of donation"
FROM donation
GROUP BY sponsor
ORDER BY sponsor;


--1.2 list of children, the projects they have participated and the location of the projects
SELECT 	ch.name AS "child name", 
		full_name  AS "project name", 
		location
FROM child ch
INNER JOIN child_project cp ON ch.id = cp.child_id
INNER JOIN project pj ON pj.id = cp.project_id
LEFT JOIN team ON pj.staff = team.name
ORDER BY location;


-- 1.3 list of children and their sponsors with least amount of donation
-- this query is uncorrelated
SELECT 	ch.name AS "child name",
		sp.id AS "sponsor id",
		sp.name AS "sponsor name",
		sp.anonymous AS "anonymous",
		don.amount AS "min donation amount"
FROM child ch
INNER JOIN donation don ON ch.id = don.to_child
INNER JOIN sponsor sp ON sp.id = don.sponsor
WHERE amount = 
(	SELECT MIN (amount)
	FROM donation don
 	INNER JOIN sponsor sp ON sp.id  = don.sponsor);


-- 1.4 list of children who doesn't have any donation
SELECT 	name, 
		age,
		gender
FROM child ch
WHERE NOT EXISTS
(	SELECT * FROM donation
	WHERE to_child = ch.id); 


-- 2.1  Common Table Expressions/WITH-Statements: 
-- uncorrelated subquery
-- list of all staff members which are leading a project with it's name
SELECT	t.name AS "staff name",
		t.role AS "project role",
		pj.id AS "project id",
		pj.full_name AS "project name"
FROM team t
INNER JOIN project pj ON pj.staff = t.name
WHERE t.role = 
(	SELECT DISTINCT t.role
	FROM team t
 	INNER JOIN project pj ON pj.staff = t.name
	WHERE t.role = 'Projektleiter');

-- common table expression
WITH staffleadingprojects AS (
	SELECT	
		t.name AS "staff name",
		t.role AS "project role",
		pj.id AS "project id",
		pj.full_name AS "project name"
	FROM team t
	INNER JOIN project pj ON pj.staff = t.name
	WHERE t.role = 'Projektleiter'
)
SELECT * FROM staffleadingprojects;


-- 2.2 
-- sum up total amount that has been collected for each project, ordered by project IDs
-- group by
SELECT 	pj.id AS "id", 
		full_name AS "project",
		SUM(don.amount) AS "total amount"
FROM project pj
INNER JOIN donation don ON pj.id = don.to_project
GROUP BY pj.id
ORDER BY pj.id;

-- list of all project arranged by their rank of donation
-- window function
SELECT 	pj.full_name,
		don.amount,
		don.sponsor,
		RANK() OVER (PARTITION BY pj.id ORDER BY don.amount DESC)
FROM project pj
INNER JOIN donation don ON don.to_project = pj.id;


--3.1 Fiance view for sponsor to see the financial status for each project
CREATE VIEW finance(pid, pname, budget, total) AS
  SELECT DISTINCT p.id pid, p.full_name pname, p.budget budget, total
  FROM project p
  INNER JOIN donation d ON d.to_project = p.id
  INNER JOIN (
    SELECT to_project, SUM(amount) total
    FROM donation
    GROUP BY to_project
    ORDER by to_project
    ) collected ON collected.to_project = p.id
    ORDER BY p.id
;

SELECT * FROM finance
ORDER BY total DESC;


--3.2 In projects view, update "parent" project to "sport" and person-in-charge to "Hassler, Hans"
CREATE VIEW projects (id, full_name,staff) AS
SELECT id, full_name, staff
FROM project;

SELECT * FROM projects 
WHERE id = 7;

UPDATE projects 
SET full_name = 'sport', staff ='Hassler, Hans'
WHERE id = 7;

SELECT * FROM projects 
WHERE id = 7;

