package de.leuphana.shop_service.component.structure.feignobjects;

import java.math.BigDecimal;
import java.util.UUID;
import javax.persistence.GeneratedValue;

public class ArticleFeignObject {
	
	@GeneratedValue
	private UUID articleId;
	
	private String articleName;
	private String manufactor;
	private BigDecimal price;
	private String articleType;
	private int stockQuantity;
	
	public UUID getArticleId() {return articleId;}
	public void setArticleId(UUID articleId) {this.articleId = articleId;}
	public String getArticleName() {return articleName;}
	public void setArticleName(String articleName) {this.articleName = articleName;}
	public String getManufactor() {return manufactor;}
	public void setManufactor(String manufactor) {this.manufactor = manufactor;}
	public BigDecimal getPrice() {return price;}
	public void setPrice(BigDecimal price) {this.price = price;}
	public String getArticleType() {return articleType;}
	public void setArticleType(String articleType) {this.articleType = articleType;}
	public int getStockQuantity() {return stockQuantity;}
	public void setStockQuantity(int stockQuantity) {this.stockQuantity = stockQuantity;}
}
