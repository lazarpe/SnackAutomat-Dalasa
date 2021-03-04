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
        double tempprice = 1; //quick test                                          //for calculating

        System.out.println("==========================================================");
        System.out.println("You entered this number : " + usernumber);
        System.out.println("This item costs : " + Products.get(usernumber).getPrice());
        while(tempprice > 0){
            System.out.print("Please Enter Money : ");
            usersbalance = coinreader.nextDouble();
            System.out.println(tempprice);
            productprice = productprice - usersbalance;
            if(tempprice == 0){
                break;
            }
        }
        System.out.println("bruhh");
        System.out.println("==========================================================");

        return usernumber;
    }
}
