package com.salestax;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SalesTaxesUI implements ActionListener {
    private JFrame f = new JFrame("Sales Tax Exercise");
    // Static variable
    private JTextArea goodsBeforeTax;
    private JTextPane goodsAfterTax;
    private JButton calculateButton;
    private JPanel mainPanel = new JPanel();

    public SalesTaxesUI() {

        mainPanel.add(goodsBeforeTax);
        mainPanel.add(goodsAfterTax);
        mainPanel.add(calculateButton);

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

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == calculateButton) {
            try {
                goodsAfterTax.setText(setGoodsBeforeTax());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}