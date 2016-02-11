package de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.game.achievement;

import java.util.Observable;
import java.util.Observer;

import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.entity.Player;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.game.Game;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.game.Map;

/**
 * This is an achievement we made up ourselves. It is achieved, if a player has
 * a streak of marking more than half of the fields on the map in one turn. Its
 * named because achieving it has fatal consequences for the other players -
 * they have no chance to win.
 */
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
				Player active = Game.players.getActive();
				if (streakingPlayer == active) {
					c++;
					if (c > size / 2 + 1 && !((Map) o).isEnd()) {
						achieved = true;
						System.out.println("Finish him!");
					}
				} else {
					streakingPlayer = active;
					c = 0;
				}
			} else if (achieved && arg.equals(NotifyMessage.GAME_END) && streakingPlayer.isHuman()) {
				System.out.println(streakingPlayer + " achieved fatality!");
			}
		}
	}

}
