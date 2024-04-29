package join;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    private void run() {
        double start = 0;
        double end = Math.PI;
        int n = 1000_000_000;
        int nThreads = 20;
        double delta = (end - start) / nThreads;
        double result = 0;
        long startTime = System.currentTimeMillis();
        List<Thread> threads = new ArrayList<>(100);
        for (int i = 0; i < nThreads; i++) {
            double a = start + delta * i;
            double b = a + delta;
            ThreadIntegralCalculator calculator = new ThreadIntegralCalculator(a, b, n / nThreads, Math::sin);
            threads.add(calculator);
            calculator.start();
        }
        try {
            for (Thread thread : threads) {
                thread.join();
                result += ((ThreadIntegralCalculator)thread).getResult();
            }
            long endTime = System.currentTimeMillis();
            System.out.println("result = " + result);
            System.out.println("elapsed time: " + (endTime - startTime));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}
