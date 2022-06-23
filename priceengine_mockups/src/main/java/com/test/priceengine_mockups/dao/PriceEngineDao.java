package com.test.priceengine_mockups.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.test.priceengine_mockups.model.ProductVO;

@Repository
public class PriceEngineDao {

    private List<ProductVO> DB = new ArrayList<>();

    public ProductVO getProductById(int id) {
        ProductVO product = DB.stream()
                .filter(prod -> id == prod.getId())
                .findAny()
                .orElse(null);

        return product;
    }

    public void addProduct(ProductVO product) {
        DB.add(product);
    }

    public void updateProduct(ProductVO product) {
        int indexOfExistingProd = DB.indexOf(getProductById(product.getId()));
        DB.add(indexOfExistingProd, product);
    }

    public void deleteProduct(int id) {
        int indexOfExistingProd = DB.indexOf(getProductById(id));
        DB.remove(indexOfExistingProd);
    }
}
