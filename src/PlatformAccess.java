public class PlatformAccess {

  // TODO: Add appropriate privacy to the variable
  boolean busy;

  public synchronized void arrive() throws InterruptedException {
    // While the platform is busy, wait.
    while(busy) {
      wait();
    }

    // Model the arrival of a train at the platform: the platform is now busy.
    busy = true;
    notifyAll();
  }

  public synchronized void depart() throws PlatformDepartureException {
    if(!busy) {
      throw new PlatformDepartureException("ERROR:" + 
                        " Trying to leave an empty platforrm.");
    }
    busy = false;
    notifyAll();
  }
}