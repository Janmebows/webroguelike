package PlaceholderTesting;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.fdm.controller.MapAndActorThreadController;
import com.fdm.model.Actor;
import com.fdm.model.Enemy;
import com.fdm.model.Map;
import com.fdm.model.PlayerCharacter;

public class PlaceholderApp {

	public static void main(String[] args) {
		Object key = new Object();
		Map map = new Map("20x20test");
		PlayerCharacter playerCharacter = new PlayerCharacter("jim", 5, 5,  key);
		playerCharacter.setCharacterSymbol('µ');
		Actor enemy = new Enemy("hi", 2, 4, key);
		Actor enemy2 = new Enemy("bye",  5, 8, key);
		Actor enemy3 = new Enemy("running out of things", 2, 4, key);
		List<Actor> actorList = new ArrayList<Actor>();
		actorList.add(enemy);
		actorList.add(enemy2);
		actorList.add(enemy3);
		actorList.add(playerCharacter);
		actorList.forEach(x -> x.setMap(map));
		map.addActors(actorList);
		inputThread(playerCharacter);
		MapAndActorThreadController controller = new MapAndActorThreadController(map, key, actorList);
		controller.handle();


	}
	
	private static void inputThread(PlayerCharacter playerCharacter) {
		(new Thread() {
			  public void run() {
				  Scanner scanner = new Scanner(System.in);
				  while(MapAndActorThreadController.isRunning) {
					  char next = scanner.next().charAt(0);
					  if(next=='q') {
						  MapAndActorThreadController.isRunning=false;
						  break;
					  }
					  playerCharacter.setInput(next);
				  }
				  scanner.close();
			  }
			 }).start();
	}

}
