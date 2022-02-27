package de.leuphana.shop_service.connector.feign;

import java.util.List;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import de.leuphana.shop_service.component.structure.feignobjects.ArticleFeignObject;

@FeignClient(value="ArticleFeignClient", url="${custom.feignClient.articleURL}")
public interface ArticleFeignClient {
	
	@RequestMapping(value="/{articleId}", method=RequestMethod.GET)
	public ArticleFeignObject getArticleById(@PathVariable(value="articleId") UUID articleId);
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public List<ArticleFeignObject> getAllArticles();
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	public ArticleFeignObject createNewArticle(@RequestBody ArticleFeignObject articleFeignObejct);
	
	@RequestMapping(value="/{articleId}", method=RequestMethod.PUT)
	public ArticleFeignObject replaceArticleById(@RequestBody ArticleFeignObject articleFeignObejct, @PathVariable(value="articleId") UUID articleId);
	
	@RequestMapping(value="/{articleId}", method=RequestMethod.DELETE)
	public void deleteArticle(@PathVariable(value="articleId") UUID articleId);
}
