package tasks.task1.variant7.view.table;

import tasks.task1.variant7.model.ammunition.Ammunition;
import tasks.task1.variant7.model.ammunition.Armor;
import tasks.task1.variant7.model.ammunition.Weapon;
import tasks.task1.variant7.model.unit.Unit;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class UnitKitbagTable extends Table {

    private Comparator<Ammunition> comparator;
    private Unit unit;

    public UnitKitbagTable(Unit unit, Comparator<Ammunition> comparator) {
        super(null, null, null);
        this.unit = unit;
        this.comparator = comparator;
        tag = "UNIT'S KITBAG CONTENTS:";
        header();
        rows();
        fitColumnWidthsToContent();
    }

    public String toString() {
        return super.toString();
    }

    private void header() {
        header.add("ITEM");
        header.add("DESCRIPTION");
        header.add("TYPE");
        header.add("WEIGHT");
        header.add("STRENGTH");
        header.add("DAMAGE");
        header.add("RANGE");
    }

    private void rows() {
        List<Ammunition> ammoList;
        if (unit == null || (ammoList = unit.getAmmunition()).isEmpty()) {
            return;
        }
        List<String> row;
        int counter = 1;
        ammoList.sort(comparator);
        for (Ammunition amm : ammoList) {
            row = new ArrayList<>();
            row.add(String.valueOf(counter));
            row.add(amm.getName());
            String type = amm.getClass().getSimpleName();
            row.add(type);
            row.add(String.valueOf(amm.getWeight()));
            if (type.equals("Armor")) {
                row.add(String.valueOf(((Armor) amm).getStrength()));
            } else {
                row.add("--");
            }
            if (type.equals("Weapon")) {
                row.add(String.valueOf(((Weapon) amm).getDamage()));
                row.add(String.valueOf(((Weapon) amm).getRange()));
            } else {
                row.add("--");
                row.add("--");
            }
            rows.add(row);
            counter++;
        }
    }
}
