package tasks.task1.variant7.model.ammunition;

import java.io.Serializable;

/**
 * Represents a range of instances, which cab be used in instances of
 * a Shop class as a shop item within the ArmouryApp.java application.
 * Interface extends Cloneable interface and declares abstract #clone()
 * method compelling all inheritors to override clone() method of an Object class,
 * it's a measure to be possible to store instances of inheritors in containers,
 * where immutable objects are required (e.g. Map), also extends Serializable
 * interface to be possible to store instances of inheritors in external files
 * in serializable form.
 *
 * @author Sergey terletskiy
 * @version 1.0 10/11/2015
 */
public interface Saleable extends Serializable, Cloneable {

    /**
     * Compels all inheritors to override clone() method of an Object class,
     * it's a measure to be possible to store instances of inheritors in Map
     * containers, where immutable objects are required (e.g. Map).
     */
    Saleable clone();
}
