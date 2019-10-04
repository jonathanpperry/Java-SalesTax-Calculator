import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class SalesTax {

    public static void main(String[] args)throws Exception
    {
        // We need to provide file path as the parameter:
        File file = new File("./src/goods.txt");

        BufferedReader br = new BufferedReader(new FileReader(file));

        ArrayList goodsQuantities = new ArrayList<Integer>();
        ArrayList goodsImported = new ArrayList<Boolean>();
        ArrayList goodsNames = new ArrayList<String>();
        ArrayList goodsPrices = new ArrayList<Number>();

        double salesTaxes = 0;
        double totalPrice = 0;

        TypeDefs definitions = new TypeDefs();

        String st;

        int numOfItems = 0;
        // Add information to the arraylists
        while ((st = br.readLine()) != null) {
            boolean isExempt = false;
            String[] splitString = st.split(" ");
            goodsQuantities.add(Integer.parseInt(splitString[0]));
            int goodStartIndex;
            if (splitString[1] == "imported") {
                goodsImported.add(true);
                goodStartIndex = 2;
            } else {
                goodsImported.add(false);
                goodStartIndex = 1;
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
                    System.out.println("Book matched");
                    isExempt = true;
                }
            }
            for (int i=0; i<goodsNameArr.size(); i++) {
                if (Arrays.asList(definitions.food).contains(goodsNameArr.get(i))) {
                    System.out.println("Food matched");
                    isExempt = true;
                }
            }
            for (int i=0; i<goodsNameArr.size(); i++) {
                if (Arrays.asList(definitions.medical).contains(goodsNameArr.get(i))) {
                    System.out.println("Medical matched");
                    isExempt = true;
                }
            }

            // This is the p variable
            double thisGoodPrice = Double.parseDouble(splitString[splitString.length-1]);

            double taxCost = 0;
            if (isExempt) {
                 taxCost = (10 * thisGoodPrice) / 100;

            }
            System.out.println("Tax cost is: ");
            System.out.println(taxCost);
            totalPrice += thisGoodPrice;
            goodsPrices.add(thisGoodPrice);
            numOfItems++;
        }


        // Iterate through constructed lists and print the information
        for (int i=0; i<numOfItems; i++) {
            System.out.print(goodsQuantities.get(i));
            System.out.print(" ");
            System.out.print(goodsNames.get(i));
            System.out.print(": ");
            System.out.println(goodsPrices.get(i));
        }
        System.out.print("Sales Taxes: ");
        System.out.println("Print calculated tax here");
        System.out.print("Total: ");
        System.out.println(totalPrice);

    }

}
