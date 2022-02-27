package de.leuphana.article_service.connector.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.leuphana.article_service.component.structure.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, UUID>{
	
}