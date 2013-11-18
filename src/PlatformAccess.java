public class PlatformAccess {

  /* True if there is a car waiting at the platform, false otherwise */
  private boolean busy;

  public synchronized void arrive() throws InterruptedException {
    /* While the platform is busy, i.e. there is a car, wait */
    while(busy) {
      wait();
    }

    /* Model the arrival of a train at the platform:
       the platform is now busy */
    busy = true;

    notifyAll();
  }

  public synchronized void depart() throws PlatformDepartureException {
    /* If the platform is not occupied, a problem has occured */
    if(!busy) {
      throw new PlatformDepartureException("ERROR:" + 
                        " Trying to leave an empty platforrm.");
    }
    
    /* Model the departure of a train from the platform:
       the platform is now empty */
    busy = false;

    notifyAll();
  }
}