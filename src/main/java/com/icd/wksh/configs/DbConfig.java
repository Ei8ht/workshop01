package com.icd.wksh.configs;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class DbConfig {
    @Value("${app.datasource.mysql.url}")
    private String url;
    @Value("${app.datasource.mysql.username}")
    private String username;
    @Value("${app.datasource.mysql.password}")
    private String password;
    @Value("${app.datasource.mysql.driver-class-name}")
    private String driverName;

    @Bean(name = "msDataSource")
    public DataSource msDataSource() {
        HikariConfig dbConfig = new HikariConfig();
        dbConfig.setJdbcUrl(url);
        dbConfig.setDriverClassName(driverName);
        dbConfig.setUsername(username);
        dbConfig.setPassword(password);
        dbConfig.setAutoCommit(true);
        return new HikariDataSource(dbConfig);
    }

    @Bean(name = "msJdbcTemplate")
    public JdbcTemplate msJdbcTemplate(@Qualifier("msDataSource") DataSource mySqlDataSource) {
        return new JdbcTemplate(mySqlDataSource);
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource mySqlDataSource) {
        return new NamedParameterJdbcTemplate(mySqlDataSource);
    }

    @Bean
    public SimpleJdbcCall simpleJdbcCall(DataSource msDataSource) {
        return new SimpleJdbcCall(msDataSource);
    }

    @Bean(name = "msTransaction")
    public PlatformTransactionManager msTransaction(DataSource msDataSource)  {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(msDataSource);
        return transactionManager;
    }
}
