/*
Singleton - An object created in memory only once and is shared across multiple classes.

A) All constructors must be marked as private
B) The Singleton Class instantiates the Singleton Object as a private static final object, with the reference defined as
instance.
C) A public getInstance() method returns the Singleton Object
D) All instance variables are marked as private
 */

public class SingletonPattern {


    //A - constructor must be marked as private
   private SingletonPattern(){};

    //B - actual instance of the SingletonPattern
   private static final SingletonPattern singleton = new SingletonPattern();

   //C - static (class-level) public getter
    public static SingletonPattern getInstance(){
        return singleton;
    }

    public static void main(String[] args){

    }

}


