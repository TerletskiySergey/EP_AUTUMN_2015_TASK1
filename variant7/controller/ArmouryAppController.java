package tasks.task1.variant7.controller;

import tasks.task1.variant7.controller.checkers.BorderedIntChecker;
import tasks.task1.variant7.controller.checkers.ConsoleChecker;
import tasks.task1.variant7.controller.checkers.StringEqualityChecker;
import tasks.task1.variant7.model.ammunition.Ammunition;
import tasks.task1.variant7.model.shop.Armoury;
import tasks.task1.variant7.view.ArmouryAppView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ArmouryAppController extends AbstractConsoleMenuController {
    public static final Comparator<Ammunition> BY_WEIGHT_COMPARATOR = (am1, am2) ->
            am1.getWeight() - am2.getWeight();
    public static final Comparator<Ammunition> DEAFAULT_COMPARATOR = (am1, am2) ->
            am1.getClass() == am2.getClass()
                    ? am1.getName().compareTo(am2.getName())
                    : am1.getClass().getSimpleName().compareTo(am2.getClass().getSimpleName());

    public enum Menu {
        EXIT(null), BUY(new BorderedIntChecker()), FILTER(new BorderedIntChecker()), SORT_BY_WEIGHT(null), RESET_FILTER(null), SORT_BY_TYPE_NAME(null);

        public static final StringEqualityChecker EXIT_CONDITION_CHECKER = new StringEqualityChecker();
        public static final BorderedIntChecker MENU_ITEM_CHECKER = new BorderedIntChecker();

        private ConsoleChecker checker;
        private String consoleRepresentation;

        Menu(ConsoleChecker checker) {
            this.checker = checker;
        }

        public ConsoleChecker getChecker() {
            return checker;
        }

        public void setConsoleRepresentation(String consoleRepresentation) {
            this.consoleRepresentation = consoleRepresentation;
        }

        private static List<Menu> getDefaultMenu() {
            List<Menu> menu = new ArrayList<>();
            menu.add(EXIT);
            menu.add(BUY);
            menu.add(FILTER);
            menu.add(SORT_BY_WEIGHT);
            return menu;
        }
    }

    private Armoury armoury;
    private List<Menu> curMenu;
    private Armoury filter;
    private boolean isSetDefaultComparator;

    public ArmouryAppController(ArmouryAppView view, Armoury armoury) {
        super(view, Menu.MENU_ITEM_CHECKER, Menu.EXIT_CONDITION_CHECKER);
        Menu.EXIT_CONDITION_CHECKER.setToCompareWith("0");
        this.armoury = armoury;
        this.isSetDefaultComparator = true;
    }

    @Override
    public void start() {
        ArmouryAppView view = (ArmouryAppView) this.view;
        view.clearConsole();
        view.showArmoury(armoury, DEAFAULT_COMPARATOR);
        super.start();
    }

    @Override
    protected List<String> getMenu() {
        refreshMenu();
        List<String> menu = new ArrayList<>();
        for (Menu item : curMenu) {
            menu.add(item.consoleRepresentation);
        }
        return menu;
    }

    @Override
    protected void processInput(String input) {
        Menu item = curMenu.get(Integer.parseInt(input));
        switch (item) {
            case BUY:
                buy();
                break;
            case FILTER:
                filter();
                break;
            case SORT_BY_WEIGHT:
                sortByWeight();
                break;
            case RESET_FILTER:
                resetFilter();
                break;
            case SORT_BY_TYPE_NAME:
                sortByTypeName();
                break;
        }
    }

    private void buy() {
        ArmouryAppView view = (ArmouryAppView) this.view;
        Armoury curShop = (filter == null) ? armoury : filter;
        Comparator<Ammunition> curComp = (isSetDefaultComparator)
                ? DEAFAULT_COMPARATOR
                : BY_WEIGHT_COMPARATOR;
        view.clearConsole();
        view.showArmoury(curShop, curComp);
        view.showEmptyRow();
        List<Ammunition> wares = curShop.getWares();
        ((BorderedIntChecker) Menu.BUY.checker).setRange(1, wares.size());
        int input = Integer.parseInt(requestInput(Menu.BUY.checker));
        wares.sort(curComp);
        Ammunition toBuy = wares.get(input - 1);
        curShop.sellItem(toBuy);
        if (filter != null) {
            armoury.removeItem(toBuy);
        }
        view.clearConsole();
        view.showArmoury(curShop, curComp);
    }

    private void filter() {
        ArmouryAppView view = (ArmouryAppView) this.view;
        Comparator<Ammunition> curComp = (isSetDefaultComparator)
                ? DEAFAULT_COMPARATOR
                : BY_WEIGHT_COMPARATOR;
        view.clearConsole();
        view.showArmoury(armoury, curComp);
        view.showEmptyRow();
        BorderedIntChecker checker = (BorderedIntChecker) Menu.FILTER.checker;
        checker.setRange(0, Integer.MAX_VALUE);
        int min = Integer.parseInt(requestInput(checker));
        checker.setRange(min, Integer.MAX_VALUE);
        int max = Integer.parseInt(requestInput(checker));
        filter = new Armoury();
        filter.admitCustomer(armoury.getCustomer());
        int curPrice;
        for (Ammunition ammo : armoury.getWares()) {
            curPrice = armoury.getPriceForItem(ammo);
            if (curPrice >= min && curPrice <= max) {
                filter.addItem(ammo, curPrice, armoury.getAvailableQuantityForItem(ammo));
            }
        }
        view.clearConsole();
        view.showArmoury(filter, curComp);
    }

    private void refreshMenu() {
        curMenu = Menu.getDefaultMenu();
        process:
        {
            if (armoury == null) {
                curMenu.clear();
                curMenu.add(Menu.EXIT);
                break process;
            }
            Armoury shop = (filter == null) ? armoury : filter;
            if (shop.getCustomer() == null && curMenu.contains(Menu.BUY)) {
                curMenu.remove(Menu.BUY);
            }
            if (shop.getWares().isEmpty()) {
                curMenu.clear();
                curMenu.add(Menu.EXIT);
                curMenu.add(Menu.RESET_FILTER);
                break process;
            }
            if (filter != null) {
                curMenu.set(curMenu.indexOf(Menu.FILTER), Menu.RESET_FILTER);
            }
            if (!isSetDefaultComparator) {
                curMenu.set(curMenu.indexOf(Menu.SORT_BY_WEIGHT), Menu.SORT_BY_TYPE_NAME);
            }
        }
        Menu.MENU_ITEM_CHECKER.setRange(0, curMenu.size() - 1);
    }

    private void resetFilter() {
        ArmouryAppView view = (ArmouryAppView) this.view;
        Comparator<Ammunition> curComp = (isSetDefaultComparator)
                ? DEAFAULT_COMPARATOR
                : BY_WEIGHT_COMPARATOR;
        filter = null;
        view.clearConsole();
        view.showArmoury(armoury, curComp);
    }

    private void sortByTypeName() {
        ArmouryAppView view = (ArmouryAppView) this.view;
        Armoury curShop = (filter == null) ? armoury : filter;
        isSetDefaultComparator = true;
        view.clearConsole();
        view.showArmoury(curShop, DEAFAULT_COMPARATOR);
    }

    private void sortByWeight() {
        ArmouryAppView view = (ArmouryAppView) this.view;
        Armoury curShop = (filter == null) ? armoury : filter;
        isSetDefaultComparator = false;
        view.clearConsole();
        view.showArmoury(curShop, BY_WEIGHT_COMPARATOR);
    }
}