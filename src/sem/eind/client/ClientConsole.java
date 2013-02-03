package sem.eind.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import sem.eind.client.prompt.Prompt;
import sem.eind.net.Command;
import sem.eind.net.ErrorCodes;

public class ClientConsole implements DisplayIF
{
  //Class variables *************************************************

  /**
   * The default port to connect on.
   */
  final public static int DEFAULT_PORT = 5555;

  //Instance variables **********************************************

  /**
   * The instance of the client that created this ConsoleChat.
   */
  HRClient client;
  
  BufferedReader fromConsole;


  //Constructors ****************************************************

  /**
   * Constructs an instance of the ClientConsole UI.
   *
   * @param host The host to connect to.
   * @param port The port to connect on.
   */
  public ClientConsole(String host, int port)
  {
    try
    {
      client= new HRClient(host, port, this);
    }
    catch(IOException exception)
    {
      System.out.println("Error: Can't setup connection!"
                + " Terminating client.");
      System.exit(1);
    }
  }


  //Instance methods ************************************************

  /**
   * This method waits for input from the console.  Once it is
   * received, it sends it to the client's message handler.
   */
  public void accept()
  {
     fromConsole =
        new BufferedReader(new InputStreamReader(System.in));
     
      printMenu();
      
        handleInput();
      }
    
    
  
  private void handleInput() {
	  while (true)
      {
	  String message = readLine();
      Command command;
      
      try{
      	command = Command.values()[Integer.parseInt(message.split(" ")[0])];
      }catch(NumberFormatException E){
      	display("Het eerste woord dient het getal te zijn wat overeenkomt met een keuze uit het menu");
      	continue;
      }
      String result=""+command.ordinal()+Command.DELIM+ErrorCodes.NOERROR.ordinal();
      System.out.println(result);
      for(Prompt<?> prompt:command.getPrompts()){
			boolean promptFilled=false;
      	while(!promptFilled){
      		display(prompt.getPrompt());
  			message= readLine();

  			if(prompt.isInputValid(message)){
  				result=result+Command.DELIM+message;
  				promptFilled=true;
  			}
  			else{
  				display(prompt.getPrompt());
  			}
  		}
      }
      client.handleMessageFromClientUI(result);
      }
}


/**
   * This method overrides the method in the ChatIF interface.  It
   * displays a message onto the screen.
   *
   * @param message The string to be displayed.
   */
  public void display(String message)
  {
    System.out.println(message);
  }
  
  private String readLine(){
	  String line="";
	try {
		line = fromConsole.readLine();
	}
	catch (Exception ex){
	      System.out.println("Unexpected error while reading from console!");
	    }
	  
	  return line;
  }
  @Override
  public void printMenu(){
	 display("Hotel reserverings systeem");
	 display("Maak uw keuze:");
	 Command[] commands=Command.values();
	 for(int i=0;i<commands.length;i++){
		 display(i+") "+commands[i].toString());
	 }

  }

  //Class methods ***************************************************

  /**
   * This method is responsible for the creation of the Client UI.
   *
   * @param args[0] The host to connect to.
   */
  public static void main(String[] args)
  {
    String host = "";
    int port = 0;  //The port number

    try
    {
      host = args[0];
    }
    catch(ArrayIndexOutOfBoundsException e)
    {
      host = "localhost";
    }
    

    try
    {
    	port = Integer.parseInt(args[1]);
    }
	catch(NumberFormatException e){
		port = DEFAULT_PORT;
	}
    catch(ArrayIndexOutOfBoundsException e)
    {
		port = DEFAULT_PORT;
    }
    
    ClientConsole chat= new ClientConsole(host, port);
    chat.accept();  //Wait for console data
  }
		
	
	
	
	
	
	
	
	
	
}

//End of ClientConsole class
