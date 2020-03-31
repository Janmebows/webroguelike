package PlaceholderTesting;

public interface IView {
	
	String getUserInput();
	void setUserOutput(String output);
	void setUserOutput(double output);
	void setUserOutput(String string, String message);

}
