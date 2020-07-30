package com.ex;

import java.util.HashMap;
import java.util.Locale;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.ex.model.Particular;
import com.ex.model.PostalAddress;
import com.ex.model.financial.Credit;
import com.ex.model.financial.Debit;
import com.ex.model.financial.JournalEntry;
import com.ex.model.payment.Cheque;
import com.ex.model.payment.ModeOfPayment;
import com.ex.model.voucher.CashVoucher;
import com.ex.model.voucher.ChequeVoucher;
import com.ex.model.voucher.PettyCashVoucher;
import com.ex.model.voucher.Voucher;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "db1entityManagerFactory", transactionManagerRef = "db1transactionManager", basePackages = {
		"com.ex.repo" })
public class Database1Config {

	@Bean
	@Primary
	@ConfigurationProperties("spring.datasource")
	public DataSourceProperties db1DataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource.configuration")
	public DataSource db1dataSource() {
		return db1DataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}

	@Primary
	@Bean(name = "db1entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean db1entityManagerFactory(EntityManagerFactoryBuilder builder) {
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", "update");
		properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");

		LocalContainerEntityManagerFactoryBean em = builder.dataSource(db1dataSource()).packages(CashVoucher.class,
				ChequeVoucher.class, Particular.class, PettyCashVoucher.class, Voucher.class,
				// newly added classes
				PostalAddress.class, Cheque.class, ModeOfPayment.class, JournalEntry.class, Debit.class, Credit.class)
				.build();
		em.setJpaPropertyMap(properties);

		return em;
	}

	@Primary
	@Bean(name = "db1transactionManager")
	public PlatformTransactionManager db1transactionManager(
			@Qualifier("db1entityManagerFactory") LocalContainerEntityManagerFactoryBean db1entityManagerFactory) {
		return new JpaTransactionManager(db1entityManagerFactory.getObject());
	}

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
		sessionLocaleResolver.setDefaultLocale(new Locale("en", "ph"));
		return sessionLocaleResolver;
	}
}
