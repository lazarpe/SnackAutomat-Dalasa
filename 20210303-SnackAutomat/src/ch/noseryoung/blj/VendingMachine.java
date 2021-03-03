package ch.noseryoung.blj;

import java.util.ArrayList;
import java.util.Random;

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
    String[] name = {"John", "Marcus", "Susan", "Henry"};
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
            Product p1 = new Product(name[generate.nextInt(name.length)], generate.nextDouble()*10, generate.nextInt(dimensions-k), generate.nextInt(100));
            products.add(p1);
        }

        int i = 0;
        for (int k = 0; k < width; k++) {
            for (int l = 0; l < height; l++) {

                addedProducts[height][width] = products.get(i);
                i++;

            }

        }

        for (int k = 0; k < width; k++) {
            for (int l = 0; l < height; l++) {
                System.out.println(products.get(i).getName());
                i++;
            }
        }

    }

    public VendingMachine(int height, int width) {
        this.height = height;
        this.width = width;
    }
}
