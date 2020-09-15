package org.subhashis.microservices.mycurrencyconversionservice.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@RefreshScope
@Profile("dev")
@Configuration
public class DataSourceConfigDev {

    @Value("${spring.datasource.h2.driverClassName}")
    private String h2DriverClassName;
    @Value("${spring.datasource.h2.url}")
    private String h2DataSourceUrl;
    @Value("${spring.datasource.h2.username}")
    private String h2DataSourceUserName;
    @Value("${spring.datasource.h2.password}")
    private String h2DataSourcePassword;

    @Bean(name = "h2DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.h2")
    public DataSource h2DataSource() {
        var h2DataSource = new BasicDataSource();
        h2DataSource.setUrl(h2DataSourceUrl);
        h2DataSource.setUsername(h2DataSourceUserName);
        h2DataSource.setPassword(h2DataSourcePassword);
        h2DataSource.setDriverClassName(h2DriverClassName);
        return h2DataSource;
    }
}

