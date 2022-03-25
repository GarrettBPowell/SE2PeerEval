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