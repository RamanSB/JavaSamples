/*
Singleton - An object created in memory only once and is shared across multiple classes.

A) All constructors must be marked as private
B) The Singleton Class instantiates the Singleton Object as a private static final object, with the reference defined as
instance.
C) A public getInstance() method returns the Singleton Object
D) All instance variables are marked as private
 */

public class ClassicSingleton {


    //A - constructor must be marked as private
   private ClassicSingleton(){};

    //B - actual instance of the SingletonPattern
   private static final ClassicSingleton singleton = new ClassicSingleton();

   //C - static (class-level) public getter
    public static ClassicSingleton getInstance(){
        return singleton;
    }

    public static void main(String[] args){
        ClassicSingleton instance1 = ClassicSingleton.getInstance();
        ClassicSingleton instance2 = ClassicSingleton.getInstance();
        System.out.println(instance1.equals(instance2)); //There is only 1 instance in memory at all times
    }

}


