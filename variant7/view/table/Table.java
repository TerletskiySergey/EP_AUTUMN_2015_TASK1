package tasks.task1.variant7.view.table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Generates table representation according to the input-parameters:
 * - widths: List of integer values, each value represents width of corresponding table field (units: symbols quantity);
 * - header: List of string values, each value represents name of corresponding table field;
 * - data: List of lists of string values (2D String list), each sublist represents row of table.
 */
public class Table {

    protected List<String> header;
    protected List<List<String>> rows;
    protected String tag;
    protected List<Integer> widths;
    private boolean[] fitFlags;
    private int horShift;
    private StringBuilder toPrint;
    private boolean validated;
    private int verShift;

    public Table(List<Integer> widths, List<String> header,
                 List<List<String>> rows) {
        this.widths = (widths != null) ? widths : new ArrayList<>();
        this.header = (header != null) ? header : new ArrayList<>();
        this.rows = (rows != null) ? rows : new ArrayList<>();
    }

    public static String concatHor(Table... tables) {
        String[][] tablesArray = new String[tables.length][];
        int maxRowQty = 0;
        StringBuilder toReturn = new StringBuilder();
        for (int i = 0; i < tables.length; i++) {
            tablesArray[i] = tables[i].toString().split("\n");
            if (tablesArray[i].length > maxRowQty) maxRowQty = tablesArray[i].length;
        }
        for (int i = 0; i < maxRowQty; i++) {
            for (int j = 0; j < tables.length; j++) {
                if (i > tablesArray[j].length - 1)
                    toReturn.append(stringFromChar(' ', tablesArray[j][0].length()));
                else toReturn.append(tablesArray[j][i]);
            }
            toReturn.append("\n");
        }
        toReturn.setLength(toReturn.length() - 1);
        return toReturn.toString();
    }

    public static String concatHor(Table table, String str) {
        String[] tableAr = table.toString().split("\n");
        String[] stringAr = str.split("\n");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < Math.max(tableAr.length, stringAr.length); i++) {
            if (i >= tableAr.length) {
                result.append(stringFromChar(' ', table.getTableWidth()));
            } else {
                result.append(tableAr[i]);
            }
            if (i >= stringAr.length) {
                result.append("");
            } else {
                result.append(stringAr[i]);
            }
            result.append("\n");
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }

