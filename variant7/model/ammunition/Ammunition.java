package tasks.task1.variant7.model.ammunition;

import java.util.Objects;

/**
 * The abstract class Ammunition represents a range of ammunition instances,
 * with which instances of Unit class can be outfitted within the ArmouryApp.java
 * application. Class implements Saleable interface to be possible to use class
 * instances in instances of a Shop class as a shop item.
 *
 * @author Sergey Terletskiy
 * @version 1.0 10/11/2015
 */
public abstract class Ammunition implements Saleable {

    /**
     * Common for inheritors state field - title of an ammunition instance.
     */
    private String name;

    /**
     * Common for inheritors state field - weight of a ammunition instance.
     */
    private int weight;

    /**
     * Available for inheritors constructor, which initializes private fields
     * by the passing values.
     *
     * @param name   String value, representing ammunition's title,
     *               that is assigned to a private field in case
     *               if the value passes through the input check. Otherwise a default
     *               value of an empty string will be assigned.
     * @param weight integer value, representing ammunition's weight, that is assigned
     *               to a private field in case if the value passes through the input check.
     *               Otherwise a default zero value will be assigned.
     */
    protected Ammunition(String name, int weight) {
        inputFilter(name, weight);
    }

    /**
     * Overrides clone() method of an Object class and implements clone method
     * of Saleable interface.
     *
     * @return a clone of this object.
     */
    @Override
    public Ammunition clone() {
        try {
            return (Ammunition) super.clone();
        } catch (CloneNotSupportedException ex) {
            throw new InternalError(ex);
        }
    }

    /**
     * Overrides equals() method of an Object class.
     * Determines the equality of class instances under the condition
     * of coincidence of their classes, and values of private fields.
     *
     * @return boolean value, that show if other instance equals to this
     * instance.
     */
    @Override
    public boolean equals(Object other) {
        return other != null &&
                this.getClass() == other.getClass() &&
                this.name.equals(((Ammunition) other).name) &&
                this.weight == ((Ammunition) other).weight;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * Overrides hashCode() method of an Object class.
     *
     * @return hash code of this object.
     */
    @Override
    public int hashCode() {
        int toReturn = 1;
        toReturn = toReturn * 31 + Objects.hashCode(name);
        toReturn = toReturn * 31 + Objects.hashCode(weight);
        return toReturn;
    }

    /**
     * Checks passing values if they can be assigned to #name
     * and #weight fields. If not - default values of an empty
     * string and a zero value respectively are assigned.
     *
     * @param name   integer value, representing title of ammunition.
     * @param weight integer value, representing weight of ammunition.
     * @see #name state field.
     * @see #weight state field.
     */
    private void inputFilter(String name, int weight) {
        this.name = name == null ? "" : name;
        this.weight = weight < 0 ? 0 : weight;
    }
}