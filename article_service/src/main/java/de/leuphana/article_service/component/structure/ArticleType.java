package de.leuphana.article_service.component.structure;

public enum ArticleType {
	BOOK("book"),
	CD("cd"),
	BLURAY("bluray");
	
	private String displayName;
	
	ArticleType(String displayName){
		this.displayName = displayName;
	}
	
	public String getDisplayName() {
		return this.displayName;
	}
	
    public static ArticleType fromString(String text) {
        for (ArticleType at : ArticleType.values()) {
            if (at.displayName.equalsIgnoreCase(text)) {
                return at;
            }
        }
        return null;
    }
}
