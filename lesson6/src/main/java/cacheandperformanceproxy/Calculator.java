package cacheandperformanceproxy;

public interface Calculator {
    @Metric
    int calc1 (int number);
    @Metric
    int calc2 (int number);
}
