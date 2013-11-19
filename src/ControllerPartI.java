import display.*;

public class ControllerPartI {

    public static int MAX = 9;
    protected NumberCanvas passengers;
    private int waitingPassengers = 0;

    public ControllerPartI(NumberCanvas nc) {
        passengers = nc;
    }

    /* Update the number of passengers waiting on the platform */
    public synchronized void newPassenger() throws InterruptedException {
        /* Wait while there are too many passengers on the platform */
        while(waitingPassengers >= MAX) {
            wait();
        }

        /* Add a new passenger */
        waitingPassengers++;

        /* Update the display */
        passengers.setValue(waitingPassengers);

        notifyAll();
    }

    /* Get the number of passenger in a coaster car */
    public synchronized int getPassengers(int mcar) throws InterruptedException {
        /* Wait while there are not enough passengers waiting to fill the car */
        while(mcar <= 0 || mcar > waitingPassengers) {
            wait();
        }
        
        /* Model the departure of passengers that were waiting on the platform */
        waitingPassengers -= mcar;

        /* Update the display */
        passengers.setValue(waitingPassengers);

        notifyAll();
        
        return mcar;
    }

    public synchronized void goNow() {
        // complete implementation for part II
    }
}
