package ch.noseryoung.blj;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/****
 ---------------------------------------------------------------------
 Anwendung: Objektbasiert Programmieren NoserYoung
 Author: Davide
 Datum: 03.03.2021
 Zeit: 15:11
 Projekt: 20210303-SnackAutomat
 Programm: Java Programm
 Beschreibung:
 ----------------------------------------------------------------------
 ***/
public class VendingMachine {

    private final int width;
    private final int height;
    private final int min = 1;
    private final int max = 10;
    private int number;
    private int payedproduct;  // after the payment
    private final long secretKey = 86420; // long because it's unsigned
    Scanner sc = new Scanner(System.in);
    FileWriter myWriter;
    String fileName = "productAmount.txt";

    String[] productName = {
            "Cola", "Ice Tea", "Fanta", "Chocolate", "Chips", "Crackers", "Pudding", "Milk", "Vanillecake",
            "Red Bull", "M & M's", "Maltesers", "Water", "Monster Energy", "Snickers", "Twixx", "Mars",
            "Energy Bar", "Kägi Fret", "Donut", "Waffles", "Gummy Bears", "Coffee", "Jelly Babies", "Rivella",
            "Cola Zero", "Capri Sun", "Apple spritzer"};
    ArrayList<Product> products = new ArrayList<>();
    Product[][] addedProducts;

    public void run() {
        printVendingMachine();
        int inputForSecretMethods;
        number = enterCode();
        if (checkIfNumIsShutDown(number)) {
            System.exit(1);
        } else if (checkSecretKey(number)) {
            System.out.println("You found out the secret");
            System.out.println("Change item price \t\t[1]");
            System.out.println("Change product \t\t\t[2]");
            System.out.println("Refill vending machine \t[3]");
            System.out.print("Choose: ");
            inputForSecretMethods = sc.nextInt();
            switch (inputForSecretMethods) {
                case 1:
                    changeProductPrice(products);
                    break;
                case 2:
                    System.out.println("Change product...");
                    break;
                case 3:
                    refillVendingMachine();
                    System.out.println("Vending machine is getting filled");
                    sleep(4000);
                    break;
            }
        } else if (number < width * height || number >= 0) {
            payedproduct = Money.displaySingleProductPrice(number, products);
            if(payedproduct == -1) {/*if payed product is -1 then do nothing*/ }
            else{
                giveOutProducts(payedproduct);
            }
        }
    }

    public void fillVendingMachine() {
        addedProducts = new Product[width][height];
        Random generate = new Random();
        int dimensions = width * height;

        for (int k = width * height; k > 0; k--) {
            Product p1 = new Product(productName[generate.nextInt(productName.length)], (min + generate.nextFloat() *
                    (max - min)), dimensions - k, generate.nextInt(10));

            DecimalFormat df = new DecimalFormat("0.00");
            double f = min + Math.random() * (max - min);
            f = Double.parseDouble(df.format(f));
            p1.setPrice(f);
            products.add(p1);
        }

        int i = 0;
        for (int k = 0; k < height; k++) {
            for (int l = 0; l < width; l++) {
                addedProducts[l][k] = products.get(i);
                i++;
            }
        }
    }

    public void printProducts(boolean advanced) {
        for (int k = 0; k < height; k++) {
            for (int l = 0; l < width; l++) {
                if (advanced) {
                    if (addedProducts[l][k].getProduct_code() < 10) {
                        System.out.print(addedProducts[l][k].getProduct_code() + "  | " + addedProducts[l][k].getName() + ": " +
                                addedProducts[l][k].getAmount());
                    } else {
                        System.out.print(addedProducts[l][k].getProduct_code() + " | " + addedProducts[l][k].getName() + ": " +
                                addedProducts[l][k].getAmount());
                    }
                } else {
                    if (addedProducts[l][k].getProduct_code() < 10) {
                        System.out.print(addedProducts[l][k].getProduct_code() + "  | " + addedProducts[l][k].getName());
                    } else {
                        System.out.print(addedProducts[l][k].getProduct_code() + " | " + addedProducts[l][k].getName());
                    }
                }
                printPrices(l, k);
            }
        }
    }

    public void refillVendingMachine() { // ----- SECRET KEY -----
        int i = 0;
        for (int k = 0; k < height; k++) {
            for (int l = 0; l < width; l++) {
                if (addedProducts[l][k].getAmount() < 3) {
                    i++;
                }
            }
        }

        if (i != 0) {
            for (int k = 0; k < height; k++) {
                for (int l = 0; l < width; l++) {
                    Product product = new Product(addedProducts[l][k].getName(), addedProducts[l][k].getPrice(), addedProducts[l][k].getProduct_code(), 9);
                    addedProducts[l][k] = product;
                }
            }
        }
    }

    public void printPrices(int l, int k) {
        for (int j = addedProducts[l][k].getName().length(); j < 15; j++) {
            System.out.print(" ");
        }

        System.out.print("| ");
        System.out.println(addedProducts[l][k].getPrice() + " €");
    }