    public static String concatVer(Table... tables) {
        int maxWidth = maximalTableWidth(tables);
        StringBuilder result = new StringBuilder();
        StringBuilder buf;
        String[] toString;
        String toAdd;
        int delta;

        for (Table tb : tables) {
            delta = maxWidth - tb.getTableWidth();
            if (delta == 0) {
                result.append(tb.toString()).append("\n");
                continue;
            }
            toAdd = stringFromChar(' ', delta);
            toString = tb.toString().split("\n");
            for (String row : toString) {
                buf = new StringBuilder(row);
                buf.insert(buf.length(), toAdd).append("\n");
                result.append(buf);
            }
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }

    public static void fitTableWidths(Table... tables) {
        int maxWidth = maximalTableWidth(tables);
        for (Table tb : tables) {
            tb.fitTableWidthTo(maxWidth);
        }
    }

    public static int maximalTableWidth(Table... tables) {
        int maxWidth = 0;
        int curWidth;
        for (Table tb : tables) {
            curWidth = tb.getTableWidth();
            if (maxWidth < curWidth) {
                maxWidth = curWidth;
            }
        }
        return maxWidth;
    }

    public void fitColumnWidthsToContent(int... columns) {
        if (columns == null) {
            return;
        }
        if (!validated) {
            validateData();
        }
        resetCache();
        if (fitFlags == null) {
            fitFlags = new boolean[widths.size()];
        }
        if (columns.length == 0) {
            for (int i = 0; i < fitFlags.length; i++) {
                fitFlags[i] = true;
            }
        } else {
            for (int curColumn : columns) {
                if (curColumn < fitFlags.length && curColumn >= 0) {
                    fitFlags[curColumn] = true;
                }
            }
        }
        fitColumnWidthsToContent();
    }

    public void fitTableWidthTo(int val) {
        int tableWidth = getTableWidth();
        int columnIndex;

        if (val <= tableWidth) {
            return;
        }
        resetCache();
        if (widths.size() == 1) {
            while (tableWidth++ < val) {
                widths.set(0, widths.get(0) + 1);
            }
            return;
        }
        List<Integer> indexList = new ArrayList<>();
        for (int i = 0; i < widths.size(); i++) {
            indexList.add(i);
        }
        indexList.sort((o1, o2) -> widths.get(o1) - widths.get(o2));
        for (int i = 1; i < widths.size(); i++) {
            while (widths.get(indexList.get(i)) > widths.get(indexList.get(i - 1))) {
                for (int j = i - 1; j >= 0; j--) {
                    columnIndex = indexList.get(j);
                    widths.set(columnIndex, widths.get(columnIndex) + 1);
                    if (++tableWidth == val) {
                        return;
                    }
                }
            }
            if (i == widths.size() - 1) {
                columnIndex = indexList.get(i);
                widths.set(columnIndex, widths.get(columnIndex) + 1);
                if (++tableWidth == val) {
                    return;
                }
                i--;
            }
        }
    }

    public int getTableWidth() {
        if (!validated) {
            validateData();
        }
        int result = 0;
        for (Integer col : widths) {
            result += col;
        }
        return (result == 0 && widths.size() == 1)
                ? 2
                : result + widths.size() + 1;
    }

    public void setHeader(List<String> header) {
        if (header == null) {
            return;
        }
        resetCache();
        validated = false;
        this.header = header;
    }

    public void setHorShift(int horShift) {
        if (horShift < 0) {
            return;
        }
        if (this.horShift != horShift) {
            this.horShift = horShift;
            resetCache();
        }
    }

    public void setRows(List<List<String>> rows) {
        if (rows == null) {
            return;
        }
        resetCache();
        validated = false;
        this.rows = rows;
    }

    public void setTag(String tag) {
        if (tag != null) {
            resetCache();
        }
        this.tag = tag;
    }

    public void setVerShift(int verShift) {
        if (verShift < 0) {
            return;
        }
        if (this.verShift != verShift) {
            this.verShift = verShift;
            resetCache();
        }
    }

    public void setWidths(List<Integer> widths) {
        if (widths == null) {
            return;
        }
        resetCache();
        validated = false;
        this.widths = widths;
    }

    @Override
    public String toString() {
        if (toPrint != null) {
            return toPrint.toString();
        }
        if (!validated) {
            validateData();
        }
        toPrint = new StringBuilder();
        if (verShift > 0) {
            verShift();
        }
        printSymbolRow('-');
        if (tag != null && tag.length() != 0) printTag();
        printRowWithSymbolWrap(header);
        printSymbolRow('-');
        for (List<String> al : rows) {
            printRowWithWordWrap(al);
        }
        printSymbolRow('-');
        toPrint.setLength(toPrint.length() - 1);
        if (horShift > 0) {
            horShift();
        }
        return toPrint.toString();
    }

    protected List<List<String>> columnsToRows(List<List<String>> cols) {
        List<List<String>> rows = new ArrayList<>();

        List<String> row;
        int height = calcTableHeight(cols);

        for (int i = 0; i < height; i++) {
            row = new ArrayList<>();
            for (List<String> col : cols) {
                if (col.size() <= i) {
                    row.add("");
                } else {
                    row.add(col.get(i));
                }
            }
            rows.add(row);
        }
        return rows;
    }

    private static String stringFromChar(char pat, int length) {
        StringBuilder toReturn = new StringBuilder();
        for (int i = 0; i < length; i++) toReturn.append(pat);
        return toReturn.toString();
    }

    private int calcTableHeight(List<List<String>> cols) {
        int height = 0;
        int colHeight;

        for (List<String> col : cols) {
            colHeight = col.size();
            height = (height < colHeight ? colHeight : height);
        }
        return height;
    }

    private void fitColumnWidthsToContent() {
        if (fitFlags == null) {
            return;
        }
        for (int i = 0; i < widths.size(); i++) {
            if (fitFlags[i]) {
                int columnWidth = widths.get(i);
                for (List<String> row : rows) {
                    if (row.isEmpty()) {
                        continue;
                    }
                    if (columnWidth < row.get(i).length()) {
                        columnWidth = row.get(i).length();
                    }
                }
                widths.set(i, columnWidth);
            }
        }
    }

    private int getWordBegin(int currIndx, String strToSearchIn) {
        if (currIndx == 0) return currIndx;
        if ((currIndx < strToSearchIn.length() - 1) && (strToSearchIn.charAt(currIndx) == ' ')) {
            return currIndx + 1;
        }
        return currIndx;
    }

    private int getWordEnd(int currIndx, int idxOfPattMember, String strToSearchIn) {
        if (strToSearchIn.charAt(currIndx) == ' ') return currIndx - 1;
        if ((strToSearchIn.charAt(currIndx) != ' ') && (strToSearchIn.charAt(currIndx + 1) == ' ')) return currIndx;
        int counter = 0;
        while (counter < widths.get(idxOfPattMember) - 2) {
            if (strToSearchIn.charAt(currIndx - counter - 1) == ' ') {
                return currIndx - counter - 2;
            }
            counter++;
        }
        return currIndx;
    }

    private void horShift() {
        String toAdd = stringFromChar(' ', horShift);
        toPrint.insert(0, toAdd);
        Matcher rowMatcher = Pattern.compile("\n").matcher(toPrint);
        int index = 0;
        while (rowMatcher.find(index)) {
            index = rowMatcher.start() + 1;
            toPrint.insert(index, toAdd);
        }
    }

    private void printRowWithSymbolWrap(List<String> array) {
        StringBuilder buf = new StringBuilder();
        int lineCounter = 1;
        boolean isMoreLines = true;
        while (isMoreLines) {
            toPrint.append("|");
            isMoreLines = false;
            for (int i = 0; i < array.size(); i++) {
                buf.setLength(0);
                int index1 = widths.get(i) != 0 ? (lineCounter - 1) * widths.get(i) : 0;
                int index2 = lineCounter * widths.get(i) - 1;
                int endOfWordIndex = array.get(i).length() - 1;
                if ((index1 > endOfWordIndex) || ((widths.get(i) == 0))) {
                    buf.setLength(widths.get(i));
                } else if ((index1 <= endOfWordIndex) && (index2 > endOfWordIndex)) {
                    int dif = index2 - endOfWordIndex;
                    buf.setLength(dif);
                    buf.append(array.get(i).substring(index1));
                } else if (index2 <= endOfWordIndex) {
                    buf.append(array.get(i).substring(index1, index2 + 1));
                    isMoreLines = index2 != endOfWordIndex || isMoreLines;
                }
                toPrint.append(buf).append("|");
            }
            toPrint.append("\n");
            lineCounter++;
        }
    }

    private void printRowWithWordWrap(List<String> array) {
        if (array.isEmpty()) {
            printSymbolRow('-');
            return;
        }
        StringBuilder buf = new StringBuilder();
        int[] indexes = new int[array.size()];
        for (int i = 0; i < indexes.length; i++)
            indexes[i] = -1;
        int index1, index2;
        boolean isMoreLines = true;
        while (isMoreLines) {
            isMoreLines = false;
            toPrint.append("|");
            for (int i = 0; i < array.size(); i++, toPrint.append(buf)
                    .append("|")) {
                buf.setLength(0);
                index1 = getWordBegin(indexes[i] + 1, array.get(i));
                if ((index1 > array.get(i).length() - 1)
                        || ((widths.get(i) == 0))) {
                    buf.setLength(widths.get(i));
                    continue;
                }
                index2 = index1 + widths.get(i) - 1;
                if (index2 >= array.get(i).length() - 1) {
                    int dif = index2 - (array.get(i).length() - 1);
                    buf.setLength(dif);
                    buf.append(array.get(i).substring(index1,
                            array.get(i).length()));
                    indexes[i] = index2;
                } else if (index2 < array.get(i).length() - 1) {
                    isMoreLines = true;
                    int dif = index2 - (getWordEnd(index2, i, array.get(i)));
                    buf.setLength(dif);
                    buf.append(array.get(i).substring(index1, index2 + 1 - dif));
                    indexes[i] = getWordEnd(index2, i, array.get(i));
                }
            }
            toPrint.append("\n");
        }
    }

    private void printSymbolRow(char symb) {
        toPrint.append("+");
        for (Integer num : widths) {
            for (int i = 0; i < num; i++) {
                toPrint.append(symb);
            }
            toPrint.append("+");
        }
        toPrint.append("\n");
    }

    private void printTag() {
        StringBuilder buf = new StringBuilder();
        for (Integer val : widths) {
            for (int i = 0; i < val; i++) {
                buf.append(" ");
            }
            buf.append(" ");
        }
        if ((buf.length() - 2) < tag.length()) return;
        buf.setLength(buf.length() - 2 - tag.length());
        buf.insert(0, "|").append(tag).append(" |\n");
        toPrint.append(buf);
        printSymbolRow('-');
    }

    private void resetCache() {
        toPrint = null;
    }

    private void setEmptyRows() {
        rows = new ArrayList<>();
        ArrayList<String> row = new ArrayList<>();
        for (int i = 0; i < widths.size(); i++) {
            row.add("");
        }
        rows.add(row);
    }

    private void setEmptyTable() {
        widths = Arrays.asList(0);
        header = Arrays.asList("");
        setEmptyRows();
    }

    private void validateData() {
        validated = true;
        if (widths.isEmpty() && header.isEmpty()) {
            setEmptyTable();
            return;
        }
        for (int i = 0; i < widths.size(); i++) {
            if (widths.get(i) < 0) {
                widths.set(i, 0);
            }
        }
        if (widths.size() != header.size()) {
            int limit = Math.max(widths.size(), header.size());
            for (int i = 0; i < limit; i++) {
                if (i >= widths.size()) {
                    widths.add(header.get(i).length());
                }
                if (i >= header.size()) {
                    header.add("");
                }
            }
        }
        if (rows.isEmpty()) {
            setEmptyRows();
        }
    }

    private void verShift() {
        int tableWidth = 1;
        for (Integer wid : widths) {
            tableWidth += wid + 1;
        }
        for (int i = 0; i < verShift; i++) {
            toPrint.append(stringFromChar(' ', tableWidth)).append("\n");
        }
    }
}