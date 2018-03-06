package com.tsvetozar;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
	// building up example pricing strategy from the specification
        PricingStrategy pricingStrategy = new PricingStrategy();

        // add products and their prices
        pricingStrategy.addProduct('A', 50);
        pricingStrategy.addProduct('B', 30);
        pricingStrategy.addProduct('C', 20);
        pricingStrategy.addProduct('D', 15);

        // add multibuy deals
        pricingStrategy.addSpecialDeal('A', 3, 130);
        pricingStrategy.addSpecialDeal('B', 2, 45);

        // build example checkout list
        ArrayList<Character> itemsForCheckout = new ArrayList<>();
        itemsForCheckout.add('B');
        itemsForCheckout.add('A');
        itemsForCheckout.add('B');

        try {
            // calculate total amount to be paid
            double checkout = CheckoutSystem.checkout(itemsForCheckout, pricingStrategy);
            System.out.println("You bought the following items: ");
            for(Character item : itemsForCheckout){
                System.out.println(item);
            }
            System.out.println("Total amount paid: " + checkout);
        }catch (RuntimeException re){
            System.out.println(re.getMessage());
        }

    }
}
