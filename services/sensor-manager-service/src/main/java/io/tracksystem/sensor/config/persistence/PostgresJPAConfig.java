package io.tracksystem.sensor.config.persistence;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
// import org.springframework.boot.jdbc.DataSourceBuilder;
import java.util.Properties;

@Slf4j
@Configuration
@Profile("dev")
@EnableJpaRepositories(basePackages = {
        "io.tracksystem.sensor.repository"
}, entityManagerFactoryRef = "postgresqlEntityManagerFactory", transactionManagerRef = "postgresqlTransactionManager")
@PropertySource("classpath:application.properties")
public class PostgresJPAConfig {

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    /*
     * @Bean(name = "postgresqlDataSource")
     * public DataSource dataSource() {
     * return DataSourceBuilder.create()
     * .driverClassName(this.driverClassName)
     * .url(this.url)
     * .username(this.username)
     * .password(this.password)
     * .build();
     * }
     */

     @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "postgresqlDataSource")
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(password);
        hikariConfig.setPassword(password);
        hikariConfig.setDriverClassName(driverClassName);

        // Configuraciones específicas de HikariCP:
        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setMinimumIdle(5);
        hikariConfig.setIdleTimeout(30000);
        hikariConfig.setConnectionTimeout(20000);
        hikariConfig.setMaxLifetime(1800000);
        return new HikariDataSource(hikariConfig);
    }

    @Bean(name = "postgresqlEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean postgresqlEntityManagerFactory(
            @Qualifier("postgresqlDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan(getlistPackageToScan());

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        emf.setJpaVendorAdapter(vendorAdapter);

        Properties properties = new Properties();
        properties.put("hibernate.hbm2ddl.auto", "none");
        // properties.put("hibernate.dialect","org.hibernate.dialect.PostgreSQLDialect");

        emf.setJpaProperties(properties);
        emf.setPersistenceUnitName("postgresqlPersistenceUnit");

        return emf;
    }

    @Bean(name = "postgresqlTransactionManager")
    public JpaTransactionManager postgresqlTransactionManager(
            @Qualifier("postgresqlEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
    
    // Declara aqui las entidades de persistencia para acceder a la base de datos
    private String[] getlistPackageToScan() {
        return new String[] {
                "io.tracksystem.sensor.entity"
        };
    }
}
