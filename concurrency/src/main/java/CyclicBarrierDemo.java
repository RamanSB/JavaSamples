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
        int noOfCustomers = 12;
        ExecutorService service = Executors.newFixedThreadPool(noOfCustomers); //assuming there are 24 customers attending the cinema
        CyclicBarrier c1 = new CyclicBarrier(noOfCustomers, ()->{ //When c1.await() is invoked noOfCustomers time the runnable will be invoked - which begins the movie.
            System.out.println("All customers are parked and ready to watch the movie");
            CinemaManager.playMovie();
        });
        CyclicBarrier c2 = new CyclicBarrier(noOfCustomers, ()->{//When c2.await() is invoked noOfCustomers time the runnable will be invoked - which ends the movie.
            CinemaManager.endMovie();
            System.out.println("Movie has ended - Please exit the cinema car park.");

        });
        try {
            for (int i = 0; i < noOfCustomers; i++) {
                final DriveInCinemaCustomer customer = new DriveInCinemaCustomer(i); //For each iteration of the while-loop we create a DriveInCinemaCustomer object and assign an id.
                service.submit(() -> performTask(c1, c2, customer)); //We then submit #noOfCustomers (12) tasks to be executed by (12) #noOfCustomer threads.
            }
        }finally {
            if (service != null) { //Defensive (meaning even though service is never null, I still check for this condition)
                service.shutdown();
            }
        }
    }

    public static void performTask(CyclicBarrier c1, CyclicBarrier c2, DriveInCinemaCustomer customer){
        try {
            customer.enterCinema();
            c1.await(); //We will not proceed to beginning the movie until all customers have entered the cinema.
            c2.await(); //We will not proceed to end the movie until all customers have acknowledged the movie has started.
            customer.leaveCinema(); //Once the movie has ended, all customers may leave the drive in cinema.
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
