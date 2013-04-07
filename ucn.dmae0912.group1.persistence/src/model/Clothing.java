package model;

public class Clothing extends Product {
	
	String size;
	String color;
	
	public Clothing(String size, String color) {
		super();
		this.size = size;
		this.color = color;
	}
	
	public Clothing() {
		
	}
	
	public String getSize() {
		return size;
	}
	
	public void setSize(String size) {
		this.size = size;
	}
	
	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
}
