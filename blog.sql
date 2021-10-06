DROP DATABASE IF EXISTS  blogDB;
CREATE DATABASE blogDB;
USE blogDB;

DROP TABLE IF EXISTS user;
CREATE TABLE user(
id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL UNIQUE,
login_name VARCHAR(50) UNIQUE NOT NULL,
password VARCHAR(200),
email VARCHAR(50) UNIQUE NOT NULL,
name VARCHAR(50),
role ENUM('admin', 'moderator', 'user'),
reg_date TIMESTAMP
);

DROP TABLE IF EXISTS template; 	-- az összes tulajdonságot tartalmazhatná egyetlen css-file
CREATE TABLE template(
id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL UNIQUE,
name VARCHAR(50) UNIQUE NOT NULL,
background_picture BLOB,
font_color VARCHAR(50) UNIQUE NOT NULL,
category ENUM( 'minimal','casual', 'playful', 'chill')
);
-- véges számú létezik, feltölthető tesztadatokkal...

DROP TABLE IF EXISTS blog;
CREATE TABLE blog(
id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL UNIQUE,
name VARCHAR(50) UNIQUE NOT NULL,
owner_id INT UNSIGNED NOT NULL,
template_id INT UNSIGNED NOT NULL,
creation_time TIMESTAMP,
FOREIGN KEY(owner_id) REFERENCES user(id),
FOREIGN KEY(template_id) REFERENCES template(id)
);


DROP TABLE IF EXISTS blog_post;
CREATE TABLE blog_post(
id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL UNIQUE,
title VARCHAR(50) UNIQUE NOT NULL,
post_text LONGTEXT,	-- lehetne elérési út is egy fájlnévhez: txt vagy html formátum
status ENUM ('draft', 'posted', 'invisible'),
pub_time TIMESTAMP,
blog_id INT UNSIGNED NOT NULL, 
FOREIGN KEY(blog_id) REFERENCES blog(id)
);

DROP TABLE IF EXISTS comment;
CREATE TABLE comment(
id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL UNIQUE,
blog_post_id INT UNSIGNED NOT NULL, 
author_id INT UNSIGNED NOT NULL, 
preceding_id INT UNSIGNED,
pub_time TIMESTAMP,
FOREIGN KEY(blog_post_id) REFERENCES blog_post(id),
FOREIGN KEY(author_id) REFERENCES user(id),
FOREIGN KEY(preceding_id) REFERENCES comment(id)
);