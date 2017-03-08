package businessLayer;

import org.codehaus.jettison.json.JSONObject;

import dataLayer.DBHandler;
import spark.Response;

public class Delete {

	private final DBHandler dh;

	public Delete(DBHandler dh) {
		this.dh = dh;
	}

	public Object deleteActivity(String aID, Response res) {
		JSONObject jObj = new JSONObject();
		try {
			if (dh.checkActivityId(aID)) {
				dh.deleteActivity();
				jObj.put("message", "activity deleted");
				res.status(200);
			} else {
				jObj.put("message", "activity does not exist");
				res.status(404);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		res.type("application/json");
		return jObj;
	}
}
