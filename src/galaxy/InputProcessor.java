package galaxy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputProcessor {

	private static InputProcessor inputInstance = null;

	static HashMap<String, String> unitValueMap = new HashMap<String, String>();
	static HashMap<String, String> questionAnswerMap = new HashMap<String, String>();
	static ArrayList<String> declarations = new ArrayList<String>();
	static HashMap<String, Integer> unitIntegerMap = new HashMap<String, Integer>();
	static HashMap<String, Integer> creditsMap = new HashMap<String, Integer>();

	private static final String LOG_TAG = InputProcessor.class.getSimpleName();

	Logging logging = Logging.getInstance();

	protected InputProcessor() {

	}

	public static InputProcessor getInstance() {
		if (inputInstance == null)
			return new InputProcessor();
		return inputInstance;
	}

	public void ReadFile(String fname) {

		BufferedReader br = null;

		try {
			String currentLine;
			br = new BufferedReader(new FileReader(fname));
			while ((currentLine = br.readLine()) != null) {
				// System.out.println(currentLine);
				processLine(currentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void processLine(String line) {

		String[] tokens = line.split(" ");

		if (line.endsWith("?")) {
			questionAnswerMap.put(line, "");

		} else if (tokens.length == 3 && tokens[1].toLowerCase().equals("is")) {
			unitValueMap.put(tokens[0], tokens[2]);

		} else if (line.toLowerCase().endsWith("credits")) {
			processDeclaration(line);
			
		}
	}

	public int processDeclaration(String line) {
		String[] tokens = line.toLowerCase().split(" ");
		ArrayList<String> declarationStrings = new ArrayList<String>(
				Arrays.asList("silver", "gold", "iron"));
		String countStr = "";
		String creditElement = "";
		for (int i = 0; i < tokens.length; i++) {
			if (!declarationStrings.contains(tokens[i].toLowerCase())) {
				if (unitValueMap.containsKey(tokens[i]))
					countStr += unitValueMap.get(tokens[i]);
				else {
					String msg = LOG_TAG
							+ ": "
							+ "  Incorrect symantics encountered while parsing token: "
							+ "\"" + tokens[i] + "\"" 
							+ " on line: " + line;
					logging.WriteToLog(msg, "INFO");
					return -1;
				}
			} else {
				creditElement = tokens[i];
				break;
			}

		}
		if (!creditElement.equals("")) {
			try {
				int totalCredits = Integer.parseInt(tokens[tokens.length - 2]);
				creditsMap.put(
						creditElement,
						totalCredits
								/ ConversionHelper.RomanToDecimal(countStr));
			} catch (NumberFormatException ne) {
				String msg = LOG_TAG + ": "
						+ "  Unable to process the number in the declaration: "
						+ tokens[tokens.length - 2];
				logging.WriteToLog(msg, "SEVERE");
			} catch (ArithmeticException ae) {
				String msg = LOG_TAG + ": "
						+ "  Divide by zero error. Invalid roman numeral "
						+ countStr;
				logging.WriteToLog(msg, "SEVERE");
			}
		} else {
			String msg = LOG_TAG + ": "
					+ "  Invalid declaration statement: " + line;
			logging.WriteToLog(msg, "INFO");
			return -1;
		}
		return 0;
	}

	public HashMap<String, String> ProcessQuestions() {
		for (Map.Entry<String, String> entry : questionAnswerMap.entrySet()) {
			ProcessQuestion(entry.getKey());
		}
		return questionAnswerMap;
	}

	private int ProcessQuestion(String line) {
		// TODO Auto-generated method stub
		String inpline = line.toLowerCase().replaceAll("\\?", "");
		String pattern1 = "how many credits is(.*)";
		Pattern p1 = Pattern.compile(pattern1);
		Matcher m1 = p1.matcher(inpline);

		String pattern2 = "how much is(.*)";
		Pattern p2 = Pattern.compile(pattern2);
		Matcher m2 = p2.matcher(inpline);

		if (m1.matches()) {

			String updated = inpline.replaceAll(pattern1, "$1").trim();

			String[] tokens = updated.split(" ");
			ArrayList<String> declarationStrings = new ArrayList<String>(
					Arrays.asList("silver", "gold", "iron"));
			String countStr = "";
			String creditElement = "";
			int totalCredits = 0;
			for (int i = 0; i < tokens.length; i++) {
				if (!declarationStrings.contains(tokens[i].toLowerCase())) {
					if (unitValueMap.containsKey(tokens[i]))
						countStr += unitValueMap.get(tokens[i]);
					else {
						String msg = LOG_TAG
								+ ": "
								+ "  Incorrect symantics encountered while parsing token: "
								+ tokens[i];
						logging.WriteToLog(msg, "INFO");
						return -1;
					}
				} else {
					creditElement = tokens[i];
					break;
				}
			}

			if (!creditElement.equals("")
					&& creditsMap.containsKey(creditElement)) {
				totalCredits = ConversionHelper.RomanToDecimal(countStr)
						* creditsMap.get(creditElement);

				String outString = updated + " is " + totalCredits + " Credits";
				questionAnswerMap.put(line, outString);
			} else {
				String msg = LOG_TAG + ": "
						+ "  Cannot process question due to missing information " + line;
				logging.WriteToLog(msg, "INFO");
				return -1;
			}

		} else if (m2.matches()) {
			String updated = inpline.replaceAll(pattern2, "$1").trim();

			String[] tokens = updated.split(" ");
			String countStr = "";

			for (int i = 0; i < tokens.length; i++) {
				if (unitValueMap.containsKey(tokens[i]))
					countStr += unitValueMap.get(tokens[i]);
				else {
					String msg = LOG_TAG
							+ ": "
							+ "  Incorrect symantics encountered while parsing token: "
							+ tokens[i];
					logging.WriteToLog(msg, "INFO");
					return -1;
				}

			}
			int result = ConversionHelper.RomanToDecimal(countStr);

			String outString = updated + " is " + result;
			questionAnswerMap.put(line, outString);
		} else {

			String outString = "I have no idea what you are talking about";
			questionAnswerMap.put(line, outString);

		}
		return 0;
	}
}
