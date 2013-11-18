import display.*;

public class Controller {

  public static int MAX = 9;
  protected NumberCanvas passengers;
  private int waitingPassengers = 0;
  private int carCapacity;
  private boolean isButtonPressed;

  public Controller(NumberCanvas nc) {
    passengers = nc;
    isButtonPressed = false;
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
    if(mcar < 0) {
      // TODO: Throw appropriate exception ??
    }

    /* Wait while there are not enough passengers waiting to fill the car 
       or while */
    while((mcar <= 0 || mcar > waitingPassengers)
                && (!isButtonPressed) /* CONDITIONS MISSING HERE */) {
      carCapacity = mcar;
      wait();
    }

    /* If there are no enough passengers to fil the car but the button is
       pressed, then the capacity of the car is restricted to the number of
       passengers waiting on the platform */
    if(waitingPassengers < mcar && isButtonPressed) {
      mcar = waitingPassengers;
    }

    carCapacity = mcar;
    
    /* Model the departure of passengers that were waiting on the platform */
    waitingPassengers -= mcar;

    /* Set the capacity of the current car to 0 */
    carCapacity = 0;

    /* Set the button state back so pressing to button is not remembered
       between two cars */
    isButtonPressed = false;

    /* Update the display */
    passengers.setValue(waitingPassengers);

    notifyAll();
    
    return mcar;
  }

  public synchronized void goNow() {
    if(carCapacity > 0 && !isButtonPressed
        && waitingPassengers > 0 && waitingPassengers < carCapacity) {
      isButtonPressed = true;
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