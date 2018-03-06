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
        if(isPositiveOrZero(price)){
            productToPrice.put(item, price);
            productToSpecialDeals.put(item, new HashMap<>());
        }
        else{
            System.out.println("Item not added. Price cannot be negative!");
        }
    }

    /*
        Setting a products price. Behaves like addProduct if said product
        does not exist.
     */
    public void setProductPrice(Character item, double price){
        if(isPositiveOrZero(price)) {
            if (productToPrice.containsKey(item)) {
                productToPrice.put(item, price);
            } else {
                addProduct(item, price);
            }
        }else {
            System.out.println("Item not added. Price cannot be negative!");
        }
    }

    public void removeProduct(Character item){
        productToPrice.remove(item);
        productToSpecialDeals.remove(item);
    }

    /*
        Returns false if trying to add special deal to an item which isn't in the set price list
        or if price is negative.
     */
    public boolean addSpecialDeal(Character item, int quantity, double price){
        if(isPositiveOrZero(price) && quantity > 1) {
            Map<Integer, Double> deals = productToSpecialDeals.get(item);
            if (deals != null) {
                deals.put(quantity, price);
                return true;
            } else {
                return false;
            }
        }else {
            System.out.println("Deal not added. Price is negative or quantity is less than 2!");
            return false;
        }
    }

    /*
        User friendly function for setting a special deal
     */
    public void setSpecialDeal(Character item, int quantity, double price){
        addSpecialDeal(item, quantity, price);
    }

    public void removeSpecialDeal(Character item, int quantity){
        productToSpecialDeals.get(item).remove(quantity);
    }

    public Double getProductPrice(Character item) {
        return productToPrice.get(item);
    }

    public boolean productHasSpecialDeals(Character item) {
        return (!productToSpecialDeals.get(item).isEmpty());
    }

    public Double getProductDeal(Character item, int quantity){
        return productToSpecialDeals.get(item).get(quantity);
    }

    /*
        Returns the highest deal (in number of items) which is lower than
        the quantity argument.
        Returns 1 of no deals exist.
        The method also ignores deals for quantities <= 1.
     */
    public int getBestDeal(Character item, int quantity){
        Map<Integer, Double> deals = productToSpecialDeals.get(item);
        int bestDeal = 1;
        for(int i = quantity; i>1; i--){
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

    private boolean isPositiveOrZero(double parameter){
        return parameter >= 0;
    }
}
