import java.util.concurrent.*;

/**
 * Concurrency API includes classes that can be used to manage/coordinate tasks among a group of related threads
 * (thread pool).
 *
 * 2 classes of interest:
 *  - CyclicBarrier: Used to ensure that all threads accomplish a subtask before moving on to the next subtask at hand.
 *  - ForkJoinPool: Uses recursion to split a task in to multiple other tasks.
 *
 *  Here I will demonstrate how to use CyclicBarrier to coordinate tasks amongst multiple threads using a  Drive-In
 *  Cinema example (with no late comers) (Order: everybody has to park up, movie starts, everybody leaves).
 */
public class CyclicBarrierDemo {


    public static void main(String[] args){
        int noOfCustomers = 4;
        ExecutorService service = Executors.newFixedThreadPool(noOfCustomers); //assuming there are 24 customers attending the cinema
        CyclicBarrier c1 = new CyclicBarrier(noOfCustomers, ()->System.out.println("All customers are parked and ready to watch the movie"));
        CyclicBarrier c2 = new CyclicBarrier(noOfCustomers, ()->System.out.println("Movie has ended - Please exit the cinema car park."));
        for(int i=0; i<noOfCustomers; i++){
            final DriveInCinemaCustomer customer = new DriveInCinemaCustomer(i);
            service.submit(()-> performTask(c1, c2, customer));
        }

    }

    public static void performTask(CyclicBarrier c1, CyclicBarrier c2, DriveInCinemaCustomer customer){
        try {
            customer.enterCinema();
            c1.await();
            CinemaManager.playMovie();
            CinemaManager.endMovie();
            c2.await();
            customer.leaveCinema();
        }catch(BrokenBarrierException | InterruptedException ex){
            ex.printStackTrace();
        }
    }


}


class DriveInCinemaCustomer {

    int id;

    DriveInCinemaCustomer(int id){
        this.id = id;
    }

    public void enterCinema(){
        System.out.println("Customer " + this.id + " drives in to cinema parking & parks.");
    }

    public void leaveCinema(){
        System.out.println("Customer " + this.id + " exits the cinema car-park");
    }

}

final class CinemaManager {

    public static void playMovie(){
        System.out.println("Starting movie");
    }

    public static void endMovie(){
        System.out.println("End movie");
    }
}
