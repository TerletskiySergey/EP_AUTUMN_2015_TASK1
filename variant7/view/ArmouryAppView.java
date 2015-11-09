package tasks.task1.variant7.view;

import tasks.task1.variant7.model.ammunition.Ammunition;
import tasks.task1.variant7.model.shop.Armoury;
import tasks.task1.variant7.view.table.ArmouryTable;
import tasks.task1.variant7.view.table.Table;
import tasks.task1.variant7.view.table.UnitInfoTable;
import tasks.task1.variant7.view.table.UnitKitbagTable;

import java.util.Comparator;
import java.util.List;

public class ArmouryAppView implements ConsoleMenuView {

    @Override
    public void clearConsole() {
        for (int i = 0; i < 20; i++) {
            System.out.println("\n");
        }
    }

    @Override
    public void showEmptyRow() {
        System.out.println();
    }

    @Override
    public void showMenu(List<String> menu) {
        int longestItem = 0;
        for (String str : menu) {
            if (longestItem < str.length()) {
                longestItem = str.length();
            }
        }
        longestItem += String.valueOf(menu.size()).length() + 2;
        showTextIndent(longestItem / 2);
        System.out.println("MENU");
        showDecorationLine('-', longestItem);
        System.out.println();
        for (int i = 1; i < menu.size(); i++) {
            System.out.printf("%d. %s\n", i, menu.get(i));
        }
        showDecorationLine('-', longestItem);
        System.out.print("\n0. ");
        System.out.println(menu.get(0));
        System.out.println();
    }

    public void showArmoury(Armoury armoury, Comparator<Ammunition> comp) {
        Table armouryTable = new ArmouryTable(armoury, comp);
        Table unitInfoTable = new UnitInfoTable(armoury.getCustomer());
        Table unitKitbagTable = new UnitKitbagTable(armoury.getCustomer(), comp);
        Table.fitTableWidths(armouryTable, unitInfoTable, unitKitbagTable);
        System.out.println(Table.concatHor(armouryTable, Table.concatVer(unitInfoTable, unitKitbagTable)));
    }

    @Override
    public void showMessage(String message) {
        System.out.print(message);
    }

    public void showTextIndent(int val) {
        for (int i = 0; i < val; i++) {
            System.out.print(' ');
        }
    }

    private void showDecorationLine(char ch, int len) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(ch);
        }
        System.out.print(sb);
    }
}