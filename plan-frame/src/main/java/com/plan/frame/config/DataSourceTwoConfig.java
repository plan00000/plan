package com.plan.frame.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @Author: linzhihua
 * @Description: 第二数据源
 * @Date: Created in 2020/3/27 16:12
 * @Modified By:
 */
@Configuration
public class DataSourceTwoConfig {
    @Value("${mybatis.config-location}")
    private String mybatis_xml;

    @Value("${mybatis.mapper-locations}")
    private String mapper_locations;

    @Value("${spring.datasource.type}")
    private String dataSourceType;

    @Autowired
    private Environment env;

    @Bean(name="twoDataSource")
    @ConfigurationProperties(prefix="spring.datasource.druid")
    public DataSource twoDataSource(){
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        Properties prop = build(env, "spring.datasource.druid.");
        ds.setXaDataSourceClassName(dataSourceType);
        ds.setPoolSize(5);
        ds.setXaProperties(prop);
        return ds;
    }

    @Bean(name="twoSqlSessionFactory")
    public SqlSessionFactory twoSqlSessionFactory(@Qualifier("twoDataSource")DataSource dataSource) throws Exception{
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        Resource[] configLocations = new PathMatchingResourcePatternResolver().getResources(mybatis_xml);
        if (configLocations == null || configLocations.length != 1) {
            throw new RuntimeException("cannot be resolved to URL because it does not exist");
        }
        bean.setConfigLocation(configLocations[0]);
        Resource[] mapperLocations = new PathMatchingResourcePatternResolver().getResources(mapper_locations);
        if (mapperLocations == null) {
            throw new RuntimeException("cannot be resolved to URL because it does not exist");
        }
        bean.setMapperLocations(mapperLocations);

        return bean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplateTwo(@Qualifier("twoSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory);
        return template;
    }


    private Properties build(Environment env, String prefix) {
        Properties prop = new Properties();
        prop.put("name", env.getProperty(prefix + "two.name"));
        prop.put("url", env.getProperty(prefix + "two.jdbcUrl"));
        prop.put("username", env.getProperty(prefix + "two.username"));
        prop.put("password", env.getProperty(prefix + "two.password"));
        prop.put("driverClassName", env.getProperty(prefix + "two.driverClassName", ""));
        prop.put("filters", env.getProperty(prefix + "filters"));
        prop.put("maxActive", env.getProperty(prefix + "maxActive", Integer.class));
        prop.put("initialSize", env.getProperty(prefix + "initialSize", Integer.class));
        prop.put("maxWait", env.getProperty(prefix + "maxWait", Integer.class));
        prop.put("minIdle", env.getProperty(prefix + "minIdle", Integer.class));
        prop.put("timeBetweenEvictionRunsMillis",
                env.getProperty(prefix + "timeBetweenEvictionRunsMillis", Integer.class));
        prop.put("minEvictableIdleTimeMillis", env.getProperty(prefix + "minEvictableIdleTimeMillis", Integer.class));
        prop.put("validationQuery", env.getProperty(prefix + "validationQuery"));
        prop.put("testWhileIdle", env.getProperty(prefix + "testWhileIdle", Boolean.class));
        prop.put("testOnBorrow", env.getProperty(prefix + "testOnBorrow", Boolean.class));
        prop.put("testOnReturn", env.getProperty(prefix + "testOnReturn", Boolean.class));
        prop.put("poolPreparedStatements", env.getProperty(prefix + "poolPreparedStatements", Boolean.class));
        prop.put("maxOpenPreparedStatements", env.getProperty(prefix + "maxOpenPreparedStatements", Integer.class));

        /*prop.put("keepAlive",env.getProperty(prefix+"keepAlive",Boolean.class));
        prop.put("removeAbandoned",env.getProperty(prefix+"removeAbandoned",Boolean.class));
        prop.put("removeAbandonedTimeout",env.getProperty(prefix+"removeAbandonedTimeout",Integer.class));
        prop.put("logAbandoned",env.getProperty(prefix+"logAbandoned",Boolean.class));*/
        return prop;
    }
}
