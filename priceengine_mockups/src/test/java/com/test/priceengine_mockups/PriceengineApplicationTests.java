package com.test.priceengine_mockups;

import com.test.priceengine_mockups.dao.PriceEngineDao;
import com.test.priceengine_mockups.model.ProductMeasureVO;
import com.test.priceengine_mockups.model.ProductVO;
import com.test.priceengine_mockups.service.PriceEngineServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class PriceengineApplicationTests {

	@InjectMocks
	PriceEngineServiceImpl service;

	@Mock
	PriceEngineDao dao;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getProductsByIdTest()
	{
		ProductVO p1 = new ProductVO("Penguin-ears", 175F, 20);
		p1.setId(1);
		when(dao.getProductById(1)).thenReturn(p1);

		ProductVO product = dao.getProductById(1);

		assertEquals("Penguin-ears", product.getProductName());
		assertEquals(175F, product.getCartonPrice());
		assertEquals(20, product.getUnitsPerCarton());
	}

	@Test
	public void calculatePriceTest(){
		ProductVO product1 = new ProductVO("Penguin-ears", 175F, 20);
		product1.setId(1);
		when(dao.getProductById(1)).thenReturn(product1);

		ProductVO product2 = new ProductVO("Horseshoe", 825F, 5);
		product2.setId(2);
		when(dao.getProductById(2)).thenReturn(product2);

		unitsBelowUnitsPerCarton();
		unitsAboveUnitsPerCarton();
		unitsAboveThreeCarton();

		cartonBelowThree();
		cartonOverThree();
		cartonBelowThreeWithDecimal();
		cartonAboveThreeWithDecimal();
	}

	public void unitsBelowUnitsPerCarton(){
		System.out.println("-------------------- unitsBelowUnitsPerCarton -----------------------");

		ProductMeasureVO r1 = new ProductMeasureVO(4F, "units");
		r1.setId(1);
		ProductVO p1 = dao.getProductById(1);
		Float actualPrice  = service.calculatePrice(r1, p1);
		Float expectedPrice  = 45.5F;
		assertEquals(expectedPrice, actualPrice);
	}

	public void unitsAboveUnitsPerCarton(){
		System.out.println("-------------------- unitsAboveUnitsPerCarton -----------------------");

		ProductMeasureVO r1 = new ProductMeasureVO(24F, "units");
		r1.setId(1);
		ProductVO p1 = dao.getProductById(1);
		Float actualPrice  = service.calculatePrice(r1, p1);
		Float expectedPrice  = 220.5F;
		assertEquals(expectedPrice, actualPrice);
	}

	public void unitsAboveThreeCarton(){
		System.out.println("--------------------- unitsAboveThreeCarton -------------------------");

		ProductMeasureVO r1 = new ProductMeasureVO(64F, "units");
		r1.setId(1);
		ProductVO p1 = dao.getProductById(1);
		Float actualPrice  = service.calculatePrice(r1, p1);
		Float expectedPrice  = 518F;
		assertEquals(expectedPrice, actualPrice);
	}

	public void cartonBelowThree() {
		System.out.println("------------------------ cartonBelowThree ---------------------------");

		ProductMeasureVO r1 = new ProductMeasureVO(2F, "cartons");
		r1.setId(1);
		ProductVO p1 = dao.getProductById(1);
		Float actualPrice  = service.calculatePrice(r1, p1);
		Float expectedPrice  = 350F;
		assertEquals(expectedPrice, actualPrice);
	}


	public void cartonOverThree() {
		System.out.println("------------------------- cartonOverThree ---------------------------");

		ProductMeasureVO r1 = new ProductMeasureVO(5F, "cartons");
		r1.setId(1);
		ProductVO p1 = dao.getProductById(1);
		Float actualPrice  = service.calculatePrice(r1, p1);
		Float expectedPrice  = 787.5F;
		assertEquals(expectedPrice, actualPrice);
	}


	public void cartonBelowThreeWithDecimal(){
		System.out.println("-------------------- cartonBelowThreeWithDecimal --------------------");

		ProductMeasureVO r1 = new ProductMeasureVO(2.3F, "cartons");
		r1.setId(1);
		ProductVO p1 = dao.getProductById(1);
		Float actualPrice  = service.calculatePrice(r1, p1);
		Float expectedPrice  = 418.25F;
		assertEquals(expectedPrice, actualPrice);
	}


	public void cartonAboveThreeWithDecimal(){
		System.out.println("-------------------- cartonAboveThreeWithDecimal --------------------");

		ProductMeasureVO r1 = new ProductMeasureVO(5.3F, "cartons");
		r1.setId(1);
		ProductVO p1 = dao.getProductById(1);
		Float actualPrice  = service.calculatePrice(r1, p1);
		Float expectedPrice  = 855.75F;
		assertEquals(expectedPrice, actualPrice);


		ProductMeasureVO r2 = new ProductMeasureVO(5.3F, "cartons");
		r2.setId(2);
		ProductVO p2 = dao.getProductById(2);
		Float actualPrice2  = service.calculatePrice(r2, p2);
		Float expectedPrice2  = 3927F;
		assertEquals(expectedPrice2, actualPrice2);
	}

//	@Test
//	public void calculatePricesForTwoProductsTest(){
//		List<ProductMeasureVO> requestObjects = new ArrayList<>();
//		ProductMeasureVO r1 = new ProductMeasureVO(24F, "units");
//		r1.setId(1);
//		requestObjects.add(r1);
//
//		ProductMeasureVO r2 = new ProductMeasureVO(5.3F, "cartons");
//		r2.setId(2);
//		requestObjects.add(r2);
//
//		ProductVO product1 = new ProductVO("Penguin-ears", 175F, 20);
//		product1.setId(1);
//		ProductVO product2 = new ProductVO("Horseshoe", 825F, 5);
//		product2.setId(2);
//		dao.addProduct(product1);
//		dao.addProduct(product2);
//
//		Map<Integer, Float> priceMap = service.calculatePricesForTwoProducts(requestObjects);
//
//		assertEquals(220.5F, priceMap.get(1));
//		assertEquals(3927F, priceMap.get(2));
//	}
}
