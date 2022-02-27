package de.leuphana.article_service.connector;

import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import de.leuphana.article_service.component.behaviour.exceptionhandling.ArticleNotFoundException;
import de.leuphana.article_service.component.structure.Article;
import de.leuphana.article_service.connector.repository.ArticleRepository;

//##############################################################################################################

@Component
public class ArticleJPAController {

	@Autowired
	private ArticleRepository articleRepository;
	
	@Transactional
	public Article saveArticleToDB(Article article) {
		return articleRepository.save(article);
	}
	
	@Transactional
	public Article loadArticleFromDB(UUID articleId) {
		if(articleRepository.findById(articleId).isPresent()) {
			return articleRepository.findById(articleId).get();
		}else {
			return articleRepository.findById(articleId).orElseThrow(() -> new ArticleNotFoundException(articleId));
		}
	}
	
	@Transactional
	public List<Article> loadAllArticlesFromDB(){
		return articleRepository.findAll();
	}
	
	@Transactional
	public void deleteArticleFromDB(UUID articleId) {
		if(articleRepository.findById(articleId).isPresent()) {
			articleRepository.deleteById(articleId);
		}else {
			articleRepository.findById(articleId).orElseThrow(() -> new ArticleNotFoundException(articleId));
		}
	}
	
}
