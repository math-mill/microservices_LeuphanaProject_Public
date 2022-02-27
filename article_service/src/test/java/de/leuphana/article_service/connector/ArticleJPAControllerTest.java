package de.leuphana.article_service.connector;

import static org.junit.Assert.assertEquals;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import de.leuphana.article_service.component.behaviour.ArticleService;
import de.leuphana.article_service.component.behaviour.exceptionhandling.ArticleNotFoundException;
import de.leuphana.article_service.component.structure.Article;
import de.leuphana.article_service.component.structure.ArticleType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {ArticleService.class})
public class ArticleJPAControllerTest {
	
	@Autowired
	private ArticleJPAController aJPAc;
	
	private int articleCount = 0;
	
	public Article createTestArticle() {
		Article article = new Article();
		article.setArticleName("TestArticle1");
		article.setArticleType(ArticleType.BOOK);
		article.setManufactor("TestManufactor1");
		article.setPrice(BigDecimal.valueOf(10));
		article.setStockQuantity(100);
		articleCount++;
		return article;
	}
	
	@Test
	public void saveArticleToDB() {
		Article articleToSave = this.createTestArticle();
		Article articleSaved = aJPAc.saveArticleToDB(articleToSave);
		assertEquals(articleSaved, articleToSave);
	}
	
	@Test
	public void getAllArticles() {
		for(int i = 0; i < 7; i++) {
			aJPAc.saveArticleToDB(this.createTestArticle());
		}
		List<Article> articlesSaved = aJPAc.loadAllArticlesFromDB();
		assertEquals(articlesSaved.size() - 1, articleCount);
	}
	
	@Test
	public void loadArticleById() {
		Article articleToSave = aJPAc.saveArticleToDB(this.createTestArticle());
		Article articleLoaded = aJPAc.loadArticleFromDB(articleToSave.getArticleId());
		assertEquals(articleToSave.getArticleId(), articleLoaded.getArticleId());
	}
	
	@Test
	public void deleteArticleById() {
		boolean thrown = false;
		Article article = aJPAc.saveArticleToDB(this.createTestArticle());
		aJPAc.deleteArticleFromDB(article.getArticleId());
		try {
			aJPAc.loadArticleFromDB(article.getArticleId());
		} catch (ArticleNotFoundException e){
			thrown = true;
		}
		assertEquals(thrown, true);
	}
	
}
