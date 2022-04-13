\c postgres
drop database if exists cs375v1;
CREATE DATABASE cs375v1 encoding 'UTF-8';
\c cs375v1;

-- Creates category table
drop table if exists category;
CREATE TABLE category (
id char primary key,
text text,
category_type char(2)
);

-- Inserts categories into category table
insert into category(id, text, category_type) values
('C', 'Contributing to the Team''s Work', 'B5'),
('I', 'Interacting with Teammates', 'B5'),
('K', 'Keeping the Team on Track', 'B5'),
('E', 'Expecting Quality', 'B5'),
('H', 'Having Related Knowledge, Skills, and Abilities', 'B5');


-- Displays all categories in the category table
select * from category;

-- Creates response table
drop table if exists response;
CREATE TABLE response (
evalid int,
student1 int,
student2 int,
category char(3),
value int
);

-- Inserts responses into response table
insert into response(evalid, student1, student2, category, value) values
(1,1,2,'C',5),
(1,1,2,'H',4),
(1,1,2,'I',3),
(1,1,2,'K',2),
(1,1,2,'E',1),
(1,1,3,'C',1),
(1,1,3,'H',2),
(1,1,3,'I',3),
(1,1,3,'K',4),
(1,1,3,'E',5);


-- Displays all responses in the response table
select * from response;



-- Creates team table
drop table if exists team;
CREATE TABLE team (
evalid int,
teamid int,
student int
);

-- Inserts categories into response table
insert into team(evalid, teamid, student) values
(1,1,1),
(1,1,2),
(1,1,3),
(1,2,4),
(1,2,5),
(1,2,6),
(1,3,7),
(1,3,8),
(1,3,9),
(2,1,2),
(2,1,3),
(2,1,4),
(2,2,5),
(2,2,6),
(2,2,7),
(2,3,8),
(2,3,9),
(2,3,10);


-- Displays all teams in the team table
select * from team;


CREATE VIEW v_response AS
SELECT evalid as evalid, student1 as student1, student2 as student2, category as category, value as value
FROM response;

CREATE VIEW v_teams AS 
SELECT evalid as evalid, team as team, student as student
FROM team;

CREATE VIEW v_response_team AS 
SELECT r.evalid as eval, r.student1 as s1, 
r.student2 as s2, r.category as cat, r.value as v, t.teamid as team
FROM response r
inner join team t on (t.student = r.student1)
order by r.evalid, r.student1, r.student2, r.category

CREATE VIEW v_stuAvg AS 
SELECT eval, team, s2, count(v) n, avg(v) avg 
FROM v_response_team where team < 3 
group by eval, team, s2 order by team, s2;

CREATE VIEW v_stuAvgNoSelf AS
SELECT eval, team, s2, count(v) n, avg(v) avg 
FROM v_response_team where s1 != s2 
and team < 3 group by eval, team, s2 order by team, s2;

CREATE VIEW v_average AS 
SELECT eval, team, s1, s2, json_agg(json_build_object('cat',cat, 'v',v)) 
FROM v_response_team where team < 3 group by eval, team, s1, s2 order by eval, s1, s2 
limit 10;