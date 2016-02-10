package de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.game.achievement;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.entity.Field;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.entity.AI.AdvancedAI;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.game.Map;

public class PrivilegedAchievement implements Observer {

	private Field[] fields;
	private AdvancedAI ai = null;
	@Override
	public void update(Observable o, Object arg) {
		
		if (ai == null){
			ai = new AdvancedAI((Map) o ,1 ,1);
		}
		if (o instanceof Map && arg != null) {
			if (arg.equals(NotifyMessage.PRIVILEGE_CHECK)) {
				ai.suggestTurn();
				HashMap<Integer, Integer> edgeRate = ai.getEdgeHash();
				for (int edge : edgeRate.values()) {
					if (edge != 1)
							return;
				}
				System.out.println("Privelege Achievment!!!!"); //TODO assign to player
			}
		}
	}

//	private boolean isUnowned(Observable o) {
//		for (Field f : ((Map) o).getFieldArray()) {
//			if (f.isOwned()) {
//				return false;
//			}
//		}
//		return true;
//	}
}
