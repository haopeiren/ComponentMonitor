package com.ffcs.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * MyBatis配置类
 **/
@Configuration
@MapperScan(value = "com.ffcs.mapper")
public class MyBatisConfig {
    /**
     * SqlSessionFactory
     *
     * @param dataSource    数据源
     * @return  SqlSessionFactory
     * @throws Exception 初始化失败时抛异常
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setConfiguration(new org.apache.ibatis.session.Configuration());
        Resource[] mappers = resolver.getResources("classpath:mapper/*");
        factoryBean.setMapperLocations(mappers);
        factoryBean.setDataSource(dataSource);
        factoryBean.setTypeAliasesPackage("com.ffcs.model");
        return factoryBean.getObject();
    }
}
