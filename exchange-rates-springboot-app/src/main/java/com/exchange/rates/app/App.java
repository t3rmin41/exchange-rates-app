package com.exchange.rates.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import com.exchange.rates.config.ApplicationConfig;
import com.exchange.rates.config.MvcConfig;
import com.exchange.rates.config.SOAPConfig;

@SpringBootApplication
@Import({ApplicationConfig.class, MvcConfig.class, SOAPConfig.class})
public class App {

  private static Logger logger = LoggerFactory.getLogger(App.class);

  public static void main(String[] args) {
    SpringApplication springApplication = new SpringApplication(App.class);
    ApplicationContext context = springApplication.run(args);
    logger.warn("Context : " + context.getId());
  }
  
}
