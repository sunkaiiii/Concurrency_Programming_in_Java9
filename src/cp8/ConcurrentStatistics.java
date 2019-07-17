package cp8;

import cp8.basic.Record;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConcurrentStatistics {
    public static void customersFromUnitedKindom(List<Record> records) {
        System.out.println("**************************************");
        System.out.println("Customers from UnitedKingdom");
        Map<String, List<Record>> map = records.parallelStream().filter(r -> r.getCountry().equalsIgnoreCase("the United Kingdom")).collect(Collectors.groupingBy(Record::getCustomer)); //groupyBy是保持的原始输入顺序，groupingByConcurrent是无序的
        map.forEach((k, l) -> System.out.println(k + ": " + l.size()));
        System.out.println("**************************************");
    }

    public static void quantityFromUnitedKindom(List<Record> records) {
        System.out.println("****************************************");
        System.out.println("Quantity from the United Kingdom");
        DoubleSummaryStatistics statistics = records.parallelStream().filter(r -> r.getCountry().equalsIgnoreCase("the United Kingdom")).collect(Collectors.summarizingDouble(Record::getQuantity));
        System.out.println("Min: " + statistics.getMin());
        System.out.println("Max: " + statistics.getMax());
        System.out.println("Average: " + statistics.getAverage());
        System.out.println("****************************************");
    }

    public static void countriesForProduct(List<Record> records){
        System.out.println("****************************************");
        System.out.println("Countries for product 85123A");
        records.parallelStream().filter(r->r.getStockCode().equalsIgnoreCase("85123A")).map(Record::getCountry).distinct().sorted().forEachOrdered(System.out::println);
        System.out.println("****************************************");
    }

    public static void multipleFilterData(List<Record> records){
        System.out.println("****************************************");
        System.out.println("Multiple Filter");
        Stream<Record> stream1=records.parallelStream().filter(r->r.getQuantity()>50);
        Stream<Record> stream2=records.parallelStream().filter(r->r.getUnitPrice()>10);
        Stream<Record> complete=Stream.concat(stream1,stream2); //这样操作会分别创建两个流，然后再合成单一的流
        var value=complete.parallel().unordered().map(Record::getStockCode).distinct().count();
        System.out.println("Number of products: " + value);
        System.out.println("****************************************");

        //当然，这不是最优雅的方法
        //下面提供更好的方法
        System.out.println("****************************************");
        System.out.println("Multiple filter with Predicate");
        Predicate<Record> p1=r->r.getQuantity()>50;
        Predicate<Record> p2=r->r.getUnitPrice()>10;
        Predicate<Record> pred=Stream.of(p1,p2).reduce(Predicate::or).get();
        var pred2=p1.or(p2); //也可以这么写
        value=records.parallelStream().filter(pred).count();
        System.out.println("Number of products: " + value);
        System.out.println("****************************************");


    }
}