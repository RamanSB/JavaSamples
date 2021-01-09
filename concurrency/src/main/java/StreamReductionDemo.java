import java.util.Arrays;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

/**
 * See: https://www.baeldung.com/java-stream-reduce
 * Here we will demonstrate the time advantage of using a parallel stream in order to process tasks in parallel opposed to
 * using a regular Serial Stream whereby single elements are processed in order - one at a time.
 *
 * Demonstrate avoiding Stateful operations in parallel streams.
 *
 * Creating Parallel stream:
 *  -Stream(instance).parallel()
 *  -(Collection).parallelStream();
 *
 * Show how findAny(), findFirst(), limit(), skip() or any order based operation differs for serial & paralllel streams
 * & Why?
 *
 * Demonstrate Parallel reduction: reduce() method: Identity<T>, Accumulator(BiFunction<T, U, T>), Combiner(BinaryOperator<T>).
 * Where U is the generic type used in the Stream<U>
 *
 * State the rules for the reduce method parameters for parallel streams.
 *
 * 1) Identity must satisfy: combiner.apply(identity, stream) returns stream
 * 2) Accumulator operation op, must be associative: (a op b) op c = a op (b op c) & stateless
 * 3) Combiner operator must also be associative and stateless.
 *
 *  Parallel Stream operation
 *
 *  Note:
 *  if we use sequential streams and the types of the accumulator arguments and the types of its implementation match,
 *  we don't need to use a combiner.
 *
 */
public class StreamReductionDemo {

    public static void main(String[] args){
        //Creating parallel stream(s)
        Stream<Integer> serialStream =  Stream.of(1,4,5,9,12,17);
        Stream<Integer> serialStream2 = Stream.of(4,9,1,3,4,0); //parallel() is an intermediate operation, operating on an existing (serial) stream.
        Stream<String> parallelLetterStream = Stream.of("c", "o", "r", "o", "n", "a").parallel();


        //single arg reduce method with no Identity, only accumulator has a return type of Optional
        Optional<Integer> singleArgReductionResult = serialStream.reduce((x, y)->x+y); //Accumulator is of type BinaryOperator<U> where U is the type of the Stream.
        singleArgReductionResult.ifPresent(System.out::println); //this should be fairly trivial - note this is the only reduction with a return type using Optional.

        //2-arg reduce method overload (includes Identity<U>, BinaryOperator<U>) where U is the type of the stream, Stream<U>, return type U.
        int twoArgReductionResult = serialStream2.reduce(-21, Integer::sum); //Integer::sum replaces (x,y)->x+y;
        // Integer.sum(int x, int y) return type Integer, satisfies BinaryOperator<Integer>
        System.out.println(twoArgReductionResult); //expecting to return 0 as sum of values are 21 and we are starting from the identity which is -21.


        /*
            3-arg reduction method - should always be used for parallel streams. Return type T, the type of the Identity param.
            Param 1: Identity<T>
            Param 2: BiFunction<T, U, T> - Accumulator
            Param 3: BinaryOperator<T> - Combiner
            --This reduction will sum the byte values represented by each letter (String) in the stream as per UTF-8 charset encoding.
         */
        Integer identity = 0;
        BiFunction<Integer, String, Integer> accumulator = (t, u) -> t + ((int) u.getBytes()[0]);
        BinaryOperator<Integer> combiner = (a, b)->a+b;

        int byteSum = parallelLetterStream
                .map(String::toUpperCase)
                .reduce(identity,  //Identity is set to be of type integer
                        accumulator, //Parameters of BiFunction are now <Integer, String, Integer> (summing the byte values representing the letters)
                        combiner); //Adding accumulated values across different threads.

        System.out.println("The letters constituting the capitalized word of corona has a byte value sum equal to: " + byteSum);

        byte[] byteArray = "CORONA".getBytes();
        Byte[] byteArrayBoxed = new Byte[byteArray.length];
        for(int i=0; i<byteArray.length; i++){
            byteArrayBoxed[i] = byteArray[i];
        }
        int byteSumCheck = Arrays.stream(byteArrayBoxed).mapToInt(x->x).sum();
        System.out.println(byteSumCheck == byteSum);
    }

}
