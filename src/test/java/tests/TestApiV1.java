package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class TestApiV1 {

	@Test
	public void testValidLogin1() {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.post("http://localhost:4567/login")
					.header("accept", "application/json").field("username", "foo").field("password", "bar").asJson();

			assertEquals("{\"key\":\"abc\"}", jsonResponse.getBody().toString());
			assertEquals("200", String.valueOf(jsonResponse.getStatus()));

		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testInvalidLogin1() {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.post("http://localhost:4567/login")
					.header("accept", "application/json").field("username", "invalid").field("password", "invalid")
					.asJson();

			assertEquals("{\"message\":\"user does not exist\"}", jsonResponse.getBody().toString());
			assertEquals("404", String.valueOf(jsonResponse.getStatus()));

		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testValidLogout1() {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.post("http://localhost:4567/logout/validKey")
					.header("accept", "application/json").asJson();

			assertEquals("{\"message\":\"user logged out\"}", jsonResponse.getBody().toString());
			assertEquals("200", String.valueOf(jsonResponse.getStatus()));

		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testInvalidLogout1() {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.post("http://localhost:4567/logout/invalidKey")
					.header("accept", "application/json").asJson();

			assertEquals("{\"message\":\"user does not exist\"}", jsonResponse.getBody().toString());
			assertEquals("401", String.valueOf(jsonResponse.getStatus()));

		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testValidCreateUser1() {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.post("http://localhost:4567/user/validAdminKey")
					.header("accept", "application/json").field("username", "valid").field("password", "valid").asJson();

			assertEquals("{\"user-id\":\"XXX\"}", jsonResponse.getBody().toString());
			assertEquals("201", String.valueOf(jsonResponse.getStatus()));

		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testInvalidCreateUser1() {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.post("http://localhost:4567/user/validAdminKey")
					.header("accept", "application/json").field("username", "invalid").field("password", "ab").asJson();

			assertEquals("{\"message\":\"not allowed\"}", jsonResponse.getBody().toString());
			assertEquals("403", String.valueOf(jsonResponse.getStatus()));

		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testInvalidCreateUser2() {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.post("http://localhost:4567/user/invalidKey")
					.header("accept", "application/json").field("username", "a").field("password", "ab").asJson();

			assertEquals("{\"message\":\"key does not exist\"}", jsonResponse.getBody().toString());
			assertEquals("401", String.valueOf(jsonResponse.getStatus()));

		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testValidCreateProject1() {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.post("http://localhost:4567/project/validAdminKey")
					.header("accept", "application/json").field("project-name", "project1").asJson();

			assertEquals("{\"project-id\":\"XXX\"}", jsonResponse.getBody().toString());
			assertEquals("201", String.valueOf(jsonResponse.getStatus()));

		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testInvalidCreateProject1() {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.post("http://localhost:4567/project/validAdminKey")
					.header("accept", "application/json").field("project-name", "invalid").asJson();

			assertEquals("{\"message\":\"not allowed\"}", jsonResponse.getBody().toString());
			assertEquals("403", String.valueOf(jsonResponse.getStatus()));

		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testInvalidCreateProject2() {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.post("http://localhost:4567/project/invalidKey")
					.header("accept", "application/json").field("project-name", "project1").asJson();

			assertEquals("{\"message\":\"key does not exist\"}", jsonResponse.getBody().toString());
			assertEquals("401", String.valueOf(jsonResponse.getStatus()));

		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testValidAddUserManagementAndActionAndProjectName1() {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.put("http://localhost:4567/project/validAdminKey")
					.header("accept", "application/json").field("project-id", "validProjectId")
					.field("action", "add users").field("user-ids", "{\"name\":\"user-id1\", \"name\":\"user-id2\"}")
					.asJson();

			assertEquals("{\"message\":\"users added\"}", jsonResponse.getBody().toString());
			assertEquals("200", String.valueOf(jsonResponse.getStatus()));

		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testValidRemoveUserManagementAndActionAndProjectName1() {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.put("http://localhost:4567/project/validAdminKey")
					.header("accept", "application/json").field("project-id", "validProjectId")
					.field("action", "remove users").field("user-ids", "{\"name\":\"user-id1\", \"name\":\"user-id2\"}")
					.asJson();

			assertEquals("{\"message\":\"users removed\"}", jsonResponse.getBody().toString());
			assertEquals("200", String.valueOf(jsonResponse.getStatus()));

		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testInvalidAddUserManagement1() {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.put("http://localhost:4567/project/validAdminKey")
					.header("accept", "application/json").field("project-id", "validProjectId")
					.field("action", "add users").field("user-ids", "{\"name\":\"invalid\", \"name\":\"invalid\"}")
					.asJson();

			assertEquals("{\"message\":\"invalid user names\"}", jsonResponse.getBody().toString());
			assertEquals("404", String.valueOf(jsonResponse.getStatus()));

		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testInvalidRemoveUserManagement1() {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.put("http://localhost:4567/project/validAdminKey")
					.header("accept", "application/json").field("project-id", "validProjectId")
					.field("action", "remove users").field("user-ids", "{\"name\":\"invalid\", \"name\":\"invalid\"}")
					.asJson();

			assertEquals("{\"message\":\"invalid user names\"}", jsonResponse.getBody().toString());
			assertEquals("404", String.valueOf(jsonResponse.getStatus()));

		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testInvalidAction1() {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.put("http://localhost:4567/project/validAdminKey")
					.header("accept", "application/json").field("project-id", "validProjectId")
					.field("action", "invalid").field("user-ids", "{\"name\":\"user-id1\", \"name\":\"user-id2\"}")
					.asJson();

			assertEquals("{\"message\":\"invalid action\"}", jsonResponse.getBody().toString());
			assertEquals("403", String.valueOf(jsonResponse.getStatus()));

		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testInvalidProjectID1() {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.put("http://localhost:4567/project/validAdminKey")
					.header("accept", "application/json").field("project-id", "invalid").field("action", "remove users")
					.field("user-ids", "{\"name\":\"user-id1\", \"name\":\"user-id2\"}").asJson();

			assertEquals("{\"message\":\"project-id does not exist\"}", jsonResponse.getBody().toString());
			assertEquals("404", String.valueOf(jsonResponse.getStatus()));

		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testInvalidKey1() {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.put("http://localhost:4567/project/invalidKey")
					.header("accept", "application/json").field("project-id", "invalid").field("action", "remove users")
					.field("user-ids", "{\"name\":\"user-id1\", \"name\":\"user-id2\"}").asJson();

			assertEquals("{\"message\":\"key does not exist\"}", jsonResponse.getBody().toString());
			assertEquals("401", String.valueOf(jsonResponse.getStatus()));

		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testValidGetActivities1() {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest
					.get("http://localhost:4567/activities/validProjectId/validUserKey")
					.header("accept", "application/json").asJson();

			assertEquals("{\"activity-id\":\"abcdf\",\"title\":\"this is a valid activity\",\"status\":\"planned\"}",
					jsonResponse.getBody().toString());
			assertEquals("200", String.valueOf(jsonResponse.getStatus()));

		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testInvalidGetActivities1() {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.get("http://localhost:4567/activities/invalid/validUserKey")
					.header("accept", "application/json").asJson();

			assertEquals("{\"message\":\"activity does not exist\"}", jsonResponse.getBody().toString());
			assertEquals("404", String.valueOf(jsonResponse.getStatus()));

		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testInvalidGetActivities2() {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.get("http://localhost:4567/activities/abcdf/invalidKey")
					.header("accept", "application/json").asJson();

			assertEquals("{\"message\":\"key does not exist\"}", jsonResponse.getBody().toString());
			assertEquals("401", String.valueOf(jsonResponse.getStatus()));

		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testValidCreateSprint1() {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.post("http://localhost:4567/sprint/validUserKey")
					.header("accept", "application/json").field("project-id", "validProjectId")
					.field("title", "sprint1").field("index", "1").asJson();

			assertEquals("{\"sprint-id\":\"xxx\"}", jsonResponse.getBody().toString());
			assertEquals("201", String.valueOf(jsonResponse.getStatus()));

		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testInvalidCreateSprint1() {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.post("http://localhost:4567/sprint/invalidKey")
					.header("accept", "application/json").field("project-id", "validProjectId")
					.field("title", "sprint1").field("index", "1").asJson();

			assertEquals("{\"message\":\"key does not exist\"}", jsonResponse.getBody().toString());
			assertEquals("401", String.valueOf(jsonResponse.getStatus()));

		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testInvalidCreateSprint2() {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.post("http://localhost:4567/sprint/validUserKey")
					.header("accept", "application/json").field("project-id", "invalidProjectId")
					.field("title", "sprint1").field("index", "1").asJson();

			assertEquals("{\"message\":\"project does not exist\"}", jsonResponse.getBody().toString());
			assertEquals("404", String.valueOf(jsonResponse.getStatus()));

		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testValidGetActivity1() {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest
					.get("http://localhost:4567/activity/validActivityId/validUserKey")
					.header("accept", "application/json").asJson();

			assertEquals(
					"{\"additional-time\":\"xxx\",\"sprint-id\":\"xxx\",\"user-id\":\"xxx\",\"description\":\"xxx\",\"expected-time\":\"xxx\",\"activity-id\":\"xxx\",\"project-id\":\"xxx\",\"title\":\"xxx\",\"priority\":\"xxx\",\"status\":\"xxx\"}",
					jsonResponse.getBody().toString());
			assertEquals("200", String.valueOf(jsonResponse.getStatus()));

		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testInvalidGetActivity1() {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest
					.get("http://localhost:4567/activity/validActivityId/invalidKey")
					.header("accept", "application/json").asJson();

			assertEquals("{\"message\":\"key does not exist\"}", jsonResponse.getBody().toString());
			assertEquals("401", String.valueOf(jsonResponse.getStatus()));

		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testInvalidGetActivity2() {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest
					.get("http://localhost:4567/activity/invalidActivityId/validUserKey")
					.header("accept", "application/json").asJson();

			assertEquals("{\"message\":\"activity does not exist\"}", jsonResponse.getBody().toString());
			assertEquals("404", String.valueOf(jsonResponse.getStatus()));

		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testValidEditActivity1() {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.put("http://localhost:4567/activity/validUserKey")
					.header("accept", "application/json").field("activity-id", "validActivityId")
					.field("project-id", "validProjectId").field("title", "short description")
					.field("description", "long description").field("status", "planned").field("priority", "high")
					.field("expected-time", "12:00").field("additional-time", "15:00")
					.field("sprint-id", "validSprintID").field("user-id", "user").asJson();

			assertEquals("{\"message\":\"activity changed\"}", jsonResponse.getBody().toString());
			assertEquals("200", String.valueOf(jsonResponse.getStatus()));

		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testInvalidEditActivity1() {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.put("http://localhost:4567/activity/invalidKey")
					.header("accept", "application/json").field("activity-id", "validActivityId")
					.field("project-id", "ValidProjectId").field("title", "short description")
					.field("description", "long description").field("status", "planned").field("priority", "high")
					.field("expected-time", "12:00").field("additional-time", "15:00")
					.field("sprint-id", "validSprintID").field("user-id", "user").asJson();

			assertEquals("{\"message\":\"key does not exist\"}", jsonResponse.getBody().toString());
			assertEquals("401", String.valueOf(jsonResponse.getStatus()));

		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testInvalidEditActivity2() {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.put("http://localhost:4567/activity/validUserKey")
					.header("accept", "application/json").field("activity-id", "invalidActivityId")
					.field("project-id", "validProjectId").field("title", "short description")
					.field("description", "long description").field("status", "planned").field("priority", "high")
					.field("expected-time", "12:00").field("additional-time", "15:00")
					.field("sprint-id", "validSprintID").field("user-id", "user").asJson();

			assertEquals("{\"message\":\"activity does not exist\"}", jsonResponse.getBody().toString());
			assertEquals("404", String.valueOf(jsonResponse.getStatus()));

		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testInvalidEditActivity3() {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.put("http://localhost:4567/activity/validUserKey")
					.header("accept", "application/json").field("activity-id", "validActivityId")
					.field("project-id", "invalidProjectId").field("title", "short description")
					.field("description", "long description").field("status", "planned").field("priority", "high")
					.field("expected-time", "12:00").field("additional-time", "15:00")
					.field("sprint-id", "validSprintID").field("user-id", "user").asJson();

			assertEquals("{\"message\":\"project does not exist\"}", jsonResponse.getBody().toString());
			assertEquals("404", String.valueOf(jsonResponse.getStatus()));

		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}

}
