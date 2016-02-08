package de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.game.achievement;
import java.util.Observable;
import java.util.Observer;

import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.game.Map;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.game.achievement.NotifyMessage;

public class SurvivorAchievement implements Observer {

	@Override
	public void update(Observable o, Object arg) {
		if(arg.equals(NotifyMessage.FANCY_ACTION_START) && o instanceof Map){
			Map map = (Map) o;
		} 
		
	}

}
