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
    public static int displaySingleProductPrice(int usernumber, ArrayList<Product> Products){
        char cancel;
        double productprice = Products.get(usernumber).getPrice();
        Scanner cancelbutton = new Scanner(System.in);
        System.out.println("==========================================================");
        System.out.println("Item number : " + usernumber);
        System.out.println("This item costs : " + Products.get(usernumber).getPrice());
        while(true) {
            try{
                System.out.println("Would you like to continue ? [ Y / N ] :");
                cancel = cancelbutton.next().charAt(0);
                if (cancel == 'Y' || cancel == 'y') {
                    Money.payForProduct(productprice);              //pay product section
                    break;
                }else if(cancel == 'N' || cancel == 'n'){
                    System.out.println("Canceling ... ");


                    usernumber = -1;    //canceling value
                    return usernumber;
                }
            } catch (Exception e) {
            }
            System.err.println("Invalid input");
        }
        return usernumber;
    }


    public static double addMoney(){
        Scanner coinreader = new Scanner(System.in);
        double usersbalance;
        System.out.print("How much will you be adding : ");
        usersbalance = coinreader.nextDouble();
        System.out.println(usersbalance);                       //users credit balance variable for calculating


        return usersbalance;
    }

    public static double payForProduct(double productprice){
        Scanner coinreader = new Scanner(System.in);
        double usercoins = 0;
        while(productprice > 0){
            System.out.println("Insert Coins : ");
            usercoins = coinreader.nextDouble();
            productprice = productprice - usercoins;
        }
        if(productprice < 0){
            usercoins = productprice + usercoins;
        }
        System.out.println(usercoins);
        System.out.println(productprice);
        return productprice;
    }

}


