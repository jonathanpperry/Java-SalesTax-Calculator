package com.inputgoods;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.midi.SysexMessage;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import static java.lang.System.err;

class Demo {

    Scanner scan;
    static String Name, Surname;

    public void open() throws IOException {
        String current = new java.io.File( "." ).getCanonicalPath();
        System.out.println("Current dir:"+current);


        try {
            scan = new Scanner(new File("./src/com/inputgoods/input1.txt"));
            System.out.println("File opened successfully!");
        } catch (FileNotFoundException e) {
            System.out.println("Error in opening file. Please check the directory path.");
        }
    }

    public void Read() {
        do {
            Name = scan.next();

            if (scan.hasNext())
                Surname = scan.next();

        } while (scan.hasNext());
        System.out.println(Name + Surname);

        scan.close();
    }

    public static void main(String[] args) throws IOException {
        new DemoSwing();

        Demo f = new Demo();
            f.open();
        f.Read();
    }

}