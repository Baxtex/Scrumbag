package businessLayer;

import org.codehaus.jettison.json.JSONObject;

import dataLayer.DataHandler;
import spark.Response;

public class Post {

	private final DataHandler dataHandler;

	public Post(DataHandler dataHandler) {
		this.dataHandler = dataHandler;
	}

	public JSONObject createProject(String projectName, Response res) {
		JSONObject jObj = new JSONObject();
		try {
			if (dataHandler.checkProjectName(projectName)) {
				String proj = dataHandler.createProject(projectName);
				jObj.put("project-id", proj);
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
			if (dataHandler.checkProjectId(pID)) {
				String sprint = dataHandler.createSprint(pID, title, Integer.parseInt(index));
				jObj.put("sprint-id", sprint);
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
