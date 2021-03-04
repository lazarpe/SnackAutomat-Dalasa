package ch.noseryoung.blj;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Float.parseFloat;

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

    private final int height;
    private final int width;
    private final int min = 1;
    private final int max = 10;
    String[] name = {"Cola", "Icetea", "Fanta", "Chocolate", "Chips", "Crackers", "Pudding", "Milk", "Vanillecake", "Red Bull", "M & M's", "Maltesers", "Water", "Monster Energy", "Snickers", "Twixx", "Mars", "Energy Bar", "Kägi Fret", "Donut", "Waffles", "Gummy Bears", "Coffee", "Jelly Babies", "Rivella", "Cola Zero", "Capri Sonne", "Apple spritzer"};
    ArrayList<Product> products = new ArrayList<>();

    Product[][] addedProducts;

    public void run() {
        fillVendingMachine();

    }

    public void fillVendingMachine() {
        addedProducts = new Product[height][width];
        Random generate = new Random();
        int dimensions = height * width;

        for (int k = height * width; k > 0; k--) {
            Product p1 = new Product(name[generate.nextInt(name.length)], (min + generate.nextFloat() * (max - min)), dimensions - k, generate.nextInt(100));

            DecimalFormat df = new DecimalFormat("0.00");
            double f = min + Math.random() * (max - min);
            f = Double.parseDouble(df.format(f));

            p1.setPrice(f);

            products.add(p1);
        }

        int i = 0;
        for (int k = 0; k < width; k++) {
            for (int l = 0; l < height; l++) {
                addedProducts[l][k] = products.get(i);
                i++;
            }
        }

        i = 0;
    }

    public void printProducts() {
        int i = 0;
        for (int k = 0; k < width; k++) {
            for (int l = 0; l < height; l++) {
                System.out.println(products.get(i).getName() + ": " + products.get(i).getAmount());
                i++;
            }
        }
    }

    public VendingMachine(int height, int width) {
        this.height = height;
        this.width = width;
    }
}
