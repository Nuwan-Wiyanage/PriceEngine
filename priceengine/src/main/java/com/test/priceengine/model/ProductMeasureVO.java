package com.test.priceengine.model;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public class ProductMeasureVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //quantity = no of units user want to buy
    private Float quantity;

    //measurement = units measured of quantity, either 'cartons' or 'units'
    private String measurement;

    public ProductMeasureVO(Float quantity, String measurement) {
        this.setQuantity(quantity);
        this.setMeasurement(measurement);
    }

    public ProductMeasureVO() { }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }
}
