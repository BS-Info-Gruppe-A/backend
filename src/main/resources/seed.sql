INSERT INTO customers (id, last_name, first_name, birth_date, gender)
VALUES ('d7726d0e-a42e-4f5a-8be9-e80358f9dd37',
        'Karl',
        'Klose',
        now(),
        'D');

INSERT INTO customers (id, last_name, first_name, birth_date, gender)
VALUES ('92dad020-b9ac-4e6b-9b15-a02128ce2bce',
        'Dietmar',
        'Klinger',
        now(),
        'D');
INSERT INTO customers (id, last_name, first_name, birth_date, gender)
VALUES ('f889d010-3b3d-4517-9694-df6bcc806fba',
        'Norbert',
        'Kar',
        now(),
        'D');

INSERT INTO customers (id, last_name, first_name, birth_date, gender)
VALUES ('0e6cf4ab-ec75-4922-80f2-9e4e23d06ad5',
        'Kienle',
        'Niklas',
        now(),
        'U');

INSERT INTO readings (id, customer_id, read_date, meter_id, meter_type, meter_count, substitute, comment)
VALUES ('92dad020-b9ac-4e6b-9b15-a02128ce2bce',
        '0e6cf4ab-ec75-4922-80f2-9e4e23d06ad5',
        '2023-11-02',
        499,
        'HEIZUNG',
        133.7,
        false,
        'Graf Draculalala');

INSERT INTO readings (id, customer_id, read_date, meter_id, meter_type, meter_count, substitute, comment)
VALUES ('d7726d0e-a42e-4f5a-8be9-e80358f9dd37',
        '0e6cf4ab-ec75-4922-80f2-9e4e23d06ad5',
        '2023-11-03',
        499,
        'WASSER',
        133.7,
        false,
        'Graf Draculalala');
INSERT INTO readings (id, customer_id, read_date, meter_id, meter_type, meter_count, substitute, comment)
VALUES ('f889d010-3b3d-4517-9694-df6bcc806fba',
        '0e6cf4ab-ec75-4922-80f2-9e4e23d06ad5',
        '2023-11-04',
        499,
        'STROM',
        133.7,
        false,
        'Graf Draculalala');