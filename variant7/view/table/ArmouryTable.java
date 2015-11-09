package tasks.task1.variant7.view.table;

import tasks.task1.variant7.model.ammunition.Ammunition;
import tasks.task1.variant7.model.ammunition.Armor;
import tasks.task1.variant7.model.ammunition.Weapon;
import tasks.task1.variant7.model.shop.Armoury;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ArmouryTable extends Table {

    private Armoury shop;
    private Comparator<Ammunition> comparator;

    public ArmouryTable(Armoury shop, Comparator<Ammunition> comparator) {
        super(null, null, null);
        this.shop = shop;
        this.comparator = comparator;
        tag = "CURRENTLY AVAILABLE IN STOCK:";
        header();
        rows();
        fitColumnWidthsToContent();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    private void rows() {
        List<Ammunition> wares;
        if (shop == null || (wares = shop.getWares()).isEmpty()) {
            return;
        }
        ArrayList<String> row;
        int counter = 1;
        wares.sort(comparator);
        for (Ammunition item : wares) {
            row = new ArrayList<>();
            char sign = shop.canItemBeBought(item) ? '+' : '-';
            row.add(String.valueOf(counter) + sign);
            row.add(item.getName());
            String type = item.getClass().getSimpleName();
            row.add(type);
            row.add(String.valueOf(item.getWeight()));
            if (type.equals("Armor")) {
                row.add(String.valueOf(((Armor) item).getStrength()));
            } else {
                row.add("--");
            }
            if (type.equals("Weapon")) {
                row.add(String.valueOf(((Weapon) item).getDamage()));
                row.add(String.valueOf(((Weapon) item).getRange()));
            } else {
                row.add("--");
                row.add("--");
            }
            row.add(String.valueOf(shop.getPriceForItem(item)));
            row.add(String.valueOf(shop.getAvailableQuantityForItem(item)));
            rows.add(row);
            counter++;
        }
    }

    private void header() {
        header.add("ITEM");
        header.add("DESCRIPTION");
        header.add("TYPE");
        header.add("WEIGHT");
        header.add("STRENGTH");
        header.add("DAMAGE");
        header.add("RANGE");
        header.add("PRICE");
        header.add("Q-TY");
    }
}
