package org.bathbranchringing.emailer.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class PersistenceConfig {

	private static final String DIALECT = "org.hibernate.dialect.MySQL5Dialect";
	private static final String SHOW_SQL = "false";
	private static final String AUTO_ACTION = "create-drop";
	private static final String IMPORT_FILES = "sql/staticData.sql";

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan(
				new String[] { "org.bathbranchringing.emailer.core.domain" });
		
		Properties hibernateProperties = new Properties();
		sessionFactory.setHibernateProperties(hibernateProperties);
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", AUTO_ACTION);
		hibernateProperties.setProperty("hibernate.hbm2ddl.import_files", IMPORT_FILES);
		hibernateProperties.setProperty("hibernate.show_sql", SHOW_SQL);
		hibernateProperties.setProperty("hibernate.dialect", DIALECT);
		hibernateProperties.setProperty("hibernate.dialect", DIALECT);
		hibernateProperties.setProperty("hibernate.globally_quoted_identifiers", "true");
		
		return sessionFactory;

	}

	@Bean
	public DataSource dataSource() {
		final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
		dsLookup.setResourceRef(true);
		DataSource dataSource = dsLookup.getDataSource("jdbc/emailerDS");
		return dataSource;
	}

	@Bean
	public HibernateTransactionManager transactionManager() {
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory().getObject());
		return txManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

}
