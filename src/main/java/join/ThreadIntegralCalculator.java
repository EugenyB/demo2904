package join;

import demo.locks.Main;

import java.util.function.DoubleUnaryOperator;

public class ThreadIntegralCalculator extends Thread {
    private final IntegralCalculator calculator;
    private double result;

    public ThreadIntegralCalculator(double start, double end, int n, DoubleUnaryOperator f) {
        calculator = new IntegralCalculator(start, end, n, f);

    }

    @Override
    public void run() {
        result = calculator.calculate();
    }

    public double getResult() {
        return result;
    }
}
