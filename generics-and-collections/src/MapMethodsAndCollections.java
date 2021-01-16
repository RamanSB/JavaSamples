import java.util.*;
import java.util.function.BiFunction;

/**
 * Demonstrating new methods added to the Collections framework (Collection is the interface):
 * List (replaceAll)
 * Collections (removeIf)
 * Map (merge)
 */
public class MapMethodsAndCollections {

    public static void main(String[] args){
        demoReplaceAll();
        demoMerge();
    }

    /**
     * replaceAll accepts an UnaryOperator<T>
     */
    static void demoReplaceAll(){
        //Recall: any Lists that are created from Arrays.asList cannot be resized.
        List<String> names = Arrays.asList("David", "Simon", "Leo", "Liliana", "Serena", "Joshua", "Merca", "Matias");
        //Any names starting with S or L, remove the first character.
        names.replaceAll(s -> { //Accepts an UnaryOperator<String>
            if(s.startsWith("S") || s.startsWith("L")){
                return s.substring(1);
            }
            return s;
        });
        System.out.println(names);
    }

    /**
     * merge(Key K, Value V, BiFunction<V, V, V>)
     */
    static void demoMerge(){
        Map<String, Integer> testScoreMap = new HashMap<>();
        testScoreMap.put(null, 99);
        testScoreMap.put("Dilan", 85);
        testScoreMap.put("Jas", 84);
        testScoreMap.put("Tarlochan", 73);
        testScoreMap.put("Jeremy", 32);
        testScoreMap.put("Henry", null);
        testScoreMap.put("Lola", 56);
        testScoreMap.put("Idiot", 0);
        testScoreMap.put("KidAverage", 58);
        testScoreMap.put("Clive", null);
        testScoreMap.put("Seema", 100);

        final int passMark = 65;
        BiFunction<Integer, Integer, Integer> mergeMapper = (v1, v2)-> {
            if((v1+v2)/2<passMark){
                return 0;
            }else{
                return 100;
            }
        };
        for(String name : testScoreMap.keySet()){
            //We are attempting to correct the test scores by the correction value
            if(name!=null) {
                Integer value = testScoreMap.get(name);
                if(value != null) {
                    testScoreMap.merge(name, value+10, mergeMapper);
                }
            }
        }
        System.out.println(testScoreMap);
    }



}
