package com.test.priceengine_mockups.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ProductVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String productName;
    private Float cartonPrice;
    private Integer unitsPerCarton;


    public ProductVO(String productName, Float cartonPrice, Integer unitsPerCarton) {
        this.setProductName(productName);
        this.setCartonPrice(cartonPrice);
        this.setUnitsPerCarton(unitsPerCarton);
    }

    public ProductVO() { }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Float getCartonPrice() {
        return cartonPrice;
    }

    public void setCartonPrice(Float cartonPrice) {
        this.cartonPrice = cartonPrice;
    }

    public Integer getUnitsPerCarton() {
        return unitsPerCarton;
    }

    public void setUnitsPerCarton(Integer unitsPerCarton) {
        this.unitsPerCarton = unitsPerCarton;
    }
}
