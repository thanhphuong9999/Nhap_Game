package uet.oop.bomberman.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * T?t c? sprite (hình ?nh game) ???c l?u tr? vào m?t ?nh duy nh?t
 * Class này giúp l?y ra các sprite riêng t? 1 ?nh chung duy nh?t ?ó
 */
public class SpriteSheet {

	private String _path;
	public final int SIZE;
	public int[] _pixels;
	
	public static SpriteSheet tiles = new SpriteSheet("/textures/classic.png", 256);
	
	public SpriteSheet(String path, int size) {
		_path = path;
		SIZE = size;
		_pixels = new int[SIZE * SIZE];
		load();
	}
	
	private void load() {
		try {
			URL a = SpriteSheet.class.getResource(_path);
			BufferedImage image = ImageIO.read(a);
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, _pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
