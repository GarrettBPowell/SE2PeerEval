\c postgres
drop database if exists cs375v1;
CREATE DATABASE cs375v1 encoding 'UTF-8';
\c cs375v1;


-- Creates category table
drop table if exists category cascade;
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
--select * from category;

-- Creates response table
drop table if exists response cascade;
CREATE TABLE response (
evalid int,
student1 int,
student2 int,
category char(3),
value int
);


insert into response (evalid, student1, student2, category, value) values (1, 1, 1, 'C', 3);
insert into response (evalid, student1, student2, category, value) values (1, 1, 2, 'C', 2);
insert into response (evalid, student1, student2, category, value) values (1, 1, 3, 'C', 3);
insert into response (evalid, student1, student2, category, value) values (1, 4, 4, 'C', 3);
insert into response (evalid, student1, student2, category, value) values (1, 4, 5, 'C', 3);
insert into response (evalid, student1, student2, category, value) values (1, 4, 6, 'C', 5);
insert into response (evalid, student1, student2, category, value) values (1, 7, 7, 'C', 3);
insert into response (evalid, student1, student2, category, value) values (1, 7, 8, 'C', 5);
insert into response (evalid, student1, student2, category, value) values (1, 7, 9, 'C', 4);
insert into response (evalid, student1, student2, category, value) values (1, 10, 10, 'C', 4);
insert into response (evalid, student1, student2, category, value) values (1, 10, 11, 'C', 5);
insert into response (evalid, student1, student2, category, value) values (1, 10, 12, 'C', 4);
insert into response (evalid, student1, student2, category, value) values (1, 13, 13, 'C', 4);
insert into response (evalid, student1, student2, category, value) values (1, 13, 14, 'C', 4);
insert into response (evalid, student1, student2, category, value) values (1, 13, 15, 'C', 4);
insert into response (evalid, student1, student2, category, value) values (1, 16, 16, 'C', 5);
insert into response (evalid, student1, student2, category, value) values (1, 16, 17, 'C', 5);
insert into response (evalid, student1, student2, category, value) values (1, 18, 18, 'C', 4);
insert into response (evalid, student1, student2, category, value) values (1, 18, 19, 'C', 4);
insert into response (evalid, student1, student2, category, value) values (1, 2, 1, 'C', 4);
insert into response (evalid, student1, student2, category, value) values (1, 2, 2, 'C', 4);
insert into response (evalid, student1, student2, category, value) values (1, 2, 3, 'C', 4);
insert into response (evalid, student1, student2, category, value) values (1, 5, 4, 'C', 2);
insert into response (evalid, student1, student2, category, value) values (1, 5, 5, 'C', 3);
insert into response (evalid, student1, student2, category, value) values (1, 5, 6, 'C', 5);
insert into response (evalid, student1, student2, category, value) values (1, 8, 7, 'C', 4);
insert into response (evalid, student1, student2, category, value) values (1, 8, 8, 'C', 5);
insert into response (evalid, student1, student2, category, value) values (1, 8, 9, 'C', 5);
insert into response (evalid, student1, student2, category, value) values (1, 11, 10, 'C', 3);
insert into response (evalid, student1, student2, category, value) values (1, 11, 11, 'C', 5);
insert into response (evalid, student1, student2, category, value) values (1, 11, 12, 'C', 2);
insert into response (evalid, student1, student2, category, value) values (1, 14, 13, 'C', 4);
insert into response (evalid, student1, student2, category, value) values (1, 14, 14, 'C', 5);
insert into response (evalid, student1, student2, category, value) values (1, 14, 15, 'C', 4);
insert into response (evalid, student1, student2, category, value) values (1, 17, 16, 'C', 5);
insert into response (evalid, student1, student2, category, value) values (1, 17, 17, 'C', 4);
insert into response (evalid, student1, student2, category, value) values (1, 19, 18, 'C', 4);
insert into response (evalid, student1, student2, category, value) values (1, 19, 19, 'C', 3);
insert into response (evalid, student1, student2, category, value) values (1, 3, 1, 'C', 5);
insert into response (evalid, student1, student2, category, value) values (1, 3, 2, 'C', 4);
insert into response (evalid, student1, student2, category, value) values (1, 3, 3, 'C', 5);
insert into response (evalid, student1, student2, category, value) values (1, 6, 4, 'C', 2);
insert into response (evalid, student1, student2, category, value) values (1, 6, 5, 'C', 2);
insert into response (evalid, student1, student2, category, value) values (1, 6, 6, 'C', 4);
insert into response (evalid, student1, student2, category, value) values (1, 9, 7, 'C', 2);
insert into response (evalid, student1, student2, category, value) values (1, 9, 8, 'C', 2);
insert into response (evalid, student1, student2, category, value) values (1, 9, 9, 'C', 5);
insert into response (evalid, student1, student2, category, value) values (1, 12, 10, 'C', 4);
insert into response (evalid, student1, student2, category, value) values (1, 12, 11, 'C', 5);
insert into response (evalid, student1, student2, category, value) values (1, 12, 12, 'C', 4);
insert into response (evalid, student1, student2, category, value) values (1, 15, 13, 'C', 4);
insert into response (evalid, student1, student2, category, value) values (1, 15, 14, 'C', 5);
insert into response (evalid, student1, student2, category, value) values (1, 15, 15, 'C', 4);

