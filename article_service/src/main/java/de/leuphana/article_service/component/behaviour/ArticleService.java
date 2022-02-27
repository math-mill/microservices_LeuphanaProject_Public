package de.leuphana.article_service.component.behaviour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//##############################################################################################################

@SpringBootApplication
@ComponentScan(basePackages = "de.leuphana.article_service")
@EntityScan(basePackages = "de.leuphana.article_service")
@EnableJpaRepositories(basePackages = {"de.leuphana.article_service.connector"})
@EnableDiscoveryClient
public class ArticleService {

//##############################################################################################################
	
	public static void main(String[] args) {
		SpringApplication.run(ArticleService.class);
	}

}
