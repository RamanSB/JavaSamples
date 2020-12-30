import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A race condition is a condition that occurs when two threads have an inconsistent view of data they are operating on
 * which leads to unexpected outcome, this usually stems from the order of execution of code from Threads happening in
 * different sequences.
 *
 * Please see file (zoo_stock_output_(race_condition).txt) - Notice on Traditional Thread RUN #1
 * --OUTPUT-- hay: 5020, 5010, 5030, 5040
 * The ZooWorker Threads (4 of them):
 * 1) 1 Thread has incremented the water counter by 10, but has not yet printed the result.
 * 2) Another thread (thread-2 suppose) has incremented the water counter by 10 & the result has been printed (520).
 * 3) Thread 1 who sees the value of water at 510 (as it only incremented the water value by 10 once) prints the value it reads (510).
 * 4) Thread 3 reads the value of the water at 520 & increments by 10 and prints (530).
 * 5) Thread 4 does the same as thread 3 and increments by 10 and prints (540).
 *
 * These values are not in 'Sync' and to do so require Synchronization - this will ensure that only 1 thread at a time can invoke
 * code effecting the values in question - this will allow the view of the data shared amongst the threads to be consistent.
 */
public class RaceConditionDemo {

    public static void main(String[] args){
        ZooStock zooStockNew = new ZooStock(1000, 750, 5000);
        ExecutorService executorService = null;
        try{
            executorService = Executors.newFixedThreadPool(10); //Creating a Thread pool of size 10
            ZooWorker zooWorker = new ZooWorker(zooStockNew);
            for(int i=0; i<4; i++){
                executorService.submit(zooWorker::addGrass); //
            }
        }finally{
            if(executorService != null) executorService.shutdown();
        }
        System.out.println("\n" + zooStockNew);

        System.out.println("--------------------------------------------");

        //This demonstrates how we can achieve a race condition by creating Threads the old fashioned way
        ZooStock zooStockTraditional = new ZooStock(1000, 750, 5000);
        ZooWorker[] workerThreads = new ZooWorker[4]; //Set all elements in the array to be a ZooWorker object
        Arrays.fill(workerThreads, new ZooWorker(zooStockTraditional));
        for(int i=0; i<workerThreads.length; i++){
            new Thread(workerThreads[i]).start(); //Start the worker threads off (this invokes the run method in the ZooWorker class)
        }
        System.out.println("\n" + zooStockTraditional);
    }

}

/**
 * This class represents the Stock of food a Zoo has to feed their animals.
 */
class ZooStock {

     int grass; //Kg
     int hay; //Kg
     int water; //L

    ZooStock(int grassAmount, int hayAmount, int waterAmount){
        grass = grassAmount;
        hay = hayAmount;
        water = waterAmount;
    }

    public String toString(){
        return String.format("Hay: %d | Water: %d | Grass: %d", hay, water, grass);
    }


}

/**
 * This class represents a ZooWorker, the work of a ZooWorker will be contained by a Thread, we wil create several ZooWorker
 * threads & invoke them and observe the outcome.
 */
class ZooWorker implements Runnable {

    ZooStock zooStock;

    ZooWorker(ZooStock zooStock){
        this.zooStock = zooStock;
    }

    @Override
    public void run() {
        addGrass();
        addWater();
        addHay();
    }

    public void addGrass(){
        zooStock.grass++;
        System.out.print(zooStock.grass + "g ");
    }

    public void addHay(){
        zooStock.hay++;
        System.out.print(zooStock.hay + "h ");
    }

    public void addWater(){
        zooStock.water = zooStock.water + 10;
        System.out.print(zooStock.water + "w ");
    }


}
