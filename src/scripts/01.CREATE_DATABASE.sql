DROP TABLE IF EXISTS role;
CREATE TABLE role (
    id integer NOT NULL,
    name VARCHAR(32) NOT NULL,
    PRIMARY KEY (id)
);


DROP TABLE IF EXISTS user;

CREATE TABLE user (
  id int(11) NOT NULL AUTO_INCREMENT,
  active int(11) DEFAULT '1',
  username varchar(255) DEFAULT NULL,
  password varchar(255) DEFAULT NULL,
  name varchar(255) DEFAULT NULL,
  surname varchar(255) DEFAULT NULL,
  email varchar(255) DEFAULT NULL,
  ura varchar(255) DEFAULT NULL,
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
INSERT INTO role (id, name) VALUES (2, 'ura');
INSERT INTO user (id, active, password, username) VALUES (1, 1, '$2a$10$rka.WSq6t.6K3PQspxBXh.MPi5RIeqI45tUFrLjRAIM/dMAoeKnPK', 'admin');
INSERT INTO user_role (id_user, id_role) VALUES (1, 1);


DROP TABLE IF EXISTS dir3;

CREATE TABLE dir3(
	C_ID_UD_ORGANICA varchar(255),
	C_DNM_UD_ORGANICA varchar(255),
	C_ID_NIVEL_ADMON varchar(255),
	C_ID_AMB_PROVINCIA varchar(255),
	C_DESC_PROV varchar(255),
	C_ID_DEP_UD_SUPERIOR varchar(255),
	C_DNM_UD_ORGANICA_SUPERIOR varchar(255),
	C_ID_DEP_UD_PRINCIPAL varchar(255),
	C_DNM_UD_ORGANICA_PRINCIPAL varchar(255)
);

ALTER TABLE dir3
  ADD PRIMARY KEY (C_ID_UD_ORGANICA),
  ADD KEY C_ID_UD_ORGANICA (C_ID_UD_ORGANICA),
  ADD KEY C_ID_NIVEL_ADMON (C_ID_NIVEL_ADMON);
  
  
DROP TABLE IF EXISTS files;
  
CREATE TABLE files (
  id varchar(255) NOT NULL,
  data longblob,
  name varchar(255) DEFAULT NULL,
  type varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
) ;


