import com.tsvetozar.PricingStrategy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PricingStrategyTest {

    PricingStrategy pricingStrategy;

    @Before
    public void init(){
        pricingStrategy = new PricingStrategy();
    }

    @Test
    public void testAddProductAutomaticallyInitilizesDealsMap(){
        Character item = 'A';
        pricingStrategy.addProduct(item, 50);
        assertNotNull(pricingStrategy.getProductToSpecialDeals().get(item));
    }

    @Test
    public void testGetBestDealReturnsHighestDealWhichIsLessThanTheNumberOfItemsSpecified(){
        Character item = 'B';

        // add products and their prices
        pricingStrategy.addProduct(item, 50);
        // add multibuy deals
        pricingStrategy.addSpecialDeal(item, 2, 45);
        pricingStrategy.addSpecialDeal(item, 4, 70);

        assertEquals(4,pricingStrategy.getBestDeal(item, 5));
        assertEquals(2,pricingStrategy.getBestDeal(item, 3));
    }

    @Test
    public void testGetBestDealReturns1IfNoDeals(){
        Character item = 'B';

        // add products and their prices
        pricingStrategy.addProduct(item, 50);

        assertEquals(1,pricingStrategy.getBestDeal(item, 5));
    }

    @Test
    public void testAddProductAddsToPricingMap(){
        Character item = 'B';

        // add products and their prices
        pricingStrategy.addProduct(item, 50);

        assertTrue(pricingStrategy.getProductToPrice().containsKey(item));
    }

    @Test
    public void testAddProductRejectsNegativePrices(){
        Character item = 'B';

        // add products and their prices
        pricingStrategy.addProduct(item, -1);
        assertFalse(pricingStrategy.getProductToPrice().containsKey(item));
    }

    @Test
    public void testRemoveProductRemovesFromPricingMap(){
        Character item = 'B';

        // add products and their prices
        pricingStrategy.addProduct(item, 50);
        pricingStrategy.removeProduct(item);
        assertFalse(pricingStrategy.getProductToPrice().containsKey(item));
    }

    @Test
    public void testRemoveProductAlsoRemovesAssociatedDeals(){
        Character item = 'B';

        // add products and their prices
        pricingStrategy.addProduct(item, 50);
        pricingStrategy.removeProduct(item);
        assertFalse(pricingStrategy.getProductToSpecialDeals().containsKey(item));
    }

    @Test
    public void testAddSpecialDealsReturnsFalseWhenProductDoesNotExist(){
        Character item = 'B';

        boolean isProductDealAdded = pricingStrategy.addSpecialDeal(item, 2, 45);
        assertEquals(false, isProductDealAdded);
    }

    @Test
    public void testAddSpecialDealAddsToItemDealsMap(){
        Character item = 'B';
        // add products and their prices
        pricingStrategy.addProduct(item, 50);
        pricingStrategy.addSpecialDeal(item, 2, 45);

        assertFalse(pricingStrategy.getProductToSpecialDeals().get(item).isEmpty());
    }

    @Test
    public void testAddSpecialDealRejectsNegativePrices(){
        Character item = 'B';
        // add products and their prices
        pricingStrategy.addProduct(item, 50);
        pricingStrategy.addSpecialDeal(item, 2, -1);

        assertFalse(pricingStrategy.getProductToSpecialDeals().get(item).containsKey(2));
    }

    @Test
    public void testAddSpecialDealRejectsDealsForQuantitiesLessThan2(){
        Character item = 'B';
        // add products and their prices
        pricingStrategy.addProduct(item, 50);
        pricingStrategy.addSpecialDeal(item, 1, 90);

        assertFalse(pricingStrategy.getProductToSpecialDeals().get(item).containsKey(1));
    }


    @Test
    public void testRemoveSpecialDealRemovesFromItemDeaslMap(){
        Character item = 'B';
        // add products and their prices
        pricingStrategy.addProduct(item, 50);
        pricingStrategy.addSpecialDeal(item, 2, 45);
        pricingStrategy.removeSpecialDeal(item,2);

        assertTrue(pricingStrategy.getProductToSpecialDeals().get(item).isEmpty());
    }

    @Test
    public void testSetProductPriceUpdatesPrice(){
        Character item = 'B';
        // add products and their prices
        pricingStrategy.addProduct(item, 50);
        pricingStrategy.setProductPrice(item, 40);

        assertEquals(40, pricingStrategy.getProductPrice(item).intValue());
    }

    @Test
    public void testSetProductPriceActsLikeAddProductIfProductDoesNotExist(){
        Character item = 'B';
        pricingStrategy.setProductPrice(item, 40);
        assertNotNull(pricingStrategy.getProductToSpecialDeals().get(item));
    }

    @Test
    public void testSetProductPriceRejectsNegativePrices(){
        Character item = 'B';
        pricingStrategy.setProductPrice(item, -1);
        assertFalse(pricingStrategy.getProductToPrice().containsKey(item));
    }
}
