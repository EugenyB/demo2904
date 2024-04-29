package demo;

import demo.adapter.CallableIntegralCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    private void run() {
        double start = 0;
        double end = Math.PI;
        int n = 1000_000_000;
        int nThreads = 10;
        int maxThreads = Runtime.getRuntime().availableProcessors();

        long startTime = System.currentTimeMillis();
        List<Future<Double>> futures = new ArrayList<>();
        try (ExecutorService executorService = Executors.newFixedThreadPool(maxThreads)) {
            double delta = (end - start)/nThreads;
            for (int i = 0; i < nThreads; i++) {
                double a = i * delta;
                double b = a + delta;
                CallableIntegralCalculator calculator = new CallableIntegralCalculator(a, b, n / nThreads, Math::sin);
                Future<Double> future = executorService.submit(calculator);
                futures.add(future);
            }
            executorService.close();
        }
        try {
            double total = 0;
            for (Future<Double> future : futures) {
                total += future.get();
            }

            long endTime = System.currentTimeMillis();
            System.out.println("Total: " + total);
            System.out.println("Elapsed time: " + (endTime - startTime));
        } catch (Exception e) {
            System.err.println("error executing tasks");
        }

    }
}
