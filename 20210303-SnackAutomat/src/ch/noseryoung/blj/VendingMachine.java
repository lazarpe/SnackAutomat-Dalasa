package ch.noseryoung.blj;

import java.util.ArrayList;

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

    Product[][] addedProducts;

    public void run() {
        addedProducts = new Product[height][width];

        ArrayList<Product>products = new ArrayList<>();

        fillVendingMachine();

    }

    public void fillVendingMachine() {
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                addedProducts[i][j] = new Product("Test", 1, 1, 1);
            }
        }
    }

    public VendingMachine(int height, int width) {
        this.height = height;
        this.width = width;
    }
}
