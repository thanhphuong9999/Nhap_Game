package uet.oop.bomberman.entities.character;

import java.util.ArrayList;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.Keyboard;

import java.util.Iterator;
import java.util.List;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.character.enemy.Balloon;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.entities.tile.item.Item;
import uet.oop.bomberman.level.Coordinates;
import uet.oop.bomberman.sound.Audio;

public class Bomber extends Character {

    private List<Bomb> _bombs;
    protected Keyboard _input;
    private int step = 5;
    public static List<Item> _powerups = new ArrayList<>(); 
    protected int _timeBetweenPutBombs = 0;
    
    Audio audio;
    
    // Neu gia tri nay < 0 thi cho phep dat doi tuong Bomb tiep theo
    // Cu moi lan dat 1 Bomb moi, gia tri nay se duoc reset ve 0 va giam dan trong moi lan update()
    public Bomber(int x, int y, Board board) {
        super(x, y, board);
        _bombs = _board.getBombs();
        _input = _board.getInput();
        _sprite = Sprite.player_right;
    }

    @Override
    public void update() {
        clearBombs();
        if (!_alive) {
            afterKill();
            return;
        }
        if (_timeBetweenPutBombs < -7500) _timeBetweenPutBombs = 0;
        else _timeBetweenPutBombs--;
        animate();
        calculateMove();
        detectPlaceBomb();
    }

    @Override
    public void render(Screen screen) {
        calculateXOffset();

        if (_alive)
            chooseSprite();
        else
            _sprite = Sprite.player_dead1;

        screen.renderEntity((int) _x, (int) _y - _sprite.SIZE, this);
    }

    public void calculateXOffset() {
        int xScroll = Screen.calculateXOffset(_board, this);
        Screen.setOffset(xScroll, 0);
    }

    // Kiem tra xem co dat duoc bom hay khong? neu co thi dat bom tai vi tri hien tai cua Bomber
    private void detectPlaceBomb() {
        /*
         TODO: Kiem tra xem phim dieu khien dat bom co duoc go va gia tri _timeBetweenPutBombs, Game.getBombRate() co thoa man hay khong
         TODO: Game.getBombRate() se tra ve so luong bom co the dat lien tiep toi thoi diem hien tai
         TODO: _timeBetweenPutBombs dung de ngan chan Bomber dat 2 Bomb cung toi 1 vi tri trong 1 khoang thoi gian qua ngan
         TODO: Neu 3 dieu khien tren thoa man thi thuc hien dat bom bang placeBomb()
         TODO: Sau khi dat, nho giam sat luong BombRate va reset _timeBetweenPutBombs va 0
        
            "_input.space: tin hieu dat bom tu nguoi choi"
        */
        
        if (_input.space && _timeBetweenPutBombs < 0 && Game.getBombRate() > 0) {
            placeBomb(getXTile(), getYTile());
            _timeBetweenPutBombs = 0;
            Game.addBombRate(-1);
            _timeBetweenPutBombs = 30;
        }
    }

    protected void placeBomb(int x, int y) {
        // TODO: thuc hien tao doi tuong bom, dat vào vi trí (x, y)
        Bomb b = new Bomb(x, y, _board);
        _board.addBomb(b);
        //TODO: am thanh dat bom
        Audio.playBombDrop();
    }

    private void clearBombs() {
        
        Iterator<Bomb> bs = _bombs.iterator();
        Bomb b;
        while (bs.hasNext()) {
            b = bs.next();
            if (b.isRemoved()) {
                bs.remove();
                Game.addBombRate(1);
            }
        }

    }

    @Override
    public void kill() {
        //TODO: am thanh game over
        Audio.gameOver();
        
        if (!_alive) return;
        _alive = false;

    }

    @Override
    protected void afterKill() {
        if (_timeAfter > 0) {
            --_timeAfter;
        }
        else {
            _board.endGame();
        }
    }

    @Override
    protected void calculateMove() {
        // TODO: xu lý nhan tín hieu dieu khien huong di tu _input và goi move() de thuc hien di chuyen
        // TODO: nhe cap nhat lai giá tri co _moving khi thay toi trang thái di chuyen
        // lay toc do di chuyen nhan vat = Game.getPlayerSpeed ()
        // khi di chuyen thi  co _moving = true, nguoc lai false        
        step--;

        _moving = true;

        if (_input.up) {
            move(0, -Game.getBomberSpeed());
        } 
        else if (_input.down) {
            move(0, Game.getBomberSpeed());
        } 
        else if (_input.left) {
            move(-Game.getBomberSpeed(), 0);
        } 
        else if (_input.right) {
            move(Game.getBomberSpeed(), 0);
        } 
        else {
            _moving = false;
        }
         
    }

