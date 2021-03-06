package com.epam.spring.core;

import com.epam.spring.core.aspects.LoggingAspect;
import com.epam.spring.core.aspects.StatisticsAspect;
import com.epam.spring.core.loggers.CacheFileEventLogger;
import com.epam.spring.core.loggers.CombinedEventLogger;
import com.epam.spring.core.loggers.ConsoleEventLogger;
import com.epam.spring.core.loggers.DBLogger;
import com.epam.spring.core.loggers.EventLogger;
import com.epam.spring.core.loggers.FileEventLogger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.ArrayList;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@PropertySource({"classpath:client.properties", "classpath:loggers.properties"})
public class LoggersConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer pp = new PropertySourcesPlaceholderConfigurer();
        pp.setIgnoreResourceNotFound(true);
        return pp;
    }

    @Bean
    @Qualifier("cacheFileEventLogger")
    public CacheFileEventLogger cacheFileEventLogger() throws Exception {
        return new CacheFileEventLogger(10);
    }

    @Bean
    @Qualifier("fileEventLogger")
    public FileEventLogger fileEventLogger() {
        return new FileEventLogger();
    }

    @Bean
    @Qualifier("consoleEventLogger")
    public ConsoleEventLogger consoleEventLogger() {
        return new ConsoleEventLogger();
    }

    @Bean
    @Qualifier("combinedEventLogger")
    public CombinedEventLogger combinedEventLogger() throws Exception {
        return new CombinedEventLogger(new ArrayList<EventLogger>() {{
            add(cacheFileEventLogger());
            add(consoleEventLogger());
        }});
    }

    @Bean
    public LoggingAspect loggingAspect(){
        return new LoggingAspect();
    }

    @Bean
    public StatisticsAspect statisticsAspect() {
        return new StatisticsAspect();
    }

    @Value("${jdbc.url}")
    String url;

    @Value("${jdbc.driverClassName}")
    String driver;
    @Bean
    public JdbcTemplate jdbcTemplate(){
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(driver);
        driverManagerDataSource.setUrl(url);
        return new JdbcTemplate(driverManagerDataSource);
    }

    @Bean
    public DBLogger dbLogger() {
        return new DBLogger();
    }
}
