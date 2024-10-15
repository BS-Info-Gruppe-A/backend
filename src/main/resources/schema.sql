DROP TABLE IF EXISTS readings;
DROP TABLE IF EXISTS customers;

DROP TYPE IF EXISTS Gender;
CREATE TYPE Gender AS ENUM ('M', 'W', 'D', 'U');

CREATE TABLE customers
(
    id         uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    last_name  TEXT,
    first_name TEXT,
    birth_date TIMESTAMP,
    gender     Gender
);

DROP TYPE IF EXISTS MeterType;
CREATE TYPE MeterType AS ENUM ('HEIZUNG','STROM','WASSER','UNBEKANNT');

CREATE TABLE readings
(
    id         uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    customer_id uuid NULL REFERENCES customers (id) ON DELETE SET NULL,
    read_date   TIMESTAMP,
    meter_id    INT,
    meter_type  MeterType,
    meter_count DOUBLE,
    substitute  BOOLEAN,
    comment     TEXT
);