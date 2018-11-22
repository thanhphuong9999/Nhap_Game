package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.entities.character.enemy.Enemy;
public class Flame extends Entity {

	protected Board _board;
	protected int _direction;
	private int _radius;
	protected int xOrigin, yOrigin;
	protected FlameSegment[] _flameSegments = new FlameSegment[0];

	/**
	 *
	 * @param x hoanh do cua Flame
	 * @param y tung do cua Flame
	 * @param direction là huong cua Flame
	 * @param radius do dai cua Flame
	 */
	public Flame(int x, int y, int direction, int radius, Board board) {
		xOrigin = x;
		yOrigin = y;
		_x = x;
		_y = y;
		_direction = direction;
		_radius = radius;
		_board = board;
		createFlameSegments();
	}

	/**
         * Tao ra cac tia lua va luu vao mang _flameSegments
	 * Tao cac FlameSegment, moi segment ung voi mot don vi do dai
	 */
	private void createFlameSegments() {
		// tinh toan do dai Flame, tuon ung voi so luong segment
                /* bien last dùng de dánh dau cho segment cuoi cùng
                last trong Contructor cuar FlameSegment de xac dinh tia lua co phai cuoi cung hay k?
                (vi phan dau tia lua co hinh danhg khac so voi cac phan khac
                */
		_flameSegments = new FlameSegment[calculatePermitedDistance()];
                // TODO: tao các segment duoi dây
                boolean last = false;
                int x = (int)_x;
                int y = (int)_y;
                int n = _flameSegments.length;
                for(int i = 0; i < n; i++){
                    last = (i == n - 1);
                    switch(_direction){
                        case 0:
                            y--;
                            break;
                        case 1:
                            x++;
                            break;
                        case 2:
                            y++;
                            break;
                        case 3:
                            x--;
                            break;
                    }
                    _flameSegments[i] = new FlameSegment(x, y, _direction, last);
                }
                
	}

	/**
         * calculatePermitedDistance () se tinh toan do dai tia lua
         * Luu y: Neu tia lua cham voi doi tuong khac (VD: tuong) thi se bi chan lai
	 * tinh toan do dai cua Flame, neu gap vat can là Brick/Wall, do dài se bi cat ngan
	 * @return
	 */
	private int calculatePermitedDistance() {
		// TODO: thuc hien tinh toan do dai cua Flame "lua"
                int radius = 0;
                int x = (int) _x;
                int y = (int) _y;
                while (radius < _radius) {
                        if (_direction == 0) y--;
                        if (_direction == 1) x++;
                        if (_direction == 2) y++;
                        if (_direction == 3) x--;
            
                        Entity a = _board.getEntity(x, y, null);
            
                        if (a instanceof Character) {
                                ++radius;
                        }
                        if (a instanceof Bomb) {
                                ++radius;
                        }
                        if (a.collide(this) == false) {
                                break;
                        }
                        radius++;
                }
                return radius;
	}
	
	public FlameSegment flameSegmentAt(int x, int y) {
		for (int i = 0; i < _flameSegments.length; i++) {
			if(_flameSegments[i].getX() == x && _flameSegments[i].getY() == y)
				return _flameSegments[i];
		}
		return null;
	}

	@Override
	public void update() {}
	
	@Override
	public void render(Screen screen) {
		for(int i = 0; i < _flameSegments.length; i++) {
			_flameSegments[i].render(screen);
		}
	}

	@Override
	public boolean collide(Entity e) {
		// TODO: xu ly va cham voi Bomber, Enemy. Chú ý doi tuong này có vi trí chính là vi trí cua Bomb da no
		if(e instanceof Bomber){
                    ((Bomber) e).kill();
                    return false;
                }
                if(e instanceof Enemy){
                    ((Enemy) e).kill();
                    return false;
                }
                return true;
	}
}