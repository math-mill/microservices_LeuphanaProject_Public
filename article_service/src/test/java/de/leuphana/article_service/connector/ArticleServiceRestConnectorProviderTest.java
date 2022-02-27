package de.leuphana.article_service.connector;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import de.leuphana.article_service.component.structure.Article;
import de.leuphana.article_service.component.structure.ArticleType;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@TestPropertySource(locations="classpath:test.properties")
public class ArticleServiceRestConnectorProviderTest {
	
	@InjectMocks
	private ArticleJPAController aJPAcMock = mock(ArticleJPAController.class);
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void getAllArticlesTest() throws Exception {
		Article article1 = new Article();
		article1.setArticleName("TestArticle1");
		article1.setArticleType(ArticleType.BOOK);
		article1.setManufactor("TestManufactor1");
		article1.setPrice(BigDecimal.valueOf(10));
		article1.setStockQuantity(100);
		Article article2 = new Article();
		article2.setArticleName("TestArticle2");
		article2.setArticleType(ArticleType.BOOK);
		article2.setManufactor("TestManufactor2");
		article2.setPrice(BigDecimal.valueOf(20));
		article2.setStockQuantity(200);
		List<Article> articles = new ArrayList<Article>();
		articles.add(article1); articles.add(article2);
		
		when(aJPAcMock.loadAllArticlesFromDB()).thenReturn(articles);
		
		mockMvc.perform(get("/article_service/rest")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
}
