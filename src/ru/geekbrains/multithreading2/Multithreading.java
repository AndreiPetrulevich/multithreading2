package ru.geekbrains.multithreading2;

public class Multithreading {

    private final Object THREAD = new Object();
    private volatile char currentLetter = 'C';
    private static int CHAR_COUNT = 5;

    private Thread startCharProducer(char symbol, char prevSymbol) {
        return new Thread(() -> {
            int count = 0;
            synchronized (THREAD) {
                try {
                    while (count < CHAR_COUNT) {
                        while (currentLetter != prevSymbol) {
                            THREAD.wait();
                        }
                        System.out.println(symbol);
                        currentLetter = symbol;
                        count++;
                        THREAD.notifyAll();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        Multithreading charProducer = new Multithreading();

        Thread threadA = charProducer.startCharProducer('A', 'C');
        Thread threadB = charProducer.startCharProducer('B', 'A');
        Thread threadC = charProducer.startCharProducer('C', 'B');

        threadA.start();
        threadB.start();
        threadC.start();
    }
}
