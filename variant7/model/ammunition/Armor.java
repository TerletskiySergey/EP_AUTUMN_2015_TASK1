package tasks.task1.variant7.model.ammunition;

import java.util.Objects;

/**
 * The class is an extension of Ammunition class.
 * Represents a range of Armor instances,
 * with which instances of a Unit class can be outfitted within
 * the ArmouryApp.java application.
 *
 * @author Sergey Terletskiy
 * @version 1.0 10/11/2015
 */
public class Armor extends Ammunition {

    /**
     * Represents abstraction - strength of unit's armor.
     */
    private int strength;

    /**
     * Initializes private fields by passing values.
     *
     * @param name     String value, representing armor's title,
     *                 that is to a private field in case
     *                 if the value passes through the input check. Otherwise a default
     *                 value of an empty string will be assigned.
     * @param weight   integer value, representing armor's weight, that is assigned
     *                 to a private field in case if the value passes through the input check.
     *                 Otherwise a default zero value will be assigned.
     * @param strength integer value, representing armor's strength, that is assigned
     *                 to a private field in case if the value passes through the input check.
     *                 Otherwise a default zero value will be assigned.
     */
    public Armor(String name, int weight, int strength) {
        super(name, weight);
        inputFilter(strength);
    }

    /**
     * Overrides clone() method of an Ammunition class.
     *
     * @return clone of this object.
     */
    @Override
    public Armor clone() {
        return (Armor) super.clone();
    }

    /**
     * Overrides equals() method of an Ammunition class.
     * Determines the equality of class instances under the condition
     * of coincidence of their classes, and values of private fields.
     *
     * @return boolean value, that shows if  other instance equals to this
     * instance.
     */
    @Override
    public boolean equals(Object other) {
        return super.equals(other) &&
                this.strength == ((Armor) other).strength;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    /**
     * Overrides hashCode() method of an Ammunition class.
     *
     * @return hash code of this object.
     */
    @Override
    public int hashCode() {
        return super.hashCode() * 31 + Objects.hashCode(strength);
    }

    /**
     * Checks passing value if it can be assigned to #strength fields.
     * If not - default value of a zero value is assigned.
     *
     * @param strength integer value, representing strength of unit's armor.
     * @see #strength state field.
     */
    private void inputFilter(int strength) {
        this.strength = strength < 0 ? 0 : strength;
    }
}