package com.salestax;

import com.salestax.config.Config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class Calculator {

    // Store number of items and decimal format object in global state
    public int numOfItems = 0;
    DecimalFormat numberFormat = new DecimalFormat("#0.00");

    BufferedReader br;

    private ArrayList goodsQuantities = new ArrayList<Integer>();
    private ArrayList goodsImported = new ArrayList<Boolean>();
    private ArrayList goodsNames = new ArrayList<String>();
    private ArrayList goodsPrices = new ArrayList<Number>();

    double salesTaxes = 0;
    double totalPrice = 0;


    Config config = new Config();

    Calculator() throws IOException {
        // Open will assigned the buffered reader to the correct file based on config
        this.open();
    }

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


    public String setGoodsAfterTax() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(config.filePath()));

        try {
            StringBuilder sb = new StringBuilder();
            for (int i=0; i<numOfItems; i++) {
                sb.append(goodsQuantities.get(i));
                sb.append(" ");
                sb.append(goodsNames.get(i));
                sb.append(": ");
                sb.append(numberFormat.format(goodsPrices.get(i)));
                sb.append("\n");
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }

    public void printData() {
        System.out.print("Number of items is");
        System.out.println(numOfItems);

        // Iterate through constructed lists and print the information
        for (int i=0; i<numOfItems; i++) {
            System.out.print(goodsQuantities.get(i));
            System.out.print(" ");
            System.out.print(goodsNames.get(i));
            System.out.print(": ");
            System.out.println(numberFormat.format(goodsPrices.get(i)));
        }
    }


}
