package tasks.task1.variant7.controller.checkers;

import java.util.regex.Pattern;

public class BorderedIntChecker extends ConsoleChecker {

    private static final Pattern INT_PATTERN = Pattern.compile("\\p{Digit}+");
    private int max;
    private int min;

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }

    public void setRange(int min, int max) {
        this.min = (min > max) ? max : min;
        this.max = (min > max) ? min : max;
    }

    @Override
    public String getInvalidInputMessage() {
        String ancestor = super.getInvalidInputMessage();
        return (ancestor == null) ? null : String.format("%s[%d..%d]: ", ancestor, min, max);
    }

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