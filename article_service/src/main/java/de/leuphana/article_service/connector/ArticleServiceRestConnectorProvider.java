package de.leuphana.article_service.connector;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import de.leuphana.article_service.component.behaviour.exceptionhandling.ArticleNotFoundException;
import de.leuphana.article_service.component.structure.Article;

@RestController
public class ArticleServiceRestConnectorProvider {
	
	private static final String serviceURL = "${service.URL}";
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@Autowired
	private ArticleJPAController articleJPAController;
	
	@GetMapping(serviceURL)
	public List<Article> getAllArticles() {
		return articleJPAController.loadAllArticlesFromDB();
	}
	
	@GetMapping(serviceURL + "/{articleId}")
	public Article getArticleById(@PathVariable UUID articleId) {
		return articleJPAController.loadArticleFromDB(articleId);
	}
	
	@PostMapping(serviceURL)
	public void createNewArticle(@RequestBody Article newArticle) {
		articleJPAController.saveArticleToDB(newArticle);
	}
	
	@PutMapping(serviceURL + "/{articleId}")
	public Article replaceArticleById(@RequestBody Article newArticle, @PathVariable UUID articleId) {
		try {
			Article article = articleJPAController.loadArticleFromDB(articleId);
			article.setArticleName(newArticle.getArticleName());
			article.setManufactor(newArticle.getManufactor());
			article.setPrice(newArticle.getPrice());
			article.setArticleType(newArticle.getArticleType());
			article.setStockQuantity(newArticle.getStockQuantity());
			articleJPAController.saveArticleToDB(article);
			return article;
		} catch (ArticleNotFoundException e) {
			articleJPAController.saveArticleToDB(newArticle);
			return newArticle;
		}
	}
	
	@DeleteMapping(serviceURL + "/{articleId}")
	public void deleteArticleById(@PathVariable UUID articleId) {
		articleJPAController.deleteArticleFromDB(articleId);
	}
	
	@RequestMapping("/service-instance/{ApplicationService}")
	public @ResponseBody String getClientsByApplicationName(@PathVariable String ApplicationService) {
		return this.discoveryClient.getInstances(ApplicationService).get(0).getUri().toString();
	}

}