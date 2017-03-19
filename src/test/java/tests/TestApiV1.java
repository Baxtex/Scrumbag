package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Test methods that test the public web api with requests.
 * 
 * @author station
 *
 */
public class TestApiV1 {

	// Two users are automatically created when the program starts executing:

	private final String ADMIN_USERNAME = "adminUsername";
	private final String ADMIN_PASSWORD = "adminPassword";
	private final String ADMIN_KEY = "benjoVtfsobnfbenjoQbttxpse";

	private final String UNAUTHORIZED_USERNAME = "unauthorizedUsername";
	private final String UNAUTHORIZED_PASSWORD = "unauthorizedPassword";
	private final String UNAUTHORIZED_KEY = "vobvuipsj{feVtfsobnfvobvuipsj{feQbttxpse";

	// Test get all projects.

	/**
	 * Setups some basic values
	 */
	@Before
	public void setup() {
		String username = ADMIN_USERNAME;
		String password = ADMIN_PASSWORD;
		String key = ADMIN_KEY;
		String projectName = "setup project";

		String projectId = "0";
		String index = "0";
		String sprintId = "1";
		String sprintTitle = "sprint0";

		String title = "short description";
		String description = "long description";
		String status = "planned";
		String priority = "high";
		String timeExpected = "15:00";
		String timeSpent = "01:00";
		String respUser = "user";

		login(username, password);
		createProject(projectName, key);
		createSprint(projectId, sprintTitle, index, key);

		createActivity(projectId, sprintId, title, description, timeExpected, timeSpent, respUser, status, priority,
				key);

		createActivity(projectId, sprintId, title, description, timeExpected, timeSpent, respUser, status, priority,
				key);
	}

	@Test
	public void testValidGetAllProjects() {
		String username = ADMIN_USERNAME;
		String password = ADMIN_PASSWORD;
		String key = ADMIN_KEY;

		String projectName1 = "projektX";
		String projectName2 = "projektY";

		login(username, password);
		createProject(projectName1, key);
		createProject(projectName2, key);

		HttpResponse<JsonNode> response = getAllProjects();

		String expectedBody = "All the projects.";
		String expectedStatus = "200";

		String resultBody = response.getBody().toString();
		String resultStatus = String.valueOf(response.getStatus());

		assertEquals(expectedBody, resultBody);
		assertEquals(expectedStatus, resultStatus);
	}

	// Login with existing user

	@Test
	public void testValidLogin1() {

		String username = ADMIN_USERNAME;
		String password = ADMIN_PASSWORD;
		String key = ADMIN_KEY;

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

		String username = ADMIN_USERNAME;
		String password = ADMIN_PASSWORD;
		String key = ADMIN_KEY;

		login(username, password);
		HttpResponse<JsonNode> response = logout(key);

		String expectedBody = "{\"Message\":\"Succefully logged out.\",\"Key\":\"" + key + "\"}";
		String expectedStatus = "200";

		String resultBody = response.getBody().toString();
		String resultStatus = String.valueOf(response.getStatus());

		assertEquals(expectedBody, resultBody);
		assertEquals(expectedStatus, resultStatus);
	}

	// Logout from an offline or none existing user

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

		login(ADMIN_USERNAME, ADMIN_PASSWORD);

		String username = "user123";
		String password = "123";
		String authority = "0";
		String key = ADMIN_KEY;

		HttpResponse<JsonNode> response = createUser(username, password, authority, key);

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

		login(ADMIN_USERNAME, ADMIN_PASSWORD);

		String username = "user";
		String password = "123";
		String authority = "0";
		String key = "invalid";

		HttpResponse<JsonNode> response = createUser(username, password, authority, key);

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

		login(UNAUTHORIZED_USERNAME, UNAUTHORIZED_PASSWORD);

		String username = "user";
		String password = "123";
		String authority = "0";
		String key = UNAUTHORIZED_KEY;

