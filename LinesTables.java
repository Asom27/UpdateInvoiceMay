
package com.sales_model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class LinesTables extends AbstractTableModel {

    private ArrayList<LineContant> lines;
    private String[] columns = {"No.", "item Name", "Item fees", "Count", "Item Total"};

    public LinesTables(ArrayList<LineContant> lines) {
        this.lines = lines;
    }

    public ArrayList<LineContant> getLines() {
        return lines;
    }
    
     //for the rows
    @Override
    public int getRowCount() {
        return lines.size();
    }
      
     //for the columns
    @Override
    public int getColumnCount() {
        return columns.length;
    }
      // for the column name
    @Override
    public String getColumnName(int x) {
        return columns[x];
    }
    
      //get the value
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        LineContant line = lines.get(rowIndex);
        
        switch(columnIndex) {
            case 0: return line.getInvoice().getNumber();
            case 1: return line.getItem();
            case 2: return line.getfees();
            case 3: return line.getCount();
            case 4: return line.getLineTotal();
            default : return "";
        }
    }
    
}
