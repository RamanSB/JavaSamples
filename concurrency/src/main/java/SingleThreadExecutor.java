import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SingleThreadExecutor {

    public static void main(String[] args){
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor(); //An executor which consists of a single-thread
        singleThreadExecutor.execute(()->{System.out.println("We submit tasks for the single thread executor to perform via a" +
                " Runnable argument passed to the execute method");});
        //We can also submit a task that will performed by the executor thread in the future using the submit method (which returns a Future).
        //The task passed in is a Lambda representing the Callable functional interface, similar to Runnable however the abstract method has a return type.
        Future<Integer> future = singleThreadExecutor.submit(()->{ //The task simply increments a counter in a while-loop for 5s.
            int counter = 0;
            System.out.println("The body of the submitted task to the executor (single) thread - which will be executed and a result will be returned when invoking" +
                    "future.get()");
            LocalTime startTime = LocalTime.now();
            Duration duration = Duration.of(5, ChronoUnit.SECONDS);
            while(startTime.plus(duration).isAfter(LocalTime.now())){
                counter++;
            }
            return counter;
        });
        System.out.println(future.isDone()); //Checks if task is completed i.e. if completed, exception is thrown or cancelled
        System.out.println(singleThreadExecutor.isShutdown());
        /*
            Attempting to receive the result/value returned from the submitted task.
         */
        try{
            System.out.println(future.get());
        }catch(InterruptedException | ExecutionException e){
            System.out.println("An exception occurred when attempting to retrieve the result from the submitted task.");
            e.printStackTrace();
        }finally{
            if(singleThreadExecutor!=null){
                singleThreadExecutor.shutdown(); //no new tasks can be submitted to the executor thread.
            }
        }
        System.out.println(future.isDone()); //Checks if task is completed i.e. if completed, exception is thrown or cancelled
        System.out.println(singleThreadExecutor.isShutdown());
    }
}
