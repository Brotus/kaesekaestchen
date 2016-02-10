package de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.game.achievement;

import java.util.Observable;
import java.util.Observer;

import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.entity.Player;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.game.Game;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.game.Map;

public class FatalAchievement implements Observer {

	int size = 0, c = 0;
	Player streakingPlayer;
	boolean achieved = false;

	@Override
	public void update(Observable o, Object arg) {

		if (size == 0) {
			size = ((Map) o).getFieldArray().length;
		}
		if (arg != null) {
			if (!achieved && arg.equals(NotifyMessage.FIELD_CLOSED)) {
				if (streakingPlayer == Game.players.getActive()) {
					c++;
					if (c > size / 2 && !((Map) o).isEnd()) {
						achieved = true;
						System.out.println("Finish him!");
					}
				} else {
					c = 0;
				}
			} else if (arg.equals(NotifyMessage.GAME_END)) {
				System.out.println("Fatility! - Player " + streakingPlayer + " achieved the Fatal Achievement!");
			}
		}
	}

}