insert into response (evalid, student1, student2, category, value) values (1, 1, 1, 'I', 3);
insert into response (evalid, student1, student2, category, value) values (1, 1, 2, 'I', 3);
insert into response (evalid, student1, student2, category, value) values (1, 1, 3, 'I', 3);
insert into response (evalid, student1, student2, category, value) values (1, 4, 4, 'I', 4);
insert into response (evalid, student1, student2, category, value) values (1, 4, 5, 'I', 4);
insert into response (evalid, student1, student2, category, value) values (1, 4, 6, 'I', 2);
insert into response (evalid, student1, student2, category, value) values (1, 7, 7, 'I', 3);
insert into response (evalid, student1, student2, category, value) values (1, 7, 8, 'I', 3);
insert into response (evalid, student1, student2, category, value) values (1, 7, 9, 'I', 4);
insert into response (evalid, student1, student2, category, value) values (1, 10, 10, 'I', 5);
insert into response (evalid, student1, student2, category, value) values (1, 10, 11, 'I', 5);
insert into response (evalid, student1, student2, category, value) values (1, 10, 12, 'I', 5);
insert into response (evalid, student1, student2, category, value) values (1, 13, 13, 'I', 4);
insert into response (evalid, student1, student2, category, value) values (1, 13, 14, 'I', 4);
insert into response (evalid, student1, student2, category, value) values (1, 13, 15, 'I', 4);
insert into response (evalid, student1, student2, category, value) values (1, 16, 16, 'I', 5);
insert into response (evalid, student1, student2, category, value) values (1, 16, 17, 'I', 5);
insert into response (evalid, student1, student2, category, value) values (1, 18, 18, 'I', 5);
insert into response (evalid, student1, student2, category, value) values (1, 18, 19, 'I', 5);
insert into response (evalid, student1, student2, category, value) values (1, 2, 1, 'I', 4);
insert into response (evalid, student1, student2, category, value) values (1, 2, 2, 'I', 4);
insert into response (evalid, student1, student2, category, value) values (1, 2, 3, 'I', 4);
insert into response (evalid, student1, student2, category, value) values (1, 5, 4, 'I', 3);
insert into response (evalid, student1, student2, category, value) values (1, 5, 5, 'I', 4);
insert into response (evalid, student1, student2, category, value) values (1, 5, 6, 'I', 4);
insert into response (evalid, student1, student2, category, value) values (1, 8, 7, 'I', 5);
insert into response (evalid, student1, student2, category, value) values (1, 8, 8, 'I', 5);
insert into response (evalid, student1, student2, category, value) values (1, 8, 9, 'I', 5);
insert into response (evalid, student1, student2, category, value) values (1, 11, 10, 'I', 4);
insert into response (evalid, student1, student2, category, value) values (1, 11, 11, 'I', 3);
insert into response (evalid, student1, student2, category, value) values (1, 11, 12, 'I', 3);
insert into response (evalid, student1, student2, category, value) values (1, 14, 13, 'I', 3);
insert into response (evalid, student1, student2, category, value) values (1, 14, 14, 'I', 3);
insert into response (evalid, student1, student2, category, value) values (1, 14, 15, 'I', 3);
insert into response (evalid, student1, student2, category, value) values (1, 17, 16, 'I', 5);
insert into response (evalid, student1, student2, category, value) values (1, 17, 17, 'I', 4);
insert into response (evalid, student1, student2, category, value) values (1, 19, 18, 'I', 4);
insert into response (evalid, student1, student2, category, value) values (1, 19, 19, 'I', 3);
insert into response (evalid, student1, student2, category, value) values (1, 3, 1, 'I', 5);
insert into response (evalid, student1, student2, category, value) values (1, 3, 2, 'I', 4);
insert into response (evalid, student1, student2, category, value) values (1, 3, 3, 'I', 5);
insert into response (evalid, student1, student2, category, value) values (1, 6, 4, 'I', 2);
insert into response (evalid, student1, student2, category, value) values (1, 6, 5, 'I', 3);
insert into response (evalid, student1, student2, category, value) values (1, 6, 6, 'I', 3);
insert into response (evalid, student1, student2, category, value) values (1, 9, 7, 'I', 4);
insert into response (evalid, student1, student2, category, value) values (1, 9, 8, 'I', 4);
insert into response (evalid, student1, student2, category, value) values (1, 9, 9, 'I', 5);
insert into response (evalid, student1, student2, category, value) values (1, 12, 10, 'I', 3);
insert into response (evalid, student1, student2, category, value) values (1, 12, 11, 'I', 3);
insert into response (evalid, student1, student2, category, value) values (1, 12, 12, 'I', 3);
insert into response (evalid, student1, student2, category, value) values (1, 15, 13, 'I', 5);
insert into response (evalid, student1, student2, category, value) values (1, 15, 14, 'I', 5);
insert into response (evalid, student1, student2, category, value) values (1, 15, 15, 'I', 5);

