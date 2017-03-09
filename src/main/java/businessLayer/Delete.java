package businessLayer;

import org.codehaus.jettison.json.JSONObject;

import dataLayer.DataHandler;
import spark.Response;

public class Delete {

	private final DataHandler dataHandler;

	public Delete(DataHandler dataHandler) {
		this.dataHandler = dataHandler;
	}

	public Object deleteActivity(String aID, Response res) {
		JSONObject jObj = new JSONObject();
		try {
			if (dataHandler.checkActivityId(aID)) {
				dataHandler.deleteActivity();
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
