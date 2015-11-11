package tasks.task1.variant7.controller.checkers;

import java.util.regex.Pattern;

/**
 * The class serves for the same aims, as super class ConsoleChecker,
 * provides verification of a String variable, which is passed to the inherited
 * from Checker class check(Object... args) method as a zero-index array member.
 * Verification is implemented in two steps: integer value verification and
 * verification of integer value belonging to the range, that can be estimated by
 * invoking of #setRange(int min, int max) method.
 *
 * @author Sergey Terletskiy
 * @version 1.0 10/11/2015
 */
public class BorderedIntChecker extends ConsoleChecker {

    /**
     * Static constant, that is used for integer verification, i.e. for checking
     * if an integer value can be parsed from input String value.
     */
    private static final Pattern INT_PATTERN = Pattern.compile("\\p{Digit}+");

    /**
     * Upper border of a range, belonging to which is to be verified.
     */
    private int max;

    /**
     * Lower border of a range, belonging to which is to be verified.
     */
    private int min;

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }

    /**
     * Assignment of range borders, belonging to which is to be verified.
     * Both borders are included to the range.
     */
    public void setRange(int min, int max) {
        this.min = (min > max) ? max : min;
        this.max = (min > max) ? min : max;
    }

    /**
     * Overridden method of super class ConsoleChecker. Functionality of the super
     * class method is extended by appending to the result of super class method call
     * of a string representation of a range, belonging to which is to be verified.
     *
     * @return string message, that can be send to the standard output stream as a prompt
     * for user about illegal input of data.
     */
    @Override
    public String getInvalidInputMessage() {
        String ancestor = super.getInvalidInputMessage();
        return (ancestor == null) ? null : String.format("%s[%d..%d]: ", ancestor, min, max);
    }

    /**
     * Implementation of an abstract method of super class Checker.
     * Creates an array of conditions to be checked. First condition:
     * possibility of parsing of an integer value from string input message,
     * second: belonging of a parsed integer value to the range, tah is estimated
     * by #min and #max state fields.
     *
     * @return array of BooleanMethod interface inheritors i.e.
     * an array of conditions, that will be checked by invoking of #check(Object... args).
     */
    @Override
    protected BooleanMethod[] createConditions() {
        BooleanMethod[] toReturn = new BooleanMethod[2];
        toReturn[0] = (args) -> INT_PATTERN.matcher((String) args[0]).matches();
        toReturn[1] = (args) -> {
            int input = Integer.parseInt((String) args[0]);
            return input >= min && input <= max;
        };
        return toReturn;
    }
}