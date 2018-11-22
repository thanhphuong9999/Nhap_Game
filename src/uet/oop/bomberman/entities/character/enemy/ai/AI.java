package uet.oop.bomberman.entities.character.enemy.ai;

import java.util.Random;

public abstract class AI {
	
	protected Random random = new Random();

	/**
	 * Thuat toán tìm duong di
	 * @return huong di xuong/phai/trái/lên tuong ung voi các giá tri 0/1/2/3
	 */
	public abstract int calculateDirection();
}
