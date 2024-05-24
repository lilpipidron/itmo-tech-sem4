package ru.kramskoi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({CatWebClientConf.class, HttpCatClientConf.class})
public class CatClientConf {}
