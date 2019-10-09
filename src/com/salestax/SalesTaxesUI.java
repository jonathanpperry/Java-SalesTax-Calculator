package com.salestax;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SalesTaxesUI {
    private JFrame f = new JFrame("Sales Tax Exercise");

    // The UI Components used in the application
    private JTextArea goodsBeforeTax = new JTextArea(20, 20);
    private JTextArea goodsAfterTax = new JTextArea(20, 20);
    private JButton calculateButton;
    private JPanel mainPanel = new JPanel();

    // Gridbag Constrains object
    GridBagConstraints constraints = new GridBagConstraints();

    public String setGoodsBeforeTax() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/com/salestax/input1.txt"));

        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }


    public SalesTaxesUI() {

        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    goodsAfterTax.setText(setGoodsBeforeTax());
                } catch (IOException ex) {
                    System.out.println("Error setting the text");
                    ex.printStackTrace();
                }
            }
        } );

        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.BOTH;

        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        mainPanel.setLayout(new GridBagLayout());

        // Add the first text area in top left
        mainPanel.add(goodsBeforeTax, constraints);

        // Change grid x to put next text area to the right
        constraints. gridx = 1;
        mainPanel.add(goodsAfterTax, constraints);

        constraints.gridy = 1;
        mainPanel.add(calculateButton, constraints);

        JScrollPane sp = new JScrollPane(goodsBeforeTax, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        mainPanel.add(sp);

        f.add(mainPanel);

        f.setVisible(true);
        f.setSize(1000, 1000);

        // Set the text in the left area
        try {
            goodsBeforeTax.setText(setGoodsBeforeTax());
        } catch (IOException ex) {
            System.err.println("Something went wrong setting the text in the left area");
            ex.printStackTrace();
        }
    }

}