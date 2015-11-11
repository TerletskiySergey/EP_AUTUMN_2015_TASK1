package tasks.task1.variant7.controller.checkers;

/**
 * Class serves for the same aims as Checker class,
 * the functionality is extended for console requests of user data,
 * when the request is preceded by the request message and
 * incorrect input is followed by the invalid input message, that
 * can be send to the standard output stream as a prompt for user.
 *
 * @author Sergey Terletskiy
 * @version 1.0 10/11/2015
 */
public abstract class ConsoleChecker extends Checker {

    /**
     * Invalid input message, that follows after user's incorrect
     * input and can be send to the standard output stream as a prompt for user
     */
    private String invalidInputMessage;

    /**
     * Request message, that precedes the request of user's data and can
     * be send to the standard output stream as a prompt for user
     */
    private String requestMessage;

    public String getInvalidInputMessage() {
        return invalidInputMessage;
    }

    public void setInvalidInputMessage(String invalidInputMessage) {
        this.invalidInputMessage = invalidInputMessage;
    }

    public String getRequestMessage() {
        return requestMessage;
    }

    public void setRequestMessage(String requestMessage) {
        this.requestMessage = requestMessage;
    }
}
