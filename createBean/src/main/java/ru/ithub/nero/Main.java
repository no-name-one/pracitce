package ru.ithub.nero;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        MyThread myThread = new MyThread(new Object());

        Thread thread1 = new Thread(myThread, "1");
        thread1.start();

        Thread thread2 = new Thread(myThread, "2");
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Counter: " + Singleton.getCounter());
    }
}