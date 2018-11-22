package uet.oop.bomberman.level;

import java.io.*;
import java.util.*;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Balloon;
import uet.oop.bomberman.entities.character.enemy.Oneal;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.item.FlameItem;
import uet.oop.bomberman.entities.tile.item.SpeedItem;
import uet.oop.bomberman.exceptions.LoadLevelException;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

public class FileLevelLoader extends LevelLoader {

	// Ma tran chua th�ng tin ban do, moi phan tu luu gi� tri k� tu doc duoc
	// tu ma tran ban do trong tep cau h�nh
	
        private static char[][] _map;
	
	public FileLevelLoader(Board board, int level) throws LoadLevelException {
		super(board, level);
	}
	
	@Override
	public void loadLevel(int level) {
            // TODO: doc du lieu tu tep cau h�nh /levels/Level{level}.txt
            // TODO: cap nhat c�c gi� tri doc duoc v�o _width, _height, _level, _map
            
            ArrayList<String> s = new ArrayList<>();
        FileReader fr = null;
        try {
            // TODO: ??c d? li?u t? t?p c?u h?nh /levels/Level{level}.txt
            fr = new FileReader("res\\levels\\Level" + level + ".txt");
            BufferedReader br = new BufferedReader(fr);
            String str = br.readLine();
            int line = 0;
            while (str != null) {
                line++;
                s.add(str);
                str = br.readLine();
            }
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }
        // TODO: cap nhat cac gia tri ??c ???c vao _width, _height, _level, _map
        String[] ar = s.get(0).trim().split(" ");
        _level = Integer.parseInt(ar[0]);
        _height = Integer.parseInt(ar[1]);
        _width = Integer.parseInt(ar[2]);
        _map = new char[_height][_width];
        for (int i = 0; i < _height; i++) {
            for (int j = 0; j < _width; j++) {
                _map[i][j] = s.get(i + 1).charAt(j);
            }
        }
	}

	@Override
	public void createEntities() {
		// TODO: tao c�c Entity cua m�n choi
		// TODO: sau khi tao xong, goi _board.addEntity() ?? th�m Entity v�o game

		// TODO: phan code mau o duoi de huong dan c�ch th�m c�c loai Entity v�o game
		// TODO: h�y x�a n� khi ho�n th�nh chuc na load m�n choi tu tep cau h�nh
		// th�m Wall
                
            for (int y = 0; y < getHeight(); y++){
                for (int x = 0; x < getWidth(); x++){
                
                    int pos = x + y * getWidth();

                    switch (_map[y][x]){
                        case '#': 
                            _board.addEntity(pos, new Wall(x, y, Sprite.wall));
                            break;
                        
                        case 'p': 
                            _board.addCharacter(new Bomber(Coordinates.tileToPixel(x), 
                                                Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
                            Screen.setOffset(0, 0);
                            _board.addEntity(pos, new Grass(x, y, Sprite.grass));
                            break;
                        
                        case '*': 
                            _board.addEntity(pos,new LayeredEntity(x, y,
                                        new Grass(x, y, Sprite.grass),
                                        new Brick(x, y, Sprite.brick)));
                            break;
                        
                        case '1': 
                            _board.addCharacter(new Balloon(Coordinates.tileToPixel(x), 
                                            Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
                            _board.addEntity(pos, new Grass(x, y, Sprite.grass));
                            break;
                        
                        case '2': 
                            _board.addCharacter(new Oneal(Coordinates.tileToPixel(x), 
                                            Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
                            _board.addEntity(pos, new Grass(x, y, Sprite.grass));
                            break;
                        
                        case 'x': 
                            _board.addEntity(pos, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass),
                                new Portal(x, y, _board, Sprite.portal),
                                new Brick(x, y, Sprite.brick)));
                            break;
                   
                        case 'f': 
                            _board.addEntity(pos,
                                new LayeredEntity(x, y,new Grass(x, y, Sprite.grass),
                                                new FlameItem(x, y, Sprite.powerup_flames),
                                                new Brick(x, y, Sprite.brick)));
                            break;
                    
                        default:
                            _board.addEntity(pos, new Grass(x, y, Sprite.grass));
                            break;
                    }
                }
            }
	}
}