package com.tsvetozar;

import java.util.HashMap;
import java.util.Map;

public class PricingStrategy {
    private Map<Character, Double> productToPrice;
    private Map<Character, Map<Integer, Double>> productToSpecialDeals;

    public PricingStrategy() {
        productToPrice = new HashMap<>();
        productToSpecialDeals = new HashMap<>();
    }

    /*
        Add product and initiate deals map for it
     */
    public void addProduct(Character item, double price){
        productToPrice.put(item, price);
        productToSpecialDeals.put(item, new HashMap<>());
    }

    /*
        Setting a products price. Behaves like addProduct if said product
        does not exist.
     */
    public void setProductPrice(Character item, double price){
        if(productToPrice.containsKey(item)) {
            productToPrice.put(item, price);
        }else{
            addProduct(item, price);
        }
    }

    public void removeProduct(Character item){
        productToPrice.remove(item);
        productToSpecialDeals.remove(item);
    }

    /*
        Returns false if trying to add special deal to an item which isn't in the set price list
     */
    public boolean addSpecialDeal(Character item, int numberOfItems, double price){
        Map<Integer, Double> deals = productToSpecialDeals.get(item);
        if(deals != null){
            deals.put(numberOfItems, price);
            return true;
        }else{
            return false;
        }
    }

    /*
        User friendly function for setting a special deal
     */
    public void setSpecialDeal(Character item, int numberOfItems, double price){
        addSpecialDeal(item, numberOfItems, price);
    }

    public void removeSpecialDeal(Character item, int numberOfItems){
        productToSpecialDeals.get(item).remove(numberOfItems);
    }

    public Double getProductPrice(Character item) {
        return productToPrice.get(item);
    }

    public boolean productHasSpecialDeals(Character item) {
        return (!productToSpecialDeals.get(item).isEmpty());
    }

    public Double getProductDeal(Character item, int numberOfItems){
        return productToSpecialDeals.get(item).get(numberOfItems);
    }

    /*
        Returns the highest deal (in number of items) which is lower than
        the numberOfItems argument.
        Returns 1 of no deals exist.
     */
    public int getBestDeal(Character item, int numberOfItems){
        Map<Integer, Double> deals = productToSpecialDeals.get(item);
        int bestDeal = 1;
        for(int i = numberOfItems; i>1; i--){
            Double deal = deals.get(i);
            if(deal != null){
                bestDeal = i;
                break;
            }
        }
        return bestDeal;
    }

    public Map<Character, Map<Integer, Double>> getProductToSpecialDeals() {
        return productToSpecialDeals;
    }

    public Map<Character, Double> getProductToPrice() {
        return productToPrice;
    }
}
