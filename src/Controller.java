import display.*;

public class Controller {

    public static int MAX = 9;
    protected NumberCanvas passengers;
    private int numPassengers = 0;
    private boolean go;

    public Controller(NumberCanvas nc) {
        passengers = nc;
        go = false;
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

    /* Get the number of passenger in a coaster car and return the number of
       passagers who actually left the platform */
    public synchronized int getPassengers(int mcar)
            throws InterruptedException, NegativeCarCapacityException {
        /* If an incorrect value is given to mcar, throw an exception */
        if(mcar < 0) {
            throw new NegativeCarCapacityException("ERROR:"
                + " The given car capacity is negative.");
        }

        /* Wait while there are not enough passengers waiting to fill the car 
          or while */
        while(numPassengers < mcar && !go) {
            wait();
        }

        /* If there are no enough passengers to fil the car but the button is
           pressed, then the capacity of the car is restricted to the number of
           passengers waiting on the platform.
           Since numPassengers must be less than mcar, and that numPassengers is
           always greater or equal to 0, then mcar is greater or equal to 1, 
           which matches the requirement for a car to leave (car non-empty). */
        if(numPassengers < mcar && go) {
            mcar = numPassengers;
        }
        
        /* Model the departure of passengers that were waiting on the platform */
        numPassengers -= mcar;

        /* Set the button state back so pressing to button is not remembered
             between two cars */
        go = false;

        /* Update the display */
        passengers.setValue(numPassengers);

        notifyAll();
        
        return mcar;
    }

    public synchronized void goNow() {
        if(0 < numPassengers) {
            go = true;
        }

        notifyAll();
    }
}

/* 
BUTTON:
    - Car can not leave if empty
    - Only works if a car is waiting at the platform
    - Button press should not be remembered once the car leaves the platform
*/