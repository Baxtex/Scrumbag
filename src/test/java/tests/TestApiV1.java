package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import apiLayer.ApiV1;

/**
 * Test methods that test the public web api with requests.
 * 
 * @author station
 *
 */
public class TestApiV1 {
	
	// Login with existing user
	
	@Test
	public void testValidLogin1() {	
		
		String username = "adminUsername";
		String password = "adminPassword";
		String key = "benjoVtfsobnfbenjoQbttxpse";
		
		HttpResponse<JsonNode> response = login(username, password);	
		
		String expectedBody = "{\"Message\":\"Successfully logged in.\",\"Key\":\"" + key + "\"}";
		String expectedStatus = "201";
		
		String resultBody = response.getBody().toString();
		String resultStatus = String.valueOf(response.getStatus());
		
		assertEquals(expectedBody, resultBody);
		assertEquals(expectedStatus, resultStatus);
	}
	
	// Login with none existing user

	@Test
	public void testInvalidLogin1() {
		
		String username = "invalid";
		String password = "invalid";
		
		HttpResponse<JsonNode> response = login(username, password);	
			
		String expectedBody = "{\"Message\":\"Failed to log in.\",\"Username\":\"" + username + "\"}";
		String expectedStatus = "401";
		
		String resultBody = response.getBody().toString();
		String resultStatus = String.valueOf(response.getStatus());

		assertEquals(expectedBody, resultBody);
		assertEquals(expectedStatus, resultStatus);

	}
	
	// Logout an online user

	@Test
	public void testValidLogout1() {
		
		String username = "adminUsername";
		String password = "adminPassword";
		String key = "benjoVtfsobnfbenjoQbttxpse";
		
		login(username, password);
		HttpResponse<JsonNode> response = logout(key);
			
		String expectedBody = "{\"Message\":\"Succefully logged out.\",\"Key\":\"" + key + "\"}";
		String expectedStatus = "200";
		
		String resultBody = response.getBody().toString();
		String resultStatus = String.valueOf(response.getStatus());

		assertEquals(expectedBody, resultBody);
		assertEquals(expectedStatus, resultStatus);
	}
	
	// Logout from a offline or none existing user

	@Test
	public void testInvalidLogout1() {
		
		String key = "invalid";
		HttpResponse<JsonNode> response = logout(key);
		
		String expectedBody = "{\"Message\":\"Failed to log out.\",\"Key\":\"" + key + "\"}";
		String expectedStatus = "401";
		
		String resultBody = response.getBody().toString();
		String resultStatus = String.valueOf(response.getStatus());

		assertEquals(expectedBody, resultBody);
		assertEquals(expectedStatus, resultStatus);
	}
	
	// Create user with authorized key
	
	@Test
	public void testValidCreateUser1() {
		
		login("adminUsername", "adminPassword");
		
		String username = "newUser";
		String password = "newPassword";
		String key = "benjoVtfsobnfbenjoQbttxpse";
		
		HttpResponse<JsonNode> response = createUser(username, password, key);
		
		String expectedBody = "{\"Message\":\"User was successfully created.\",\"Username\":\"" + username + "\"}";
		String expectedStatus = "201";
		
		String resultBody = response.getBody().toString();
		String resultStatus = String.valueOf(response.getStatus());
				
		assertEquals(expectedBody, resultBody);
		assertEquals(expectedStatus, resultStatus);

	}
	
	// Create user with invalid key

	@Test
	public void testInvalidCreateUser1() {
		
		login("adminUsername", "adminPassword");
		
		String username = "user";
		String password = "123";
		String key = "invalid";
		
		HttpResponse<JsonNode> response = createUser(username, password, key);
		
		String expectedBody = "{\"Message\":\"Key is invalid.\",\"Key\":\"" + key + "\"}";
		String expectedStatus = "401";
		
		String resultBody = response.getBody().toString();
		String resultStatus = String.valueOf(response.getStatus());
				
		assertEquals(expectedBody, resultBody);
		assertEquals(expectedStatus, resultStatus);
	}
	
	// Create user with unauthorized key

