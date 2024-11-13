--- users ----
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY NOT NULL,
    email character varying(255) UNIQUE,
    name character varying(255),
    create_time timestamp(6) with time zone,
    version_id character varying(100)
);

--- users_addresses ----
CREATE TABLE IF NOT EXISTS users_addresses (
    id SERIAL PRIMARY KEY NOT NULL,
    city character varying(255),
    address character varying(255),
    user_id  BIGINT,
    create_time timestamp(6) with time zone,
    version_id character varying(100)
);


--- users_roles ----
CREATE TABLE IF NOT EXISTS users_roles (
    id SERIAL PRIMARY KEY NOT NULL,
    role character varying(255) NOT NULL,
    create_time timestamp(6) with time zone,
    version_id character varying(100)
);

--- users_roles_join ----
CREATE TABLE IF NOT EXISTS users_roles_join (
    id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY(id, role_id),
    CONSTRAINT vkawad3tmplm8jkp5ykab4cyhg3
          FOREIGN KEY(id)
    	  REFERENCES users(id),
    CONSTRAINT vkpo1nilhx367cevbqvppjg8oah
          FOREIGN KEY(role_id)
          REFERENCES users_roles(id)
);