insert into response (evalid, student1, student2, category, value) values (1, 1, 1, 'K', 3);
insert into response (evalid, student1, student2, category, value) values (1, 1, 2, 'K', 2);
insert into response (evalid, student1, student2, category, value) values (1, 1, 3, 'K', 3);
insert into response (evalid, student1, student2, category, value) values (1, 4, 4, 'K', 3);
insert into response (evalid, student1, student2, category, value) values (1, 4, 5, 'K', 3);
insert into response (evalid, student1, student2, category, value) values (1, 4, 6, 'K', 3);
insert into response (evalid, student1, student2, category, value) values (1, 7, 7, 'K', 5);
insert into response (evalid, student1, student2, category, value) values (1, 7, 8, 'K', 3);
insert into response (evalid, student1, student2, category, value) values (1, 7, 9, 'K', 4);
insert into response (evalid, student1, student2, category, value) values (1, 10, 10, 'K', 5);
insert into response (evalid, student1, student2, category, value) values (1, 10, 11, 'K', 5);
insert into response (evalid, student1, student2, category, value) values (1, 10, 12, 'K', 4);
insert into response (evalid, student1, student2, category, value) values (1, 13, 13, 'K', 4);
insert into response (evalid, student1, student2, category, value) values (1, 13, 14, 'K', 4);
insert into response (evalid, student1, student2, category, value) values (1, 13, 15, 'K', 4);
insert into response (evalid, student1, student2, category, value) values (1, 16, 16, 'K', 5);
insert into response (evalid, student1, student2, category, value) values (1, 16, 17, 'K', 5);
insert into response (evalid, student1, student2, category, value) values (1, 18, 18, 'K', 4);
insert into response (evalid, student1, student2, category, value) values (1, 18, 19, 'K', 4);
insert into response (evalid, student1, student2, category, value) values (1, 2, 1, 'K', 4);
insert into response (evalid, student1, student2, category, value) values (1, 2, 2, 'K', 4);
insert into response (evalid, student1, student2, category, value) values (1, 2, 3, 'K', 5);
insert into response (evalid, student1, student2, category, value) values (1, 5, 4, 'K', 2);
insert into response (evalid, student1, student2, category, value) values (1, 5, 5, 'K', 4);
insert into response (evalid, student1, student2, category, value) values (1, 5, 6, 'K', 5);
insert into response (evalid, student1, student2, category, value) values (1, 8, 7, 'K', 5);
insert into response (evalid, student1, student2, category, value) values (1, 8, 8, 'K', 5);
insert into response (evalid, student1, student2, category, value) values (1, 8, 9, 'K', 5);
insert into response (evalid, student1, student2, category, value) values (1, 11, 10, 'K', 4);
insert into response (evalid, student1, student2, category, value) values (1, 11, 11, 'K', 4);
insert into response (evalid, student1, student2, category, value) values (1, 11, 12, 'K', 3);
insert into response (evalid, student1, student2, category, value) values (1, 14, 13, 'K', 3);
insert into response (evalid, student1, student2, category, value) values (1, 14, 14, 'K', 5);
insert into response (evalid, student1, student2, category, value) values (1, 14, 15, 'K', 5);
insert into response (evalid, student1, student2, category, value) values (1, 17, 16, 'K', 5);
insert into response (evalid, student1, student2, category, value) values (1, 17, 17, 'K', 4);
insert into response (evalid, student1, student2, category, value) values (1, 19, 18, 'K', 4);
insert into response (evalid, student1, student2, category, value) values (1, 19, 19, 'K', 3);
insert into response (evalid, student1, student2, category, value) values (1, 3, 1, 'K', 5);
insert into response (evalid, student1, student2, category, value) values (1, 3, 2, 'K', 4);
insert into response (evalid, student1, student2, category, value) values (1, 3, 3, 'K', 5);
insert into response (evalid, student1, student2, category, value) values (1, 6, 4, 'K', 2);
insert into response (evalid, student1, student2, category, value) values (1, 6, 5, 'K', 3);
insert into response (evalid, student1, student2, category, value) values (1, 6, 6, 'K', 3);
insert into response (evalid, student1, student2, category, value) values (1, 9, 7, 'K', 2);
insert into response (evalid, student1, student2, category, value) values (1, 9, 8, 'K', 2);
insert into response (evalid, student1, student2, category, value) values (1, 9, 9, 'K', 5);
insert into response (evalid, student1, student2, category, value) values (1, 12, 10, 'K', 3);
insert into response (evalid, student1, student2, category, value) values (1, 12, 11, 'K', 5);
insert into response (evalid, student1, student2, category, value) values (1, 12, 12, 'K', 3);
insert into response (evalid, student1, student2, category, value) values (1, 15, 13, 'K', 4);
insert into response (evalid, student1, student2, category, value) values (1, 15, 14, 'K', 4);
insert into response (evalid, student1, student2, category, value) values (1, 15, 15, 'K', 4);

