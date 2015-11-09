package tasks.task1.variant7.model.ammunition;

import java.util.Objects;

public class Armor extends Ammunition {

    private int strength;

    public Armor(String name, int weight, int strength) {
        super(name, weight);
        inputFilter(strength);
    }

    @Override
    public Armor clone() {
        return (Armor) super.clone();
    }

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

    @Override
    public int hashCode() {
        return super.hashCode() * 31 + Objects.hashCode(strength);
    }

    private void inputFilter(int strength) {
        this.strength = strength < 0 ? 0 : strength;
    }
}