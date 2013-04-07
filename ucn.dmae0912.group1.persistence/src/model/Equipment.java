package model;

public class Equipment extends Product {

	String type;
	String description;
	
	public Equipment(String type, String description) {
		super();
		this.type = type;
		this.description = description;
	}
	
	public Equipment() {
		
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
}
