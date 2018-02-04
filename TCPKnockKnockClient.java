/**
 *  Robert Henderson
 */
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author john.mcdowall3
 * Code adapted and extended from code in Computer Networking: A Top Down Approach
 * by Kurose and Ross
 * Enhanced TCPClient by Robert Henderson to implement KnockKnock Protocol
 */
public class TCPKnockKnockClient {

	/**
	 * @param args
	 */
	
	private static String textFromUser;
	private static String responseFromServer;
	private static Boolean running = true;
	
	public static void main(String[] args) {
		//Connect to the server and send messages
		BufferedReader inputFromUser = new BufferedReader(new InputStreamReader(System.in));
		//Make the connection and start talking to the server
		try {
			//Set up an outgoing connection
			Socket clientSocket = new Socket("localhost", 8989);
			//Get ready to send the client message
			DataOutputStream outputToServer = new DataOutputStream(clientSocket.getOutputStream());
			//Get ready to read the server's response
			BufferedReader inputFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String responseFromServer = new String();
			System.out.println("The client is up and running");
			while (running == true){//&&!responseFromServer.equals("goodbye")) {
				//Get the text from the user's console
				textFromUser = inputFromUser.readLine();
				//Send the text to the server
				outputToServer.writeBytes(textFromUser + "\n");
				//System.out.println("Sent text to server");
				if(textFromUser.equalsIgnoreCase("goodbye")){
					running = false;
				}
				else{
					//Read the server's response
					responseFromServer = inputFromServer.readLine();
					
					//Print the server's response to the console
					System.out.println("Server Response: " + responseFromServer+"\n");
					
					if(responseFromServer.equals("goodbye"))
					{
					running=false;
					}
				}
				
			}
			clientSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
