package PlaceholderTesting;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.fdm.controller.GameController;
import com.fdm.controller.GameLogicController;
import com.fdm.model.Actor;
import com.fdm.model.Enemy;
import com.fdm.model.Map;
import com.fdm.model.PlayerCharacter;

public class PlaceholderApp {

	public static void main(String[] args) {
		Map map = new Map("20x20test");
		PlayerCharacter playerCharacter = new PlayerCharacter("jim", 5, 5);
		playerCharacter.setCharacterSymbol('µ');
		Actor enemy = new Enemy("hi", 2, 4);
		Actor enemy2 = new Enemy("bye", 5, 8);
		Actor enemy3 = new Enemy("running out of things", 2, 4);
		List<Actor> actorList = new ArrayList<Actor>();
		actorList.add(enemy);
		actorList.add(enemy2);
		actorList.add(enemy3);
		actorList.add(playerCharacter);
		map.addActors(actorList);
		inputThread(playerCharacter);
		GameLogicController controller = new GameLogicController(map, actorList);
		controller.handle();

	}

	private static void inputThread(PlayerCharacter playerCharacter) {
		(new Thread() {
			public void run() {
				Scanner scanner = new Scanner(System.in);
				while (GameLogicController.isRunning) {
					char next = scanner.next().charAt(0);
					if (next == 'q') {
						GameLogicController.isRunning = false;
						break;
					}
					playerCharacter.setInput(next);
				}
				scanner.close();
			}
		}).start();
	}

}
