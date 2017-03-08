package securityLayer;

import java.util.Random;

import dataLayer.SDBHandler;

public class SecurityHandler {
	
	private SDBHandler dbHandler;
	
	/**
	 * Other
	 */
	private Random rdm;
	
	private final String USERNAME_User = "Anton2005";
	private final String PASSWORD_User = "coolkille1337";
	private int key_User;
	
	private final String USERNAME_Admin = "Coffe1337";
	private final String PASSWORD_Admin = "kisekatt123";
	private int key_Admin;
	
	public SecurityHandler(){
		dbHandler = new SDBHandler();
		rdm = new Random();
		dbHandler.connect();
	}
	
	public int login(String username, String password){
		if(username.equals(USERNAME_User) && password.equals(PASSWORD_User)){
			key_User = rdm.nextInt() + 1; 
			return key_User;			
		}else if(username.equals(USERNAME_Admin) && password.equals(PASSWORD_Admin)){
			key_Admin = rdm.nextInt();
			return key_Admin;
		}else{
			
			return 0;
		}
		
	}
	
	public String logout(String key){
		return key + " : Has been logged out!";
	}
	
	public int checkAuthority(int key){
		if(key == key_Admin){
			return 2;
		}else if(key == key_User){
			return 1;
		}else{
			return 0;
		}
	}
	
	
	public void validateSession(){
		dbHandler.validateSession();
	}
	
}
