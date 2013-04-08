package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class ProductTest {

	@Test
	public void testProduct() {
		Product prod = new Product();
		assertEquals("Alex", prod.getName());
		assertEquals(2.3, prod.getPurchasePrice(),0);
		assertEquals(4, prod.getSalePrice(),0);
		assertEquals(3, prod.getRentPrice(),0);
		assertEquals("China", prod.getCountryOfOrigin());
		assertEquals(3, prod.getMinStock());
	}

	@Test
	public void testGetProductId() {
		Product prod1 = new Product();
		prod1.setProductId(31);
		assertEquals(31, prod1.getProductId());
	}

	@Test
	public void testGetName() {
		Product prod2 = new Product();
		prod2.setName("Alex");
		assertEquals("Alex", prod2.getName());
	}

}
