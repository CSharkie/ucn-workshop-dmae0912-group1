package model;

public class GunReplicas extends Product {
	
	String fabric;
	double caliber;
	
	public GunReplicas(String fabric, double caliber) {
		super();
		this.fabric = fabric;
		this.caliber = caliber;
	}
	
	public GunReplicas() {
		
	}

	public String getFabric() {
		return fabric;
	}
	
	public void setFabric(String fabric) {
		this.fabric = fabric;
	}
	
	public double getCaliber() {
		return caliber;
	}
	
	public void setCaliber(double caliber) {
		this.caliber = caliber;
	}
	
	

}
