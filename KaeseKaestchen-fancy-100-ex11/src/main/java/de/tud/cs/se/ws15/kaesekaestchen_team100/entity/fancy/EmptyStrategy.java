package de.tud.cs.se.ws15.kaesekaestchen_team100.entity.fancy;

import de.tud.cs.se.ws15.kaesekaestchen_team100.entity.Map;

/**
 * 	Creates an empty strategy doing absolutely nothing when action is called. Mainly used for testing.
 * 
 *
 */
public class EmptyStrategy implements FancyHandle {

	@Override
	public void action(Map gameMap) { }

}
