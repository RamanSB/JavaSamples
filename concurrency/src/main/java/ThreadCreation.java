/**
 * There existed 2 ways to create a Thread.
 *
 * 1) Directly extending the Thread class & overriding the run method & invoking Thread.start() on the subclass.
 *
 * 2) Implementing the Runnable (Functional) interface and then passing an instance of the implementation to the Thread constructor
 * & then invoking Thread.start on the newly instantiated Thread instance.
 *
 * Advantages of 1) - We can specify a priority value for the Thread directly within our class, i.e. control over the Threads priority
 * + other settings if required.
 *
 * Cons of 1) Java does not support multiple inheritance (hence by extending the Thread class we can no longer extend any other classes).
 *
 * Advantages of 2) - Seperates the Task (Work - defined in the run method) from the Thread (the unit of execution that performs the work)
 *
 */
public class ThreadCreation {

    class ExampleThread extends Thread{
        @Override
        public void run(){
            System.out.println("This thread has been created by extending java.lang.Thread & overriding the run method");
        }
    }

    class ExampleThreadTwo implements Runnable { //An instance of this class can be passed to the constructor of the Thread class.

        @Override
        public void run(){
            System.out.println("This thread has been created by implementing the Runnable interface & implementing the run method");
        }
    }

    public static void main(String[] args){
        //Instantiating Threads & classes that implement Runnable
        ThreadCreation threadCreation = new ThreadCreation();
        ExampleThread exampleThread = threadCreation.new ExampleThread();
        ExampleThreadTwo exampleThreadTwo = threadCreation.new ExampleThreadTwo();

        System.out.println("Demonstrating various thread creation concepts");
        exampleThread.start();
        new Thread(exampleThreadTwo).start();
        System.out.println("End of demonstration");

    }
}
