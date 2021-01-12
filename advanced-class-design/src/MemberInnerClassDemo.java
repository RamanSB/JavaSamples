import java.io.Serializable;

/**
 * Here we will demonstrate how Nested classes work in Java, there are 4 types.
 * A) Inner Class:
     *  1) Member Inner class (Defined at the same level as instance variables) (any access modifier can be used)
     *  2) Local Inner class (Defined within a method.)
     *  3) Anonymous Inner class (A special case of 2).)
 * B)
 *      4) Static Nested class
 *
 * In this class we will model a Member Inner Class:
 *  1) Can use any access modifier
 *  2) Can be marked as abstract or final
 *  3) Can NOT declare static fields
 *  4) Can extend/implement any interface/superclass
 *  5) Can access members in outer class
 */

public class MemberInnerClassDemo {

    String outerClassString = "I've been defined in the outer class";

    private class MemberInnerClass implements Serializable { //Demonstrating it can implement an interface

        private int id = 12;
        protected String innerClassString = "I've been defined in the member inner class";

        void printSomeVariables(){
            System.out.format("Here are some variables that are defined in different places:\n %s, %s, %d",
                    outerClassString, innerClassString, id);
        }


    }

    public static void main(String[] args){
        MemberInnerClassDemo outer = new MemberInnerClassDemo();
        MemberInnerClass inner = outer.new MemberInnerClass(); //We must instantiate the inner class instance by using the outer class
        //the inner class is not static it requires an instance of the outer class to be used.
        inner.printSomeVariables();

        System.out.println("\n"+outer);
        System.out.println(inner); //the member inner class instance by definition requires the outer class instance to exist.
    }
}
