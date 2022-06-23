package com.test.priceengine.repository;

import com.test.priceengine.model.ProductVO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Products extends JpaRepository<ProductVO, Integer> {
}
