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
role ENUM('guest', 'admin', 'moderator', 'user'),
reg_date TIMESTAMP,
is_suspended BOOLEAN
);

INSERT INTO user (login_name, password, email, name, role, is_suspended  ) VALUES
			('Thor', 'hgehsna43f', 'thor@avengers.com', 'Thor, son of Odin', 'user', false),
            ('CaptainAmerica', 'jh3736fn', 'captainamerica@avangers.com', 'Steve Rogers', 'admin', false),
            ('Hulk', 'jkhnx654378rtg', 'hulk@avangers.com', 'Bruce Banner', 'moderator', false),
            ('BlackWidow', 'hb36poj', 'blackwidow@avangers.com', 'Natasa Romanoff', 'moderator', false),
            ('Hawkeye', 'jrhen7489p', 'hawkeye@avangers.com', 'Clint Barton', 'user', false),
            ('Loki', 'cjsnc7486839', 'loki@avangers.com', 'Loki', 'user', true);


DROP TABLE IF EXISTS template; 	-- az összes tulajdonságot tartalmazhatná egyetlen css-file
CREATE TABLE template(
id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL UNIQUE,
name VARCHAR(50) UNIQUE NOT NULL,
background_picture BLOB,
font_color VARCHAR(50)  NOT NULL,
category ENUM( 'minimal','casual', 'playful')
);

INSERT INTO template (name, font_color, category) VALUES 
					('NewYork', 'blue', 'minimal'),
                    ('Asgard', 'silver', 'playful'),
                    ('Titan', 'purple', 'casual'),
                    ('Sakaar', 'blue', 'playful');
                    

DROP TABLE IF EXISTS blog;
CREATE TABLE blog(
id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL UNIQUE,
title VARCHAR(50) UNIQUE NOT NULL,
owner_id INT UNSIGNED NOT NULL,
template_id INT UNSIGNED NOT NULL,
creation_time TIMESTAMP,
FOREIGN KEY(owner_id) REFERENCES user(id),
FOREIGN KEY(template_id) REFERENCES template(id)
);


INSERT INTO blog(title, owner_id, template_id) VALUES
				('Thundersorms', 1, 1),
                ('SHIELD', 2, 3),
                ('Gladiator life', 3, 2),
                ('Science', 3, 2),
                ('Interrogations', 4, 1),
                ('Tricks', 6, 3);


DROP TABLE IF EXISTS blog_post;
CREATE TABLE blog_post(
id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL UNIQUE,
title VARCHAR(50) UNIQUE NOT NULL,
post_text LONGTEXT,	-- lehetne elérési út is egy fájlnévhez: txt vagy html formátum
status ENUM ('draft', 'posted', 'invisible'),
pub_time TIMESTAMP,
readed INT UNSIGNED,
blog_id INT UNSIGNED NOT NULL, 
FOREIGN KEY(blog_id) REFERENCES blog(id)
);

INSERT INTO blog_post (title, post_text, status, blog_id) VALUES
					('Two years', ' after the battle of Sokovia,', 'draft', 1),
                    ('Thor is imprisoned', ' by the fire demon Surtur, who reveals that Thors father Odin is no longer on Asgard.', 'posted', 2),
                    ('He explains', ' that the realm will soon be destroyed during the prophesied Ragnarök, once Surtur unites his crown with the Eternal Flame that burns in Odins vault. ', 'invisible', 3),
                    ('Thor frees himself', ' defeats Surtur and takes his crown, believing he has prevented Ragnarök.', 'posted', 4),
                    ('Thor returns', ' to Asgard to find Heimdall gone and his estranged brother Loki posing as Odin.', 'draft', 4),
                    ('After exposing', ' Loki, Thor forces him to help find their father, and with directions from Stephen Strange at the Sanctum Sanctorum in New York City, they locate Odin in Norway. ', 'posted', 5),
                    ('Odin explains', ' that he is dying, Ragnarök is imminent despite Thors efforts to prevent it, and his passing will free his firstborn child, Hela, from a prison she was sealed in long ago.', 'posted', 6);

DROP TABLE IF EXISTS comment;
CREATE TABLE comment(
id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL UNIQUE,
blog_post_id INT UNSIGNED NOT NULL, 
author_id INT UNSIGNED NOT NULL, 
preceding_id INT UNSIGNED DEFAULT 0,
comment_text VARCHAR(250),
is_visible BOOLEAN,
pub_time TIMESTAMP,
FOREIGN KEY(blog_post_id) REFERENCES blog_post(id),
FOREIGN KEY(author_id) REFERENCES user(id),
FOREIGN KEY(preceding_id) REFERENCES comment(id)
);

  

INSERT INTO comment (blog_post_id, author_id, preceding_id,comment_text, is_visible) VALUES
                    (1,2,0, 'Deciding to help Thor',true ),
                    (1,3,0, 'The Grandmaster orders 142 and Loki to find Thor',true ),
                    (2,4,0, ' and Hulk, but the pair come to blows and ',false ),
                    (2,5,0 , 'Loki forces her to relive the deaths of her Valkyrie ', TRUE),
                    (2,3,0, 'companions at the hands of Hela. , she takes Loki captive. Unwilling to ',TRUE ),
                    (3,6,0, 'be left behind, Loki provides the', FALSE),
                    (3,4,0 , 'group with the means to steal', TRUE),
                    (4,2,0, ' one of the Grandmasters ships.', TRUE),
                    (4,3,0, 'They then liberate the other g', TRUE),
                    (4,4,0, 'ladiators who, incited by two aliens named K', FALSE),
                    (4,5,0, ' Korg and Miek, stage a revolution', TRUE);
                    
          --          Error Code: 1452. Cannot add or update a child row: a foreign key constraint fails (`blogdb`.`comment`, CONSTRAINT `comment_ibfk_3` FOREIGN KEY (`preceding_id`) REFERENCES `comment` (`id`))

