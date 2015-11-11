package tasks.task1.variant7.controller.checkers;

/**
 * Class provides verification of a sequence of conditions by invoking
 * of the #check(Objects...args) method. Conditions are stored in the internal
 * container in the form of implementors of protected BooleanMethod interface,
 * which has one abstract method: boolean #call(Objects[] args). This method represents
 * the checking of condition using passing array of possibly required arguments for checking.
 * The concrete inheritor of Checker class implements protected method #createConditions(),
 * which represents the process of filling of the internal conditions array by the implementors
 * of of protected BooleanMethod interface. I.e. any condition, that requires a boolean value as
 * a result of verifying, can be determined by Checker class inheritor.
 *
 * @author Sergey Terletskiy
 * @version 1.0 10/11/2015
 */
public abstract class Checker {

    /**
     * Internal container of conditions
     */
    private BooleanMethod[] conditions;

    /**
     * Interface, which abstract method #call(Object[] args) represents
     * the process of checking of condition using passing array
     * of possibly required arguments for checking.
     */
    protected interface BooleanMethod {

        /**
         * Method represents the process of checking of any condition,
         * that requires a boolean value as a result of verifying.
         *
         * @param args array of arguments possibly required for checking of condition.
         * @return boolean value, that shows the result of verifying of
         * condition using passed arguments.
         */
        boolean call(Object[] args);
    }

    /**
     * Initializes internal container of conditions.
     * For initialization is used an abstract method #createConditions().
     *
     * @see #createConditions()
     */
    public Checker() {
        conditions = createConditions();
    }

    /**
     * Returns an array of implementors of BooleanMethod interface i.e.
     * an array of conditions, that will be checked by invoking of #check(Object... args)
     * method. Method is to be implemented by non-abstract inheritors.
     *
     * @return array of BooleanMethod interface inheritors i.e.
     * an array of conditions, that will be checked by invoking of #check(Object... args).
     */
    protected abstract BooleanMethod[] createConditions();

    /**
     * Implements the process of checking of conditions,
     * stored in internal container using passing array of arguments.
     *
     * @param args array of Object instances, represents a set of
     *             possibly required arguments for checking.
     * @return boolean value, that shows the result of checking.
     */
    public boolean check(Object... args) {
        for (BooleanMethod m : conditions) {
            if (!m.call(args)) {
                return false;
            }
        }
        return true;
    }
}