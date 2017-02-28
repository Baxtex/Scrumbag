package api;

import static spark.Spark.get;

public class ApiV1 {

	public static void main(String[] args) {
		// The ULR for this is: http://localhost:4567/hello
		get("/hello", (req, res) -> "Hello World");
	}
}
