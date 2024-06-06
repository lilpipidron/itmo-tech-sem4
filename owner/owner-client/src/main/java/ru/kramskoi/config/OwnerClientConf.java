package ru.kramskoi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({OwnerWebClientConf.class, HttpOwnerClientConf.class})
public class OwnerClientConf {
}
