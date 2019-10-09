package com.salestax;

import com.salestax.config.Config;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

class SalesTax {

    Config config = new Config();

    Scanner scan;
    static String InputGoods;
    String outputGoods;
    BufferedReader br;

    ArrayList goodsQuantities = new ArrayList<Integer>();
    ArrayList goodsImported = new ArrayList<Boolean>();
    ArrayList goodsNames = new ArrayList<String>();
    ArrayList goodsPrices = new ArrayList<Number>();

    double salesTaxes = 0;
    double totalPrice = 0;


    public void open() throws IOException {
        File file = null;
        try {
            file = new File(config.filePath());
            System.out.println("File opened successfully!");
        } catch (NullPointerException e) {
            System.out.println("Error in opening file. Please check the directory path.");
        }

        br = new BufferedReader(new FileReader(file));

    }

    public void ReadData() throws IOException {
        TypeDefs definitions = new TypeDefs();

        String st;

        int numOfItems = 0;
        // Add information to the arraylists
        while ((st = br.readLine()) != null) {
            boolean isExempt = false;
            boolean isImport = false;

            double exemptTax = 0;
            double importTax = 0;

            double taxCost = 0;
            String[] splitString = st.split(" ");
            goodsQuantities.add(Integer.parseInt(splitString[0]));
            int goodStartIndex = 1;

            if (splitString[1].equals("imported")) {
                goodsImported.add(true);
                isImport = true;
            } else {
                goodsImported.add(false);
            }
            int goodEndIndex = splitString.length-2;

            String goodsName = new String();
            ArrayList goodsNameArr = new ArrayList<String>();

            // Get goods name
            for (int i=goodStartIndex; i<goodEndIndex; i++) {
                // Add parts of the good name to an array to detect types later
                goodsNameArr.add(splitString[i]);
                goodsName = goodsName.concat(splitString[i]);
                if (i != goodEndIndex-1) {
                    goodsName = goodsName.concat(" ");
                }
            }

            // Force to lower for comparison purposes
            goodsName.toLowerCase();
            goodsNames.add(goodsName);

            // Find if good is exempt
            for (int i=0; i<goodsNameArr.size(); i++) {
                if (Arrays.asList(definitions.book).contains(goodsNameArr.get(i))) {
                    isExempt = true;
                }
            }
            for (int i=0; i<goodsNameArr.size(); i++) {
                if (Arrays.asList(definitions.food).contains(goodsNameArr.get(i))) {
                    isExempt = true;
                }
            }
            for (int i=0; i<goodsNameArr.size(); i++) {
                if (Arrays.asList(definitions.medical).contains(goodsNameArr.get(i))) {
                    isExempt = true;
                }
            }

            // This is the p variable
            double thisGoodPrice = Double.parseDouble(splitString[splitString.length-1]);

            // Only calculate sales tax if the good is not exempt
            if (!isExempt) {
                double unAdjtaxCost = (10 * thisGoodPrice)/100;

                double n = unAdjtaxCost % .05;
                // Add a small adjuster to ensure the format has the correct 2 digits
                double adj = .0501-n;

                exemptTax = unAdjtaxCost+adj;

                salesTaxes += exemptTax;
            }

            if (isImport) {
                double unAdjtaxCost = (5 * thisGoodPrice)/100;

                double n = unAdjtaxCost % .05;
                // Add a small adjuster to ensure the format has the correct 2 digits
                double adj = .0501-n;

                importTax = unAdjtaxCost+adj;

                // Add the tax cost to both the sales taxes and the goods price
                salesTaxes += importTax;
            }

            // This good's new price now includes all the taxes on it
            thisGoodPrice = thisGoodPrice + exemptTax + importTax;

            // Add this good's price (which includes tax) to the total
            totalPrice += thisGoodPrice;

            goodsPrices.add(thisGoodPrice);
            numOfItems++;
        }
    }

    public static void main(String[] args) throws IOException {
        // Create a new instance of the GUI for the sales tax exercise
        new SalesTaxesUI();

        SalesTax f = new SalesTax();
        f.open();
        f.ReadData();
    }

}