	@Test
	public void testInvalidCreateUser2() {
		
		login("unauthorizedUsername", "unauthorizedPassword");
		
		String username = "user";
		String password = "123";
		String key = "vobvuipsj{feVtfsobnfvobvuipsj{feQbttxpse";
		
		HttpResponse<JsonNode> response = createUser(username, password, key);
		
		String expectedBody = "{\"Message\":\"User is unauthorized to do this.\",\"Key\":\"" + key + "\"}";
		String expectedStatus = "403";
		
		String resultBody = response.getBody().toString();
		String resultStatus = String.valueOf(response.getStatus());
				
		assertEquals(expectedBody, resultBody);
		assertEquals(expectedStatus, resultStatus);
	}
	

	
	
	
	
//	@Test
//	public void testValidCreateProject1() {		
//		try {
//			HttpResponse<JsonNode> jsonResponse = Unirest.post("http://localhost:4567/project/validKey")
//					.header("accept", "application/json").field("project-name", "validActivityName").asJson();
//
//			assertEquals("{\"project-id\":\"XXX\"}", jsonResponse.getBody().toString());
//			assertEquals("201", String.valueOf(jsonResponse.getStatus()));
//
//		} catch (UnirestException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testInvalidCreateProject1() {
//		try {
//			HttpResponse<JsonNode> jsonResponse = Unirest.post("http://localhost:4567/project/validKey")
//					.header("accept", "application/json").field("project-name", "invalid").asJson();
//
//			assertEquals("{\"message\":\"not allowed\"}", jsonResponse.getBody().toString());
//			assertEquals("403", String.valueOf(jsonResponse.getStatus()));
//
//		} catch (UnirestException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testInvalidCreateProject2() {
//		try {
//			HttpResponse<JsonNode> jsonResponse = Unirest.post("http://localhost:4567/project/invalidKey")
//					.header("accept", "application/json").field("project-name", "project1").asJson();
//
//			assertEquals("{\"message\":\"key does not exist\"}", jsonResponse.getBody().toString());
//			assertEquals("401", String.valueOf(jsonResponse.getStatus()));
//
//		} catch (UnirestException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testValidAddUserManagementAndActionAndProjectName1() {
//		try {
//			HttpResponse<JsonNode> jsonResponse = Unirest.put("http://localhost:4567/project/validKey")
//					.header("accept", "application/json").field("project-id", "validProjectId")
//					.field("action", "add users").field("user-ids", "{\"name\":\"user-id1\", \"name\":\"user-id2\"}")
//					.asJson();
//
//			assertEquals("{\"message\":\"users added\"}", jsonResponse.getBody().toString());
//			assertEquals("200", String.valueOf(jsonResponse.getStatus()));
//
//		} catch (UnirestException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testValidRemoveUserManagementAndActionAndProjectName1() {
//		try {
//			HttpResponse<JsonNode> jsonResponse = Unirest.put("http://localhost:4567/project/validKey")
//					.header("accept", "application/json").field("project-id", "validProjectId")
//					.field("action", "remove users").field("user-ids", "{\"name\":\"user-id1\", \"name\":\"user-id2\"}")
//					.asJson();
//
//			assertEquals("{\"message\":\"users removed\"}", jsonResponse.getBody().toString());
//			assertEquals("200", String.valueOf(jsonResponse.getStatus()));
//
//		} catch (UnirestException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testInvalidAddUserManagement1() {
//		try {
//			HttpResponse<JsonNode> jsonResponse = Unirest.put("http://localhost:4567/project/validKey")
//					.header("accept", "application/json").field("project-id", "validProjectId")
//					.field("action", "add users").field("user-ids", "{\"name\":\"invalid\", \"name\":\"invalid\"}")
//					.asJson();
//
//			assertEquals("{\"message\":\"invalid user names\"}", jsonResponse.getBody().toString());
//			assertEquals("404", String.valueOf(jsonResponse.getStatus()));
//
//		} catch (UnirestException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testInvalidRemoveUserManagement1() {
//		try {
//			HttpResponse<JsonNode> jsonResponse = Unirest.put("http://localhost:4567/project/validKey")
//					.header("accept", "application/json").field("project-id", "validProjectId")
//					.field("action", "remove users").field("user-ids", "{\"name\":\"invalid\", \"name\":\"invalid\"}")
//					.asJson();
//
//			assertEquals("{\"message\":\"invalid user names\"}", jsonResponse.getBody().toString());
//			assertEquals("404", String.valueOf(jsonResponse.getStatus()));
//
//		} catch (UnirestException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testInvalidAction1() {
//		try {
//			HttpResponse<JsonNode> jsonResponse = Unirest.put("http://localhost:4567/project/validKey")
//					.header("accept", "application/json").field("project-id", "validProjectId")
//					.field("action", "invalid").field("user-ids", "{\"name\":\"user-id1\", \"name\":\"user-id2\"}")
//					.asJson();
//
//			assertEquals("{\"message\":\"invalid action\"}", jsonResponse.getBody().toString());
//			assertEquals("403", String.valueOf(jsonResponse.getStatus()));
//
//		} catch (UnirestException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testInvalidProjectID1() {
//		try {
//			HttpResponse<JsonNode> jsonResponse = Unirest.put("http://localhost:4567/project/validKey")
//					.header("accept", "application/json").field("project-id", "invalid").field("action", "remove users")
//					.field("user-ids", "{\"name\":\"user-id1\", \"name\":\"user-id2\"}").asJson();
//
//			assertEquals("{\"message\":\"project-id does not exist\"}", jsonResponse.getBody().toString());
//			assertEquals("404", String.valueOf(jsonResponse.getStatus()));
//
//		} catch (UnirestException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testInvalidKey1() {
//		try {
//			HttpResponse<JsonNode> jsonResponse = Unirest.put("http://localhost:4567/project/invalidKey")
//					.header("accept", "application/json").field("project-id", "invalid").field("action", "remove users")
//					.field("user-ids", "{\"name\":\"user-id1\", \"name\":\"user-id2\"}").asJson();
//
//			assertEquals("{\"message\":\"key does not exist\"}", jsonResponse.getBody().toString());
//			assertEquals("401", String.valueOf(jsonResponse.getStatus()));
//
//		} catch (UnirestException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testValidGetActivities1() {
//		try {
//			HttpResponse<JsonNode> jsonResponse = Unirest
//					.get("http://localhost:4567/activities/validProjectId/validKey")
//					.header("accept", "application/json").asJson();
//
//			assertEquals("{\"activity-id\":\"abcdf\",\"title\":\"this is a valid activity\",\"status\":\"planned\"}",
//					jsonResponse.getBody().toString());
//			assertEquals("200", String.valueOf(jsonResponse.getStatus()));
//
//		} catch (UnirestException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testInvalidGetActivities1() {
//		try {
//			HttpResponse<JsonNode> jsonResponse = Unirest.get("http://localhost:4567/activities/invalid/validKey")
//					.header("accept", "application/json").asJson();
//
//			assertEquals("{\"message\":\"activity does not exist\"}", jsonResponse.getBody().toString());
//			assertEquals("404", String.valueOf(jsonResponse.getStatus()));
//
//		} catch (UnirestException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testInvalidGetActivities2() {
//		try {
//			HttpResponse<JsonNode> jsonResponse = Unirest.get("http://localhost:4567/activities/abcdf/invalidKey")
//					.header("accept", "application/json").asJson();
//
//			assertEquals("{\"message\":\"key does not exist\"}", jsonResponse.getBody().toString());
//			assertEquals("401", String.valueOf(jsonResponse.getStatus()));
//
//		} catch (UnirestException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testValidCreateSprint1() {
//		try {
//			HttpResponse<JsonNode> jsonResponse = Unirest.post("http://localhost:4567/sprint/validKey")
//					.header("accept", "application/json").field("project-id", "validProjectId")
//					.field("title", "sprint1").field("index", "1").asJson();
//
//			assertEquals("{\"sprint-id\":\"xxx\"}", jsonResponse.getBody().toString());
//			assertEquals("201", String.valueOf(jsonResponse.getStatus()));
//
//		} catch (UnirestException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testInvalidCreateSprint1() {
//		try {
//			HttpResponse<JsonNode> jsonResponse = Unirest.post("http://localhost:4567/sprint/invalidKey")
//					.header("accept", "application/json").field("project-id", "validProjectId")
//					.field("title", "sprint1").field("index", "1").asJson();
//
//			assertEquals("{\"message\":\"key does not exist\"}", jsonResponse.getBody().toString());
//			assertEquals("401", String.valueOf(jsonResponse.getStatus()));
//
//		} catch (UnirestException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testInvalidCreateSprint2() {
//		try {
//			HttpResponse<JsonNode> jsonResponse = Unirest.post("http://localhost:4567/sprint/validKey")
//					.header("accept", "application/json").field("project-id", "invalidProjectId")
//					.field("title", "sprint1").field("index", "1").asJson();
//
//			assertEquals("{\"message\":\"project does not exist\"}", jsonResponse.getBody().toString());
//			assertEquals("404", String.valueOf(jsonResponse.getStatus()));
//
//		} catch (UnirestException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testValidGetActivity1() {
//		try {
//			HttpResponse<JsonNode> jsonResponse = Unirest.get("http://localhost:4567/activity/validActivityId/validKey")
//					.header("accept", "application/json").asJson();
//
//			assertEquals(
//					"{\"additional-time\":\"xxx\",\"sprint-id\":\"xxx\",\"user-id\":\"xxx\",\"description\":\"xxx\",\"expected-time\":\"xxx\",\"activity-id\":\"xxx\",\"project-id\":\"xxx\",\"title\":\"xxx\",\"priority\":\"xxx\",\"status\":\"xxx\"}",
//					jsonResponse.getBody().toString());
//			assertEquals("200", String.valueOf(jsonResponse.getStatus()));
//
//		} catch (UnirestException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testInvalidGetActivity1() {
//		try {
//			HttpResponse<JsonNode> jsonResponse = Unirest
//					.get("http://localhost:4567/activity/validActivityId/invalidKey")
//					.header("accept", "application/json").asJson();
//
//			assertEquals("{\"message\":\"key does not exist\"}", jsonResponse.getBody().toString());
//			assertEquals("401", String.valueOf(jsonResponse.getStatus()));
//
//		} catch (UnirestException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testInvalidGetActivity2() {
//		try {
//			HttpResponse<JsonNode> jsonResponse = Unirest
//					.get("http://localhost:4567/activity/invalidActivityId/validKey")
//					.header("accept", "application/json").asJson();
//
//			assertEquals("{\"message\":\"activity does not exist\"}", jsonResponse.getBody().toString());
//			assertEquals("404", String.valueOf(jsonResponse.getStatus()));
//
//		} catch (UnirestException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testValidEditActivity1() {
//		try {
//			HttpResponse<JsonNode> jsonResponse = Unirest.put("http://localhost:4567/activity/validKey")
//					.header("accept", "application/json").field("activity-id", "validActivityId")
//					.field("project-id", "validProjectId").field("title", "short description")
//					.field("description", "long description").field("status", "planned").field("priority", "high")
//					.field("expected-time", "12:00").field("additional-time", "15:00")
//					.field("sprint-id", "validSprintID").field("user-id", "user").asJson();
//
//			assertEquals("{\"message\":\"activity changed\"}", jsonResponse.getBody().toString());
//			assertEquals("200", String.valueOf(jsonResponse.getStatus()));
//
//		} catch (UnirestException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testInvalidEditActivity1() {
//		try {
//			HttpResponse<JsonNode> jsonResponse = Unirest.put("http://localhost:4567/activity/invalidKey")
//					.header("accept", "application/json").field("activity-id", "validActivityId")
//					.field("project-id", "ValidProjectId").field("title", "short description")
//					.field("description", "long description").field("status", "planned").field("priority", "high")
//					.field("expected-time", "12:00").field("additional-time", "15:00")
//					.field("sprint-id", "validSprintID").field("user-id", "user").asJson();
//
//			assertEquals("{\"message\":\"key does not exist\"}", jsonResponse.getBody().toString());
//			assertEquals("401", String.valueOf(jsonResponse.getStatus()));
//
//		} catch (UnirestException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testInvalidEditActivity2() {
//		try {
//			HttpResponse<JsonNode> jsonResponse = Unirest.put("http://localhost:4567/activity/validKey")
//					.header("accept", "application/json").field("activity-id", "invalidActivityId")
//					.field("project-id", "validProjectId").field("title", "short description")
//					.field("description", "long description").field("status", "planned").field("priority", "high")
//					.field("expected-time", "12:00").field("additional-time", "15:00")
//					.field("sprint-id", "validSprintID").field("user-id", "user").asJson();
//
//			assertEquals("{\"message\":\"activity does not exist\"}", jsonResponse.getBody().toString());
//			assertEquals("404", String.valueOf(jsonResponse.getStatus()));
//
//		} catch (UnirestException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testInvalidEditActivity3() {
//		try {
//			HttpResponse<JsonNode> jsonResponse = Unirest.put("http://localhost:4567/activity/validKey")
//					.header("accept", "application/json").field("activity-id", "validActivityId")
//					.field("project-id", "invalidProjectId").field("title", "short description")
//					.field("description", "long description").field("status", "planned").field("priority", "high")
//					.field("expected-time", "12:00").field("additional-time", "15:00")
//					.field("sprint-id", "validSprintID").field("user-id", "user").asJson();
//
//			assertEquals("{\"message\":\"project does not exist\"}", jsonResponse.getBody().toString());
//			assertEquals("404", String.valueOf(jsonResponse.getStatus()));
//
//		} catch (UnirestException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testValidDelete1() {
//		try {
//			HttpResponse<JsonNode> jsonResponse = Unirest
//					.delete("http://localhost:4567/activity/validActivityId/validKey")
//					.header("accept", "application/json").asJson();
//
//			assertEquals("{\"message\":\"activity deleted\"}", jsonResponse.getBody().toString());
//			assertEquals("200", String.valueOf(jsonResponse.getStatus()));
//
//		} catch (UnirestException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testinValidDelete1() {
//		try {
//			HttpResponse<JsonNode> jsonResponse = Unirest
//					.delete("http://localhost:4567/activity/validActivityId/invalidUserKey")
//					.header("accept", "application/json").asJson();
//
//			assertEquals("{\"message\":\"key does not exist\"}", jsonResponse.getBody().toString());
//			assertEquals("401", String.valueOf(jsonResponse.getStatus()));
//
//		} catch (UnirestException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testinValidDelete2() {
//		try {
//			HttpResponse<JsonNode> jsonResponse = Unirest
//					.delete("http://localhost:4567/activity/invalidActivityId/validKey")
//					.header("accept", "application/json").asJson();
//
//			assertEquals("{\"message\":\"activity does not exist\"}", jsonResponse.getBody().toString());
//			assertEquals("404", String.valueOf(jsonResponse.getStatus()));
//
//		} catch (UnirestException e) {
//			e.printStackTrace();
//		}
//	}
	
	
	
	
	
	
	
	
	// Private methods
	
	private HttpResponse<JsonNode> login(String username, String password) {
		HttpResponse<JsonNode> response = null;
		try {
			response = Unirest.post("http://localhost:4567/login")
								.header("accept", "application/json")
								.field("username", username)
								.field("password", password).asJson();
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	private HttpResponse<JsonNode> logout(String key) {
		HttpResponse<JsonNode> response = null;
		try {
			response = Unirest.post("http://localhost:4567/logout/" + key)
						.header("accept", "application/json").asJson();
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	private HttpResponse<JsonNode> createUser(String username, String password, String key) {
		HttpResponse<JsonNode> response = null;
		try {
			response = Unirest.post("http://localhost:4567/user/" + key)
						.header("accept", "application/json")
						.field("username", username)
						.field("password", password).asJson();
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		return response;
	}
}