		HttpResponse<JsonNode> response = createUser(username, password, authority, key);

		String expectedBody = "{\"Message\":\"User is unauthorized to do this.\",\"Key\":\"" + key + "\"}";
		String expectedStatus = "403";

		String resultBody = response.getBody().toString();
		String resultStatus = String.valueOf(response.getStatus());

		assertEquals(expectedBody, resultBody);
		assertEquals(expectedStatus, resultStatus);
	}

	// Create user with user name that is already taken

	@Test
	public void testInvalidCreateUser3() {

		login(ADMIN_USERNAME, ADMIN_PASSWORD);

		String username = "user";
		String password = "123";
		String authority = "0";
		String key = ADMIN_KEY;

		createUser("user", "xxx", "0", key);

		HttpResponse<JsonNode> response = createUser(username, password, authority, key);

		String expectedBody = "{\"Message\":\"Could not create user. Username is taken.\",\"Username\":\"" + username
				+ "\"}";
		String expectedStatus = "409";

		String resultBody = response.getBody().toString();
		String resultStatus = String.valueOf(response.getStatus());

		assertEquals(expectedBody, resultBody);
		assertEquals(expectedStatus, resultStatus);
	}

	// Create a project with authorized key

	@Test
	public void testValidCreateProject1() {
		login(ADMIN_USERNAME, ADMIN_PASSWORD);

		String projectName = "validProjectName";
		String key = ADMIN_KEY;

		HttpResponse<JsonNode> response = createProject(projectName, key);

		String expectedBody = "{\"Message\":\"Successfully created project.\",\"Project-ID\":\"X\"}";
		String expectedStatus = "201";

		String resultBody = response.getBody().toString();
		String resultStatus = String.valueOf(response.getStatus());

		assertEquals(expectedBody, resultBody);
		assertEquals(expectedStatus, resultStatus);
	}

	// Create project with invalid key

	@Test
	public void testInvalidCreateProject1() {

		login(ADMIN_USERNAME, ADMIN_PASSWORD);

		String projectName = "validProjectName";
		String key = "invalidKey";

		HttpResponse<JsonNode> response = createProject(projectName, key);

		String expectedBody = "{\"Message\":\"Key is invalid.\",\"Key\":\"" + key + "\"}";
		String expectedStatus = "401";

		String resultBody = response.getBody().toString();
		String resultStatus = String.valueOf(response.getStatus());

		assertEquals(expectedBody, resultBody);
		assertEquals(expectedStatus, resultStatus);
	}

	// Create project with unauthorized key

	@Test
	public void testInvalidCreateProject2() {
		login(UNAUTHORIZED_USERNAME, UNAUTHORIZED_PASSWORD);

		String projectName = "validProjectName";
		String key = UNAUTHORIZED_KEY;

		HttpResponse<JsonNode> response = createProject(projectName, key);

		String expectedBody = "{\"Message\":\"User is unauthorized to do this.\",\"Key\":\"" + key + "\"}";
		String expectedStatus = "403";

		String resultBody = response.getBody().toString();
		String resultStatus = String.valueOf(response.getStatus());

		assertEquals(expectedBody, resultBody);
		assertEquals(expectedStatus, resultStatus);
	}

	// Change a project - add users

	@Test
	public void testValidAddUserManagementAndActionAndProjectName1() {

		String username = ADMIN_USERNAME;
		String password = ADMIN_PASSWORD;
		String key = ADMIN_KEY;

		String projectId = "0";
		String action = "add users";
		String users = "[{\"name\" : \"userName1\"}, {\"name\" : \"userName2\"}]";

		login(username, password);

		HttpResponse<JsonNode> response = changeProject(projectId, action, users, key);

		String expectedBody = "{\"message\":\"users added\"}";
		String expectedStatus = "200";

		String resultBody = response.getBody().toString();
		String resultStatus = String.valueOf(response.getStatus());

		assertEquals(expectedBody, resultBody);
		assertEquals(expectedStatus, resultStatus);
	}

	// Change a project - remove users

	@Test
	public void testValidRemoveUserManagementAndActionAndProjectName1() {

		String username = ADMIN_USERNAME;
		String password = ADMIN_PASSWORD;
		String key = ADMIN_KEY;

		String projectId = "0";
		String action1 = "add users";
		String action2 = "remove users";
		String users = "[{\"name\" : \"userName1\"}, {\"name\" : \"userName2\"}]";

		login(username, password);
		changeProject(projectId, action1, users, key);

		HttpResponse<JsonNode> response = changeProject(projectId, action2, users, key);

		String expectedBody = "{\"message\":\"users removed\"}";
		String expectedStatus = "200";

		String resultBody = response.getBody().toString();
		String resultStatus = String.valueOf(response.getStatus());

		assertEquals(expectedBody, resultBody);
		assertEquals(expectedStatus, resultStatus);
	}

	// Change a project - add user that does not exist

	@Test
	public void testInvalidAddUserManagement1() {

		String username = ADMIN_USERNAME;
		String password = ADMIN_PASSWORD;
		String key = ADMIN_KEY;

		String projectId = "0";
		String action = "add users";
		String users = "[{\"name\" : \"userName1\"}, {\"name\" : \"invalid\"}]";

		login(username, password);

		HttpResponse<JsonNode> response = changeProject(projectId, action, users, key);

		String expectedBody = "{\"message\":\"invalid user names\"}";
		String expectedStatus = "404";

		String resultBody = response.getBody().toString();
		String resultStatus = String.valueOf(response.getStatus());

		assertEquals(expectedBody, resultBody);
		assertEquals(expectedStatus, resultStatus);
	}

	// Change a project - remove user that does not exist

	@Test
	public void testInvalidRemoveUserManagement1() {

		String username = ADMIN_USERNAME;
		String password = ADMIN_PASSWORD;
		String key = ADMIN_KEY;

		String projectId = "0";
		String action1 = "add users";
		String action2 = "remove users";
		String users1 = "[{\"name\" : \"userName1\"}, {\"name\" : \"userName2\"}]";
		String users2 = "[{\"name\" : \"userName1\"}, {\"name\" : \"inval\"}]";

		login(username, password);
		changeProject(projectId, action1, users1, key);

		HttpResponse<JsonNode> response = changeProject(projectId, action2, users2, key);

		String expectedBody = "{\"message\":\"invalid user names\"}";
		String expectedStatus = "404";

		String resultBody = response.getBody().toString();// TODO This throws
															// Nullpointer, not
															// sure why,
															// JSonarray works
															// on others tests,
															// exactly the same.
		String resultStatus = String.valueOf(response.getStatus());

		assertEquals(expectedBody, resultBody);
		assertEquals(expectedStatus, resultStatus);
	}

	// Change a project - try non existing action

	@Test
	public void testInvalidAction1() {

		String username = ADMIN_USERNAME;
		String password = ADMIN_PASSWORD;
		String key = ADMIN_KEY;

		String projectName = "validProjectName";
		String projectId = "0";
		String action = "invalid";
		String users = "[{\"name\" : \"userName1\"}, {\"name\" : \"userName2\"}]";

		login(username, password);

		HttpResponse<JsonNode> response = changeProject(projectId, action, users, key);

		String expectedBody = "{\"message\":\"invalid action\"}";
		String expectedStatus = "403";

		String resultBody = response.getBody().toString();
		String resultStatus = String.valueOf(response.getStatus());

		assertEquals(expectedBody, resultBody);
		assertEquals(expectedStatus, resultStatus);
	}

	// Change a project - with invalid project id

	@Test
	public void testInvalidProjectID1() {

		String username = ADMIN_USERNAME;
		String password = ADMIN_PASSWORD;
		String key = ADMIN_KEY;

		String projectId = "invalid";
		String action = "add users";
		String users = "[{\"name\" : \"userName1\"}, {\"name\" : \"inval\"}]";

		login(username, password);

		HttpResponse<JsonNode> response = changeProject(projectId, action, users, key);

		String expectedBody = "{\"message\":\"project-id does not exist\"}";
		String expectedStatus = "404";

		String resultBody = response.getBody().toString();
		String resultStatus = String.valueOf(response.getStatus());

		assertEquals(expectedBody, resultBody);
		assertEquals(expectedStatus, resultStatus);
	}

	// Change a project - with invalid key

	@Test
	public void testInvalidKey1() {

		String username = ADMIN_USERNAME;
		String password = ADMIN_PASSWORD;
		String key = "invalid";

		String projectId = "0";
		String action = "add users";
		String users = "[{\"name\" : \"userName1\"}, {\"name\" : \"inval\"}]";

		login(username, password);

		HttpResponse<JsonNode> response = changeProject(projectId, action, users, key);

		String expectedBody = "{\"Message\":\"Key is invalid.\",\"Key\":\"" + key + "\"}";
		String expectedStatus = "401";

		String resultBody = response.getBody().toString();
		String resultStatus = String.valueOf(response.getStatus());

		assertEquals(expectedBody, resultBody);
		assertEquals(expectedStatus, resultStatus);
	}

	// TODO: Get activities need to be implemented correct. Supposed to return a
	// JSONArray with activities.

	// @Test
	// public void testValidGetActivities1() {
	//
	// try {
	// HttpResponse<JsonNode> jsonResponse = Unirest
	// .get("http://localhost:4567/activities/validProjectId/validKey")
	// .header("accept", "application/json").asJson();
	// assertEquals("{\"activity-id\":\"abcdf\",\"title\":\"this is a valid
	// activity\",\"status\":\"planned\"}",
	// jsonResponse.getBody().toString());
	// assertEquals("200", String.valueOf(jsonResponse.getStatus()));
	//
	// } catch (UnirestException e) {
	// e.printStackTrace();
	// }
	// }
	//
	// private HttpResponse<JsonNode> getActivites(String projectId, String key)
	// {
	//
	// HttpResponse<JsonNode> response = null;
	// try {
	// response = Unirest
	// .get("http://localhost:4567/activities/" + projectId + "/" + key + "")
	// .header("accept", "application/json").asJson();
	// } catch (UnirestException e) {
	// e.printStackTrace();
	// }
	// return response;
	// }
	//
	// @Test
	// public void testInvalidGetActivities1() {
	// try {
	// HttpResponse<JsonNode> jsonResponse =
	// Unirest.get("http://localhost:4567/activities/invalid/validKey")
	// .header("accept", "application/json").asJson();
	//
	// assertEquals("{\"message\":\"activity does not exist\"}",
	// jsonResponse.getBody().toString());
	// assertEquals("404", String.valueOf(jsonResponse.getStatus()));
	//
	// } catch (UnirestException e) {
	// e.printStackTrace();
	// }
	// }
	//
	// @Test
	// public void testInvalidGetActivities2() {
	// try {
	// HttpResponse<JsonNode> jsonResponse =
	// Unirest.get("http://localhost:4567/activities/abcdf/invalidKey")
	// .header("accept", "application/json").asJson();
	//
	// assertEquals("{\"message\":\"key does not exist\"}",
	// jsonResponse.getBody().toString());
	// assertEquals("401", String.valueOf(jsonResponse.getStatus()));
	//
	// } catch (UnirestException e) {
	// e.printStackTrace();
	// }
	// }
	//

	// Create a sprint

	@Test
	public void testValidCreateSprint1() {

		String username = ADMIN_USERNAME;
		String password = ADMIN_PASSWORD;
		String key = ADMIN_KEY;

		String projectId = "0";
		String title = "sprint2";
		String index = "1";

		login(username, password);

		HttpResponse<JsonNode> response = createSprint(projectId, title, index, key);

		String expectedBody = "{\"Message\":\"Successfully created sprint.\",\"Sprint-ID\":\"x\"}";
		String expectedStatus = "201";

		String resultBody = response.getBody().toString();
		String resultStatus = String.valueOf(response.getStatus());

		assertEquals(expectedBody, resultBody);
		assertEquals(expectedStatus, resultStatus);

	}

	// Create a sprint - with invalid key

	@Test
	public void testInvalidCreateSprint1() {

		String username = ADMIN_USERNAME;
		String password = ADMIN_PASSWORD;
		String key = "invalid";

		String projectName = "validProjectName";
		String projectId = "validProjectId";
		String title = "sprint1";
		String index = "1";

		login(username, password);
		createProject(projectName, key);

		HttpResponse<JsonNode> response = createSprint(projectId, title, index, key);

		String expectedBody = "{\"Message\":\"Key is invalid.\",\"Key\":\"" + key + "\"}";
		String expectedStatus = "401";

		String resultBody = response.getBody().toString();
		String resultStatus = String.valueOf(response.getStatus());

		assertEquals(expectedBody, resultBody);
		assertEquals(expectedStatus, resultStatus);
	}

	// Create a sprint - with a project id that does not exist

	@Test
	public void testInvalidCreateSprint2() {

		String username = ADMIN_USERNAME;
		String password = ADMIN_PASSWORD;
		String key = ADMIN_KEY;

		String projectName = "validProjectName";
		String projectId = "invalid";
		String title = "sprint1";
		String index = "1";

		login(username, password);
		createProject(projectName, key);

		HttpResponse<JsonNode> response = createSprint(projectId, title, index, key);

		String expectedBody = "{\"Sprint title\":\"sprint1\",\"Message\":\"Failed to create sprint. It seems project does not exist.\"}";
		String expectedStatus = "404";

		String resultBody = response.getBody().toString();
		String resultStatus = String.valueOf(response.getStatus());

		assertEquals(expectedBody, resultBody);
		assertEquals(expectedStatus, resultStatus);
	}

	// create activity
	@Test
	public void testValidCreateActivity1() {

		String username = ADMIN_USERNAME;
		String password = ADMIN_PASSWORD;
		String key = ADMIN_KEY;

		String projectId = "0";
		String title = "short description";
		String description = "long description";
		String status = "planned";
		String priority = "high";
		String timeExpected = "15:00";
		String timeSpent = "01:00";
		String respUser = "user";

		String sprintId = "1";
		String sprintTitle = "sprint1";
		String index = "1";

		login(username, password);
		createSprint(projectId, sprintTitle, index, key);

		HttpResponse<JsonNode> response = createActivity(projectId, sprintId, title, description, timeExpected,
				timeSpent, respUser, status, priority, key);

		String expectedBody = "{\"Message\":\"Successfully created activity.\",\"Activity-ID\":\"X\"}";

		String expectedStatus = "201";

		String resultBody = response.getBody().toString();
		String resultStatus = String.valueOf(response.getStatus());

		assertEquals(expectedBody, resultBody);
		assertEquals(expectedStatus, resultStatus);
	}
	// Get activity

	@Test
	public void testValidGetActivity1() {

		String username = ADMIN_USERNAME;
		String password = ADMIN_PASSWORD;
		String key = ADMIN_KEY;
		String activityId = "2";

		login(username, password);
		HttpResponse<JsonNode> response = getActivity(activityId, key);

		String expectedBody = "{\"sprint-id\":\"1\",\"user-id\":\"user\",\"description\":\"long description\",\"expected-time\":\"15:0\",\"activity-id\":\"2\",\"message\":\"Successfully returned activity.\",\"title\":\"short description\",\"project-id\":\"0\",\"priority\":\"high\",\"status\":\"planned\"}";

		String expectedStatus = "200";

		String resultBody = response.getBody().toString();
		String resultStatus = String.valueOf(response.getStatus());
		
		assertEquals(expectedBody, resultBody);
 		assertEquals(expectedStatus, resultStatus);
	}

	// Get activity - with invalid key

	@Test
	public void testInvalidGetActivity1() {

		String username = ADMIN_USERNAME;
		String password = ADMIN_PASSWORD;
		String key = "invalid";
		String activityId = "validActivityId";

		login(username, password);
		HttpResponse<JsonNode> response = getActivity(activityId, key);

		String expectedBody = "{\"Message\":\"Key is invalid.\",\"Key\":\"" + key + "\"}";
		String expectedStatus = "401";

		String resultBody = response.getBody().toString();
		String resultStatus = String.valueOf(response.getStatus());

		assertEquals(expectedBody, resultBody);
		assertEquals(expectedStatus, resultStatus);
	}

	// Get activity - with invalid activity id

	@Test
	public void testInvalidGetActivity2() {

		String username = ADMIN_USERNAME;
		String password = ADMIN_PASSWORD;
		String key = ADMIN_KEY;
		String activityId = "invalidActivityId";

		login(username, password);
		HttpResponse<JsonNode> response = getActivity(activityId, key);

		String expectedBody = "{\"Message\":\"Failed to return activity. It seems it does not exist.\",\"Activity-ID\":\"invalidActivityId\"}";
		String expectedStatus = "404";

		String resultBody = response.getBody().toString();
		String resultStatus = String.valueOf(response.getStatus());

		assertEquals(expectedBody, resultBody);
		assertEquals(expectedStatus, resultStatus);
	}

	// Change activity

	@Test
	public void testValidEditActivity1() {

		String username = ADMIN_USERNAME;
		String password = ADMIN_PASSWORD;
		String key = ADMIN_KEY;
		String projectId = "0";
		String sprintId = "1";
		String activityId = "3";
		String title = "short description";
		String description = "long description";
		String status = "planned";
		String priority = "high";
		String expectedTime = "10:00";
		String additionalTime = "15:00";
		String userId = "user";

		login(username, password);
		HttpResponse<JsonNode> response = changeActivity(activityId, projectId, title, description, status, priority,
				expectedTime, additionalTime, sprintId, userId, key);

		String expectedBody = "{\"message\":\"activity changed\"}";
		String expectedStatus = "200";

		String resultBody = response.getBody().toString();
		String resultStatus = String.valueOf(response.getStatus());

		assertEquals(expectedBody, resultBody);
		assertEquals(expectedStatus, resultStatus);
	}

	// Change activity - with invalid key

	@Test
	public void testInvalidEditActivity1() {

		String username = ADMIN_USERNAME;
		String password = ADMIN_PASSWORD;
		String key = "invalid";
		String activityId = "validActivityId";
		String projectId = "validProjectId";
		String title = "short description";
		String description = "long description";
		String status = "planned";
		String priority = "high";
		String expectedTime = "12:00";
		String additionalTime = "15:00";
		String sprintId = "validSprintID";
		String userId = "user";

		login(username, password);
		HttpResponse<JsonNode> response = changeActivity(activityId, projectId, title, description, status, priority,
				expectedTime, additionalTime, sprintId, userId, key);

		String expectedBody = "{\"Message\":\"Key is invalid.\",\"Key\":\"" + key + "\"}";
		String expectedStatus = "401";

		String resultBody = response.getBody().toString();
		String resultStatus = String.valueOf(response.getStatus());

		assertEquals(expectedBody, resultBody);
		assertEquals(expectedStatus, resultStatus);
	}

	// Change activity - with invalid activity id

	@Test
	public void testInvalidEditActivity2() {

		String username = ADMIN_USERNAME;
		String password = ADMIN_PASSWORD;
		String key = ADMIN_KEY;
		String activityId = "invalid";
		String projectId = "0";
		String title = "short description";
		String description = "long description";
		String status = "planned";
		String priority = "high";
		String expectedTime = "12:00";
		String additionalTime = "15:00";
		String sprintId = "validSprintID";
		String userId = "user";

		login(username, password);
		HttpResponse<JsonNode> response = changeActivity(activityId, projectId, title, description, status, priority,
				expectedTime, additionalTime, sprintId, userId, key);

		String expectedBody = "{\"message\":\"activity does not exist\"}";
		String expectedStatus = "404";

		String resultBody = response.getBody().toString();
		String resultStatus = String.valueOf(response.getStatus());

		assertEquals(expectedBody, resultBody);
		assertEquals(expectedStatus, resultStatus);
	}

	// Change activity - with invalid project id

	@Test
	public void testInvalidEditActivity3() {

		String username = ADMIN_USERNAME;
		String password = ADMIN_PASSWORD;
		String key = ADMIN_KEY;
		String activityId = "validActivityId";
		String projectId = "invalid";
		String title = "short description";
		String description = "long description";
		String status = "planned";
		String priority = "high";
		String expectedTime = "12:00";
		String additionalTime = "15:00";
		String sprintId = "validSprintID";
		String userId = "user";

		login(username, password);
		HttpResponse<JsonNode> response = changeActivity(activityId, projectId, title, description, status, priority,
				expectedTime, additionalTime, sprintId, userId, key);

		String expectedBody = "{\"message\":\"project does not exist\"}";
		String expectedStatus = "404";

		String resultBody = response.getBody().toString();
		String resultStatus = String.valueOf(response.getStatus());

		assertEquals(expectedBody, resultBody);
		assertEquals(expectedStatus, resultStatus);
	}

	// Remove an activity

	@Test
	public void testValidDelete1() {

		String username = ADMIN_USERNAME;
		String password = ADMIN_PASSWORD;
		String key = ADMIN_KEY;
		String activityId = "3";

		login(username, password);
		HttpResponse<JsonNode> response = removeActivity(activityId, key);

		String expectedBody = "{\"Message\":\"Successfully removed activity.\",\"Activity-ID\":\"3\"}";
		String expectedStatus = "200";

		String resultBody = response.getBody().toString();
		String resultStatus = String.valueOf(response.getStatus());

		assertEquals(expectedBody, resultBody);
		assertEquals(expectedStatus, resultStatus);
	}

	// Remove an activity - with invalid key

	@Test
	public void testinValidDelete1() {

		String username = ADMIN_USERNAME;
		String password = ADMIN_PASSWORD;
		String key = "invalid";
		String activityId = "validActivityId";

		login(username, password);
		HttpResponse<JsonNode> response = removeActivity(activityId, key);

		String expectedBody = "{\"Message\":\"Key is invalid.\",\"Key\":\"" + key + "\"}";
		String expectedStatus = "401";

		String resultBody = response.getBody().toString();
		String resultStatus = String.valueOf(response.getStatus());

		assertEquals(expectedBody, resultBody);
		assertEquals(expectedStatus, resultStatus);
	}

	// Remove an activity - with invalid activity id

	@Test
	public void testinValidDelete2() {

		String username = ADMIN_USERNAME;
		String password = ADMIN_PASSWORD;
		String key = ADMIN_KEY;
		String activityId = "invalid";

		login(username, password);
		HttpResponse<JsonNode> response = removeActivity(activityId, key);

		String expectedBody = "{\"Message\":\"Failed to remove activity. It seems it does not exist.\",\"Activity-ID\":\"invalid\"}";
		String expectedStatus = "404";

		String resultBody = response.getBody().toString();
		String resultStatus = String.valueOf(response.getStatus());

		assertEquals(expectedBody, resultBody);
		assertEquals(expectedStatus, resultStatus);
	}

	// Private methods for calls to API

	private HttpResponse<JsonNode> login(String username, String password) {
		HttpResponse<JsonNode> response = null;
		try {
			response = Unirest.post("http://localhost:4567/login").header("accept", "application/json")
					.field("username", username).field("password", password).asJson();
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		return response;
	}

	private HttpResponse<JsonNode> logout(String key) {
		HttpResponse<JsonNode> response = null;
		try {
			response = Unirest.post("http://localhost:4567/logout/" + key).header("accept", "application/json")
					.asJson();
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		return response;
	}

	private HttpResponse<JsonNode> createUser(String username, String password, String authority, String key) {
		HttpResponse<JsonNode> response = null;
		try {
			response = Unirest.post("http://localhost:4567/user/" + key).header("accept", "application/json")
					.field("username", username).field("password", password).field("authority", authority).asJson();
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		return response;
	}

	private HttpResponse<JsonNode> createProject(String projectName, String key) {
		HttpResponse<JsonNode> response = null;

		try {
			response = Unirest.post("http://localhost:4567/project/" + key).header("accept", "application/json")
					.field("project-name", projectName).asJson();
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		return response;
	}

	private HttpResponse<JsonNode> changeProject(String projectId, String action, String users, String key) {
		HttpResponse<JsonNode> response = null;
		try {
			response = Unirest.put("http://localhost:4567/project/" + key).header("accept", "application/json")
					.field("project-id", projectId).field("action", action).field("user-ids", users).asJson();
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		return response;
	}

	private HttpResponse<JsonNode> createSprint(String projectId, String title, String index, String key) {
		HttpResponse<JsonNode> response = null;
		try {
			response = Unirest.post("http://localhost:4567/sprint/" + key).header("accept", "application/json")
					.field("project-id", projectId).field("title", title).field("index", index).asJson();
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		return response;
	}

	private HttpResponse<JsonNode> getActivity(String activityId, String key) {
		HttpResponse<JsonNode> response = null;
		try {
			response = Unirest.get("http://localhost:4567/activity/" + activityId + "/" + key)
					.header("accept", "application/json").asJson();
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		return response;
	}

	private HttpResponse<JsonNode> createActivity(String projectId, String sprintId, String title, String description,
			String timeExpected, String timeSpent, String respUser, String status, String priority, String key) {
		HttpResponse<JsonNode> response = null;

		try {
			response = Unirest.post("http://localhost:4567/activity/" + key).header("accept", "application/json")
					.field("project-id", projectId).field("title", title).field("description", description)
					.field("status", status).field("priority", priority).field("timeExpected", timeExpected)
					.field("timeSpent", timeSpent).field("sprint-id", sprintId).field("respUser", respUser).asJson();
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		return response;
	}

	private HttpResponse<JsonNode> changeActivity(String activityId, String projectId, String title, String description,
			String status, String priority, String expectedTime, String additionalTime, String sprintId, String userId,
			String key) {
		HttpResponse<JsonNode> response = null;

		try {
			response = Unirest.put("http://localhost:4567/activity/" + key).header("accept", "application/json")
					.field("activity-id", activityId).field("project-id", projectId).field("title", title)
					.field("description", description).field("status", status).field("priority", priority)
					.field("expected-time", expectedTime).field("additional-time", additionalTime)
					.field("sprint-id", sprintId).field("user-id", userId).asJson();
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		return response;
	}

	private HttpResponse<JsonNode> removeActivity(String activityId, String key) {
		HttpResponse<JsonNode> response = null;

		try {
			response = Unirest.delete("http://localhost:4567/activity/" + activityId + "/" + key)
					.header("accept", "application/json").asJson();
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		return response;
	}

	private HttpResponse<JsonNode> getAllProjects() {
		HttpResponse<JsonNode> response = null;

		try {
			response = Unirest.get("http://localhost:4567/projects").header("accept", "application/json").asJson();
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		return response;
	}

}
