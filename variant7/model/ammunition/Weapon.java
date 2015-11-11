package tasks.task1.variant7.model.ammunition;

import java.util.Objects;

/**
 * The class is an extension of Ammunition class.
 * Represents a range of Weapon instances,
 * with which instances of a Unit class can be outfitted within
 * the ArmouryApp.java application.
 *
 * @author Sergey Terletskiy
 * @version 1.0 10/11/2015
 */
public class Weapon extends Ammunition {

    /**
     * Represents abstraction - damage, caused by weapon.
     */
    private int damage;

    /**
     * Represents abstraction - maximal destruction range of weapon.
     */
    private int range;

    /**
     * Initializes private fields by passing values.
     *
     * @param name   String value, representing weapon's title,
     *               that is to a private field in case
     *               if the value passes through the input check. Otherwise a default
     *               value of an empty string will be assigned.
     * @param weight integer value, representing weapon's weight, that is assigned
     *               to a private field in case if the value passes through the input check.
     *               Otherwise a default zero value will be assigned.
     * @param damage integer value, representing weapon's damage, that is assigned
     *               to a private field in case if the value passes through the input check.
     *               Otherwise a default zero value will be assigned.
     * @param range  integer value, representing weapon's maximal destruction range,
     *               that is assigned to a private field in case if the value passes
     *               through the input check. Otherwise a default zero value will be assigned.
     */
    public Weapon(String name, int weight, int damage, int range) {
        super(name, weight);
        inputFilter(damage, range);
    }

    /**
     * Overrides clone() method of an Ammunition class.
     *
     * @return a clone of this object.
     */
    @Override
    public Weapon clone() {
        return (Weapon) super.clone();
    }

    /**
     * Overrides equals() method of an Ammunition class.
     * Determines the equality of class instances under the condition
     * of coincidence of their classes, and values of private fields.
     *
     * @return boolean value, that show if other instance equals to this
     * instance.
     */
    @Override
    public boolean equals(Object other) {
        return super.equals(other) &&
                this.damage == ((Weapon) other).damage &&
                this.range == ((Weapon) other).range;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    /**
     * Overrides hashCode() method of an Ammunition class.
     *
     * @return hash code of this object.
     */
    @Override
    public int hashCode() {
        int toReturn = super.hashCode();
        toReturn = toReturn * 31 + Objects.hashCode(damage);
        toReturn = toReturn * 31 + Objects.hashCode(range);
        return toReturn;
    }

    /**
     * Checks passing values if they can be assigned to #damage, #range fields.
     * If not - default zero value are assigned.
     *
     * @param damage integer value, representing damage, caused by weapon.
     * @param range  integer value, representing maximal destruction range of weapon.
     * @see #damage state field.
     * @see #range state field.
     */
    private void inputFilter(int damage, int range) {
        this.damage = damage < 0 ? 0 : damage;
        this.range = range < 0 ? 0 : range;
    }
}