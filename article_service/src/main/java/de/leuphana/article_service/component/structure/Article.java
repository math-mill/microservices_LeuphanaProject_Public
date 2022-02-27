package de.leuphana.article_service.component.structure;

import java.math.BigDecimal;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;

//##############################################################################################################

@Entity
public class Article {

	@Id
	@GeneratedValue
	@Column(columnDefinition = "BINARY(16)")
	private UUID articleId;
	
	private String articleName;
	private String manufactor;
	private BigDecimal price;
	private ArticleType articleType;
	private int stockQuantity;

//##############################################################################################################

	public UUID getArticleId() {
		return this.articleId;
	}
	
	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}
	
	public String getArticleName() {
		return this.articleName;
	}
	
	public void setManufactor(String manufactor) {
		this.manufactor = manufactor;
	}
	
	public String getManufactor() {
		return this.manufactor;
	}
	
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public BigDecimal getPrice() {
		return this.price;
	}
	
	public void setArticleType(ArticleType articleType) {
		this.articleType = articleType;
	}
	
	public ArticleType getArticleType() {
		return this.articleType;
	}
	
	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
	
	public int getStockQuantity() {
		return this.stockQuantity;
	}
	
}
