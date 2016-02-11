package de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.game.achievement;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.entity.AI.AdvancedAI;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.game.Map;

public class PrivilegedAchievement implements Observer {

	private AdvancedAI ai = null;
	private boolean unlocked;
	
	
	@Override
	public void update(Observable o, Object arg) {
		if (ai == null){
			ai = new AdvancedAI((Map) o ,1 ,1);
		}
		if (o instanceof Map) {
			System.out.println(arg);
			if (arg.equals(NotifyMessage.PRIVILEGE_CHECK) && (!unlocked)) {
				ai.suggestTurn();
				HashMap<Integer, Integer> edgeRate = ai.getEdgeHash();
				for (int edge : edgeRate.values()) {
					if (edge != 0)
							return;
				}
				System.out.println("Privelege Achievment!!!!"); //TODO assign to player
				unlocked = true;

			}
		}
	}
}
