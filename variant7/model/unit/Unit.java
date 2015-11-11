package tasks.task1.variant7.model.unit;


import tasks.task1.variant7.model.ammunition.Ammunition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The class represents an abstraction of a unit, one of the main actors
 * in the ArmouryApp.java application, within which unit can be outfitted
 * by some armoury items. Class implements Serializable interface to be able
 * to store class instances in external file in serializable form.
 *
 * @author Sergey Terletskiy
 * @version 1.0 10/11/2015
 */
public class Unit implements Serializable {

    /**
     * Carrying force of a unit, determines how many ammunition items
     * can be placed in unit's kitbag.
     */
    private int carryForce;

    /**
     * Cash available to this unit for buying items in shops.
     */
    private int cash;

    /**
     * Container for unit's ammunition.
     */
    private List<Ammunition> kitbag;

    /**
     * Name of this unit.
     */
    private String name;

    /**
     * Initializes fields by the passing values.
     * If passing values doesn't pass the input check, the corresponding fields
     * are assigned by the default values:
     * name by an empty string;
     * carryForce by zero value;
     * cash by zero value;
     *
     * @param name       String value, that represents unit's name.
     * @param carryForce integer value, that represents unit's carrying force.
     * @param cash       integer value, that represents unit's cash.
     */
    public Unit(String name, int carryForce, int cash) {
        inputFilter(name, carryForce, cash);
        this.kitbag = new ArrayList<>();
    }

    /**
     * Determines if the passing ammunition item can be outfitted by
     * this unit. The logic of condition is as follows:
     * unit can be outfitted by the passing ammunition item if item's weight is
     * less or equal to unit's carrying force.
     *
     * @return boolean value, that shows if this unit can add passing
     * ammunition item to his kitbag.
     */
    public boolean canBeOutfittedWith(Ammunition item) {
        return item != null && item.getWeight() <= this.carryForce;
    }

    /**
     * Returns a deep copy of unit's ammunition container.
     *
     * @return List instance, containing clones of ammunition items of unit's kitbag.
     */
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

    /**
     * Adds passing ammunition item to unit's kitbag under the condition,
     * which is laid down in #canBeOutfittedWith(Ammunition item) method.
     *
     * @param item instance of Ammunition class representing ammunition item to
     *             be added to unit's kitbag.
     */
    public void outfit(Ammunition item) {
        if (canBeOutfittedWith(item)) {
            this.kitbag.add(item.clone());
            this.carryForce -= item.getWeight();
            System.out.println(kitbag);
        }
    }

    /**
     * Removes passing ammunition item from unit's kitbag under the condition
     * of presence of an item in kitbag. Herewith the value of unit's carrying force
     * is decreased by the value of weight of the removing item.
     *
     * @param item instance of Ammunition class representing ammunition item to
     *             be removed from unit's kitbag.
     */

    public void putoff(Ammunition item) {
        if (kitbag.contains(item)) {
            kitbag.remove(item);
            this.carryForce += item.getWeight();
        }
    }

    /**
     * Checks if the passing parameters can be used as assigning values for
     * corresponding state fields and assign these values if the check is positive,
     * otherwise default values of an empty string and zero values respectively
     * are assigned.
     *
     * @param name       String value, that represents unit's name.
     * @param carryForce integer value, that represents unit's carrying force.
     * @param cash       integer value, that represents unit's cash.
     */
    private void inputFilter(String name, int carryForce, int cash) {
        this.name = name == null ? "" : name;
        this.carryForce = carryForce < 0 ? 0 : carryForce;
        this.cash = cash < 0 ? 0 : cash;
    }
}