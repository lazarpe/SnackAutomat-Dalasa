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

    private int height;
    private int width;

    public void run() {
        Product[][] vendingMachine = new Product[height][width];

        ArrayList<Product>products = new ArrayList<>();


    }

    public VendingMachine(int height, int width) {
        this.height = height;
        this.width = width;
    }
}
