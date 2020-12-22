import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Immutable pattern objective: Read-only objects that can be shared & used by multiple classes
 *
 * 1) No setter methods
 * 2) All instance variables are private final
 * 3) public getters
 * 4) mark class as final so it cannot be overridden - methods cannot be overriden (mark method(s) as final or class as final)
 * 5) don't return any direct references to mutable objects. i.e. don't return the reference to a List<?> which can be modified directly
 */
public final class ImmutableObject {

    //2
    private final List<String> names;
    private final int id;

    ImmutableObject(List<String> names, int id){
        this.names = names;
        this.id = id;
    }

    //no setters - 1

    //3
    public String getName(int index){
        return names.get(index);
    }

    //3
    public int getId(){
        return id;
    }

    //The below method will violate rule 4. Someone can invoke getNames().clear() and modify the contents of the names instance variable.
    //Hence we shall not return the reference to a mutable object within an Immutable object
    public List<String> getNamesIncorrect(){
        return this.names; //DO NOT DO THIS
    }

    //We prefer the following instead
    public List<String> getNames(){
        return new ArrayList<>(names);
    }

    public static void main(String[] args){
        List<String> names = Arrays.asList("James", "Rajesh", "Emily", "Clive", "Serena");
        ImmutableObject immutableObject = new ImmutableObject(names, 23);

        immutableObject.getNames().clear(); //invoking .clear is not on the instance variable, but it is invoked on a copy
        System.out.println(immutableObject.getNames()); //results still appear.

        immutableObject.getNamesIncorrect().clear(); //throws UnsupportedOperationException
        //This prints an empty list as we have invoked .clear() on the direct reference to the mutable list object from using getNamesIncorrect();
        System.out.println(immutableObject.getNames());

    }

}
