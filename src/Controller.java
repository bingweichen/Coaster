import display.*;

public class Controller {

  public static int MAX = 9;
  protected NumberCanvas passengers;

  private int waitingPassengers = 0;


  public Controller(NumberCanvas nc) {
    passengers = nc;
  }

  public void newPassenger() throws InterruptedException {
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

  public int getPassengers(int mcar) throws InterruptedException {
    /* Wait while there are not enough passengers waiting to fill the car */
    while(waitingPassengers <= 0 || mcar > waitingPassengers) {
      wait();
    }
    
    /* Decrement the number of passengers waiting */
    waitingPassengers -= mcar;

    /* Update the display */
    passengers.setValue(waitingPassengers);

    notifyAll();
    
    return mcar; // Or shall we return waitingPassengers?
  }

  public synchronized void goNow() {
    // complete implementation for part II
  }

}