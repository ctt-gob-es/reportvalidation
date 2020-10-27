DROP TABLE IF EXISTS role;
CREATE TABLE role (
    id integer NOT NULL,
    name VARCHAR(32) NOT NULL,
    PRIMARY KEY (id)
);


DROP TABLE IF EXISTS user;
CREATE TABLE user (
  id int(11) NOT NULL,
  active int(11) DEFAULT '1',
  username varchar(255) DEFAULT NULL,
  password varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS user_role;

CREATE TABLE user_role (
  id_user int(11) NOT NULL,
  id_role int(11) NOT NULL,
  PRIMARY KEY (id_user,id_role),
  CONSTRAINT user_role_ibfk_1 FOREIGN KEY (id_user) REFERENCES user (id) ON UPDATE CASCADE,
  CONSTRAINT user_role_ibfk_2 FOREIGN KEY (id_role) REFERENCES role (id) ON UPDATE CASCADE
) ;


INSERT INTO role (id, name) VALUES (1, 'admin');
INSERT INTO user (id, active, password, username) VALUES (1, 1, '$2a$10$rka.WSq6t.6K3PQspxBXh.MPi5RIeqI45tUFrLjRAIM/dMAoeKnPK', 'admin');
INSERT INTO user_role (id_user, id_role) VALUES (1, 1);
