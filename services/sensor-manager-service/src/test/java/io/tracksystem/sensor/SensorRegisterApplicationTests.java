package io.tracksystem.sensor;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import io.tracksystem.sensor.dto.request.SensorRequestDTO;
import io.tracksystem.sensor.entity.SensorEntity;
import io.tracksystem.sensor.repository.SensorRepository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import io.tracksystem.sensor.utils.DateUtils;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
// @ContextConfiguration(classes = { NewSensorAddedConfig.class })
class SensorRegisterApplicationTests {

	@Autowired
	private SensorRepository sensorRepository;

	@Value("${persistence.unit.name}")
	private String persistenceUnitName;

	@PersistenceUnit(unitName = "H2EntityManagerFactory")
	private EntityManagerFactory entityManagerFactory;

	@Autowired
	private DataSource dataSource;

	@BeforeEach
	void initDatabase() {
		String scriptSchema = "schema-test.sql";
		String scriptData = "data-test.sql";

		log.info("Inicializando la base de datos: {}", persistenceUnitName);

		try (Connection conn = dataSource.getConnection()) {
			log.info("Execute script: {}", scriptSchema);
			ScriptUtils.executeSqlScript(conn, new ClassPathResource(scriptSchema));
			log.info("Execute script: {}", scriptData);
			ScriptUtils.executeSqlScript(conn, new ClassPathResource(scriptData));
			conn.close();
		} catch (SQLException e) {
			log.error("SQLException: {}", e.getMessage());
			throw new RuntimeException("Error resetting database", e);
		}
	}

	@Test
	void shouldReturnASensorListAndObjectsShouldNotBeEmpty() {
		log.info("Running Test: {}","shouldReturnASensorListAndObjectsShouldNotBeEmpty");
		List<SensorEntity> sensors = sensorRepository.findAll();

		for (SensorEntity sensorEntity : sensors) {
			log.info("{}", sensorEntity);
		}
	}

	@Test
	@DirtiesContext
	void shouldReturnAListOfSensorsWhenItIsListed() {
		log.info("Running Test: {}", "shouldReturnAListOfSensorsWhenItIsListed");
		List<SensorEntity> sensorList = sensorRepository.findAll();

		long total = sensorList.size();
		assertThat(total).isEqualTo(5L);

		// 'SENSOR001', 'DHT22', 'Warehouse A','2024-01-15 00:00:00', 'Active'

		assertNotNull(sensorList.get(0).getSensorId());
		assertThat(sensorList.get(0).getSensorId()).isEqualTo("SENSOR001");
		assertThat(sensorList.get(0).getSensorName()).isEqualTo("DHT22");
		assertThat(sensorList.get(0).getLocation()).isEqualTo("Warehouse A");
		assertThat(sensorList.get(0).getInstallationDate())
				.isEqualTo(DateUtils.convertStringToLocalDateTime("2024-01-15 00:00:00"));
		assertThat(sensorList.get(0).getStatus()).isEqualTo("Active");

		// Otras pruebas podrias hacer casos de uso y casos de uso especiales para
		// realizar pruebas
	}

	@Test
	void shouldReturnASensorWhenSensorIdIsProvided() {
		log.info("Running Test: {}", "shouldReturnASensorWhenSensorIdIsProvided");
		Optional<SensorEntity> sensorFinded = sensorRepository.findById("SENSOR005");

		Assertions.assertTrue(sensorFinded.isPresent());
		SensorEntity sensorFromDB = sensorFinded.get();

		assertThat(sensorFromDB.getSensorId()).isEqualTo("SENSOR005");
	}

	@Test
	void shouldNotReturnASensorWhenIdDoesNotExist() {
		log.info("Running Test: {}", "shouldNotReturnASensorWhenIdDoesNotExist");
		Optional<SensorEntity> sensorOptional = sensorRepository.findById("SENSOR0010_NOT_EXISTS");
		Assertions.assertTrue(sensorOptional.isEmpty());
	}

	@Test
	void shouldReturnASensorWhenItIsSaved() {
		log.info("Running Test: {}", "shouldReturnASensorWhenItIsSaved");
		LocalDateTime currentLocalDateTime = DateUtils.getCurrentLocalDateTime();

		SensorRequestDTO newSensor = new SensorRequestDTO();
		newSensor.setSensorId("SENSOR006");
		newSensor.setSensorName("DHT22");
		newSensor.setLocation("Warehouse A");
		newSensor.setInstallationDate(currentLocalDateTime);
		newSensor.setStatus("Active");

		// Mapeo de DTO a Entity
		SensorEntity sensorEntity = mapperSensorRequestDTOtoEntity(newSensor);

		// Respuesta del servidor
		SensorEntity sensorSaved = sensorRepository.saveAndFlush(sensorEntity);

		assertNotNull(sensorSaved.getSensorId());
		assertThat(sensorEntity.getSensorId()).isEqualTo("SENSOR006");
		assertThat(sensorEntity.getSensorName()).isEqualTo("DHT22");
		assertThat(sensorEntity.getLocation()).isEqualTo("Warehouse A");
		assertThat(sensorEntity.getInstallationDate()).isEqualTo(currentLocalDateTime);
		assertThat(sensorEntity.getStatus()).isEqualTo("Active");
	}

	public SensorEntity mapperSensorRequestDTOtoEntity(SensorRequestDTO sensorRequestDTO) {
		SensorEntity entity = new SensorEntity();

		entity.setSensorId(sensorRequestDTO.getSensorId());
		entity.setSensorName(sensorRequestDTO.getSensorName());
		entity.setLocation(sensorRequestDTO.getLocation());
		entity.setInstallationDate(sensorRequestDTO.getInstallationDate());
		entity.setStatus(sensorRequestDTO.getStatus());

		return entity;
	}
}
