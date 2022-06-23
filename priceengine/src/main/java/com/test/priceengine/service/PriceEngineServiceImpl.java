package com.test.priceengine.service;

import com.test.priceengine.model.ProductMeasureVO;
import com.test.priceengine.model.ProductVO;
import com.test.priceengine.repository.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PriceEngineServiceImpl implements PriceEngineService{

    @Autowired
    private Products productsRepository;

    @Override
    public Map<Integer, Float> calculatePricesForTwoProducts(List<ProductMeasureVO> products) {
        Map<Integer, Float> priceUpdatedProducts = new HashMap<>();

        for (ProductMeasureVO product: products) {
            Optional<ProductVO> productData = productsRepository.findById(product.getId());
            priceUpdatedProducts.put(Math.toIntExact(product.getId()), calculatePrice(Optional.of(product), productData));
        }

        return priceUpdatedProducts;
    }

    @Override
    public Float calculatePrice(Optional<ProductMeasureVO> product, Optional<ProductVO> productData) {
        Float price = 0.0f;
        int noOfCartons = 0, noOfUnits = 0;

        try {
            if (product.get().getMeasurement().equals("units")) {
                noOfCartons = (int) (product.get().getQuantity() / productData.get().getUnitsPerCarton());
                if (noOfCartons > 0) {
                    noOfUnits = (int) (product.get().getQuantity() % productData.get().getUnitsPerCarton());
                } else {
                    noOfUnits = Math.round(product.get().getQuantity());
                }
            } else {
                //measurement is 'cartons'
                noOfCartons = (int) Math.floor(product.get().getQuantity());
                float qtyDecimalPart = Float.parseFloat(String.format("%.02f", (product.get().getQuantity() - noOfCartons)));

                if(qtyDecimalPart != 0){
                    //Assumption => Only closest units provided.
                    //That means if the output value of (qtyDecimalPart * productData.getUnitsPerCarton()) is 1.6, we consider this as 1 unit. we take only the int part
                    noOfUnits = (int) (qtyDecimalPart * productData.get().getUnitsPerCarton());
                }
            }

            price = calculateDiscountOrIncrease(noOfCartons, noOfUnits, productData);
        }catch (Exception e){
            System.out.println(e.getStackTrace());
        }

        return price;
    }

    @Override
    public Float calculateDiscountOrIncrease(int noOfCartons, int noOfUnits, Optional<ProductVO> productData) {
        Float discountedPrice;
        Float priceForCartons = 0.0F;
        Float priceForUnits = 0.0F;
        if(noOfCartons > 0){
            if(noOfCartons >= 3){
                priceForCartons = (productData.get().getCartonPrice() - ((float)(productData.get().getCartonPrice() * 0.1))) * noOfCartons;
            }
            else {
                priceForCartons = productData.get().getCartonPrice() * noOfCartons;
            }
        }

        if(noOfUnits > 0){
            priceForUnits = (((float) (productData.get().getCartonPrice() * 1.3)) / productData.get().getUnitsPerCarton()) * noOfUnits;
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
