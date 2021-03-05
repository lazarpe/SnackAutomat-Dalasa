package ch.noseryoung.blj;

public class Starter {
    public static void main(String[] args) {
        VendingMachine vendingMachine = new VendingMachine(10, 5);
        vendingMachine.fillVendingMachine();
        //vendingMachine.loadFromFile();
        vendingMachine.printToFile();
        while (true) {
            vendingMachine.run();
        }
    }
}