/**
 *  Robert Henderson
 */
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author john.mcdowall3
 * Code adapted and extended from code in Computer Networking: A Top Down Approach
 * by Kurose and Ross
 * Enhanced TCPServer by Robert Henderson to implement KnockKnock Protocol
 */
public class TCPKnockKnockServer {
	private static String inputFromClient;
	private static String outputToClient;
	
	// so we can keep track of the name. make sure the joke is consistent
	private static String name;
	
	public enum STATE{
		// Here we have 3 states
		KNOCK(0), // where we respond to a client knock
		NAME(1), // where we respond to a client telling us whos there (name)
		NAME_WHO_PUNCHLINE(2); // where we respond to a client telling us whos there (full name/punchline)
	
		private final int stateNumber;
		
		STATE(int stateNumber){
			this.stateNumber=stateNumber;
		}
		
		private int getStateNumber(){
			return this.stateNumber;
		}
	}
	
		public static int i=0;

	/**
	 * @param args
	 */
	//private static String inputFromClient;
	//private static String outputToClient;
	
	//We will have 1 instance of the protocol
	//private static KnockKnockJokeController KnockKnockJoke;
	
	public static void main(String[] args) {
		//Set up a server and start listening for clients		
		try{
			//Set up a server socket
			ServerSocket serverSocket = new ServerSocket(8989);
			System.out.println("TCP Server is up and running");
			
			//Create a socket to accept incoming connections 
			Socket clientSocket = serverSocket.accept();
         
			// Lets run the knock knock joke and protocol in server mode
			//KnockKnockJokeController KnockKnockJoke = new KnockKnockJokeController(true);
			
			//Handle incoming connections
			//In this loop we are sending and receiving
			//We will have (atleast) 3 roundtrips and will end for sure with Shutdown message
         //for(int i=0;;i++)
         while(true){
				
				//Create an input stream to read what the client sends
				BufferedReader textFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				//Create an output stream to send a reply
				
				DataOutputStream textToClient = new DataOutputStream(clientSocket.getOutputStream());
				inputFromClient = textFromClient.readLine();
				
				//Decide if this is a shutdown command
				if(inputFromClient.equalsIgnoreCase("goodbye")){
					System.out.println("Server shutting down");
					serverSocket.close();
					System.exit(1);
				}
				
				try{
				if(i==STATE.KNOCK.getStateNumber())
				{
					// ignoring case and allowing for a comma
					if(inputFromClient.trim().toLowerCase().equals("knock knock")||inputFromClient.trim().toLowerCase().equals("knock, knock"))
						{
                     //startOver:
							outputToClient="Who's there?";
							i++;
						}
					else throw new Exception("You're supposed to say Knock Knock. Please try again starting with Knock Knock. Type goodbye to terminate.");
				}
				
				else if(i==STATE.NAME.getStateNumber())
				{
					// let the user give us whatever name they want
					name=inputFromClient;
					outputToClient=name +" who?";
					i++;
				}
				
				else //if(i==STATE.NAME_WHO_PUNCHLINE.getStateNumber())
				{
					// the persons full name/punchline should start with the first name and then something else
					if(inputFromClient.trim().toLowerCase().startsWith(name.trim().toLowerCase())&&inputFromClient.trim().length()>name.trim().length())
						{
                  outputToClient="HaHa. Would you like to tell another joke? If yes, please proceed starting with Knock Knock. Type goodbye to terminate.";
						//i++;
                  i=0;
                  }
					else throw new Exception("You're supposed to say "+name+" and deliver the punchline. Please try again starting with Knock Knock. Type goodbye to terminate.");
				}
            
				}

				catch(Exception e){
					outputToClient=e.getMessage();
					i=0;
				}
				
				finally{
				System.out.println("client: " + inputFromClient);
				textToClient.writeBytes(outputToClient+"\n");
				System.out.println("server: " + outputToClient);
				
				// Server should decide if this is a shutdown command
				/*if(outputToClient.equalsIgnoreCase("goodbye")){
					System.out.println("Server shutting down");
					serverSocket.close();
					System.exit(1);
				}	*/
			}}
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}

	
	public static String reverseString(String s){
		//Take the input string, reverse it, and return it
		String outputString = "";
		char[] inputString = s.toCharArray();
		for(int i = inputString.length - 1; i >= 0; i--){
			outputString = outputString + inputString[i];
		}
		
		return outputString;
	}

}
