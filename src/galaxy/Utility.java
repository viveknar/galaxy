package galaxy;

import java.util.HashMap;
import java.util.Map;

public class Utility {
	public static void Print(HashMap<String, String> resultMap) {
		for (Map.Entry<String, String> entry : resultMap.entrySet()) {
			System.out.println(entry.getValue());
		}
	}
}
