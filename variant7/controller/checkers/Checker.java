package tasks.task1.variant7.controller.checkers;

public abstract class Checker {

    private BooleanMethod[] conditions;

    protected interface BooleanMethod {
        boolean call(Object[] args);
    }

    public Checker() {
        conditions = createConditions();
    }

    protected abstract BooleanMethod[] createConditions();

    public boolean check(Object... args) {
        for (BooleanMethod m : conditions) {
            if (!m.call(args)) {
                return false;
            }
        }
        return true;
    }
}