insert into response (evalid, student1, student2, category, value) values (1, 1, 1, 'E', 3);
insert into response (evalid, student1, student2, category, value) values (1, 1, 2, 'E', 3);
insert into response (evalid, student1, student2, category, value) values (1, 1, 3, 'E', 3);
insert into response (evalid, student1, student2, category, value) values (1, 4, 4, 'E', 4);
insert into response (evalid, student1, student2, category, value) values (1, 4, 5, 'E', 4);
insert into response (evalid, student1, student2, category, value) values (1, 4, 6, 'E', 3);
insert into response (evalid, student1, student2, category, value) values (1, 7, 7, 'E', 3);
insert into response (evalid, student1, student2, category, value) values (1, 7, 8, 'E', 3);
insert into response (evalid, student1, student2, category, value) values (1, 7, 9, 'E', 4);
insert into response (evalid, student1, student2, category, value) values (1, 10, 10, 'E', 4);
insert into response (evalid, student1, student2, category, value) values (1, 10, 11, 'E', 4);
insert into response (evalid, student1, student2, category, value) values (1, 10, 12, 'E', 4);
insert into response (evalid, student1, student2, category, value) values (1, 13, 13, 'E', 4);
insert into response (evalid, student1, student2, category, value) values (1, 13, 14, 'E', 4);
insert into response (evalid, student1, student2, category, value) values (1, 13, 15, 'E', 4);
insert into response (evalid, student1, student2, category, value) values (1, 16, 16, 'E', 5);
insert into response (evalid, student1, student2, category, value) values (1, 16, 17, 'E', 5);
insert into response (evalid, student1, student2, category, value) values (1, 18, 18, 'E', 4);
insert into response (evalid, student1, student2, category, value) values (1, 18, 19, 'E', 4);
insert into response (evalid, student1, student2, category, value) values (1, 2, 1, 'E', 4);
insert into response (evalid, student1, student2, category, value) values (1, 2, 2, 'E', 4);
insert into response (evalid, student1, student2, category, value) values (1, 2, 3, 'E', 4);
insert into response (evalid, student1, student2, category, value) values (1, 5, 4, 'E', 3);
insert into response (evalid, student1, student2, category, value) values (1, 5, 5, 'E', 3);
insert into response (evalid, student1, student2, category, value) values (1, 5, 6, 'E', 3);
insert into response (evalid, student1, student2, category, value) values (1, 8, 7, 'E', 5);
insert into response (evalid, student1, student2, category, value) values (1, 8, 8, 'E', 5);
insert into response (evalid, student1, student2, category, value) values (1, 8, 9, 'E', 5);
insert into response (evalid, student1, student2, category, value) values (1, 11, 10, 'E', 4);
insert into response (evalid, student1, student2, category, value) values (1, 11, 11, 'E', 4);
insert into response (evalid, student1, student2, category, value) values (1, 11, 12, 'E', 4);
insert into response (evalid, student1, student2, category, value) values (1, 14, 13, 'E', 5);
insert into response (evalid, student1, student2, category, value) values (1, 14, 14, 'E', 5);
insert into response (evalid, student1, student2, category, value) values (1, 14, 15, 'E', 5);
insert into response (evalid, student1, student2, category, value) values (1, 17, 16, 'E', 5);
insert into response (evalid, student1, student2, category, value) values (1, 17, 17, 'E', 5);
insert into response (evalid, student1, student2, category, value) values (1, 19, 18, 'E', 4);
insert into response (evalid, student1, student2, category, value) values (1, 19, 19, 'E', 4);
insert into response (evalid, student1, student2, category, value) values (1, 3, 1, 'E', 5);
insert into response (evalid, student1, student2, category, value) values (1, 3, 2, 'E', 4);
insert into response (evalid, student1, student2, category, value) values (1, 3, 3, 'E', 5);
insert into response (evalid, student1, student2, category, value) values (1, 6, 4, 'E', 3);
insert into response (evalid, student1, student2, category, value) values (1, 6, 5, 'E', 3);
insert into response (evalid, student1, student2, category, value) values (1, 6, 6, 'E', 3);
insert into response (evalid, student1, student2, category, value) values (1, 9, 7, 'E', 2);
insert into response (evalid, student1, student2, category, value) values (1, 9, 8, 'E', 2);
insert into response (evalid, student1, student2, category, value) values (1, 9, 9, 'E', 4);
insert into response (evalid, student1, student2, category, value) values (1, 12, 10, 'E', 5);
insert into response (evalid, student1, student2, category, value) values (1, 12, 11, 'E', 5);
insert into response (evalid, student1, student2, category, value) values (1, 12, 12, 'E', 3);
insert into response (evalid, student1, student2, category, value) values (1, 15, 13, 'E', 4);
insert into response (evalid, student1, student2, category, value) values (1, 15, 14, 'E', 4);
insert into response (evalid, student1, student2, category, value) values (1, 15, 15, 'E', 4);

