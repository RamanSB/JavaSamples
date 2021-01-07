import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Stream;

/**
 * ForkJoin Framework is used when we do not know how many threads will be required for a task and we intend to
 * recursively break down (fork) the task at hand in to subtasks until a task can be completed. We then wait for the subtasks
 * to be complete and then we join the results.
 *
 * Step 1)  Create a ForkJoinTask (We either extend RecursiveAction or RecursiveTask both have an abstract method called compute()) - Task has a return type V & Action void.
 *
 * Step 2)  Create the ForkJoinPool (ForkJoinPool forkJoinPool = new ForkJoinPoool(int noOfCores)
 *
 * Step 3) Start the ForkJoinTask
 *
 * Here we will demonstrate an application of the ForkJoin framework using RecursiveTask;
 * Let's assume we have a String of n letters and a (ForJoin)WorkerThread can only operate on the String if it has less
 * than a Threshold amount (3) characters. If it doesn't we will have to recursively allocate the work to another worker-
 * thread. We intend to sum the byte values of the byte[] represented by the String.
 *
 *
 * Note: fork() must be invoked on the otherTask before the currentThread invokes compute() & join() is called afterwards to
 * read the result of the forked task.
 */

public class ForkJoinDemo extends RecursiveTask<Integer> { //Step 1 (Extending RecursiveAction or RecursiveTask)


    private static final int WORKER_TASK_THRESHOLD = 3;
    private String word;

    public ForkJoinDemo(String data){
        this.word = data;
    }

    @Override
    protected Integer compute(){ //This method is all a part of step 1
        if(word.length() <= WORKER_TASK_THRESHOLD){//This if-block is the recursive base-case.
            int sum = 0;
            System.out.println(word);
            for(byte val : word.getBytes()){
                sum+=val;
            }
            return sum;
        } else { //The recursive case - if the string contains more than 3 characters we will break the string down in to
            System.out.println("breaking it down");
            int wordLength = word.length();
            int startIdx = 0; //separate tasks,
            int middleIdx = (startIdx + word.length())/2;
            System.out.format("Start: [%d] | Middle: [%d] | End: [%d]\n", startIdx, middleIdx,wordLength);
            RecursiveTask<Integer> otherTask = new ForkJoinDemo(word.substring(startIdx, middleIdx)); //Defining another task which contains a smaller string.
            otherTask.fork(); //Submits a new task to the ForkJoinPool
            return new ForkJoinDemo(word.substring(middleIdx)).compute() + otherTask.join(); //join causes current thread to wait until othertask is done & then returns the result.
        }
    }

    public static void main(String[] args){
        String data = "CaviarDreams";
        ForkJoinTask<Integer> sumBytesInStringForkJoinTask = new ForkJoinDemo(data); //step 1
        ForkJoinPool forkJoinPool = new ForkJoinPool(2); //step 2
        int result = forkJoinPool.invoke(sumBytesInStringForkJoinTask); //step 3
        System.out.println("The sum of the bytes for each char in " + data + " is equal to: " + result);
    }

}
