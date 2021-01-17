import java.util.IllegalFormatPrecisionException;
import java.util.Optional;

/**
 * Demonstrating the use of Optionals.
 */
public class Optionals {

    public static void main(String[] args) throws Exception {
        Optional<Double> optional = Optional.of(32.1);
        double value = optional.get();
        System.out.println(value);

        //The following methods cater for when the optional is empty, an alternative action can occur. Typically methods start with or
        Optional<Integer> emptyOptional = Optional.empty();

        System.out.println(emptyOptional.or(()->Optional.of(10)).get());
        System.out.println(emptyOptional.orElse(15));
        emptyOptional.orElseGet(()->50);
        if(false) { //set to true if needed
            emptyOptional.orElseThrow(); //Throws a no such element exception
            emptyOptional.orElseThrow(() -> new IllegalFormatPrecisionException(10)); //Can throw a random exception if optional is empty
        }
        
        //An optional that will return a value if a non-null value is present, otherwise throws NoSuchElementException
        int random = (int) (Math.random() * 10);
        System.out.println(random);
        Optional<Integer> optionalInt = Optional.ofNullable(random%2==0 ? null : random);
        optionalInt.orElseThrow(()->new Exception("Number " + random + " is even"));
    }


}
