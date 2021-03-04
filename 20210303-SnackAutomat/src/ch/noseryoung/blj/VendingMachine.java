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
        fillVendingMachine();
        printVendingMachine();
        enterCode();
    }

    public void fillVendingMachine() {
        addedProducts = new Product[width][height];
        Random generate = new Random();
        int dimensions = width * height;

        for (int k = width * height; k > 0; k--) {
            Product p1 = new Product(productName[generate.nextInt(productName.length)], (min + generate.nextFloat() * (max - min)), dimensions - k, generate.nextInt(100));

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

        i = 0;
    }

    public void printProducts(boolean advanced) {
        int i = 0;
        for (int k = 0; k < height; k++) {
            for (int l = 0; l < width; l++) {
                if (advanced) {
                    if (products.get(i).getProduct_code() < 10) {
                        System.out.print(products.get(i).getProduct_code() + "  | " + products.get(i).getName() + ": " + products.get(i).getAmount());
                    } else {
                        System.out.print(products.get(i).getProduct_code() + " | " + products.get(i).getName() + ": " + products.get(i).getAmount());
                    }
                } else {
                    if (products.get(i).getProduct_code() < 10) {
                        System.out.print(products.get(i).getProduct_code() + "  | " + products.get(i).getName());
                    } else {
                        System.out.print(products.get(i).getProduct_code() + " | " + products.get(i).getName());
                    }
                }
                printPrices(i);
                i++;
            }
        }
    }

    public void printPrices(int i) {
        for (int j = products.get(i).getName().length(); j < 15; j++) {
            System.out.print(" ");
        }

        System.out.print("| ");
        System.out.println(products.get(i).getPrice() + " €");
    }

    public void printVendingMachine() {
        int j = 0;

        System.out.print("╔");
        for (int m = 1; m < width; m++) {
            System.out.print("══╦");
        }
        System.out.println("══╗");

        for (int k = 0; k < height; k++) {
            for (int l = 0; l < width; l++) {
                if (products.get(j).getProduct_code() < 10) {
                    System.out.print("║ ");
                } else {
                    System.out.print("║");
                }
                System.out.print(products.get(j).getProduct_code());
                j++;
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
        int number = 0;
        while (true) {
            try {
                number = sc.nextInt();
                break;
            } catch (Exception ignored) {
            }
        }
        return number;
    }

    public boolean checkIfNumIsShutDown(int num) {
        num = enterCode();
        if (num == 9999) {
            return true;
        } else {
            return false;
        }
    }
/*
    public boolean checkSecretKey(long secretKey) {
        while () {
            if () {
                return true;
            } else {
                return false;
            }
        }
    }
*/
    public VendingMachine(int width, int height) {
        this.width = width;
        this.height = height;
    }
}
