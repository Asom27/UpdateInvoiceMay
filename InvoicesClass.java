
package com.sales_model;

import java.util.ArrayList;

public class InvoicesClass {
    private int number;
    private String date;
    private String client;
    private ArrayList<LineContant> lines;
    
    public InvoicesClass() {
    }

    public InvoicesClass(int number, String date, String client) {
        this.number = number;
        this.date = date;
        this.client = client;
    }

    public double getInvoiceTotal() {
        double total = 0.0;
        for (LineContant line : getLines()) {
            total += line.getLineTotal();
        }
        return total;
    }
    
    public ArrayList<LineContant> getLines() {
        if (lines == null) {
            lines = new ArrayList<>();
        }
        return lines;
    }

    public String getClient() {
        return client;
    }
      //clients names 
    public void setClient(String customer) {
        this.client = customer;
    }
       // dates
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
       //numbers
     public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Invoice{" + "number=" + number + ", date=" + date + ", client=" + client + '}';
    }
    
    public String getAsCSV() {
        return number + "," + date + "," + client;
    }
    
}
