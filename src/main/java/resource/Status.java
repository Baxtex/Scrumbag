package resource;

/**
 * Enum used for status codes and operations.
 * 
 */

public enum Status {
	//Status codes
		 STATUSCODE_OK(200),
		 STATUSCODE_CREATED(201),
		 STATUSCODE_INVALID(401),
		 STATUSCODE_UNAUTHORIZED(403),
		 STATUSCODE_DUPLICATE(409),
	
		 STATUSCODE_NO_RESOURCE(404),

		//Operations
		 OPERATION_LOGIN(0),
		 OPERATION_LOGOUT(1),
		 OPERATION_VALIDATEKEY(3),
		 OPERATION_AUTHORIZATION(4),
		 OPERATION_CREATEUSER(5),
		 AUTHORITY_ADMIN(1),	 OPERATION_DELETE_ACTIVITY(0),  OPERATION_GET_ACTIVITIES(1), OPERATION_GET_ACTIVITY(0), OPERATION_POST_PROJECT(0), OPERATION_POST_SPRINT(1), OPERATION_POST_ACTIVITY(2);
	
	  private final int val;

	  Status(int val) {
	        this.val = val;
	    }
	  
	  public int getVal() {
		  return val;
	  }
}
