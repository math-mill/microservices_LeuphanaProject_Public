package de.leuphana.customer_service.component.behaviour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//##############################################################################################################

@SpringBootApplication
@ComponentScan(basePackages = {"de.leuphana.customer_service"})
@EntityScan(basePackages = {"de.leuphana.customer_service"})
@EnableJpaRepositories(basePackages = {"de.leuphana.customer_service.connector"})
@EnableDiscoveryClient
public class CustomerService {

//##############################################################################################################
	
	public static void main(String[] args) {
		SpringApplication.run(CustomerService.class);
	}

}
