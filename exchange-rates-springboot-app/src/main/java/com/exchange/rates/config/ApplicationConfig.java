package com.exchange.rates.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.exchange.rates.service", "com.exchange.rates.mapper", "com.exchange.rates.errorhandling", "com.exchange.rates.soapclient"})
public class ApplicationConfig {

}
