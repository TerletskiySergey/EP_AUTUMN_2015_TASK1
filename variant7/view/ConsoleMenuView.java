package tasks.task1.variant7.view;

import java.util.List;

public interface ConsoleMenuView {
    void clearConsole();

    void showEmptyRow();

    void showMenu(List<String> menu);

    void showMessage(String message);
}