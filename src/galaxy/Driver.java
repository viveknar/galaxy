package galaxy;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InputProcessor ip = InputProcessor.getInstance();
		ip.ReadFile("test");
		Utility.Print(ip.ProcessQuestions());
		//System.out.println(ConversionHelper.RomanToDecimal("MCMIII"));
	}

}
