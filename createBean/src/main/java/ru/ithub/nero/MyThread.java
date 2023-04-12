package ru.ithub.nero;

public class MyThread implements Runnable {
    private final Object res;

    public MyThread(Object res) {
        this.res = res;
    }

    @Override
    public void run() {
        while(Singleton.getCounter() <=100) {
            Integer num = Singleton.increment();
            System.out.println(Thread.currentThread().getName() + " thread -> " + num);

            synchronized (res) {
                if (num == 100) {
                    try {
                        res.wait();
                    } catch (InterruptedException e) {
                    }
                    System.out.println(Thread.currentThread().getName() + " -> I'm winner");

                } else if (num > 100) {
                    System.out.println(Thread.currentThread().getName() + " -> I'm late");
                    res.notify();
                }
            }

        }
    }
}
