package com.test.priceengine_mockups.service;

import com.test.priceengine_mockups.model.ProductMeasureVO;
import com.test.priceengine_mockups.model.ProductVO;

import java.util.List;
import java.util.Map;

public interface PriceEngineService {
    public Map<Integer, Float> calculatePricesForTwoProducts(List<ProductMeasureVO> products);
    public Float calculatePrice(ProductMeasureVO product, ProductVO productData);
    public Float calculateDiscountOrIncrease(int noOfCartons, int noOfUnits, ProductVO productData);
}
