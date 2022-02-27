package de.leuphana.api_gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class ApiGateway {
	
	public static void main(String[] args) {
		SpringApplication.run(ApiGateway.class);
	}
	
	@RestController
	class ServiceInstanceRestController{
		
		@Autowired
		private DiscoveryClient discoveryClient;
		
		@RequestMapping("/service-instance/{ApplicationService}")
		public @ResponseBody String getClientsByApplicationName(@PathVariable String ApplicationService) {
			return this.discoveryClient.getInstances(ApplicationService).get(0).getUri().toString();
		}
		
	}
	
}