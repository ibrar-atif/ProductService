
package com.comtool.product.config;


import static com.comtool.product.constant.ApplicationConstant.ENTITYMANAGER_FACTORY;
import static com.comtool.product.constant.ApplicationConstant.TRANSACTION_MANAGER;

import java.util.Optional;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
 


@Configuration
@EnableAspectJAutoProxy
@EnableJpaRepositories(basePackages = "com.comtool.product.repository", entityManagerFactoryRef = ENTITYMANAGER_FACTORY, transactionManagerRef = TRANSACTION_MANAGER)
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class DataBaseConfig {

	@Value("${comtool.entites.base.package}")
	private String basePackages;

	@Value("${comtool.datasource.driver-class-name}")
	private String hnDriverClassName;

	@Value("${comtool.datasource.url}")
	private String hnDataSourceUrl;

	@Value("${comtool.datasource.username}")
	private String hnUsername;

	@Value("${comtool.datasource.password}")
	private String hnPassword;

	/**
	 * 
	 */
	@Value("${comtool.hibernate.show.sql}")
	private Boolean showSQLQueries;

	/**
	 * 
	 */
	@Value("${comtool.persistenceUnit}")
	private String persistenceUnit;

	@Primary
	@Bean(name = "comtoolJpaVendorAdapter")
	public JpaVendorAdapter jpaVendorAdapter() {

		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.MYSQL);
		adapter.setShowSql(showSQLQueries);
		
		return adapter;
	}

	@Primary
	@Bean(name = "dataSource")
	public DataSource comtoolDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(hnDriverClassName);
		dataSource.setUrl(hnDataSourceUrl);
		dataSource.setUsername(hnUsername);
		dataSource.setPassword(hnPassword);
		return dataSource;
	}


	@Primary
	@Bean(name = ENTITYMANAGER_FACTORY)
	public LocalContainerEntityManagerFactoryBean localEntityManagerFactory() {

		LocalContainerEntityManagerFactoryBean managerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		managerFactoryBean.setDataSource(comtoolDataSource());
		managerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());
		managerFactoryBean.setPackagesToScan(basePackages);
		managerFactoryBean.setPersistenceUnitName(persistenceUnit);
		Properties properties = new Properties();
		properties.put("spring.jpa.properties.hibernate.enable_lazy_load_no_trans", true);
		properties.put("hibernate.hbm2ddl.auto", "update");
		
		properties.put("spring.jpa.properties.hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		managerFactoryBean.setJpaProperties(properties);
		return managerFactoryBean;
	}


	@Primary
	@Bean(name = TRANSACTION_MANAGER)
	public PlatformTransactionManager jpaTransactionManager(
			@Autowired @Qualifier(ENTITYMANAGER_FACTORY) EntityManagerFactory entityManagerFactory) {

		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);

		return transactionManager;
	}


	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	@Bean("auditorAware")
	public AuditorAware<String> auditorProvider() {
		return () -> Optional.ofNullable("system");
	}


}
