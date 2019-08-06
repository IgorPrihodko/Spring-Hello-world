package com.bytestree.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource(value = { "classpath:application.properties" })
@ComponentScans(value = {
		@ComponentScan("com.bytestree.dao"),
		@ComponentScan("com.bytestree.service")
})
public class RootConfig {

	private final Environment environment;

	@Autowired
	public RootConfig(Environment environment) {
		this.environment = environment;
	}

	@Bean
	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(environment.getRequiredProperty("datasource.driver"));
		dataSource.setUrl(environment.getRequiredProperty("datasource.url"));
		dataSource.setUsername(environment.getRequiredProperty("datasource.username"));
		dataSource.setPassword(environment.getRequiredProperty("datasource.password"));
		return dataSource;
	}

	private Properties getHibernateProperties() {
		Properties properties = new Properties();
		properties.put(AvailableSettings
						.DIALECT, environment
						.getRequiredProperty("hibernate.dialect"));
		properties.put(AvailableSettings
						.SHOW_SQL, environment
						.getRequiredProperty("hibernate.show_sql"));
		properties.put(AvailableSettings
						.STATEMENT_BATCH_SIZE, environment
						.getRequiredProperty("hibernate.batch.size"));
		properties.put(AvailableSettings
						.HBM2DDL_AUTO, environment
						.getRequiredProperty("hibernate.hbm2ddl.auto"));
		properties.put(AvailableSettings
						.CURRENT_SESSION_CONTEXT_CLASS, environment
						.getRequiredProperty("hibernate.current.session.context.class"));
		return properties;
	}
	
	@Bean
	public LocalSessionFactoryBean getSessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(getDataSource());
		sessionFactory.setPackagesToScan("com.bytestree.model");
		sessionFactory.setHibernateProperties(getHibernateProperties());
		return sessionFactory;
	}

	@Bean
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
		hibernateTransactionManager.setSessionFactory(sessionFactory);
		return hibernateTransactionManager;
	}
}
