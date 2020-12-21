import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class SingleThreadExecutor {

    public static void main(String[] args){

        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor(); //An executor which consists of a single-thread (guarantee tasks occur synchronously and only 1 task is in execution at a given time)
        singleThreadExecutor.execute(()->{System.out.println("We submit tasks for the single thread executor to perform via a" + //execute() is a fire & forget method (no return type)
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
                singleThreadExecutor.shutdown(); //no new tasks can be submitted to the executor thread. All pre-existing submitted tasks will be completed.
            }
        }
        System.out.println(future.isDone()); //Checks if task is completed i.e. if completed, exception is thrown or cancelled
        System.out.println(singleThreadExecutor.isShutdown());

        /**
         * -------------------------------------------------------------------------------------------------------------
         * Here we demonstrate the invokeAll & invokeAny methods of the SingleThreadExecutor
         */
        ExecutorService singleThreadExecutorNew = Executors.newSingleThreadExecutor();
        List<Callable<Integer>> taskList = new ArrayList<>();
        for(int i=0; i<10; i++){
            taskList.add(() -> {
                int result = (int) (Math.floor((Math.random()*100)));
                System.out.println("Loading task that returns: " + result);
                return result;
            });
        }

        try {
            List<Future<Integer>> resultsList = singleThreadExecutorNew.invokeAll(taskList);
            int result = singleThreadExecutorNew.invokeAny(taskList);
            for(Future<Integer> res : resultsList){
                System.out.println("Result from executed tasks: " + res.get(2, TimeUnit.SECONDS)); //Note the tasks are executed & results are returned in the same order at which the tasks are loaded - #lines 52-56
            }

            System.out.println("Returned result from invokeAny: " + result);
        }catch(InterruptedException | TimeoutException | ExecutionException ex){
            ex.printStackTrace();
            System.out.println("An exception occurred when invoking all tasks");
        } finally {
            if(singleThreadExecutorNew!=null){
                singleThreadExecutorNew.shutdown();
            }
        }





    }
}