    @Override
    public boolean canMove(double x, double y) {
        // TODO: kiem tra co doi tuong toi vi tri chuan bi di chuyen den va co the di chuyen toi do hay khong
        // Tim mot doi tuong tai 1 vi tri nhat dinh, su dung Board.getEntity (double x ,double y ,Character m )
        int tileX = Coordinates.pixelToTile(x);
        int tileY = Coordinates.pixelToTile(y);

        Entity e = _board.getEntity(tileX, tileY, this);
        return collide(e);
    }

    @Override
    public void move(double xa, double ya) {
        /*
         TODO: Su dung canMove() de kiem tra xem có the di chuyen toi diem da tính toán hay không và thuc hien thay doi toa do _x, _y
         TODO: Nho cap nhat giá tri _direction (huong di cua nhan vat) sau khi di chuyen
        */
        
        double centerX = _x + _sprite.get_realWidth() / 2;
        double centerY = _y - _sprite.get_realHeight() / 2;

        if (xa > 0) _direction = 1;
        if (xa < 0) _direction = 3;
        if (ya > 0) _direction = 2;
        if (ya < 0) _direction = 0;
        
        if (canMove(centerX + xa, centerY + ya)) {
            _x += xa;
            _y += ya;
            if(step <= 0){
                //TODO: am thanh bomber di chuyen
                Audio.bomberWalk();
                step = 30;
            }
        }

        moveCenter();
    }
    
    // collide (): kiem tra 2 doi tuong co duoc tinh la va cham hay khong. Va cham thi khong di chuyen
    @Override
    public boolean collide(Entity e) {
        // TODO: xu lý va cham voi Flame
        // TODO: xu lý va cham voi Enemy
        
        if (e instanceof Flame) {
            this.kill();
            return false;
        } 
        else if (e instanceof Enemy) {
            this.kill();
            return false;
        }
        else if (e instanceof LayeredEntity) {
            return e.collide(this);
        }
        else if (e.getSprite() == Sprite.wall) {
            return false;
        }
//        else if (e instanceof Balloon) {
//            this.kill();
//            return false;
//        }
//        else if(e.getSprite() == Sprite.bomb){
//            return true;
//        }
        return true;
    }
    
    // Center
    public void centerX() {
        int pixelE = Coordinates.tileToPixel(1);
        double centerX = _x + _sprite.get_realWidth() / 2;
        int tileX = Coordinates.pixelToTile(centerX);
        _x = Coordinates.tileToPixel(tileX) + pixelE / 2 - _sprite.get_realWidth() / 2;
    }

    public void centerY() {
        int pixelE = Coordinates.tileToPixel(1);
        double centerY = _y - _sprite.get_realHeight() / 2;
        int tileY = Coordinates.pixelToTile(centerY);
        _y = Coordinates.tileToPixel(tileY) + pixelE / 2 + _sprite.get_realHeight() / 2;
    }

    public void moveCenter() {
        int pixelE = Coordinates.tileToPixel(1);
        double centerX = _x + _sprite.get_realWidth() / 2;
        double centerY = _y - _sprite.get_realHeight() / 2;

        boolean contactTop = !canMove(centerX, centerY - pixelE / 2);
        boolean contactDown = !canMove(centerX, centerY + pixelE / 2);
        boolean contactLeft = !canMove(centerX - pixelE / 2, centerY);
        boolean contactRight = !canMove(centerX + pixelE / 2, centerY);

        if (_direction != 0 && contactDown) centerY();
        if (_direction != 1 && contactLeft) centerX();
        if (_direction != 2 && contactTop) centerY();
        if (_direction != 3 && contactRight) centerX();
    }
    
        public void addPowerup(Item p) {
		if(p.isRemoved()) return;
		
		_powerups.add(p);
		
		p.setValues();
	}
	
	public void clearUsedPowerups() {
		Item p;
		for (int i = 0; i < _powerups.size(); i++) {
			p = _powerups.get(i);
			if(p.isActive() == false)
				_powerups.remove(i);
		}
	}
	
	public void removePowerups() {
		for (int i = 0; i < _powerups.size(); i++) {
				_powerups.remove(i);
		}
	}
    
    private void chooseSprite() {
        switch (_direction) {
            case 0:
                _sprite = Sprite.player_up;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, _animate, 20);
                }
                break;
            case 1:
                _sprite = Sprite.player_right;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                }
                break;
            case 2:
                _sprite = Sprite.player_down;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, _animate, 20);
                }
                break;
            case 3:
                _sprite = Sprite.player_left;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, _animate, 20);
                }
                break;
            default:
                _sprite = Sprite.player_right;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                }
                break;
        }
    }
}
