package com.test.priceengine;

import com.test.priceengine.model.ProductMeasureVO;
import com.test.priceengine.model.ProductVO;
import com.test.priceengine.repository.Products;
import com.test.priceengine.service.PriceEngineServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PriceengineApplicationTests {
	@InjectMocks
	PriceEngineServiceImpl service;

	@Autowired
	private Products productsRepository;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getProductsByIdTest()
	{
		Optional<ProductVO> product = productsRepository.findById(1);

		assertEquals("Penguin-ears", product.get().getProductName());
		assertEquals(175F, product.get().getCartonPrice());
		assertEquals(20, product.get().getUnitsPerCarton());
	}

	@Test
	public void unitsBelowUnitsPerCarton(){
		System.out.println("-------------------- unitsBelowUnitsPerCarton -----------------------");

		ProductMeasureVO r1 = new ProductMeasureVO(4F, "units");
		r1.setId(1);
		Optional<ProductVO> p1 = productsRepository.findById(1);
		Float actualPrice  = service.calculatePrice(Optional.of(r1), p1);
		Float expectedPrice  = 45.5F;
		assertEquals(expectedPrice, actualPrice);
	}

	@Test
	public void unitsAboveUnitsPerCarton(){
		System.out.println("-------------------- unitsAboveUnitsPerCarton -----------------------");

		ProductMeasureVO r1 = new ProductMeasureVO(24F, "units");
		r1.setId(1);
		Optional<ProductVO> p1 = productsRepository.findById(1);
		Float actualPrice  = service.calculatePrice(Optional.of(r1), p1);
		Float expectedPrice  = 220.5F;
		assertEquals(expectedPrice, actualPrice);
	}

	@Test
	public void unitsAboveThreeCarton(){
		System.out.println("--------------------- unitsAboveThreeCarton -------------------------");

		ProductMeasureVO r1 = new ProductMeasureVO(64F, "units");
		r1.setId(1);
		Optional<ProductVO> p1 = productsRepository.findById(1);
		Float actualPrice  = service.calculatePrice(Optional.of(r1), p1);
		Float expectedPrice  = 518F;
		assertEquals(expectedPrice, actualPrice);
	}

	@Test
	public void cartonBelowThree() {
		System.out.println("------------------------ cartonBelowThree ---------------------------");

		ProductMeasureVO r1 = new ProductMeasureVO(2F, "cartons");
		r1.setId(1);
		Optional<ProductVO> p1 = productsRepository.findById(1);
		Float actualPrice  = service.calculatePrice(Optional.of(r1), p1);
		Float expectedPrice  = 350F;
		assertEquals(expectedPrice, actualPrice);
	}


	@Test
	public void cartonOverThree() {
		System.out.println("------------------------- cartonOverThree ---------------------------");

		ProductMeasureVO r1 = new ProductMeasureVO(5F, "cartons");
		r1.setId(1);
		Optional<ProductVO> p1 = productsRepository.findById(1);
		Float actualPrice  = service.calculatePrice(Optional.of(r1), p1);
		Float expectedPrice  = 787.5F;
		assertEquals(expectedPrice, actualPrice);
	}


	@Test
	public void cartonBelowThreeWithDecimal(){
		System.out.println("-------------------- cartonBelowThreeWithDecimal --------------------");

		ProductMeasureVO r1 = new ProductMeasureVO(2.3F, "cartons");
		r1.setId(1);
		Optional<ProductVO> p1 = productsRepository.findById(1);
		Float actualPrice  = service.calculatePrice(Optional.of(r1), p1);
		Float expectedPrice  = 418.25F;
		assertEquals(expectedPrice, actualPrice);
	}


	@Test
	public void cartonAboveThreeWithDecimal(){
		System.out.println("-------------------- cartonAboveThreeWithDecimal --------------------");

		ProductMeasureVO r1 = new ProductMeasureVO(5.3F, "cartons");
		r1.setId(1);
		Optional<ProductVO> p1 = productsRepository.findById(1);
		Float actualPrice  = service.calculatePrice(Optional.of(r1), p1);
		Float expectedPrice  = 855.75F;
		assertEquals(expectedPrice, actualPrice);


		ProductMeasureVO r2 = new ProductMeasureVO(5.3F, "cartons");
		r2.setId(2);
		Optional<ProductVO> p2 = productsRepository.findById(2);
		Float actualPrice2  = service.calculatePrice(Optional.of(r2), p2);
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
