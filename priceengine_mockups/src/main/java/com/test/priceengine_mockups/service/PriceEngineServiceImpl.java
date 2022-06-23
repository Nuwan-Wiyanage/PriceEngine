package com.test.priceengine_mockups.service;

import com.test.priceengine_mockups.dao.PriceEngineDao;
import com.test.priceengine_mockups.model.ProductMeasureVO;
import com.test.priceengine_mockups.model.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PriceEngineServiceImpl implements PriceEngineService{

    @Autowired
    PriceEngineDao dao;

    @Override
    public Map<Integer, Float> calculatePricesForTwoProducts(List<ProductMeasureVO> products) {
        Map<Integer, Float> priceUpdatedProducts = new HashMap<>();

        for (ProductMeasureVO product: products) {
            ProductVO productData = dao.getProductById(product.getId());
            priceUpdatedProducts.put(product.getId(), calculatePrice(product, productData));
        }

        return priceUpdatedProducts;
    }

    @Override
    public Float calculatePrice(ProductMeasureVO product, ProductVO productData) {
        Float price = 0.0f;
        int noOfCartons = 0, noOfUnits = 0;

        try {
            if (product.getMeasurement().equals("units")) {
                noOfCartons = (int) (product.getQuantity() / productData.getUnitsPerCarton());
                if (noOfCartons > 0) {
                    noOfUnits = (int) (product.getQuantity() % productData.getUnitsPerCarton());
                } else {
                    noOfUnits = Math.round(product.getQuantity());
                }
            } else {
                //measurement is 'cartons'
                noOfCartons = (int) Math.floor(product.getQuantity());
                float qtyDecimalPart = Float.parseFloat(String.format("%.02f", (product.getQuantity() - noOfCartons)));

                if(qtyDecimalPart != 0){
                    //Assumption => Only closest units provided.
                    //That means if the output value of (qtyDecimalPart * productData.getUnitsPerCarton()) is 1.6, we consider this as 1 unit. we take only the int part
                    noOfUnits = (int) (qtyDecimalPart * productData.getUnitsPerCarton());
                }
            }

            price = calculateDiscountOrIncrease(noOfCartons, noOfUnits, productData);
        }catch (Exception e){
            System.out.println(e.getStackTrace());
        }

        return price;
    }

    @Override
    public Float calculateDiscountOrIncrease(int noOfCartons, int noOfUnits, ProductVO productData) {
        Float discountedPrice;
        Float priceForCartons = 0.0F;
        Float priceForUnits = 0.0F;
        if(noOfCartons > 0){
            if(noOfCartons >= 3){
                priceForCartons = (productData.getCartonPrice() - ((float)(productData.getCartonPrice() * 0.1))) * noOfCartons;
            }
            else {
                priceForCartons = productData.getCartonPrice() * noOfCartons;
            }
        }

        if(noOfUnits > 0){
            priceForUnits = (((float) (productData.getCartonPrice() * 1.3)) / productData.getUnitsPerCarton()) * noOfUnits;
//            if(noOfCartons == 0){
//                priceForUnits = (((float) (productData.getCartonPrice() * 1.3)) / productData.getUnitsPerCarton()) * noOfUnits;
//            }
//            else {
//                priceForUnits = (productData.getCartonPrice() / productData.getUnitsPerCarton()) * noOfUnits;
//            }
        }

        discountedPrice = priceForUnits + priceForCartons;

        System.out.println("=====================================================================");
        System.out.println("priceForUnits ------------->"+ priceForUnits);
        System.out.println("priceForCartons ----------->"+ priceForCartons);
        System.out.println("Total discountedPrice ----->"+ discountedPrice);
        System.out.println("=====================================================================");
        System.out.println("");

        return discountedPrice;

    }
}
