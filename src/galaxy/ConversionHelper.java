package galaxy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConversionHelper {

	public static int GetDecimalFromRomanSymbol(String romanNum) {

		if (romanNum.toUpperCase().equals("I")) {
			return 1;
		} else if (romanNum.toUpperCase().equals("V")) {
			return 5;
		} else if (romanNum.toUpperCase().equals("X")) {
			return 10;
		} else if (romanNum.toUpperCase().equals("L")) {
			return 50;
		} else if (romanNum.toUpperCase().equals("C")) {
			return 100;
		} else if (romanNum.toUpperCase().equals("D")) {
			return 500;
		} else if (romanNum.toUpperCase().equals("M")) {
			return 1000;
		} else {
			System.out.println("Invalid Roman Numeral: " + romanNum);
			return -1;
		}

	}

	public static int RomanToDecimal(String roman) {

		if (ValidateRomanNumeral(roman)) 
			return ProcessRomanNumeral(roman);
		
		return 0;
	}

	private static int ProcessRomanNumeral(String roman) {
		// TODO Auto-generated method stub
		int sum = 0;
		String[] tokens = roman.split("");
		for (int i = 1; i < tokens.length - 1; i++) {
			int currVal = GetDecimalFromRomanSymbol(tokens[i]);
			if (isNextBigger(tokens[i], tokens[i + 1])) {
				currVal *= -1;
			}
			sum += currVal;
		}
		int currVal = GetDecimalFromRomanSymbol(tokens[tokens.length - 1]);
		sum += currVal;
		return sum;
	}

	private static boolean isNextBigger(String curr, String next) {
		if (GetDecimalFromRomanSymbol(curr) < GetDecimalFromRomanSymbol(next))
			return true;
		return false;
	}

	private static boolean ValidateRomanNumeral(String roman) {

		String pattern = "^M{0,3}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$";

		Pattern r = Pattern.compile(pattern);

		Matcher m = r.matcher(roman);
		if (m.find()) {
			return true;
		} else 
			return false;
	
	}

}
