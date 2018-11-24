package uet.oop.bomberman.level;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.exceptions.LoadLevelException;
import uet.oop.bomberman.sound.Audio;

// Load và l?u tr? thông tin b?n ?? các màn ch?i
public abstract class LevelLoader {

	protected int _width = 20, _height = 20; // default values just for testing
	protected int _level;
	protected Board _board;
        Audio audio;
	public LevelLoader(Board board, int level) throws LoadLevelException {
		_board = board;
		loadLevel(level);
                //TODO: am thanh tro choi
                Audio.playGameSong();
	}

	public abstract void loadLevel(int level) throws LoadLevelException;

	public abstract void createEntities();

	public int getWidth() {
		return _width;
	}

	public int getHeight() {
		return _height;
	}

	public int getLevel() {
		return _level;
	}

}