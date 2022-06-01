
package com.sales_model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class InvoicesTables extends AbstractTableModel {
    private ArrayList<InvoicesClass> invoices;
    private String[] columns = {"ID", "Date", "Client Name", "The Total Amount"};
    
    
        // create the invoices tables 
    public InvoicesTables(ArrayList<InvoicesClass> invoices) {
        this.invoices = invoices;
    }
       
        // for the columns
    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
    
    // for the rows
    @Override
    public int getRowCount() {
        return invoices.size();
    }

         //get the invoice
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoicesClass invoice = invoices.get(rowIndex);
        
        switch (columnIndex) {
            case 0: return invoice.getNumber();
            case 1: return invoice.getDate();
            case 2: return invoice.getClient();
            case 3: return invoice.getInvoiceTotal();
            default : return "";
        }
    }
}
