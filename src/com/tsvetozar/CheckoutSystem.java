package com.tsvetozar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckoutSystem {

    /*
        Calculate total sum to be paid for a list of items, applying a pricing strategy.
     */
    public static double checkout(List<Character> items, PricingStrategy pricingStrategy) throws RuntimeException {
        if (items.isEmpty()) {
            throw new RuntimeException("Checkout list is empty!");
        }
        double total = 0;
        Map<Character, Integer> occurrences = new HashMap<>();

        // count how many times each item was bought
        for (Character item : items) {
            if (!itemExistsInPriceList(item, pricingStrategy)) {
                throw new RuntimeException("There was a problem with item " + item + ". Help coming.");
            }
            occurrences.merge(item, 1, (a, b) -> a + b);
        }

        // calculate sum for each type of item and add to total
        for (Map.Entry<Character, Integer> entry : occurrences.entrySet()) {
            Character item = entry.getKey();
            Integer boughtCount = entry.getValue();
            total += checkoutItem(item, boughtCount, pricingStrategy);
        }
        return total;
    }

    /*
        Calculates total amount to be paid for a type of item.
     */
    private static double checkoutItem(Character item, int quantity, PricingStrategy pricingStrategy) {
        boolean productHasSpecialDeals = pricingStrategy.productHasSpecialDeals(item);
        if (!productHasSpecialDeals) {
            return pricingStrategy.getProductPrice(item) * quantity;
        } else {
            double totalPriceForItem = 0;
            int numberOfItemsLeft = quantity;
            while (numberOfItemsLeft > 1) {
                int bestDeal = pricingStrategy.getBestDeal(item, numberOfItemsLeft);
                if (bestDeal != 1) {
                    totalPriceForItem += pricingStrategy.getProductDeal(item, bestDeal);
                    numberOfItemsLeft -= bestDeal;
                } else {
                    break;
                }
            }
            totalPriceForItem += numberOfItemsLeft * pricingStrategy.getProductPrice(item);
            return totalPriceForItem;
        }
    }

    private static boolean itemExistsInPriceList(Character item, PricingStrategy pricingStrategy) {
        return pricingStrategy.getProductPrice(item) != null;
    }
}
