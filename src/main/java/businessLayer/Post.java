package businessLayer;

import org.codehaus.jettison.json.JSONObject;

import dataLayer.DBHandler;
import spark.Response;

public class Post {

	private final DBHandler dh;

	public Post(DBHandler dh) {
		this.dh = dh;
	}

	public JSONObject createProject(String projectName, Response res) {
		JSONObject jObj = new JSONObject();
		try {
			if (dh.checkProjectName(projectName)) {
				dh.createProject();
				jObj.put("project-id", "XXX");
				res.status(201);
			} else {
				jObj.put("message", "not allowed");
				res.status(403);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		res.type("application/json");
		return jObj;
	}

	public JSONObject createSprint(String pID, String title, String index, Response res) {
		JSONObject jObj = new JSONObject();
		try {
			if (dh.checkProjectId(pID)) {
				dh.createSprint();
				jObj.put("sprint-id", "xxx");
				res.status(201);
			} else {
				jObj.put("message", "project does not exist");
				res.status(404);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		res.type("application/json");
		return jObj;
	}
}
