/**
 * I demonstrate the DesignPattern for a ThreadSafe singleton that uses LazyInstantiation.
 *
 * Conditions for Singleton:
 * 1) private constructor (ensures no overriding or creation of new Singletons)
 * 2) public static getter of Instance
 * 3) all instance variables are marked as private (encapuslation - instance variables cannot be accessed from outside the Singleton class)
 * 4) Singleton instance must be declared as private static //Ensures only a single Singleton exists at the class level (hence static)
 *
 *
 * Lazy instantiation: The concept of creating a resource/object in memory only when it is required to be used. (avoids unnecessary overheads on memory whereby creating an object/resource and not using it takes up space on the heap).
 *
 * Thread-Safe: The concept whereby multiple threads can access data and have a consistent view of the data in question. All threads share the same consistent view of data, i.e. values/states are the same.
 *
 * In order to utilize lazy instantiation & ensure thread-safety we must use the 'synchronized' keyword - this ensures only one thread can access a method at a given time as that thread must acquire a lock.
 * Once the lock is acquired, no other threads can access the synchronized method until the lock is released by the thread (which has the lock) currently invoking the synchronized method.
 *
 */
public class ThreadSafeLazyInstantiatedSingleton {

    
    //1 - Private Constructor
    private ThreadSafeLazyInstantiatedSingleton(){

    }

    //4
    private static ThreadSafeLazyInstantiatedSingleton instance;

    //3
    public static synchronized ThreadSafeLazyInstantiatedSingleton getInstance(){
        if(instance==null){
            instance = new ThreadSafeLazyInstantiatedSingleton();
        }
        return instance;
    }



}
