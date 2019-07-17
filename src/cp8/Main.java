package cp8;

import cp8.basic.ConcurrentDataLoader;
import cp8.basic.Record;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    static Map<String, List<Double>> totalTimes = new LinkedHashMap<>();
    static List<Record> records;

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("data\\Online_Retail.csv");
        records = ConcurrentDataLoader.load(path);
        for (int i = 0; i < 10; i++) {
            measure("Customers from UnitedKingdom", () -> ConcurrentStatistics
                    .customersFromUnitedKindom(records));
            measure("Quantity from UnitedKingdom", () -> ConcurrentStatistics
                    .quantityFromUnitedKindom(records));
            measure("Countries for Product", () -> ConcurrentStatistics
                    .countriesForProduct(records));
            measure("Multiple Filter for Products", () -> ConcurrentStatistics
                    .multipleFilterData(records));
        }

        totalTimes.forEach((name, times) -> System.out.printf("%25s: %s [avg: %6.2f] ms%n", name,
                times.stream().map(t -> String.format("%6.2f", t)).collect(Collectors.joining(" ")),
                times.stream().mapToDouble(Double::doubleValue).average().getAsDouble()));
    }

    private static void measure(String name, Runnable r) {
        long start = System.nanoTime();
        r.run();
        long end = System.nanoTime();
        totalTimes.computeIfAbsent(name, k -> new ArrayList<>())
                .add((end - start) / 1_000_000.0);
    }
}
