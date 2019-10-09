package com.salestax;

import com.salestax.config.Config;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

class SalesTax {

    Scanner scan;
    static String InputGoods;
    String outputGoods;
    BufferedReader br;

    public static void main(String[] args) throws IOException {

        // Create instance of the sales tax class
        SalesTax f = new SalesTax();

        // Create instance of the calculator
        Calculator calc = new Calculator();

        // Assign the correct information in the array lists
        calc.ReadData();
        calc.printData();

        // Create a new instance of the GUI for the sales tax exercise
        new SalesTaxesUI();

    }

}