package controller;

import org.codehaus.jettison.json.JSONObject;

public class Controller {

	private final String ERR = "error";

	/**
	 * Used for checking the user with the security database.
	 * @param id
	 * @param pwd
	 * @return
	 */
	@SuppressWarnings("unused")
	public JSONObject login(String id, String pwd) {
		String token = "abcdf";

		JSONObject jsonObject = new JSONObject();
		try {
			if (token != null) {
				jsonObject.put("key", token);
			} else {
				jsonObject.put(ERR, "user does not exist.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
}
