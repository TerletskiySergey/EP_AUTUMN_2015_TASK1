package tasks.task1.variant7.model.shop;


import tasks.task1.variant7.model.ammunition.Ammunition;

/**
 * The class is an inheritor of an abstract class Shop,
 * represents the concrete realisation of a shop, that
 * uses instances of Ammunition class as shop items.
 *
 * @author Sergey Terletskiy
 * @version 1.0 10/11/2015
 */
public class Armoury extends Shop<Ammunition> {

    /**
     * Overridden method, that determines if the shop item, that passes
     * as a parameter can be bought in this shop by the current shop customer.
     * As a condition is used the following logic:
     * item can be bought if it satisfies method condition of a superclass (class Shop)
     * and if item can be outfitted by the current customer i.e. if the carrying force
     * of a customer allows to carry item in his kitbag.
     *
     * @param item instance of Ammunition class, that represents shop item.
     * @return boolean value, that shoes if the passing shop item can be bought
     * by the current customer.
     */
    @Override
    public boolean canItemBeBought(Ammunition item) {
        return super.canItemBeBought(item) && cust.canBeOutfittedWith(item);
    }

    /**
     * Realization of an abstract method, declared in Shop class.
     * Sells passing as a parameter shop item to the current customer if the condition,
     * laid down in #canBeBought() method is true.The logic of selling process is following:
     * customer's cash is reduced by the value of item's price, customer's carrying force
     * is reduced by the value of item's weight.
     *
     * @param item instance of Ammunition class, that represents shop item.
     */
    @Override
    public void sellItem(Ammunition item) {
        if (canItemBeBought(item)) {
            ShopItemAttribute attr = this.wares.get(item);
            if (attr.quant > 1) {
                attr.quant--;
            } else {
                this.wares.remove(item);
            }
            cust.setCash(cust.getCash() - attr.price);
            cust.outfit(item);
        }
    }
}