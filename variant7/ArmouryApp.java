package tasks.task1.variant7;

import tasks.task1.variant7.controller.ArmouryAppController;
import tasks.task1.variant7.model.ammunition.Armor;
import tasks.task1.variant7.model.ammunition.Weapon;
import tasks.task1.variant7.model.shop.Armoury;
import tasks.task1.variant7.model.unit.Unit;
import tasks.task1.variant7.view.ArmouryAppView;

import java.io.*;

public class ArmouryApp {

    private static ArmouryAppController controller;

    private static void initialization() throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/tasks/task1/variant7/resources/init"))) {
            Armoury shop = (Armoury) in.readObject();
            controller = new ArmouryAppController(new ArmouryAppView(), shop);

            String menuItemCheckerRequestMessage = (String) in.readObject();
            String menuItemInvalidInputMessage = (String) in.readObject();
            String exitMenuItemConsoleRepresentation = (String) in.readObject();
            String buyMenuItemConsoleRepresentation = (String) in.readObject();
            String buyMenuItemRequestMessage = (String) in.readObject();
            String filterMenuItemConsoleRepresentation = (String) in.readObject();
            String filterMenuItemRequestMessage = (String) in.readObject();
            String sortByWeightMenuItemConsoleRepresentation = (String) in.readObject();
            String resetFilterMenuItemConsoleRepresentation = (String) in.readObject();
            String sortByTypeNameMenuItemConsoleRepresentation = (String) in.readObject();

            ArmouryAppController.Menu.MENU_ITEM_CHECKER.setRequestMessage(menuItemCheckerRequestMessage);
            ArmouryAppController.Menu.MENU_ITEM_CHECKER.setInvalidInputMessage(menuItemInvalidInputMessage);
            ArmouryAppController.Menu.EXIT.setConsoleRepresentation(exitMenuItemConsoleRepresentation);
            ArmouryAppController.Menu.BUY.setConsoleRepresentation(buyMenuItemConsoleRepresentation);
            ArmouryAppController.Menu.BUY.getChecker().setRequestMessage(buyMenuItemRequestMessage);
            ArmouryAppController.Menu.BUY.getChecker().setInvalidInputMessage(menuItemInvalidInputMessage);
            ArmouryAppController.Menu.FILTER.setConsoleRepresentation(filterMenuItemConsoleRepresentation);
            ArmouryAppController.Menu.FILTER.getChecker().setRequestMessage(filterMenuItemRequestMessage);
            ArmouryAppController.Menu.FILTER.getChecker().setInvalidInputMessage(menuItemInvalidInputMessage);
            ArmouryAppController.Menu.SORT_BY_WEIGHT.setConsoleRepresentation(sortByWeightMenuItemConsoleRepresentation);
            ArmouryAppController.Menu.RESET_FILTER.setConsoleRepresentation(resetFilterMenuItemConsoleRepresentation);
            ArmouryAppController.Menu.SORT_BY_TYPE_NAME.setConsoleRepresentation(sortByTypeNameMenuItemConsoleRepresentation);
        }
    }

    private static void serialization() throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/tasks/task1/variant7/resources/init"))) {
            Unit unit = new Unit("Arthur", 500, 1000);
            Armoury shop = new Armoury();
            shop.admitCustomer(unit);
            shop.addItem(new Armor("Cuirass", 100, 500), 400, 5);
            shop.addItem(new Armor("Hauberk", 50, 150), 100, 5);
            shop.addItem(new Armor("Helmet", 40, 150), 150, 5);
            shop.addItem(new Armor("Buckler", 40, 250), 250, 5);
            shop.addItem(new Armor("Shield", 70, 400), 250, 5);
            shop.addItem(new Weapon("Arbalest", 20, 40, 20), 150, 5);
            shop.addItem(new Weapon("Bow", 20, 50, 15), 200, 5);
            shop.addItem(new Weapon("Greatsword", 60, 150, 4), 200, 5);
            shop.addItem(new Weapon("Mace", 20, 100, 1), 75, 5);
            shop.addItem(new Weapon("Sword", 40, 120, 2), 200, 5);

            String menuItemCheckerRequestMessage = "Enter menu item: ";
            String menuItemInvalidInputMessage = "Incorrect input.\n" +
                    "Enter integer value within acceptable range ";
            String exitMenuItemConsoleRepresentation = "Exit.";
            String buyMenuItemConsoleRepresentation = "Buy item.";
            String buyMenuItemRequestMessage = "Enter item number: ";
            String filterMenuItemConsoleRepresentation = "Filter price.";
            String filterMenuItemRequestMessage = "Enter filter border: ";
            String sortByWeightMenuItemConsoleRepresentation = "Sort items by weight.";
            String resetFilterMenuItemConsoleRepresentation = "Reset filter.";
            String sortByTypeNameMenuItemConsoleRepresentation = "Sort items by type and name.";

            out.writeObject(shop);
            out.writeObject(menuItemCheckerRequestMessage);
            out.writeObject(menuItemInvalidInputMessage);
            out.writeObject(exitMenuItemConsoleRepresentation);
            out.writeObject(buyMenuItemConsoleRepresentation);
            out.writeObject(buyMenuItemRequestMessage);
            out.writeObject(filterMenuItemConsoleRepresentation);
            out.writeObject(filterMenuItemRequestMessage);
            out.writeObject(sortByWeightMenuItemConsoleRepresentation);
            out.writeObject(resetFilterMenuItemConsoleRepresentation);
            out.writeObject(sortByTypeNameMenuItemConsoleRepresentation);
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        serialization();
        initialization();
        controller.start();
    }
}