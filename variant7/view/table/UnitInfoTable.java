package tasks.task1.variant7.view.table;

import tasks.task1.variant7.model.unit.Unit;

import java.util.ArrayList;
import java.util.List;

public class UnitInfoTable extends Table {
    private Unit unit;

    public UnitInfoTable(Unit unit) {
        super(null, null, null);
        this.unit = unit;
        tag = "UNIT INFO:";
        header();
        rows();
        fitColumnWidthsToContent();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    private void header() {
        header.add("TYPE");
        header.add("NAME");
        header.add("CARRYING FORCE");
        header.add("CASH");
    }

    private void rows() {
        if (unit == null) {
            return;
        }
        List<String> row = new ArrayList<>();
        row.add(String.valueOf(unit.getClass().getSimpleName()));
        row.add(unit.getName());
        row.add(String.valueOf(unit.getCarryForce()));
        row.add(String.valueOf(unit.getCash()));
        rows.add(row);
    }
}
