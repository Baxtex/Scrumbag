import static org.junit.Assert.*;

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
					  .header("accept", "application/json")
					  .field("username", "foo")
					  .field("password", "bar")
					  .asJson();
			assertEquals(jsonResponse.getBody().toString(), "{\"key\":\"abcdf\"}");			
			assertEquals(String.valueOf(jsonResponse.getStatus()), "200");			
		} catch (UnirestException e) {
			e.printStackTrace();
		} 
	}
	
	@Test
	public void testInvalidLogin1() {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.post("http://localhost:4567/login")
					  .header("accept", "application/json")
					  .field("username", "inval")
					  .field("password", "inval")
					  .asJson();
			assertEquals(jsonResponse.getBody().toString(), "{\"key\":\"abcdf\"}");			
			assertEquals(String.valueOf(jsonResponse.getStatus()), "404");			
		} catch (UnirestException e) {
			e.printStackTrace();
		} 
	}
	
	@Test
	public void testLogout1() {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.post("http://localhost:4567/logout/jhdfs")
					  .header("accept", "application/json")
					  .queryString("apiKey", "123")
					  .field("parameter", "value")
					  .field("foo", "bar")
					  .asJson();
			assertEquals(jsonResponse.getBody().toString(), "{\"message\":\"user logged out\"}");			
			assertEquals(String.valueOf(jsonResponse.getStatus()), "200");			
		} catch (UnirestException e) {
			e.printStackTrace();
		} 
	}

}
