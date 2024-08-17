CREATE TABLE IF NOT EXISTS sensors (
    sensor_id VARCHAR(50) PRIMARY KEY UNIQUE,
    sensor_name VARCHAR(100),
    location VARCHAR(100),
    installation_date timestamp not null,
    status VARCHAR(50)
);

INSERT INTO sensors (sensor_id, sensor_name, location, installation_date, status)
VALUES 
('SENSOR001', 'DHT22', 'Warehouse A','2024-01-15 00:00:00', 'Active'),
('SENSOR002', 'DS18B20', 'Warehouse B','2024-02-10 00:00:00', 'Active'),
('SENSOR003', 'BME280', 'Production Floor','2024-03-05 00:00:00', 'Inactive'),
('SENSOR004', 'LM35', 'Office','2024-04-20 00:00:00', 'Active'),
('SENSOR005', 'Si7021', 'Data Center','2024-05-25 00:00:00', 'Active');