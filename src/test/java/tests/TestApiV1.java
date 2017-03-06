package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class TestApiV1 {
	private final String ADMINKEY = "12345";

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
			HttpResponse<JsonNode> jsonResponse = Unirest.post("http://localhost:4567/logout/abc")
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
			HttpResponse<JsonNode> jsonResponse = Unirest.post("http://localhost:4567/user/" + ADMINKEY)
					.header("accept", "application/json").field("username", "a").field("password", "ab").asJson();
			assertEquals("{\"user-id\":\"XXX\"}", jsonResponse.getBody().toString());
			assertEquals("201", String.valueOf(jsonResponse.getStatus()));
		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testInvalidCreateUser1() {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.post("http://localhost:4567/user/" + ADMINKEY)
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
			HttpResponse<JsonNode> jsonResponse = Unirest.post("http://localhost:4567/project/" + ADMINKEY)
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
			HttpResponse<JsonNode> jsonResponse = Unirest.post("http://localhost:4567/project/" + ADMINKEY)
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
			HttpResponse<JsonNode> jsonResponse = Unirest.post("http://localhost:4567/project/dsklfjdlsfj")
					.header("accept", "application/json").field("project-name", "project1").asJson();
			assertEquals("{\"message\":\"key does not exist\"}", jsonResponse.getBody().toString());
			assertEquals("401", String.valueOf(jsonResponse.getStatus()));
		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}
	
	@Test //TODO WIP.
	public void testValidUserManagement1() {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.post("http://localhost:4567/project/" + ADMINKEY)
					.header("accept", "application/json").field("project-id", "project1").field("action", "add users")
					.field("user-ids", "{\"name\":\"user-id1\", \"name\":\"user-id2\"}").asJson();
			assertEquals("{\"project-id\":\"XXX\"}", jsonResponse.getBody().toString());
			assertEquals("201", String.valueOf(jsonResponse.getStatus()));
		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}

}
