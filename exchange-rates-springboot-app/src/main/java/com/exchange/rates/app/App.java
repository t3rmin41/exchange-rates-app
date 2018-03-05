package com.exchange.rates.app;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import com.exchange.rates.config.ApplicationConfig;
import com.exchange.rates.config.MvcConfig;
import com.exchange.rates.config.SOAPConfig;

@SpringBootApplication
@Import({ApplicationConfig.class, MvcConfig.class, SOAPConfig.class})
public class App { // extends SpringBootServletInitializer {

  private static Logger logger = LoggerFactory.getLogger(App.class);

  //for traditional .war deployment need to extend SpringBootServletInitializer
  //@Override
  //protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
  //    return application.sources(App.class);
  //}
  
  public static void main(String[] args) {
    SpringApplication springApplication = new SpringApplication(App.class);
    ApplicationContext context = springApplication.run(args);
    logger.warn("Context : " + context.getId());
  }
  
}