insert into response (evalid, student1, student2, category, value) values (1, 1, 1, 'H', 4);
insert into response (evalid, student1, student2, category, value) values (1, 1, 2, 'H', 2);
insert into response (evalid, student1, student2, category, value) values (1, 1, 3, 'H', 4);
insert into response (evalid, student1, student2, category, value) values (1, 4, 4, 'H', 4);
insert into response (evalid, student1, student2, category, value) values (1, 4, 5, 'H', 4);
insert into response (evalid, student1, student2, category, value) values (1, 4, 6, 'H', 5);
insert into response (evalid, student1, student2, category, value) values (1, 7, 7, 'H', 3);
insert into response (evalid, student1, student2, category, value) values (1, 7, 8, 'H', 3);
insert into response (evalid, student1, student2, category, value) values (1, 7, 9, 'H', 4);
insert into response (evalid, student1, student2, category, value) values (1, 10, 10, 'H', 5);
insert into response (evalid, student1, student2, category, value) values (1, 10, 11, 'H', 5);
insert into response (evalid, student1, student2, category, value) values (1, 10, 12, 'H', 5);
insert into response (evalid, student1, student2, category, value) values (1, 13, 13, 'H', 5);
insert into response (evalid, student1, student2, category, value) values (1, 13, 14, 'H', 5);
insert into response (evalid, student1, student2, category, value) values (1, 13, 15, 'H', 5);
insert into response (evalid, student1, student2, category, value) values (1, 16, 16, 'H', 5);
insert into response (evalid, student1, student2, category, value) values (1, 16, 17, 'H', 5);
insert into response (evalid, student1, student2, category, value) values (1, 18, 18, 'H', 4);
insert into response (evalid, student1, student2, category, value) values (1, 18, 19, 'H', 3);
insert into response (evalid, student1, student2, category, value) values (1, 2, 1, 'H', 4);
insert into response (evalid, student1, student2, category, value) values (1, 2, 2, 'H', 4);
insert into response (evalid, student1, student2, category, value) values (1, 2, 3, 'H', 4);
insert into response (evalid, student1, student2, category, value) values (1, 5, 4, 'H', 3);
insert into response (evalid, student1, student2, category, value) values (1, 5, 5, 'H', 3);
insert into response (evalid, student1, student2, category, value) values (1, 5, 6, 'H', 5);
insert into response (evalid, student1, student2, category, value) values (1, 8, 7, 'H', 5);
insert into response (evalid, student1, student2, category, value) values (1, 8, 8, 'H', 5);
insert into response (evalid, student1, student2, category, value) values (1, 8, 9, 'H', 5);
insert into response (evalid, student1, student2, category, value) values (1, 11, 10, 'H', 4);
insert into response (evalid, student1, student2, category, value) values (1, 11, 11, 'H', 5);
insert into response (evalid, student1, student2, category, value) values (1, 11, 12, 'H', 3);
insert into response (evalid, student1, student2, category, value) values (1, 14, 13, 'H', 5);
insert into response (evalid, student1, student2, category, value) values (1, 14, 14, 'H', 5);
insert into response (evalid, student1, student2, category, value) values (1, 14, 15, 'H', 5);
insert into response (evalid, student1, student2, category, value) values (1, 17, 16, 'H', 5);
insert into response (evalid, student1, student2, category, value) values (1, 17, 17, 'H', 4);
insert into response (evalid, student1, student2, category, value) values (1, 19, 18, 'H', 3);
insert into response (evalid, student1, student2, category, value) values (1, 19, 19, 'H', 3);
insert into response (evalid, student1, student2, category, value) values (1, 3, 1, 'H', 5);
insert into response (evalid, student1, student2, category, value) values (1, 3, 2, 'H', 4);
insert into response (evalid, student1, student2, category, value) values (1, 3, 3, 'H', 5);
insert into response (evalid, student1, student2, category, value) values (1, 6, 4, 'H', 3);
insert into response (evalid, student1, student2, category, value) values (1, 6, 5, 'H', 2);
insert into response (evalid, student1, student2, category, value) values (1, 6, 6, 'H', 5);
insert into response (evalid, student1, student2, category, value) values (1, 9, 7, 'H', 2);
insert into response (evalid, student1, student2, category, value) values (1, 9, 8, 'H', 2);
insert into response (evalid, student1, student2, category, value) values (1, 9, 9, 'H', 4);
insert into response (evalid, student1, student2, category, value) values (1, 12, 10, 'H', 5);
insert into response (evalid, student1, student2, category, value) values (1, 12, 11, 'H', 5);
insert into response (evalid, student1, student2, category, value) values (1, 12, 12, 'H', 5);
insert into response (evalid, student1, student2, category, value) values (1, 15, 13, 'H', 4);
insert into response (evalid, student1, student2, category, value) values (1, 15, 14, 'H', 5);
insert into response (evalid, student1, student2, category, value) values (1, 15, 15, 'H', 4);


