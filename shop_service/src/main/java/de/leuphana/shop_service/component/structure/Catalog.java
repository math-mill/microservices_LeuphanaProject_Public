package de.leuphana.shop_service.component.structure;

import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Catalog {
	
	@Id
	@GeneratedValue
	@Column(columnDefinition = "BINARY(16)")
	private UUID catalogId;
	
	@ElementCollection(targetClass=UUID.class)
	@Column(columnDefinition = "BINARY(16)")
	private List<UUID> articleIds;
	
	public UUID getCatalogId() {
		return this.catalogId;
	}
	
	public void addArticleToCatalog(UUID articleId) {
		this.articleIds.add(articleId);
	}
	
	public void deleteArticleFromCatalog(UUID articleId) {
		this.articleIds.removeIf(aid -> aid == articleId);
	}

	public List<UUID> getArticleIds() {
		return articleIds;
	}

	public void setArticleIds(List<UUID> articleIds) {
		this.articleIds = articleIds;
	}
	
	
}
