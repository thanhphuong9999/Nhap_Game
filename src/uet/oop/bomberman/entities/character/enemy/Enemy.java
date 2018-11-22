package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Message;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.entities.character.enemy.ai.AI;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

import java.awt.*;
import uet.oop.bomberman.sound.Audio;

public abstract class Enemy extends Character {

	protected int _points;
	
	protected double _speed;
	protected AI _ai;

	protected final double MAX_STEPS;
	protected final double rest;
	protected double _steps;
	
	protected int _finalAnimation = 30;
	protected Sprite _deadSprite;
	
	public Enemy(int x, int y, Board board, Sprite dead, double speed, int points) {
		super(x, y, board);
		
		_points = points;
		_speed = speed;
		
		MAX_STEPS = Game.TILES_SIZE / _speed;
		rest = (MAX_STEPS - (int) MAX_STEPS) / MAX_STEPS;
		_steps = MAX_STEPS;
		
		_timeAfter = 20;
		_deadSprite = dead;
	}
	
	@Override
	public void update() {
		animate();
		
		if(!_alive) {
			afterKill();
			return;
		}
		
		if(_alive)
			calculateMove();
	}
	
	@Override
	public void render(Screen screen) {
		
		if(_alive)
			chooseSprite();
		else {
			if(_timeAfter > 0) {
				_sprite = _deadSprite;
				_animate = 0;
			} else {
				_sprite = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, _animate, 60);
			}
				
		}
			
		screen.renderEntity((int)_x, (int)_y - _sprite.SIZE, this);
	}
	
	@Override
	public void calculateMove() {
		// TODO: Tính toán huong di và di chuyen Enemy theo _ai và cap nhat giá tri cho _direction
		// TODO: su dung canMove() de kiem tra xem có the di chuyen toi diem dã tính toán hay không
		// TODO: su dung move() de di chuyen
		// TODO: nhocap nhat lai giá tri co _moving khi thay doi trang thái di chuyen
                int xa = 0, ya = 0;
                if(_steps <= 0){
                    _direction = _ai.calculateDirection();
                    _steps = MAX_STEPS;
                }
                if(_direction == 0) ya--; 
		if(_direction == 2) ya++;
		if(_direction == 3) xa--;
		if(_direction == 1) xa++;
                
                if(canMove(xa, ya)){
                    _steps -= 1 + rest;
                    move(xa * _speed, ya * _speed);
                    _moving = true;
                }
                else{
                    _steps = 0;
                    _moving = false;
                }
	}
	
	@Override
	public void move(double xa, double ya) {
		if(!_alive) return;
		_y += ya;
		_x += xa;
	}
	
	@Override
	public boolean canMove(double x, double y) {
		// TODO: kiem tra có doi tuong toi vi trí chuan bi di chuyen den và có the di chuyen toi dó hay không
                double x1 = _x;
                double y1 = _y - 16; // tru y de co ket qua chinh xac
                
                if(_direction == 0){
                    y1 += _sprite.getSize() - 1;
                    x1 += _sprite.getSize() / 2;
                }
                if(_direction == 1){
                    y1 += _sprite.getSize() / 2;
                    x1 += 1;
                }
                if(_direction == 2){
                    x1 += _sprite.getSize() / 2;
                    y1 += 1;
                }
                if(_direction == 3){
                    x1 += _sprite.getSize() - 1;
                    y1 += _sprite.getSize() / 2;
                }
                int xx = Coordinates.pixelToTile(x1) + (int) x;
                int yy = Coordinates.pixelToTile(y1) + (int) y;
                
                Entity e = _board.getEntity(xx, yy, this);
                
		return e.collide(this);
	}

	@Override
	public boolean collide(Entity e) {
		// TODO: xu ly va cham voi Flame
		if (e instanceof Flame) {
                    kill();
                    return true;
                }
		
                // TODO: xu ly va cham voi Bomber
                if (e instanceof Bomber) {
                    ((Bomber) e).kill();
                    return false;
                }
		return true;
	}
	
	@Override
	public void kill() {
            //TODO: am thanh enemy chet
                Audio.playMenuMove();
		if(!_alive) return;
		_alive = false;
		
		_board.addPoints(_points);

		Message msg = new Message("+" + _points, getXMessage(), getYMessage(), 2, Color.white, 14);
		_board.addMessage(msg);
	}
	
	
	@Override
	protected void afterKill() {
		if(_timeAfter > 0) --_timeAfter;
		else {
			if(_finalAnimation > 0) --_finalAnimation;
			else
				remove();
		}
	}
	
	protected abstract void chooseSprite();
}