-- Displays all responses in the response table
--select * from response;


-- Creates team table
drop table if exists team cascade;
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
--select * from team;


-- Creates student table
drop table if exists student cascade;
CREATE TABLE student (
id int,
name text
);


insert into student (id, name) values ( 1, 'StudentA');
insert into student (id, name) values ( 2, 'StudentB');
insert into student (id, name) values ( 3, 'StudentC');
insert into student (id, name) values ( 4, 'StudentD');
insert into student (id, name) values ( 5, 'StudentE');
insert into student (id, name) values ( 6, 'StudentF');
insert into student (id, name) values ( 7, 'StudentG');
insert into student (id, name) values ( 8, 'StudentH');
insert into student (id, name) values ( 9, 'StudentI');
insert into student (id, name) values ( 10, 'StudentJ');
insert into student (id, name) values ( 11, 'StudentK');
insert into student (id, name) values ( 12, 'StudentL');
insert into student (id, name) values ( 13, 'StudentM');
insert into student (id, name) values ( 14, 'StudentN');
insert into student (id, name) values ( 15, 'StudentO');
insert into student (id, name) values ( 16, 'StudentP');
insert into student (id, name) values ( 17, 'StudentQ');
insert into student (id, name) values ( 18, 'StudentR');
insert into student (id, name) values ( 19, 'StudentS');

