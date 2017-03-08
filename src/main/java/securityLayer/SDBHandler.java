package securityLayer;

import java.util.Random;

/**
 * Database handler for the security database.
 * 
 * @author Anton
 *
 */
public class SDBHandler {
	
	private Random rdm;
	
	public SDBHandler(){
		rdm = new Random();
	}

	public void connect(){
		System.out.println("Connected to Security Database");
	}
	
	public void disconnect(){
		System.out.println("Disconnected from Security Database");
	}
	
	public void validateSession(){
		System.out.println("Sessions updated! " + rdm.nextInt(6) + " sessions were removed");
	}

}
