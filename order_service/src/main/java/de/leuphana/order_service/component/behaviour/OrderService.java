package de.leuphana.order_service.component.behaviour;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import de.leuphana.order_service.connector.OrderJPAController;

//##############################################################################################################

@SpringBootApplication
@ComponentScan(basePackages = {"de.leuphana.order_service"})
@EntityScan(basePackages = {"de.leuphana.order_service"})
@EnableJpaRepositories(basePackages = {"de.leuphana.order_service.connector"})
@EnableEurekaClient
public class OrderService {
	
	@Autowired
	OrderJPAController orderJPAController;

//##############################################################################################################
	
	public static void main(String[] args) {
		SpringApplication.run(OrderService.class);
	}
	
}
