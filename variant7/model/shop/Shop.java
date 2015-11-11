package tasks.task1.variant7.model.shop;

import tasks.task1.variant7.model.ammunition.Saleable;
import tasks.task1.variant7.model.unit.Unit;

import java.io.Serializable;
import java.util.*;

/**
 * The abstract class Shop represents a range of shops,
 * that can sell Saleable instances within the ArmouryApp.java
 * application. Class implements Serializable interface to be possible
 * to store instances of inheritors in external files in serializable form.
 *
 * @author Sergey Terletskiy
 * @version 1.0 10/11/2015
 */
public abstract class Shop<T extends Saleable> implements Serializable {

    /**
     * The abstract class available for all Shop class inheritors represents a container for
     * the attributes of shop item, that are used in particular shop (inheritor of Shop class).
     * Class reserves two values as a default shop item attributes common for all inheritors:
     * price and available quantity.
     */
    protected static class ShopItemAttribute implements Serializable {

        /**
         * Represents price of shop item.
         */
        int price;

        /**
         * Represents quantity of shop items available in particular shop
         * (inheritor of Shop class).
         */
        int quant;

        /**
         * Initializes fields by the passing values.
         *
         * @param price integer value, representing price of shop item,
         *              that is assigned to instance field.
         * @param quant integer value, representing quantity of shop items
         *              available in particular shop (inheritor of Shop class),
         *              that is assigned to instance field.
         */
        ShopItemAttribute(int price, int quant) {
            this.price = price;
            this.quant = quant;
        }
    }

    /**
     * Available for inheritors field, representing customer (visitor) of the shop.
     */
    protected Unit cust;

    /**
     * Available for inheritors field, representing shop items,
     * that can be be bought in this shop.
     */
    protected Map<T, ShopItemAttribute> wares;

    /**
     * Available for inheritors constructor, that initializes an empty
     * container for shop wares.
     */
    protected Shop() {
        this.wares = new HashMap<>();
    }

    /**
     * Validates the passing parameters. In case of positive validation
     * a new shop item with passed price and available quantity is added
     * to this shop. Otherwise (in case of negative validation) method
     * doesn't add a new item.
     *
     * @param item  instance of Saleable implementor, representing a new shop
     *              item to be added to this shop. Method adds the clone of
     *              passing object.
     * @param price integer value, representing a price for a new item.
     * @param quant integer value, representing available quantity of a new
     *              shop item in this shop.
     */
    @SuppressWarnings("unchecked")
    public void addItem(T item, int price, int quant) {
        if (item == null || price < 0 || quant < 1) {
            return;
        }
        this.wares.put((T) item.clone(), new ShopItemAttribute(price, quant));
    }

    /**
     * Checks if currently there is no visitor in the shop and if no admits a new
     * customer to the shop.
     *
     * @param visitor Unit class instance, that represents a new cusomer.
     */
    public void admitCustomer(Unit visitor) {
        if (this.cust != null) {
            return;
        }
        this.cust = visitor;
    }

    /**
     * Determines if a new customer can be admitted to the shop.
     *
     * @return boolean value, that shows if currently a new user \
     * can be admitted to the shop.
     */
    public boolean canAdmitCustomer() {
        return this.cust == null;
    }

    /**
     * Determines if a passed item can be be bought by current customer.
     * Method's realisation depends on particular Shop inheritor, however
     * method provides realisation based on the default attributes of shop
     * item: price and available quantity. Logic of condition can be and should be
     * overridden by inheritor.
     *
     * @param item instance of Saleable implementor, representing a shop item.
     * @return boolean value, that shows if passed item can be
     * bought by current customer.
     */
    public boolean canItemBeBought(T item) {
        ShopItemAttribute attr;
        return cust != null && (attr = wares.get(item)) != null && attr.price <= cust.getCash();
    }

    /**
     * Returns available quantity for the shop item passing as a parameter.
     *
     * @param item instance of Saleable implementor, representing a shop item.
     * @return integer value, representing available quantity for the shop item
     * passing as a parameter.
     */
    public int getAvailableQuantityForItem(T item) {
        ShopItemAttribute attr;
        return ((attr = wares.get(item)) == null) ? -1 : attr.quant;
    }

    public Unit getCustomer() {
        return this.cust;
    }

    /**
     * Returns price for the shop item passing as a parameter.
     *
     * @param item instance of Saleable implementor, representing a shop item.
     * @return integer value, representing price for the shop item
     * passing as a parameter.
     */
    public int getPriceForItem(T item) {
        ShopItemAttribute attr;
        return ((attr = wares.get(item)) == null) ? -1 : attr.price;
    }

    /**
     * Returns a list of wares available in this shop.
     *
     * @return List instance, containing Saleable implementors instances,
     * that are available in this shop.
     */
    @SuppressWarnings("unchecked")
    public List<T> getWares() {
        List<T> result = new ArrayList<>();
        Set<T> waresSet = wares.keySet();
        for (T elem : waresSet) {
            result.add((T) elem.clone());
        }
        return result;
    }

    /**
     * Lets the current customer go by assigning
     * the corresponding state field to the null value.
     */
    public void letGo() {
        this.cust = null;
    }

    /**
     * Removes passing shop item from this shop.
     *
     * @param item Saleable instance, that represents shop item.
     */
    public void removeItem(T item) {
        ShopItemAttribute attr = wares.get(item);
        if (attr == null) {
            return;
        }
        if (attr.quant == 1) {
            wares.remove(item);
        } else {
            attr.quant--;
        }
    }

    /**
     * Sells passing item to the current customer.
     * Method's realisation depends on the particular Shop inheritor.
     *
     * @param item Saleable instance, that represents shop item.
     */
    public abstract void sellItem(T item);
}