    public void printVendingMachine() {
        System.out.print("╔");
        for (int m = 1; m < width; m++) {
            System.out.print("══╦");
        }
        System.out.println("══╗");

        for (int k = 0; k < height; k++) {
            for (int l = 0; l < width; l++) {
                if (addedProducts[l][k].getProduct_code() < 10) {
                    System.out.print("║ ");
                } else {
                    System.out.print("║");
                }

                if (addedProducts[l][k].getAmount() != 0) {
                    System.out.print(addedProducts[l][k].getProduct_code());
                } else if (addedProducts[l][k].getProduct_code() < 10) {
                    System.out.print(" ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println("║");

            if (k + 1 < height) {
                System.out.print("╠");
                for (int m = 1; m < width; m++) {
                    System.out.print("══╬");
                }
                System.out.println("══╣");
            }
        }

        System.out.print("╚");
        for (int m = 1; m < width; m++) {
            System.out.print("══╩");
        }
        System.out.println("══╝");

        printProducts(false);
    }

    public int enterCode() {
        int number;
        while (true) {
            try {
                number = sc.nextInt();
                break;
            } catch (Exception e) {
                System.out.println("Please try again");
                sc.nextLine();
            }
        }
        return number;
    }

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean checkIfNumIsShutDown(int num) {
        if (num == 9999) {
            System.out.println("System is shutting down...");
            sleep(3000);
            return true;
        } else {
            return false;
        }
    }

    public void printToFile() {
        try {
            myWriter = new FileWriter("productAmount.txt", false);

            String stringToFile;
            double doubleToFile;
            int intToFile;

            for (int k = 0; k < height; k++) {
                for (int l = 0; l < width; l++) {
                    stringToFile = addedProducts[l][k].getName();
                    myWriter.write(stringToFile + "\n");
                    doubleToFile = addedProducts[l][k].getPrice();
                    myWriter.write(doubleToFile + "\n");
                    intToFile = addedProducts[l][k].getProduct_code();
                    myWriter.write(intToFile + "\n");
                    intToFile = addedProducts[l][k].getAmount();
                    myWriter.write(intToFile + "\n");
                }
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void loadFromFile() { // ----- SECRET KEY -----

        File file = new File(fileName);

        if (!file.canRead() || !file.isFile()) {
            System.exit(0);
        }

        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(fileName));
            String line = null;
            for (int k = 0; k < height; k++) {
                for (int l = 0; l < width; l++) {
                    int i = 0;
                    while (i < 4) {
                        line = in.readLine();
                        switch (i) {
                            case 0 -> addedProducts[l][k].setName(line);
                            case 1 -> addedProducts[l][k].setPrice(Double.parseDouble(line));
                            case 2 -> addedProducts[l][k].setProduct_code(Integer.parseInt(line));
                            case 3 -> addedProducts[l][k].setAmount(Integer.parseInt(line));
                        }
                        i++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                }
        }
    }

    public boolean checkSecretKey(int num) {
        return num == this.secretKey;
    }

    public void giveOutProducts(int number) {
        if (products.get(number).getAmount() > 0) {
            products.get(number).setAmount(products.get(number).getAmount() - 1);
            System.out.println("Your Product is on the way!");

            System.out.println("\n\n");


            System.out.print("╔");
            for (int j = 0; j < 17; j++) {
                System.out.print("═");
            }
            System.out.println("╗");
            System.out.print("║");
            System.out.print(" " + products.get(number).getName());
            for (int i = products.get(number).getName().length(); i < 16; i++) {
                System.out.print(" ");
            }
            System.out.println("║");
            System.out.print("╚");
            for (int k = 0; k < 17; k++) {
                System.out.print("═");
            }
            System.out.println("╝");

        } else {
            System.out.println("This products is out of stock, return later for more!");
        }
        sleep(3000);
    }

    public void changeProductPrice(ArrayList<Product> Products) { // ----- SECRET KEY -----
        char answerToChangePrice = ' ';
        double newPrice = 0;
        System.out.println("You can change the price now...");
        printProducts(true);
        int number = 0;
        while (true) {
            try {
                System.out.print("\nWhat's the product number of the price you want to change: ");
                number = sc.nextInt();
                System.out.println("You entered this number: " + number + "\nProduct name: " + Products.get(number).getName());
                break;
            } catch (Exception e) {
                System.out.print("Something went wrong with the product number. Try again.\n");
                sc.nextLine();
            }
        }
        while (true) {
            try {
                System.out.println("Are you sure you want to change the price of this product? (Y / N): ");
                answerToChangePrice = sc.next().charAt(0);
                if (answerToChangePrice == 'y' || answerToChangePrice == 'Y' || answerToChangePrice == 'n' ||
                        answerToChangePrice == 'N') {
                    break;
                } else {
                    System.out.println("Please enter a valid input!");
                }
            } catch (Exception e) {
                System.out.print("Something went wrong with your answer. Try again.\n");
                sc.nextLine();
            }
        }
        while (true) {
            try {
                if (answerToChangePrice == 'Y' || answerToChangePrice == 'y') {
                    System.out.print("Enter the new price: ");
                    newPrice = sc.nextDouble();
                    break;
                } else {
                    System.out.println("Ok leaving secret methods...");
                }
            } catch (Exception e) {
                System.out.println("Something went wrong with your answer. Try again.\n");
                sc.nextLine();
            }
        }
        while (true) {
            try {
                Products.get(number).setPrice(newPrice);
                System.out.println("You successfully changed the price to: " + Products.get(number).getPrice());
                break;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Could not change the price...");
                sc.nextLine();
            }
        }

        int i = 0;
        for (int k = 0; k < height; k++) {
            for (int l = 0; l < width; l++) {
                addedProducts[l][k] = products.get(i);
                i++;
            }
        }
    }

    public VendingMachine(int width, int height) {
        this.width = width;
        this.height = height;
    }
}