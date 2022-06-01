package com.sales_controller;

import com.sales_model.InvoicesClass;
import com.sales_model.InvoicesTables;
import com.sales_model.LineContant;
import com.sales_model.LinesTables;
import com.sales_view.Invoice;
import com.sales_view.InvoiceFrame;
import com.sales_view.Line;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ControllerClass implements ActionListener, ListSelectionListener {

    private InvoiceFrame f;
    private Invoice invoiceDialog;
    private Line lineDialog;

    public ControllerClass(InvoiceFrame frame) {
        this.f = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        System.out.println("Action: " + actionCommand);
        switch (actionCommand) {
            case "Load File":
                loadFile();
                break;
            case "Create New Invoice":
                createNewInvoice();
                break;
            case "Create New Item":
                createNewItem();
                break;
            case "Delete Invoice":
                deleteInvoice();
                break;
            case "createInvoiceCancel":
                createInvoiceCancel();
                break;
            case "Delete Item":
                deleteItem();
                break;
            case "createInvoiceOK":
                createInvoiceOK();
                break;
            
            case "createLineOK":
                createLineOK();
                break;
            case "createLineCancel":
                createLineCancel();
                break;
            case "Save File":
                saveFile();
                break;
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedIndex = f.getInvoiceTable().getSelectedRow();
        if (selectedIndex != -1) {
            System.out.println("You have selected row: " + selectedIndex);
            InvoicesClass currentInvoice = f.getInvoices().get(selectedIndex);
            f.getInvoiceNumLabel().setText("" + currentInvoice.getNumber());
            f.getInvoiceDateLabel().setText(currentInvoice.getDate());
            f.getCustomerNameLabel().setText(currentInvoice.getClient());
            f.getInvoiceTotalLabel().setText("" + currentInvoice.getInvoiceTotal());
            LinesTables linesTableModel = new LinesTables(currentInvoice.getLines());
            f.getLineTable().setModel(linesTableModel);
            linesTableModel.fireTableDataChanged();
        }
    }

    // file loading 
   private void loadFile() {
        JFileChooser fc = new JFileChooser();

        try {
            JOptionPane.showMessageDialog(f, "Select Invoice Header File",
                    "Notification Message", JOptionPane.INFORMATION_MESSAGE);
            int result = fc.showOpenDialog(f);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = fc.getSelectedFile();
                Path headerPath = Paths.get(headerFile.getAbsolutePath());
                List<String> headerLines = Files.readAllLines(headerPath);
                System.out.println("Invoices have been read");
      
                ArrayList<InvoicesClass> invoicesArray = new ArrayList<>();
                for (String headerLine : headerLines) {
                    try {
                        String[] headerParts = headerLine.split(",");
                        int invoiceNum = Integer.parseInt(headerParts[0]);
                        String invoiceDate = headerParts[1];
                        String customerName = headerParts[2];

                        InvoicesClass invoice = new InvoicesClass(invoiceNum, invoiceDate, customerName);
                        invoicesArray.add(invoice);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        
                    //error message for wrong line file format ( any file type rather than CSV )
                   
                        JOptionPane.showMessageDialog(f, "Error in line format", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                System.out.println("Checking ...");
                JOptionPane.showMessageDialog(f, "Please Select Invoice Line File",
                        "Notification Message", JOptionPane.INFORMATION_MESSAGE);
                result = fc.showOpenDialog(f);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File lineFile = fc.getSelectedFile();
                    Path linePath = Paths.get(lineFile.getAbsolutePath());
                    List<String> lineLines = Files.readAllLines(linePath);
                    System.out.println("Greate, the lines were successfully read !");
                    for (String lineLine : lineLines) {
                        try {
                            String lineParts[] = lineLine.split(",");
                            int invoiceNum = Integer.parseInt(lineParts[0]);
                            String itemName = lineParts[1];
                            double itemPrice = Double.parseDouble(lineParts[2]);
                            int count = Integer.parseInt(lineParts[3]);
                            InvoicesClass inv = null;
                            for (InvoicesClass invoice : invoicesArray) {
                                if (invoice.getNumber() == invoiceNum) {
                                    inv = invoice;
                                    break;
                                }
                            }

                            LineContant line = new LineContant(itemName, itemPrice, count, inv);
                            inv.getLines().add(line);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            
                            //Reminder to load only CSV file and error popup appear when try to choose fault file type
                            
                            JOptionPane.showMessageDialog(f, "line format error !", "Error", JOptionPane.ERROR_MESSAGE);
                                
                    
                        }
                    }
                    System.out.println("Checking ..");
                }
                f.setInvoices(invoicesArray);
                InvoicesTables invoicesTableModel = new InvoicesTables(invoicesArray);
                f.setInvoicesTableModel(invoicesTableModel);
                f.getInvoiceTable().setModel(invoicesTableModel);
                f.getInvoicesTableModel().fireTableDataChanged();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(f, "error in reading the file", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveFile() {
        ArrayList<InvoicesClass> invoices = f.getInvoices();
        String headers = "";
        String lines = "";
        for (InvoicesClass invoice : invoices) {
            String invCSV = invoice.getAsCSV();
            headers += invCSV;
            headers += "\n";

            for (LineContant line : invoice.getLines()) {
                String lineCSV = line.getAsCSV();
                lines += lineCSV;
                lines += "\n";
            }
        }
        System.out.println("Checking ..");
        
        try {
            JFileChooser fc = new JFileChooser();
            int result = fc.showSaveDialog(f);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = fc.getSelectedFile();
                FileWriter hfw = new FileWriter(headerFile);
                hfw.write(headers);
                hfw.flush();
                hfw.close();
                result = fc.showSaveDialog(f);
                JOptionPane.showMessageDialog(f, "successfully saved the files","Notification Message", JOptionPane.INFORMATION_MESSAGE);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File lineFile = fc.getSelectedFile();
                    FileWriter lfw = new FileWriter(lineFile);
                    lfw.write(lines);
                    lfw.flush();
                    lfw.close();
                }
            }
        } catch (Exception ex) {
            

        }
    }
      //creating new invoices
    private void createNewInvoice() {
        invoiceDialog = new Invoice(f);
        invoiceDialog.setVisible(true);
    }

     //creating new items
    private void createNewItem() {
        lineDialog = new Line(f);
        lineDialog.setVisible(true);
    }
       //delete the invoice
    private void deleteInvoice() {
        int selectedRow = f.getInvoiceTable().getSelectedRow();
        if (selectedRow != -1) {
            f.getInvoices().remove(selectedRow);
            f.getInvoicesTableModel().fireTableDataChanged();
        }
    }
       //delete the item
    private void deleteItem() {
        int selectedRow = f.getLineTable().getSelectedRow();

        if (selectedRow != -1) {
            LinesTables linesTableModel = (LinesTables) f.getLineTable().getModel();
            linesTableModel.getLines().remove(selectedRow);
            linesTableModel.fireTableDataChanged();
            f.getInvoicesTableModel().fireTableDataChanged();
        }
    }
         //cancel the invoice creation
    private void createInvoiceCancel() {
        invoiceDialog.setVisible(false);
        invoiceDialog.dispose();
        invoiceDialog = null;
    }
        // approve the invoice creation
    private void createInvoiceOK() {
        String date = invoiceDialog.getInvDateField().getText();
        String customer = invoiceDialog.getCustNameField().getText();
        int num = f.getNextInvoiceNum();
        try {
            String[] dateParts = date.split("-");  // 
            if (dateParts.length < 3) {
                JOptionPane.showMessageDialog(f, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int day = Integer.parseInt(dateParts[0]);
                int month = Integer.parseInt(dateParts[1]);
                int year = Integer.parseInt(dateParts[2]);
                if (day > 31 || month > 12) {
                    JOptionPane.showMessageDialog(f, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    InvoicesClass invoice = new InvoicesClass(num, date, customer);
                    f.getInvoices().add(invoice);
                    f.getInvoicesTableModel().fireTableDataChanged();
                    invoiceDialog.setVisible(false);
                    invoiceDialog.dispose();
                    invoiceDialog = null;
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(f, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void createLineOK() {
        String item = lineDialog.getItemNameField().getText();
        String countStr = lineDialog.getItemCountField().getText();
        String priceStr = lineDialog.getItemPriceField().getText();
        int count = Integer.parseInt(countStr);
        double price = Double.parseDouble(priceStr);
        int selectedInvoice = f.getInvoiceTable().getSelectedRow();
        if (selectedInvoice != -1) {
            InvoicesClass invoice = f.getInvoices().get(selectedInvoice);
            LineContant line = new LineContant(item, price, count, invoice);
            invoice.getLines().add(line);
            LinesTables linesTableModel = (LinesTables) f.getLineTable().getModel();
           
            linesTableModel.fireTableDataChanged();
            f.getInvoicesTableModel().fireTableDataChanged();
        }
        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null;
    }
      //cancel the lines creation
    private void createLineCancel() {
        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null;
    }

}
