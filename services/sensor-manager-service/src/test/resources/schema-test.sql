CREATE TABLE IF NOT EXISTS sensors (
    sensor_id VARCHAR(50) PRIMARY KEY UNIQUE,
    sensor_name VARCHAR(100),
    location VARCHAR(100),
    installation_date timestamp not null,
    status VARCHAR(50)
);

TRUNCATE TABLE sensors;