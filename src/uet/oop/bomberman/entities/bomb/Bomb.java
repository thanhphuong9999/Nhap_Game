package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.AnimatedEntitiy;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;
import uet.oop.bomberman.sound.Audio;

public class Bomb extends AnimatedEntitiy {

	protected double _timeToExplode = 120; //2 seconds
	public int _timeAfter = 20;
	
	protected Board _board;
	protected Flame[] _flames;
	protected boolean _exploded = false;
	protected boolean _allowedToPassThru = true;
	
	public Bomb(int x, int y, Board board) {
		_x = x;
		_y = y;
		_board = board;
		_sprite = Sprite.bomb;
	}
	
	@Override
	public void update() {
		if(_timeToExplode > 0) 
			_timeToExplode--;
		else {
			if(!_exploded) 
				explode();
			else
				updateFlames();
			
			if(_timeAfter > 0) 
				_timeAfter--;
			else
				remove();
		}
			
		animate();
	}
	
	@Override
	public void render(Screen screen) {
		if(_exploded) {
			_sprite =  Sprite.bomb_exploded2;
			renderFlames(screen);
		} else
			_sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, _animate, 60);
		
		int xt = (int)_x << 4;
		int yt = (int)_y << 4;
		
		screen.renderEntity(xt, yt , this);
	}
	
	public void renderFlames(Screen screen) {
		for (int i = 0; i < _flames.length; i++) {
			_flames[i].render(screen);
		}
	}
	
	public void updateFlames() {
		for (int i = 0; i < _flames.length; i++) {
			_flames[i].update();
		}
	}

    /**
     * Xu ly bom no
     */
	protected void explode() {
		_exploded = true;
		_allowedToPassThru = true;
		
                // TODO: xu ly khi Character dung tai vi tr� Bomb
                Character character = _board.getCharacterAtExcluding((int) _x, (int) _y, null);
		if(character != null){
                    character.kill();
                    //Audio.playMenuSelect(); loi
                }
                // TODO: tao c�c Flame
                 _flames = new Flame[4];
                 for(int i = 0; i < _flames.length; i++){
                     _flames[i] = new Flame((int)_x, (int)_y, i, Game.getBombRadius(), _board);
                 }
	}
	
	public FlameSegment flameAt(int x, int y) {
		if(!_exploded) return null;
		
		for(int i = 0; i < _flames.length; i++) {
			if(_flames[i] == null) return null;
			FlameSegment e = _flames[i].flameSegmentAt(x, y);
			if(e != null) return e;
		}
                
                // TODO: am thanh bom no
		Audio.playBombExplode();
		
                return null;
	}

	@Override
	public boolean collide(Entity e) {
            // TODO: xu l� khi Bomber di ra sau khi vua dat bom (_allowedToPassThru)
            
            if(e instanceof Bomber){
                    double x1 = e.getX() - Coordinates.tileToPixel(getX());
                    double y1 = e.getY() - Coordinates.tileToPixel(getY());
                    
                    if(!(x1 >= -10 && x1 < 16 && y1 >= 1 && y1 <= 28)){
                        _allowedToPassThru = false;
                    }
                    return _allowedToPassThru;
            }
            // TODO: xu l� va cham voi Flame cua Bomb kh�c
            if(e instanceof Flame && !_exploded){
                    explode();
                    //return true;
            }
            return false;
	}
}