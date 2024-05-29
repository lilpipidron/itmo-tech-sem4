package ru.kramskoi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.kramskoi.rabbitmq.RabbitConfig;

@Import(RabbitConfig.class)
@SpringBootApplication
public class CatApplication {
    public static void main(String[] args) {
        SpringApplication.run(CatApplication.class, args);
    }
}
