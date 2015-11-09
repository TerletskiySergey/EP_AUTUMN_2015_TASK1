package tasks.task1.variant7.model.shop;


import tasks.task1.variant7.model.ammunition.Ammunition;

public class Armoury extends Shop<Ammunition> {

    @Override
    public boolean canItemBeBought(Ammunition item) {
        return super.canItemBeBought(item) && cust.canBeOutfittedWith(item);
    }

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