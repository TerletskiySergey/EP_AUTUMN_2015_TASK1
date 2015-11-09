package tasks.task1.variant7.model.shop;

import tasks.task1.variant7.model.ammunition.Saleable;
import tasks.task1.variant7.model.unit.Unit;

import java.io.Serializable;
import java.util.*;

public abstract class Shop<T extends Saleable> implements Serializable {

    protected static class ShopItemAttribute implements Serializable {
        int price;
        int quant;

        public ShopItemAttribute(int price, int quant) {
            this.price = price;
            this.quant = quant;
        }
    }

    protected Unit cust;
    protected Map<T, ShopItemAttribute> wares;

    protected Shop() {
        this.wares = new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    public void addItem(T item, int price, int quant) {
        if (item == null || price < 0 || quant < 1) {
            return;
        }
        this.wares.put((T) item.clone(), new ShopItemAttribute(price, quant));
    }

    public void admitCustomer(Unit visitor) {
        if (this.cust != null) {
            return;
        }
        this.cust = visitor;
    }

    public boolean canAdmitCustomer() {
        return this.cust == null;
    }

    public boolean canItemBeBought(T item) {
        ShopItemAttribute attr;
        return cust != null && (attr = wares.get(item)) != null && attr.price <= cust.getCash();
    }

    public int getAvailableQuantityForItem(T item) {
        ShopItemAttribute attr;
        return ((attr = wares.get(item)) == null) ? -1 : attr.quant;
    }

    public Unit getCustomer() {
        return this.cust;
    }

    public int getPriceForItem(T item) {
        ShopItemAttribute attr;
        return ((attr = wares.get(item)) == null) ? -1 : attr.price;
    }

    @SuppressWarnings("unchecked")
    public List<T> getWares() {
        List<T> result = new ArrayList<>();
        Set<T> waresSet = wares.keySet();
        for (T elem : waresSet) {
            result.add((T) elem.clone());
        }
        return result;
    }

    public void letGo() {
        this.cust = null;
    }

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

    public abstract void sellItem(T item);
}