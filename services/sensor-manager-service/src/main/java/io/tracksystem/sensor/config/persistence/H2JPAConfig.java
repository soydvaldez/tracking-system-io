package io.tracksystem.sensor.config.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@Profile({ "test" })
@EnableJpaRepositories(basePackages = {
        "io.tracksystem.sensor.repository"
}, entityManagerFactoryRef = "H2EntityManagerFactory", transactionManagerRef = "H2TransactionManager")
@PropertySource("classpath:application-dev.properties")
public class H2JPAConfig {
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "H2DataSource")
    @Primary
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(driverClassName);
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);

        // Configuraciones específicas de HikariCP:
        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setMinimumIdle(5);
        hikariConfig.setIdleTimeout(30000);
        hikariConfig.setConnectionTimeout(20000);
        hikariConfig.setMaxLifetime(1800000);
        return new HikariDataSource(hikariConfig);
    }

    /*
     * @Bean(name = "H2DataSource")
     * 
     * @Primary
     * public DataSource dataSource() {
     * return DataSourceBuilder.create()
     * .driverClassName(this.driverClassName)
     * .url(this.url)
     * .username(this.username)
     * .password(this.password)
     * .build();
     * }
     */

    @Primary
    @Bean(name = "H2EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier("H2DataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan(getlistPackageToScan());

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());
        em.setPersistenceUnitName("H2PersistenceUnit");
        return em;
    }

    
    private String[] getlistPackageToScan() {
        return new String[] {
                "io.tracksystem.sensor.entity"
        };
    }

    private Properties additionalProperties() {
        java.util.Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        return properties;
    }

    @Bean(name = "H2TransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("H2EntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    // Clase de configuracion para crear e insertar datos en una tabla en H2, mediante scripts.
    @Configuration
    class DatabaseInitializer {

        @Autowired
        private DataSource dataSource;

        @PostConstruct
        public void init() {
            ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
            databasePopulator.addScripts(new ClassPathResource("schema.sql"), new ClassPathResource("data.sql"));
            try {
                Connection connection = dataSource.getConnection();
                databasePopulator.populate(connection);
                connection.close();
            } catch (ScriptException scriptException) {
                log.error("ScriptException: {}", scriptException.getMessage());
            } catch (SQLException sqlException) {
                log.error("SQLException: {}", sqlException.getMessage());
            }
        }
    }
}
