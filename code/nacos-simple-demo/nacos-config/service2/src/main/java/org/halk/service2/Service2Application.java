package org.halk.service2;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author halk
 * @Date 2021/1/25 17:05
 */
@SpringBootApplication
@RestController
public class Service2Application {

    public static void main(String[] args) {
        SpringApplication.run(Service2Application.class);
    }

    @Autowired
    ConfigurableApplicationContext applicationContext;

    //+ applicationContext.getDisplayName() + "\r\t\n "+ applicationContext.getId()+ "\r\t\n " + applicationContext.getEnvironment().getSystemEnvironment()+ "\r\t\n " + applicationContext.getEnvironment().getProperty("common.name")
    @GetMapping("/config")
    public String getName(){
        return applicationContext.getEnvironment().getProperty("common.name");
    }

    @GetMapping("/config2")
    public String getName2(){
        return applicationContext.getEnvironment().getProperty("default") + "</br>" +
                applicationContext.getEnvironment().getProperty("global") + "</br>" +
                applicationContext.getEnvironment().getProperty("refresh");
    }
}
