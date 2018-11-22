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
	 * Phuong thuc này duoc goi liên tuc trong vòng lap game,
	 * muc dích la xu lý su kien và cap nhat trang thái Entity
	 */
	@Override
	public abstract void update();

	/**
	 * Phuong thuc này duoc goi liên tuc trong vòng lap game,
	 * muc dích la cap nhat hình anh cua entity theo trang thái
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
	 * Phuong thuc này duoc goi la xu lý khi hai entity va cham vào nhau
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