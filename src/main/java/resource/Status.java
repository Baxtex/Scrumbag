package resource;

/**
 * Enum used for status codes and operations.
 * 
 */

public enum Status {
	//Status codes
		 OK(200),
		 CREATED(201),
		 INVALID(401),
		 UNAUTHORIZED(403),
		 DUPLICATE(409),
		 NO_RESOURCE(404),

		//Operations
		 LOGIN(0),
		 LOGOUT(1),
		 VALIDATEKEY(3),
		 AUTHORIZATION(4),
		 CREATEUSER(5),
		 AUTHORITY_ADMIN(1),	
		 DELETE_ACTIVITY(0), 
		 GET_ACTIVITIES(1),
		 GET_ACTIVITY(0),
		 POST_PROJECT(0),
		 POST_SPRINT(1), 
		 POST_ACTIVITY(2), OPERATION_DELETE_ACTIVITY(0);
	
	  private final int val;

	  Status(int val) {
	        this.val = val;
	    }
	  
	  public int code() {
		  return val;
	  }
}
