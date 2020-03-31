package PlaceholderTesting;

import com.fdm.controller.PlayerMovementController;
import com.fdm.model.Account;
import com.fdm.model.Map;
import com.fdm.model.PlayerCharacter;

public class PlaceholderApp {

	public static void main(String[] args) {
		IView iView = new View();
		Map map = new Map();
		Account account = new Account();
		PlayerCharacter playerCharacter = new PlayerCharacter("jim", 5, 5, account);
		playerCharacter.setMap(map);
		
		PlayerMovementController controller = new PlayerMovementController(map, playerCharacter, iView);
		controller.handle();

	}

}
