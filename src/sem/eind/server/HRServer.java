package sem.eind.server;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import sem.eind.net.Command;
import sem.eind.net.ErrorCodes;

public class HRServer extends AbstractServer {
	 //Class variables *************************************************

	  /**
	   * The default port to listen on.
	   */
	  final public static int DEFAULT_PORT = 5555;
	
	
	public HRServer(int port) {
		super(port);
	}


	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		
		String[] args =((String) msg).split(" ");
		ErrorCodes error=null;
		Command command;
		try {
			command = 	Command.values()[Integer.parseInt(args[0])];
			error = 	ErrorCodes.values()[Integer.parseInt(args[1])];
			handleCommand(command, error, args,client);
		} catch (NumberFormatException e) {
			// TODO: handle exception			
		}
		
	}

	  /**
	   * This method overrides the one in the superclass.  Called
	   * when the server starts listening for connections.
	   */
	  @Override
	  protected void serverStarted()
	  {
	    System.out.println
	      ("Server listening for connections on port " + getPort());
	  }

	  /**
	   * Called when the server stops listening for connections.
	   */
	  @Override
	  protected void serverStopped()
	  {
	    System.out.println
	      ("Server has stopped listening for connections.");
	  }
	  /**
	   *Called when a client connects to the server.
	   */
	  @Override
		protected void clientConnected(ConnectionToClient client) {
		  System.out.println("Client");
	
		  super.clientConnected(client);
		}
	  /**
	   * Called when a client connects to the server.
	   */
	  @Override
		protected synchronized void clientDisconnected(ConnectionToClient client) {
			super.clientDisconnected(client);
		}
	  
	  /**
	   * Called when an exception is thrown by the ConnectionToClient thread.
	   */
	  @Override
		protected synchronized void clientException(ConnectionToClient client,
				Throwable exception) {

		  super.clientException(client, exception);
		}
	  
	  public void handleCommand(Command command, ErrorCodes error, String[] args, ConnectionToClient client){

	//TODO invulling geven aan methoden.
	System.out.println(command+" "+ error+" "+ args+" "+client);
	  switch (command) {
	  	case CHECKIN:
			
			break;
		case CHECKUIT:
			
			break;
		case RESERVERING:
			
			break;
		case ANNULEERRESERVERING:
			
			break;
		case REKENING:
		
			break;

		default:
			break;
		}
	  }
	
	  
	//Class methods ***************************************************
	  /**
	   * This method is responsible for the creation of
	   * the server instance (there is no UI in this phase).
	   *
	   * @param args[0] The port number to listen on.  Defaults to 5555
	   *          if no argument is entered.
	   */
	  public static void main(String[] args)
	  {
	    int port = 0; //Port to listen on

	    try
	    {
	      port = Integer.parseInt(args[0]); //Get port from command line
	    }
	    catch(Throwable t)
	    {
	      port = DEFAULT_PORT; //Set port to 5555
	    }

	    HRServer sv = new HRServer(port);
	    try
	    {
	      sv.listen(); //Start listening for connections
	    }
	    catch (Exception ex)
	    {
	      System.out.println("ERROR - Could not listen for clients!");
	    }
	  }
	
}
