CREATE TABLE sensors (
  sensor_id VARCHAR2(50) PRIMARY KEY,
  sensor_name VARCHAR2(100),
  location VARCHAR2(100),
  installation_date TIMESTAMP,
  status VARCHAR2(50)
);