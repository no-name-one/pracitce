package ru.ithub.nero;

public class Singleton {
    private static volatile Integer counter = 0;

    private Singleton() {

    }

    public static Integer getCounter() { return counter; }

    public static Integer increment() {
        synchronized (Singleton.class) {
            counter += 1;
            return counter;
        }
    }
}