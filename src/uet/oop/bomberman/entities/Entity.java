package uet.oop.bomberman.entities;

import uet.oop.bomberman.graphics.IRender;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

/**
 * Lop dai dien cho tat ca thuc th? trong game (Bomber, Enemy, Wall, Brick,...)
 */
public abstract class Entity implements IRender {

	protected double _x, _y;
	protected boolean _removed = false;
	protected Sprite _sprite;

	/**
	 * Phuong thuc n�y duoc goi li�n tuc trong v�ng lap game,
	 * muc d�ch la xu l� su kien v� cap nhat trang th�i Entity
	 */
	@Override
	public abstract void update();

	/**
	 * Phuong thuc n�y duoc goi li�n tuc trong v�ng lap game,
	 * muc d�ch la cap nhat h�nh anh cua entity theo trang th�i
	 */
	@Override
	public abstract void render(Screen screen);
	
	public void remove() {
		_removed = true;
	}
	
	public boolean isRemoved() {
		return _removed;
	}
	
	public Sprite getSprite() {
		return _sprite;
	}

	/**
	 * Phuong thuc n�y duoc goi la xu l� khi hai entity va cham v�o nhau
	 * @param e
	 * @return
	 */
	public abstract boolean collide(Entity e);
	
	public double getX() {
		return _x;
	}
	
	public double getY() {
		return _y;
	}
	
	public int getXTile() {
		return Coordinates.pixelToTile(_x + _sprite.SIZE / 2);
	}
	
	public int getYTile() {
		return Coordinates.pixelToTile(_y - _sprite.SIZE / 2);
	}
}