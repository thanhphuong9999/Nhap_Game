package uet.oop.bomberman.entities.character.enemy.ai;

import java.util.Random;

public abstract class AI {
	
	protected Random random = new Random();

	/**
	 * Thuat to�n t�m duong di
	 * @return huong di xuong/phai/tr�i/l�n tuong ung voi c�c gi� tri 0/1/2/3
	 */
	public abstract int calculateDirection();
}
