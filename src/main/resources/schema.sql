DROP TABLE IF EXISTS reservation;

CREATE TABLE reservation (
    id INTEGER NOT NULL AUTO_INCREMENT,
    date DATE NOT NULL,
    time TIME NOT NULL,
    name VARCHAR(20) NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS theme;

CREATE TABLE theme (
     id INTEGER NOT NULL AUTO_INCREMENT,
     name VARCHAR(100) NOT NULL,
     desc TEXT NOT NULL,
     price INTEGER NOT NULL,
     PRIMARY KEY (id)
);

DROP TABLE IF EXISTS schedule;

CREATE TABLE schedule (
    id INTEGER NOT NULL AUTO_INCREMENT,
    themeId INTEGER NOT NULL,
    date DATE NOT NULL,
    time TIME NOT NULL,
    PRIMARY KEY (id),
    foreign key (themeId) references theme (id)
);