package com.abc.services;

import com.abc.models.Item;
import com.abc.models.Order;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class PrintService {
	
	private static final String HORIZONTAL_SEP = "-";
    private String verticalSep;
    private String joinSep;
    private String[] headers;
    private List<String[]> rows = new ArrayList<>();
    private boolean rightAlign;
    private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm");

    public PrintService() {
        setShowVerticalLines(false);
    }

    public void setRightAlign(boolean rightAlign) {
        this.rightAlign = rightAlign;
    }

    public void setShowVerticalLines(boolean showVerticalLines) {
        verticalSep = showVerticalLines ? "|" : "";
        joinSep = showVerticalLines ? "+" : " ";
    }

    public void setHeaders(List<String> headers) {
        this.headers = (String[]) headers.toArray();
    }

    public void addRow(String... cells) {
        rows.add(cells);
    }

    public void print() {
        int[] maxWidths = headers != null ?
                Arrays.stream(headers).mapToInt(String::length).toArray() : null;

        for (String[] cells : rows) {
            if (maxWidths == null) {
                maxWidths = new int[cells.length];
            }
            if (cells.length != maxWidths.length) {
                throw new IllegalArgumentException("Number of row-cells and headers should be consistent");
            }
            for (int i = 0; i < cells.length; i++) {
                maxWidths[i] = Math.max(maxWidths[i], cells[i].length());
            }
        }

        if (headers != null) {
            printLine(maxWidths);
            printRow(headers, maxWidths);
            printLine(maxWidths);
        }
        for (String[] cells : rows) {
            printRow(cells, maxWidths);
        }
        if (headers != null) {
            printLine(maxWidths);
        }
    }

    private void printLine(int[] columnWidths) {
        for (int i = 0; i < columnWidths.length; i++) {
            String line = String.join("", Collections.nCopies(columnWidths[i] +
                    verticalSep.length() + 1, HORIZONTAL_SEP));
            System.out.print(joinSep + line + (i == columnWidths.length - 1 ? joinSep : ""));
        }
        System.out.println();
    }

    private void printRow(String[] cells, int[] maxWidths) {
        for (int i = 0; i < cells.length; i++) {
            String s = cells[i];
            String verStrTemp = i == cells.length - 1 ? verticalSep : "";
            if (rightAlign) {
                System.out.printf("%s %" + maxWidths[i] + "s %s", verticalSep, s, verStrTemp);
            } else {
                System.out.printf("%s %-" + maxWidths[i] + "s %s", verticalSep, s, verStrTemp);
            }
        }
        System.out.println();
    }

    public void printValuesItems(List<String> headers, List<Item> items) {
        
       // setRightAlign(true);//if true then cell text is right aligned
        setShowVerticalLines(true);//if false (default) then no vertical lines are shown
        setHeaders(headers);//optional - if not used then there will be no header and horizontal lines
        items.forEach(item -> addRow(Integer.toString(item.getItemId()), item.getName(), item.getDescription(), Double.toString(item.getPrice())));
        print();
    }
    
    public void printValuesOrders(List<String> headers, List<Order> orders) {
        
        // setRightAlign(true);//if true then cell text is right aligned
         setShowVerticalLines(true);//if false (default) then no vertical lines are shown
         setHeaders(headers);//optional - if not used then there will be no header and horizontal lines
         for(Order order : orders) {
        	 List<Item> items = order.getItemsList();
        	 StringBuffer sb = new StringBuffer();
        	 for(int i = 0; i < items.size(); i++) {
        		 sb.append(items.get(i).getName());
        		 if(i != items.size()-1) {
        			 sb.append(",");
        		 }
        	 }
        	 addRow(Integer.toString(order.getOrderId()), Double.toString(order.getTotalBill()), dateFormat.format(order.getOrderedDate()), sb.toString());
         }
         print();
     }

}
