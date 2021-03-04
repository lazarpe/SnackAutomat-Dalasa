package ch.noseryoung.blj;

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
    private final long secretKey = 293836; // long because it's unsigned
    Scanner sc = new Scanner(System.in);
    String[] productName = {
            "Cola", "Icetea", "Fanta", "Chocolate", "Chips", "Crackers", "Pudding", "Milk", "Vanillecake",
            "Red Bull", "M & M's", "Maltesers", "Water", "Monster Energy", "Snickers", "Twixx", "Mars",
            "Energy Bar", "Kägi Fret", "Donut", "Waffles", "Gummy Bears", "Coffee", "Jelly Babies", "Rivella",
            "Cola Zero", "Capri Sonne", "Apple spritzer"};
    ArrayList<Product> products = new ArrayList<>();
    Product[][] addedProducts;

    public void run() {

        printVendingMachine();
        number = enterCode();
        if (checkIfNumIsShutDown(number)) {
            System.exit(1);
        } else if (checkSecretKey(number)) {
            System.out.println("You found out the secret");
        } else if (number == 1){
            refillVendingMachine();
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

    public void giveOutProducts() {

    }

    public void refillVendingMachine() {
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
                System.out.print(addedProducts[l][k].getProduct_code());
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
        System.out.println("First");
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

    public boolean checkSecretKey(int num) {
        return num == this.secretKey;
    }

    public VendingMachine(int width, int height) {
        this.width = width;
        this.height = height;
    }
}
