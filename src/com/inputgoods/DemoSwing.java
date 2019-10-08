package com.inputgoods;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DemoSwing implements ActionListener {
    private JTextField T = new JTextField(30);
    private JTextField T1 = new JTextField(30);
    private JFrame f = new JFrame("Demo");
    private JPanel p = new JPanel();
    private JButton B = new JButton("View");
    // Static variable
    static String N, S;

    public DemoSwing() {

        f.setVisible(true);
        f.setSize(500, 500);

        p.add(new JLabel("Name"));
        T.setEditable(false);
        p.add(T);

        p.add(new JLabel("Surname"));
        T1.setEditable(false);
        p.add(T1);

        B.addActionListener(this);
        p.add(B);

        f.add(p);
        // JFrame f.setLayout(new FlowLayout()); f.setSize(300,100);
        // f.setVisible(true);


    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == B) {
            T.setText(Demo.Name);
            T1.setText(Demo.Surname);
        }
    }
}