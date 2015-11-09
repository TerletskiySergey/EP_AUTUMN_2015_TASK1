package tasks.task1.variant7.controller.checkers;

public class StringEqualityChecker extends ConsoleChecker {

    private String toCompareWith;

    public String getToCompareWith() {
        return toCompareWith;
    }

    public void setToCompareWith(String toCompareWith) {
        this.toCompareWith = toCompareWith;
    }

    @Override
    protected BooleanMethod[] createConditions() {
        BooleanMethod[] toReturn = new BooleanMethod[1];
        toReturn[0] = (args) -> args[0].equals(toCompareWith);
        return toReturn;
    }
}