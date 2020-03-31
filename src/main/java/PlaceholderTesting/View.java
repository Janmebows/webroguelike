package PlaceholderTesting;

import java.util.Scanner;

public class View implements IView{
	
	Scanner scanner = new Scanner(System.in);

	@Override
	public String getUserInput() {
		return scanner.nextLine();
	}

	@Override
	public void setUserOutput(String output) {
		System.out.println(output);
		
	}

	@Override
	public void setUserOutput(double output) {
		System.out.println("output > " + output);	
	}

	@Override
	public void setUserOutput(String string, String message) {
		System.out.println(string + message);
	}	
}
