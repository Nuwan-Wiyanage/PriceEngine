package com.test.priceengine.repository;

import com.test.priceengine.model.ProductMeasureVO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Orders extends JpaRepository<ProductMeasureVO, Integer> {
}
