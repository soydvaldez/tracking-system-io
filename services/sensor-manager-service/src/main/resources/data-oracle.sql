INSERT INTO springboot.sensors (sensor_id, sensor_name, location, installation_date, status)
VALUES 
('SENSOR001', 'DHT22', 'Warehouse A', TO_TIMESTAMP('2024-01-15 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Active'),
('SENSOR002', 'DS18B20', 'Warehouse B', TO_TIMESTAMP('2024-02-10 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Active'),
('SENSOR003', 'BME280', 'Production Floor', TO_TIMESTAMP('2024-03-05 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Inactive'),
('SENSOR004', 'LM35', 'Office', TO_TIMESTAMP('2024-04-20 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Active'),
('SENSOR005', 'Si7021', 'Data Center', TO_TIMESTAMP('2024-05-25 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Active');


-- Podrias crear scripts que apunten a localhost para realizar pruebas de borrado y creacion de scripts de tablas de la base de datos


select * from  springboot.sensors;