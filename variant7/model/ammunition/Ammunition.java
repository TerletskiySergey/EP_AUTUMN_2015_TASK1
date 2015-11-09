package tasks.task1.variant7.model.ammunition;

import java.io.Serializable;
import java.util.Objects;

public abstract class Ammunition implements Saleable, Serializable {

    private String name;
    private int weight;

    protected Ammunition(String name, int weight) {
        inputFilter(name, weight);
    }

    @Override
    public Ammunition clone() {
        try {
            return (Ammunition) super.clone();
        } catch (CloneNotSupportedException ex) {
            throw new InternalError(ex);
        }
    }

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

    @Override
    public int hashCode() {
        int toReturn = 1;
        toReturn = toReturn * 31 + Objects.hashCode(name);
        toReturn = toReturn * 31 + Objects.hashCode(weight);
        return toReturn;
    }

    private void inputFilter(String name, int weight) {
        this.name = name == null ? "" : name;
        this.weight = weight < 0 ? 0 : weight;
    }
}