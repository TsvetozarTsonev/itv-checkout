import com.tsvetozar.CheckoutSystem;
import com.tsvetozar.PricingStrategy;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CheckoutSystemTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testCheckoutSystemPicksUpDeals(){
        PricingStrategy pricingStrategy = new PricingStrategy();
        Character item = 'B';
        // add products and their prices
        pricingStrategy.addProduct(item, 30);

        // add multibuy deals
        pricingStrategy.addSpecialDeal(item, 2, 45);

        // build example checkout list
        ArrayList<Character> itemsForCheckout = new ArrayList<>();
        itemsForCheckout.add(item);
        itemsForCheckout.add(item);

        double checkout = CheckoutSystem.checkout(itemsForCheckout, pricingStrategy);
        assertEquals(45,(int) checkout);
    }

    @Test
    public void testCheckoutSystemDoesNotOverchargeWhenThereAreDeals(){
        PricingStrategy pricingStrategy = new PricingStrategy();
        Character item = 'A';
        // add products and their prices
        pricingStrategy.addProduct(item, 50);

        // add multibuy deals
        pricingStrategy.addSpecialDeal(item, 3, 130);

        // build example checkout list
        List<Character> itemsForCheckout = new ArrayList<>();
        itemsForCheckout.add(item);
        itemsForCheckout.add(item);
        itemsForCheckout.add(item);

        double checkout = CheckoutSystem.checkout(itemsForCheckout, pricingStrategy);
        /*
            next assert checks that the problem isn't related to not picking up deals. The mentioned
            test is done in the previous test.
         */
        assertNotEquals(150, (int) checkout);
        assertEquals(130,(int) checkout);
    }

    @Test
    public void testCheckoutSystemChecksAllPossibleDealsAndPicksBestOne(){
        PricingStrategy pricingStrategy = new PricingStrategy();
        Character item = 'A';
        // add products and their prices
        pricingStrategy.addProduct(item, 50);

        // add multibuy deals
        pricingStrategy.addSpecialDeal(item, 3, 130);
        pricingStrategy.addSpecialDeal(item, 4, 160);
        pricingStrategy.addSpecialDeal(item, 2, 90);

        // build example checkout list
        List<Character> itemsForCheckout = new ArrayList<>();
        itemsForCheckout.add(item);
        itemsForCheckout.add(item);
        itemsForCheckout.add(item);
        itemsForCheckout.add(item);
        itemsForCheckout.add(item);
        itemsForCheckout.add(item);
        itemsForCheckout.add(item);

        double checkout = CheckoutSystem.checkout(itemsForCheckout, pricingStrategy);
        assertEquals(290,(int) checkout);
    }

    @Test
    public void testErrorIsReportedIfTryingToCheckoutItemNotInThePricingStrategy(){
        PricingStrategy pricingStrategy = new PricingStrategy();

        // add products and their prices
        pricingStrategy.addProduct('A', 50);

        // build example checkout list
        ArrayList<Character> itemsForCheckout = new ArrayList<>();
        itemsForCheckout.add('B');

        exception.expectMessage("There was a problem with item B. Help coming.");
        exception.expect(RuntimeException.class);
        CheckoutSystem.checkout(itemsForCheckout, pricingStrategy);
    }

    @Test
    public void testErrorIsReportedIfTryingToCheckoutEmptyList(){
        PricingStrategy pricingStrategy = new PricingStrategy();

        // build example checkout list
        ArrayList<Character> itemsForCheckout = new ArrayList<>();

        exception.expectMessage("Checkout list is empty!");
        exception.expect(RuntimeException.class);
        CheckoutSystem.checkout(itemsForCheckout, pricingStrategy);
    }

    @Test
    public void testCanApplyDifferentPricingStrategiesToSameCheckoutList(){
        PricingStrategy pricingStrategy1 = new PricingStrategy();
        PricingStrategy pricingStrategy2 = new PricingStrategy();

        // add products and their prices
        pricingStrategy1.addProduct('A', 50);
        pricingStrategy1.addProduct('B', 30);

        pricingStrategy2.addProduct('A', 40);
        pricingStrategy2.addProduct('B', 20);

        // add multibuy deals
        pricingStrategy1.addSpecialDeal('B', 2, 45);

        // build example checkout list
        ArrayList<Character> itemsForCheckout = new ArrayList<>();
        itemsForCheckout.add('B');
        itemsForCheckout.add('A');
        itemsForCheckout.add('B');

        assertEquals(95, (int) CheckoutSystem.checkout(itemsForCheckout, pricingStrategy1));
        assertEquals(80, (int) CheckoutSystem.checkout(itemsForCheckout, pricingStrategy2));

    }


}
