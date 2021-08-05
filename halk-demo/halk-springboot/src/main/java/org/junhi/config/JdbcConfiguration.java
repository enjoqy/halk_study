package org.junhi.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


/**
 * @author junhi
 * @date 2019/12/20 16:30
 */
@Configuration
//@EnableConfigurationProperties(JdbcProperties.class)
public class JdbcConfiguration {


//    @Autowired
//    private JdbcProperties jdbcProperties;

    /**
     * 把方法的返回值注入到spring容器
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "jdbc")
    public DataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        return dataSource;
    }
}
