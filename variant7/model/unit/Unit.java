package tasks.task1.variant7.model.unit;


import tasks.task1.variant7.model.ammunition.Ammunition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Unit implements Serializable {

    private int carryForce;
    private int cash;
    private List<Ammunition> kitbag;
    private String name;

    public Unit(String name, int carryForce, int cash) {
        inputFilter(name, carryForce, cash);
        this.kitbag = new ArrayList<>();
    }

    public boolean canBeOutfittedWith(Ammunition item) {
        return item != null && item.getWeight() <= this.carryForce;
    }

    public List<Ammunition> getAmmunition() {
        List<Ammunition> result = new ArrayList<>();
        for (Ammunition ammo : kitbag) {
            result.add(ammo.clone());
        }
        return result;
    }

    public int getCarryForce() {
        return carryForce;
    }

    public void setCarryForce(int carryForce) {
        this.carryForce = (carryForce < 0) ? 0 : carryForce;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = (cash < 0) ? 0 : cash;
    }

    public String getName() {
        return name;
    }

    public void outfit(Ammunition item) {
        if (canBeOutfittedWith(item)) {
            this.kitbag.add(item.clone());
            this.carryForce -= item.getWeight();
            System.out.println(kitbag);
        }
    }

    public void putoff(Ammunition item) {
        if (kitbag.contains(item)) {
            kitbag.remove(item);
            this.carryForce += item.getWeight();
        }
    }

    private void inputFilter(String name, int carryForce, int cash) {
        this.name = name == null ? "" : name;
        this.carryForce = carryForce < 0 ? 0 : carryForce;
        this.cash = cash < 0 ? 0 : cash;
    }
}