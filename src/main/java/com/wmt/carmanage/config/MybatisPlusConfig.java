package com.wmt.carmanage.config;

import com.baomidou.mybatisplus.entity.DefaultMetaObjectHandler;
import com.baomidou.mybatisplus.mapper.ISqlInjector;
import com.baomidou.mybatisplus.mapper.LogicSqlInjector;
import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybaits-plus配置
 */
@Configuration
@MapperScan("com.erp.autohome.mapper")
public class MybatisPlusConfig {

    /**
     * mybatis-plus SQL执行效率插件【生产环境可以关闭】
     */
    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean
    public MetaObjectHandler metaObjectHandler(){
        return new DefaultMetaObjectHandler();
    }

    /**
     * 注入sql注入器
     */
    @Bean
    public ISqlInjector sqlInjector(){
        return new LogicSqlInjector();
    }

   /* @Bean
    @ConfigurationProperties(prefix = "mybatis-plus.global-config")
    public GlobalConfiguration globalConfiguration() {
        GlobalConfiguration conf = new GlobalConfiguration(new LogicSqlInjector());

        return conf;
    }

//    @ConfigurationProperties(prefix = "mybatis-plus")
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource,
                                               GlobalConfiguration globalConfiguration) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resourceLoader = new PathMatchingResourcePatternResolver();
        sqlSessionFactory.setMapperLocations(resourceLoader.getResources("classpath:/mapper/*Mapper.xml"));
//        mybatis-plus.typeAliasesPackage=com.tongwei.breed.entity
        sqlSessionFactory.setTypeAliasesPackage("com.tongwei.breed.entity");
        sqlSessionFactory.setTypeEnumsPackage("com.tongwei.breed.constant");

        OptimisticLockerInterceptor optLock = new OptimisticLockerInterceptor();
        sqlSessionFactory.setPlugins(new Interceptor[]{optLock});
        sqlSessionFactory.setGlobalConfig(globalConfiguration);
        return sqlSessionFactory.getObject();
    }*/





}
