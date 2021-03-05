package ch.noseryoung.blj;
import java.util.Scanner;
import java.util.ArrayList;

/****
 ---------------------------------------------------------------------
 Anwendung: Objektbasiert Programmieren NoserYoung
 Author: Davide
 Datum: 03.03.2021
 Zeit: 16:22
 Projekt: 20210303-SnackAutomat
 Programm: Java Programm
 Beschreibung:
 ----------------------------------------------------------------------
 ***/
public class Money {

    public Money(){
    }
    public static int processMoney(int usernumber, ArrayList<Product> Products){
        Scanner coinreader = new Scanner(System.in);
        double productprice = Products.get(usernumber).getPrice();  //temp price for calculating
        double usersbalance;                                        //temp amount
        System.out.println("==========================================================");
        System.out.println("Item number : " + usernumber);
        System.out.println("This item costs : " + Products.get(usernumber).getPrice());
        System.out.print("Please Enter Money : ");

        usersbalance = coinreader.nextDouble();

        //users credit balance

        System.out.println("==========================================================");

        return usernumber;
    }

    public static int calcMoney(){
        int empty = 0;  //this must get deleted
        return empty;
    }
}
