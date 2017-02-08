CREATE DATABASE if not exists `library2` /*!40100 DEFAULT CHARACTER SET utf8 */;

CREATE USER if not exists 'librarian'@'localhost' IDENTIFIED BY 'librarian_pass';
GRANT ALL privileges on library2.* to 'librarian'@'localhost';

drop table if exists `publication_authors`;
drop table if exists `authors`;
CREATE TABLE `authors` (
  `uuid` int NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


drop table if exists `borrows`;
drop table if exists `publications`;
drop table if exists `publishers`;
CREATE TABLE `publishers` (
  `uuid`  int NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `publications` (
  `uuid` int NOT NULL,
  `title` varchar(45) NOT NULL,
  `publication_date` date NOT NULL,
  `type` enum('Book','Newspaper','Magazine') NOT NULL,
  `publisher_id` int NOT NULL,
  `nr_of_copys` int(11) NOT NULL,
  `on_stock` int(11) NOT NULL,
  PRIMARY KEY (`uuid`),
  KEY `publisher_id_idx` (`publisher_id`),
  CONSTRAINT `publisher_id` FOREIGN KEY (`publisher_id`) REFERENCES `publishers` (`uuid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `publication_authors` (
  `publication_id` int NOT NULL,
  `author_id` int NOT NULL,
  PRIMARY KEY (`publication_id`,`author_id`),
  CONSTRAINT `fk_authors` FOREIGN KEY (`author_id`) REFERENCES `authors` (`uuid`) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE,
	CONSTRAINT `fk_publications` FOREIGN KEY (`publication_id`) REFERENCES `publications` (`uuid`) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists `users_roles`;
drop table if exists `users`;
CREATE TABLE `users` (
  `uuid` int NOT NULL,
  `user_name` varchar(50) NOT NULL,
  `loyalty_index` int(11) NOT NULL,
  `password` varchar(80) NOT NULL,
  PRIMARY KEY (`uuid`),
  CONSTRAINT uk_name UNIQUE (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `borrows` (
  `uuid` int NOT NULL,
  `user_id` int NOT NULL,
  `publication_id` int NOT NULL,
  `borrow_from` date NOT NULL,
  `borrow_until` date NOT NULL,
  PRIMARY KEY (`uuid`),
  KEY `user_id` (`user_id`),
  KEY `publication_id` (`publication_id`),
  CONSTRAINT `publication_id` FOREIGN KEY (`publication_id`) REFERENCES `publications` (`uuid`) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE,
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`uuid`) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists `roles`;
CREATE TABLE `roles`
(
    `id` int NOT NULL,
    `role` varchar(80) NOT NULL,
    CONSTRAINT pk_roles PRIMARY KEY (`id`),
    CONSTRAINT uk_role UNIQUE (`role`)
);
----------------------------------------
-- Table: user_management.users_roles
-- DROP TABLE user_management.users_roles;


CREATE TABLE `users_roles`
(
    `user_id` int NOT NULL,
    `role_id` int NOT NULL,
    CONSTRAINT pk_user_roles PRIMARY KEY (`role_id`, `user_id`),
    CONSTRAINT fk_role FOREIGN KEY (`role_id`)
        REFERENCES library2.roles (`id`) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE,
    CONSTRAINT fk_user FOREIGN KEY (`user_id`)
        REFERENCES library2.users (`uuid`) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);


INSERT INTO `library2`.`authors` (`uuid`, `name`) VALUES ('1', 'Arany Janos');
INSERT INTO `library2`.`authors` (`uuid`, `name`) VALUES ('2', 'Jokai Mor');
INSERT INTO `library2`.`authors` (`uuid`, `name`) VALUES ('3', 'Moricz Zsigmond');
INSERT INTO `library2`.`authors` (`uuid`, `name`) VALUES ('4', 'Nora Roberts');
INSERT INTO `library2`.`authors` (`uuid`, `name`) VALUES ('5', 'Stephenie Mayer');


INSERT INTO `library2`.`publishers` (`uuid`, `name`) VALUES ('1', 'Mentor');
INSERT INTO `library2`.`publishers` (`uuid`, `name`) VALUES ('2', 'Corvin');
INSERT INTO `library2`.`publishers` (`uuid`, `name`) VALUES ('3', 'Scientia');


INSERT INTO `library2`.`users` (`uuid`, `user_name`, `loyalty_index`, `password`) VALUES ('1', 'Kis Katalin', '10', 'almafa');
INSERT INTO `library2`.`users` (`uuid`, `user_name`, `loyalty_index`, `password`) VALUES ('2', 'Gall Barna', '0', 'almafa');
INSERT INTO `library2`.`users` (`uuid`, `user_name`, `loyalty_index`, `password`) VALUES ('3', 'Sipos Terez', '10', 'almafa');
INSERT INTO `library2`.`users` (`uuid`, `user_name`, `loyalty_index`, `password`) VALUES ('4', 'Szilard Nagy', '5', 'almafa');
INSERT INTO `library2`.`users` (`uuid`, `user_name`, `loyalty_index`, `password`) VALUES ('5', 'Admin', '10', 'almafa');

INSERT INTO `library2`.`roles` (`id`, `role`) VALUES ('1', 'LIBRARIAN');
INSERT INTO `library2`.`roles` (`id`, `role`) VALUES ('2', 'READER');

INSERT INTO `library2`.`users_roles` (`user_id`, `role_id`) VALUES ('1', '1');
INSERT INTO `library2`.`users_roles` (`user_id`, `role_id`) VALUES ('2', '2');
INSERT INTO `library2`.`users_roles` (`user_id`, `role_id`) VALUES ('3', '2');
INSERT INTO `library2`.`users_roles` (`user_id`, `role_id`) VALUES ('4', '2');
INSERT INTO `library2`.`users_roles` (`user_id`, `role_id`) VALUES ('5', '1');


INSERT INTO `library2`.`publications` (`uuid`, `title`, `publication_date`, `type`, `publisher_id`, `nr_of_copys`, `on_stock`) VALUES ('1', 'Arany ember', '2007-02-03', 'Book', '2', '5', '4');
INSERT INTO `library2`.`publications` (`uuid`, `title`, `publication_date`, `type`, `publisher_id`, `nr_of_copys`, `on_stock`) VALUES ('2', 'Montana Sky', '2005-09-03', 'Book', '3', '10', '9');
INSERT INTO `library2`.`publications` (`uuid`, `title`, `publication_date`, `type`, `publisher_id`, `nr_of_copys`, `on_stock`) VALUES ('3', 'Elso magazin', '2017-01-01', 'Magazine', '1', '100', '100');
INSERT INTO `library2`.`publications` (`uuid`, `title`, `publication_date`, `type`, `publisher_id`, `nr_of_copys`, `on_stock`) VALUES ('4', 'Napilap', '2001-02-02', 'Newspaper', '1', '1', '1');


INSERT INTO `library2`.`publication_authors` (`publication_id`, `author_id`) VALUES ('1', '2');
INSERT INTO `library2`.`publication_authors` (`publication_id`, `author_id`) VALUES ('2', '3');
INSERT INTO `library2`.`publication_authors` (`publication_id`, `author_id`) VALUES ('3', '1');
INSERT INTO `library2`.`publication_authors` (`publication_id`, `author_id`) VALUES ('4', '4');

INSERT INTO `library2`.`borrows` (`uuid`, `user_id`, `publication_id`, `borrow_from`, `borrow_until`) VALUES ('1', '1', '1', '2017-02-01', '2017-02-21');
INSERT INTO `library2`.`borrows` (`uuid`, `user_id`, `publication_id`, `borrow_from`, `borrow_until`) VALUES ('2', '3', '2', '2017-01-21', '2017-02-12');
