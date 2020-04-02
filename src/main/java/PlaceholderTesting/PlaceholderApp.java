package PlaceholderTesting;

import java.util.ArrayList;
import java.util.List;

import com.fdm.controller.MapAndActorThreadController;
import com.fdm.controller.PlayerCharacterInputController;
import com.fdm.model.Account;
import com.fdm.model.Actor;
import com.fdm.model.Enemy;
import com.fdm.model.Map;
import com.fdm.model.PlayerCharacter;

public class PlaceholderApp {

	public static void main(String[] args) {
		Object key = new Object();
		Account account = new Account();
		Map map = new Map("20x20test");
		PlayerCharacter playerCharacter = new PlayerCharacter("jim",map, 5, 5, account,key);
		PlayerCharacterInputController pci = new PlayerCharacterInputController(playerCharacter);
		playerCharacter.setCharacterSymbol('µ');
		Actor enemy = new Enemy("hi",map, 2, 4,key);
		Actor enemy2 = new Enemy("bye",map, 5, 8,key);
		Actor enemy3 = new Enemy("running out of things",map, 2, 4,key);
		List<Actor> actorList = new ArrayList<Actor>();
		actorList.add(enemy);
		actorList.add(enemy2);
		actorList.add(enemy3);
		actorList.add(playerCharacter);
		MapAndActorThreadController controller = new MapAndActorThreadController(map, pci,key,actorList);
		controller.handle();

	}

}
