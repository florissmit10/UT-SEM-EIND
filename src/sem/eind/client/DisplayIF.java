package sem.eind.client;

/**
 * This interface implements the abstract method used to display
 * objects onto the client or server UIs.
 *
 */
public interface DisplayIF
{
  /**
   * Method that when overriden is used to display objects onto
   * a UI.
   */
  public abstract void display(String message);
  
  public void printMenu();
}