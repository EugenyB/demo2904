package demo.locks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    private double totalResult;
    private int finished;

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    private void run() {
        double start = 0;
        double end = Math.PI;
        int n = 1000_000_000;
        int nThreads = 200;

        finished = 0;
        totalResult = 0;
        double delta = (end - start)/nThreads;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < nThreads; i++) {
            double a = i * delta;
            double b = a + delta;
            Thread.startVirtualThread(new ThreadIntegralCalculator(a, b, n/nThreads, Math::sin, this)); // virtual thread
//            new Thread(new ThreadIntegralCalculator(a, b, n/nThreads, Math::sin, this)).start();  // real thread
        }
        lock = new ReentrantLock();
        condition = lock.newCondition();
        lock.lock();
        try {
            while (finished < nThreads) {
                condition.await();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Result = " + totalResult);
        System.out.println("Elapsed time: " + (endTime - startTime));


    }

    public void sendResult(double v) {
        try {
            lock.lock();
            totalResult += v;
            finished++;
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    Lock lock;
    Condition condition;
}
