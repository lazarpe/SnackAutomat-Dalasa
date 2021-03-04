package ch.noseryoung.blj;

public class Starter {
    public static void main(String[] args) {
        VendingMachine vendingMachine = new VendingMachine(10, 5);
        boolean running = true;
        while(running) {
            vendingMachine.run();
        }
    }
}