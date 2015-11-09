package tasks.task1.variant7.controller;

import tasks.task1.variant7.controller.checkers.ConsoleChecker;
import tasks.task1.variant7.view.ConsoleMenuView;

import java.util.List;
import java.util.Scanner;

public abstract class AbstractConsoleMenuController {

    protected ConsoleChecker exitConditionChecker;
    protected ConsoleChecker menuItemChecker;
    protected Scanner scanner;
    protected ConsoleMenuView view;

    public AbstractConsoleMenuController(ConsoleMenuView view, ConsoleChecker menuItemChecker, ConsoleChecker exitConditionChecker) {
        this.view = view;
        this.exitConditionChecker = exitConditionChecker;
        this.menuItemChecker = menuItemChecker;
    }

    public void start() {
        String input;
        do {
            view.showMenu(getMenu());
            input = requestInput(menuItemChecker);
            processInput(input);
        } while (!exitConditionChecker.check(input));
    }

    protected abstract List<String> getMenu();

    protected abstract void processInput(String input);

    protected String requestInput(ConsoleChecker checker) {
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }
        view.showMessage(checker.getRequestMessage());
        String input = scanner.nextLine();
        while (!checker.check(input)) {
            view.showMessage(checker.getInvalidInputMessage());
            input = scanner.nextLine();
        }
        return input;
    }
}