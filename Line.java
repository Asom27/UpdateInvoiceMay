
package com.sales_view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class Line extends JDialog{
    private JTextField itemNameField;
    private JTextField itemCountField;
    private JTextField itemPriceField;
    private JLabel itemNameLbl;
    private JLabel itemCountLbl;
    private JLabel itemPriceLbl;
    private JButton okBtn;
    private JButton cancelBtn;
    
    public Line(InvoiceFrame frame) {
            // item name field and label
        itemNameField = new JTextField(20);
        itemNameLbl = new JLabel("Item Name");
             // item count name and label
        itemCountField = new JTextField(20);
        itemCountLbl = new JLabel("Item Count");
                    // item price name and label 
        itemPriceField = new JTextField(20);
        itemPriceLbl = new JLabel("Item Price");
               //ok and cancel buttons
        okBtn = new JButton("OK");
        cancelBtn = new JButton("Cancel");
                   
        okBtn.setActionCommand("createLineOK");
        cancelBtn.setActionCommand("createLineCancel");
        
        okBtn.addActionListener(frame.getController());
        cancelBtn.addActionListener(frame.getController());
        setLayout(new GridLayout(4, 2));
             // add item, name, and price labels and fields
        add(itemNameLbl);
        add(itemNameField);
        add(itemCountLbl);
        add(itemCountField);
        add(itemPriceLbl);
        add(itemPriceField);
        add(okBtn);
        add(cancelBtn);
        
        pack();
    }
                  // item name field
    public JTextField getItemNameField() {
        return itemNameField;
    }
                 // item count field
    public JTextField getItemCountField() {
        return itemCountField;
    }
                //item price field
    public JTextField getItemPriceField() {
        return itemPriceField;
    }
}
