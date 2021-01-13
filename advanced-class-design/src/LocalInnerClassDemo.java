/**
 * Here we will demonstrate how Nested classes work in Java, there are 4 types.
 * A) Inner Class:
 *  1) Member Inner class (Defined at the same level as instance variables) (any access modifier can be used)
 *  2) Local Inner class (Defined within a method.)
 *  3) Anonymous Inner class (A special case of 2).)
 * B)
 *  4) Static Nested class
 *
 * In this class we will model a Local Inner Class:
 *  1) Defined within a method
 *  2) No Access modifiers (akin to local variables)
 *  3) Can access all fields & methods in enclosing class
 *  4) Cannot declare any static fields/
 *  5) Can only access final/effectively final local variables in enclosing method.
 *
 */

public class LocalInnerClassDemo {

    boolean isOuter = true;
    static long versionId = 1;

    LocalInnerClassDemo(long versionId){
        this.versionId = versionId;
    }

    void localInnerClassMethod(){
        final int x = 10;
        int y = 20;
        y = 5;
        class LocalInnerClass extends Thread { //Can extend a class - not necessary, if this is needed perhaps think of using a Member inner class
            public void aLocalInnerClassMethod(){
                //System.out.println(y+"x"); //y can't be used as it is not final or effectively final.
                System.out.println(x); //5)
                System.out.println(isOuter); //3)
                System.out.println(versionId); //3)
            }
        }
        new LocalInnerClass().aLocalInnerClassMethod(); //Instantiating and invoking the classes instance method.
    }


    public static void main(String[] args){
        LocalInnerClassDemo outer = new LocalInnerClassDemo(11);
        outer.localInnerClassMethod();
    }

}
