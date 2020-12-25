package config;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = {"dao"})
@EnableTransactionManagement
public class AppConfig {

    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Bean
    public DataSource dataSource() {
        try {
            EmbeddedDatabaseBuilder dbBuilder = new EmbeddedDatabaseBuilder();
            return dbBuilder.setType(EmbeddedDatabaseType.H2)
                    .addScripts("classpath:sql/schema.sql", "classpath:sql/data.sql")
                    .build();
        } catch (Exception e) {
            logger.error("Embedded DataSource bean cannot be create!", e);
            return null;
        }
    }


    private Properties hibernateProperties() {
        Properties HibernateProperties = new Properties();
        HibernateProperties.put("hibernate.dialect","org.hibernate.dialect.H2Dialect");
        HibernateProperties.put("hibernate.max_fetch_depth", 3);
        HibernateProperties.put("hibernate.jdbc.fetch_size",50);
        HibernateProperties.put("hibernate.jdbc.batch_size",10);
        HibernateProperties.put("hibernate.show_sql",true);
        HibernateProperties.put("hibernate.format_sql",true);
        HibernateProperties.put("hibernate.use_sql_comments",true);
        return HibernateProperties;
    }

    @Bean
    public SessionFactory sessionFactory() {
        return new LocalSessionFactoryBuilder(dataSource())
                .scanPackages("entities")
                .addProperties(hibernateProperties())
                .buildSessionFactory();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new HibernateTransactionManager(sessionFactory());
    }
}
