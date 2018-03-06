# itv-checkout

Solution for ITV's checkout task

### Some assumptions I have made for my solution:
1. Bigger deals always bring better value to the client ( buying 3 of some item is always better than buying 2 of them).
        Even if sometimes this isn't the case, I assume that the client will be able to pick out the best possible deal and
        gain the best possible value.
2. Because the specification of the exercise does not state that the list of items needs to be build dynamically
        by the user of the application, I did not do it. The checkout system currently just takes in an already built list
        of items. However, such dynamic build up of the list could be easily enabled by allowing interaction via the console.
        The `CheckoutSystem` class would not need to change at all as I would be providing another class with the sole
        purpose of interacting with the user and manipulating the item list.
        Eventually, checkout is always calculated for a ready list of items.
