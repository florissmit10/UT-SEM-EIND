package sem.eind.server;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import sem.eind.client.prompt.Prompt;
import sem.eind.model.*;
import sem.eind.net.*;

public class HRServer extends AbstractServer {
	 //Class variables *************************************************

	  /**
	   * The default port to listen on.
	   */
	  final public static int DEFAULT_PORT = 5555;
	
	  
	  private Hotel hotel=new Hotel();
	
	public HRServer(int port) {
		super(port);
	}


	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		String[] args =((String) msg).split(""+Command.DELIM);
		ErrorCodes error=null;
		Command command=null;
		try {
			command = 	Command.values()[Integer.parseInt(args[0])];
			error = 	ErrorCodes.values()[Integer.parseInt(args[1])];
		} catch (NumberFormatException e) {
			System.out.println("Commando gekregen van client: "+client.getName()+" waarvan de eerste en/of tweede argumenten geen nummer is");			
		}
			String[] arguments=new String[args.length-2];
			for(int i=0;i<args.length-2;i++){
				arguments[i]=args[i+2];
			}
			handleCommand(command, error, arguments,client);
		
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
		  System.out.println("Client connected");
	
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
			try {
				Method methodToExecute = hotel.getClass().getDeclaredMethod(command.getCallableMethodName(),command.getArgumentTypes());
				String response=(String)methodToExecute.invoke(hotel, unWrapArguments(command.getPrompts(), args));
				sendCommandToClient(ErrorCodes.NOERROR,new String[]{response},client);
			
			}catch(InvocationTargetException e){
				if(e.getCause() instanceof HotelException){
					handleException((HotelException)e.getCause(),client);
				}
			}
			catch(Exception e){				
					e.printStackTrace();					
			}
}
	  private Object[] unWrapArguments(Prompt<?>[] prompts,String[] args){
		  Object[] returnable=new Object[args.length];
		  for(int i=0;i<returnable.length;i++){
			  returnable[i]=prompts[i].parseObjectFromString(args[i]);
		  }
		  
		  return returnable;
	  }
	  
	  private void handleException(HotelException e, ConnectionToClient client){
		  sendCommandToClient(e.getError(), new String[]{e.getMessage()}, client);
		  }
	    
	  private void sendCommandToClient(ErrorCodes error, String[] args, ConnectionToClient client){
		  String message=""+error.ordinal();
		  for(String s:args){
			  message=message+Command.DELIM+s;
		  }
		  try {
			  System.out.println(message.replaceAll(Command.DELIM+"", "!,!"));
			client.sendToClient(message);
		} catch (IOException e) {
			System.out.println("Er is een probleem met het verzenden van een commando naar de client");
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
