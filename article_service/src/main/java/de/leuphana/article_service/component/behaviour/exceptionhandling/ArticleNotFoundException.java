package de.leuphana.article_service.component.behaviour.exceptionhandling;

import java.util.UUID;

@SuppressWarnings("serial")
public class ArticleNotFoundException extends RuntimeException{
	
	public ArticleNotFoundException(UUID articleId){
		super("Article not found (" + articleId + ").");
	}
}
