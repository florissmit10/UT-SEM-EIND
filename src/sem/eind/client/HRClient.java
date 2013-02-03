package sem.eind.client;

import java.io.IOException;

import ocsf.client.AbstractClient;
import sem.eind.net.Command;
import sem.eind.net.ErrorCodes;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class HRClient extends AbstractClient
{
		
	//Instance variables **********************************************

  /**
   * The interface type variable.  It allows the implementation of
   * the display method in the client.
   */
  DisplayIF clientUI;


  //Constructors ****************************************************

  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */

  public HRClient(String host, int port, DisplayIF clientUI)
    throws IOException
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    openConnection();
  }


  //Instance methods ************************************************

  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg){
	  if(msg!=null){
	  String[] args =((String) msg).split(""+Command.DELIM);
		ErrorCodes error=null;
		try {
			error = ErrorCodes.values()[Integer.parseInt(args[0])];
		}
		catch (NumberFormatException e) {
				System.out.println("Commando gekregen van server zonder errorcode");			
			}
		String[] arguments=new String[args.length-1];
		for(int i=0;i<args.length-1;i++){
			arguments[i]=args[i+1];
		}
		clientUI.display(arguments[0]);
		clientUI.printMenu();
	  }
		}




/**
   * This method handles all data coming from the UI
   *
   * @param message The message from the UI.
   */
  public void handleMessageFromClientUI(String message)
  {
    try
    {
      sendToServer(message);
    }
    catch(IOException e)
    {
      clientUI.display
        ("Could not send message to server.  Terminating client.");
      quit();
    }
  }

  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
  
  
  @Override
	protected void connectionException(Exception exception) {
	  exception.printStackTrace();
	  clientUI.display("Connection to the server has been terminated. Client will now shutdown.");
	  quit();
	}

}
//End of ChatClient class
