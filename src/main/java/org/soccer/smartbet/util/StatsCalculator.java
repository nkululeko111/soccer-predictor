package org.soccer.smartbet.service.util;

import java.util.List;

public class StatsCalculator {

    public static double calculateAverage(List<Double> values) {
        return values.stream()
            .mapToDouble(Double::doubleValue)
            .average()
            .orElse(0.0);
    }

    public static double calculateStandardDeviation(List<Double> values) {
        double avg = calculateAverage(values);
        double variance = values.stream()
            .mapToDouble(v -> Math.pow(v - avg, 2))
            .average()
            .orElse(0.0);
        return Math.sqrt(variance);
    }

    public static double calculateWinProbability(double odds) {
        return 1 / odds;
    }

    public static double calculateExpectedValue(double probability, double odds, double stake) {
        return (probability * (odds - 1) * stake) - ((1 - probability) * stake);
    }
}