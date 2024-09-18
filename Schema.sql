DROP DATABASE IF EXISTS backend;
CREATE DATABASE backend;

DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS reading;

DROP TYPE IF EXISTS Gender;
CREATE TYPE Gender AS ENUM ('M', 'W', 'D', 'U');

CREATE TABLE customers (
    id INT              PRIMARY KEY AUTO_INCREMENT,
    last_name TEXT,
    first_name TEXT,
    birth_date TIMESTAMP,
    gender Gender,
);

DROP TYPE IF EXISTS MeterType;
CREATE TYPE IF NOT EXISTS MeterType AS ENUM ('HEIZUNG','STROM','WASSER','UNBEKANNT');

CREATE TABLE readings (
    id INT             PRIMARY KEY AUTO_INCREMENT,
    customer_id INT   REFERENCES customers (id),
    read_date TIMESTAMP,
    meter_id INT,
    meter_type MeterType,
    meter_count INT,
    substitute BOOLEAN,
    comment TEXT,
);