
package com.sales_model;

//Line data content (items, fees, count, and invoice)
public class LineContant {
    private String item;
    private double fees;
    private int count;
    private InvoicesClass invoice;

    public LineContant() {
    }

    public LineContant(String item, double fees, int count, InvoicesClass invoice) {
        this.item = item;
        this.fees = fees;
        this.count = count;
        this.invoice = invoice;
    }

    // total amount for each line
    public double getLineTotal() {
        return fees * count;
    }
    
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
     //count the fees
    public double getfees() {
        return fees;
    }

    public void setfees(double fees) {
        this.fees = fees;
    }

    @Override
    // line content after return it 
    public String toString() {
        return "Line{" + "number=" + invoice.getNumber() + ", item=" + item + ", fees=" + fees + ", count=" + count + '}';
    }
     // get the invoice
    public InvoicesClass getInvoice() {
        return invoice;
    }
        //get the invoice as CSV file
    public String getAsCSV() {
        return invoice.getNumber() + "," + item + "," + fees + "," + count;
    }
    
}
