package demo.adapter;

import demo.logic.IntegralCalculator;

import java.util.concurrent.Callable;
import java.util.function.DoubleUnaryOperator;

public class CallableIntegralCalculator implements Callable<Double> {

    private final IntegralCalculator calculator;

    public CallableIntegralCalculator(double start, double end, int steps, DoubleUnaryOperator f) {
        calculator = new IntegralCalculator(start, end, steps, f);
    }

    @Override
    public Double call() throws Exception {
        return calculator.calculate();
    }
}
