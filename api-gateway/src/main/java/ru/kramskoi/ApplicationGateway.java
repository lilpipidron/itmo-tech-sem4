package ru.kramskoi;

import org.apache.catalina.security.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.kramskoi.config.CatClientConf;
import ru.kramskoi.config.OwnerClientConf;
import ru.kramskoi.rabbitmq.RabbitConfig;

@Import({
        SecurityConfig.class,
        RabbitConfig.class,
        OwnerClientConf.class,
        CatClientConf.class
})
@SpringBootApplication
public class ApplicationGateway {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationGateway.class, args);
    }
}
