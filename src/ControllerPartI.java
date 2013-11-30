import display.*;

public class ControllerPartI {

    public static int MAX = 9;
    protected NumberCanvas passengers;
    private int numPassengers = 0;

    public ControllerPartI(NumberCanvas nc) {
        passengers = nc;
    }

    /* Update the number of passengers waiting on the platform */
    public synchronized void newPassenger() throws InterruptedException {
        /* Wait while there are too many passengers on the platform */
        while(numPassengers >= MAX) {
            wait();
        }

        /* Add a new passenger */
        numPassengers++;

        /* Update the display */
        passengers.setValue(numPassengers);

        notifyAll();
    }

    /* Get the number of passenger in a coaster car */
    public synchronized int getPassengers(int mcar)
                throws InterruptedException, NegativeCarCapacityException {
        /* If an incorrect value is given to mcar, throw an exception */
        if(mcar < 0) {
            throw new NegativeCarCapacityException("ERROR:"
                + " The given car capacity is negative.");
        }

        /* Wait while there are not enough passengers waiting to fill the car */
        while(numPassengers < mcar) {
            wait();
        }
        
        /* Model the departure of passengers that were waiting on the platform */
        numPassengers -= mcar;

        /* Update the display */
        passengers.setValue(numPassengers);

        notifyAll();
        
        return mcar;
    }

    public synchronized void goNow() {
        // complete implementation for part II
    }
}
