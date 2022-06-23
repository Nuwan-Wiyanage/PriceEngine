package com.test.priceengine.service;

import com.test.priceengine.model.ProductMeasureVO;
import com.test.priceengine.model.ProductVO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PriceEngineService {
    public Map<Integer, Float> calculatePricesForTwoProducts(List<ProductMeasureVO> products);
    public Float calculatePrice(Optional<ProductMeasureVO> product, Optional<ProductVO> productData);
    public Float calculateDiscountOrIncrease(int noOfCartons, int noOfUnits, Optional<ProductVO> productData);
}
