package tasks.task1.variant7.controller.checkers;

/**
 * The class serves for the same aims, as super class ConsoleChecker,
 * provides verification of a String variable, which is passed to the inherited
 * from Checker class check(Object... args) method as a zero-index array member.
 * The logic of verification is as follows: equality of the input string to the
 * value of the state field toCompareWith is to be verified. String value, equality
 * to which is to be estimated, can be assigned by invoking
 * #setToCompareWith(String toCompareWith) method.
 *
 * @author Sergey Terletskiy
 * @version 1.0 10/11/2015
 */
public class StringEqualityChecker extends ConsoleChecker {

    /**
     * State field, which string value serves as an object, comparison with which
     * of input value is to be estimated.
     */
    private String toCompareWith = "";

    public String getToCompareWith() {
        return toCompareWith;
    }

    public void setToCompareWith(String toCompareWith) {
        this.toCompareWith = toCompareWith;
    }

    /**
     * Implementation of an abstract method of super class Checker.
     * Creates an array of conditions to be checked. Sets the only condition:
     * equality of the input string to the value of the state field toCompareWith.
     *
     * @return array of BooleanMethod interface inheritors i.e.
     * an array of conditions, that will be checked by invoking of #check(Object... args).
     */
    @Override
    protected BooleanMethod[] createConditions() {
        BooleanMethod[] toReturn = new BooleanMethod[1];
        toReturn[0] = (args) -> args[0].equals(toCompareWith);
        return toReturn;
    }
}