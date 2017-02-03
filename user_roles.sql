drop SCHEMA if exists user_role;
CREATE SCHEMA if not exists user_role;
CREATE USER if not exists 'user_role'@'localhost' IDENTIFIED BY 'user_role_pass';
GRANT ALL privileges on user_role.* to 'user_role'@'localhost';

-- Table: user_management.pg_users

-- DROP TABLE user_management.pg_users;

CREATE TABLE user_role.users
(
    id integer NOT NULL,
    username varchar(50) NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (`id`),
    CONSTRAINT uk_name UNIQUE (`username`)
);

------------------------------
-- Table: user_management.roles

-- DROP TABLE user_management.roles;
CREATE TABLE user_role.roles
(
    id integer NOT NULL,
    role varchar(80) NOT NULL,
    CONSTRAINT pk_roles PRIMARY KEY (`id`),
    CONSTRAINT uk_role UNIQUE (`role`)
);


----------------------------------------


-- Table: user_management.users_roles

-- DROP TABLE user_management.users_roles;

CREATE TABLE user_role.users_roles
(
    user_id integer NOT NULL,
    role_id integer NOT NULL,
    CONSTRAINT pk_user_roles PRIMARY KEY (`role_id`, `user_id`),
    CONSTRAINT fk_role FOREIGN KEY (`role_id`)
        REFERENCES user_role.roles (`id`) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE,
    CONSTRAINT fk_user FOREIGN KEY (`user_id`)
        REFERENCES user_role.users (`id`) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);