-- Displays all students
--select * from student;

--Create class table 

drop table if exists course cascade;
create table course (
id int,
abbrev text,
title text,
primary key (id)
);

insert into course (id, abbrev, title) values
(1, 'CS374', 'Software Engineering I'),
(2, 'CS375', 'Software Engineering II'),
(3, 'CS356', 'Operating Systems'),
(4, 'CS115', 'S1'),
(5, 'CS116', 'S2'),
(6, 'CS120', 'P1'),
(7, 'CS130', 'P2'),
(8, 'CS230', 'OOP')
;


drop table if exists section cascade ;
create table section (
id int,
course_id int,
title text,
start date,
primary key (id)
);

insert into section (id, course_id, title, start) values
(1, 1, 'Fall 21','2021/09/01'),
(2, 1, 'Spring 22', '2022/01/01'),
(3, 2, 'Spring 21', '2021/02/01'),
(4, 2, 'Fall 21', '2021/09/01'),
(5, 3, 'Spring 21', '2021/01/01'),
(6, 4, 'Fall 21', '2021/09/01'),
(7, 5, 'Spring 22', '2022/01/01')
;

drop table if exists eval_section;
create table eval_section (
eval_id int,
section_id int,
primary key (eval_id, section_id)
);

insert into eval_section (eval_id, section_id) values
(1, 1),
(2, 1),
(3, 2),
(4, 3),
(5, 3),
(6, 4),
(7, 5),
(8, 6),
(9, 7);

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
order by r.evalid, r.student1, r.student2, r.category;

CREATE VIEW v_stuAvg AS 
SELECT eval, team, s2, count(v) n, avg(v) avg 
FROM v_response_team
group by eval, team, s2 order by team, s2;

CREATE VIEW v_stuAvgNoSelf AS
SELECT eval, team, s2, count(v) n, avg(v) avg 
FROM v_response_team where s1 != s2 
group by eval, team, s2 order by team, s2;

CREATE VIEW v_average AS 
SELECT eval, team, s1, s2, json_agg(json_build_object('cat',cat, 'v',v)) 
FROM v_response_team group by eval, team, s1, s2 order by eval, s1, s2 
limit 10;

/*
1. Teacher view with anonymized data to show to class (teacher can only see their class).

2. Teacher view with raw (not anonymized) data.

3. Student view for a specific student with anonymized data.

4. Student lifetime view with anonymized data (data across classes).

5. Student view for team statistics.

6. Admin view with raw (not anonymized) data that spans previous classes and includes roll-ups.
*/

CREATE VIEW teacherAnon AS 
SELECT category, value
FROM response 
GROUP BY category, value, student1; 

CREATE VIEW teacherAll AS 
SELECT *
FROM response; 

CREATE VIEW studentImmediate AS 
SELECT category, value
FROM response
JOIN student ON response.student1 = student.id; 

CREATE VIEW studentLifetime AS 
SELECT category, value
FROM response
JOIN student ON response.student1 = student.id ;

CREATE VIEW studentTeams AS 
SELECT category, avg(value)
FROM response
JOIN team ON response.student1 = team.student 
GROUP BY category; 

CREATE VIEW adminAll AS 
SELECT * 
FROM response; 


GRANT ALL on category to mrblee;
GRANT ALL on response to mrblee;
GRANT ALL on student to mrblee;
GRANT ALL on team to mrblee;
GRANT ALL on eval_section to mrblee;
GRANT ALL on course to mrblee; 
GRANT ALL on section to mrblee; 
GRANT ALL on eval_section to mrblee; 

GRANT ALL on v_average to mrblee;
GRANT ALL on v_response to mrblee;
GRANT ALL on v_response_team to mrblee;
GRANT ALL on v_stuavg to mrblee;
GRANT ALL on v_stuavgnoself to mrblee;
GRANT ALL on v_teams to mrblee;
GRANT ALL on teacherAnon to mrblee; 
GRANT ALL on teacherAll to mrblee; 
GRANT ALL on studentImmediate to mrblee; 
GRANT ALL on studentLifetime to mrblee;  
GRANT ALL on studentTeams to mrblee; 
GRANT ALL on adminAll to mrblee; 
