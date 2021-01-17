import java.util.IllegalFormatPrecisionException;
import java.util.Optional;

/**
 * Demonstrating the use of Optionals.
 */
public class Optionals {

    public static void main(String[] args){
        Optional<Double> optional = Optional.of(32.1);
        double value = optional.get();
        System.out.println(value);

        //The following methods cater for when the optional is empty, an alternative action can occur. Typically methods start with or
        Optional<Integer> emptyOptional = Optional.empty();

        System.out.println(emptyOptional.or(()->Optional.of(10)).get());
        System.out.println(emptyOptional.orElse(15));
        emptyOptional.orElseGet(()->50);
        emptyOptional.orElseThrow(); //Throws a no such element exception
        emptyOptional.orElseThrow(()->new IllegalFormatPrecisionException(10)); //Can throw a random exception if optional is empty

        

    }


}
