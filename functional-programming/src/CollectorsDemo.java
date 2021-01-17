import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * We will demonstrate how to use the Collectors: joining, groupingBy & partitionBy
 */
public class CollectorsDemo {

    public static void main(String[] args){
        demoJoining();
        demoPartitionBy();
        demoGroupingBy();
        demoMaxBy();
        demoCounting();
        demoToMapKeyAndValue();
        demoToCollection();
    }

    public static void demoJoining(){
        Stream<String> colors = Stream.of("RED","YELLOW","BLUE","GREEN");
        String result = colors.collect(Collectors.joining(" & "));
        System.out.println(result);
    }

    /**
     * A special case of groupingBy, the only results are either true or false:
     * The grouping function in this case is a predicate, the return value is a Boolean.
     * returns Map<Boolean, List<T>>
     */
    public static void demoPartitionBy(){}

    /**
     * Collectors.groupingBy(Function<T, R> function) returns Map<R, List<T>>
     * Each element of the stream will be passed to the function, the outputs of the function form the keySet of the map.
     * The values are a List of all elements who had the same output when the function was evaluted on them.
     *
     * Suppose we do not want to use a List, we can pass a 'Downstream' Collector
     */
    public static void demoGroupingBy(){
        Stream<String> colors = Stream.of("RED","BLUE","GREEN","YELLOW","CYAN","BROWN","PURPLE","BLACK","WHITE","LILAC");
        Function<String, Integer> lengthMapper = (s)->s.length();
        System.out.println(colors.collect(Collectors.groupingBy(lengthMapper)));

        Stream<String> colors2 = Stream.of("RED","BLUE","GREEN","YELLOW","CYAN","BROWN","PURPLE","BLACK","WHITE","LILAC");
        System.out.println(colors2.collect(Collectors.groupingBy(lengthMapper, Collectors.toSet())));
    }

    public static void demoMaxBy(){}

    public static void demoCounting(){}

    public static void demoToMapKeyAndValue(){
        Stream<String> colors = Stream.of("RED","BLUE","GREEN","YELLOW","CYAN","BROWN","PURPLE","BLACK","WHITE","LILAC");
        colors.collect(Collectors.toMap(x->x.length(), x->x));
        //ToDo: throws collection because keys are duplicated
    }

    public static void demoToCollection(){
        Stream<Integer> ages = Stream.of(18, 18, 23, 23, 15, 83, 28, 38, 35, 45, 53, 73, 12, 45);
        ConcurrentSkipListSet<Integer> ageSet = ages.collect(Collectors.toCollection(ConcurrentSkipListSet::new));
        System.out.println(ageSet);
    }

}
