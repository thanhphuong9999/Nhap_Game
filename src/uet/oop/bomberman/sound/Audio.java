package uet.oop.bomberman.sound;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Audio {

    Clip MenuMove, MenuSelect, BombDrop, BombExplode, Victory, walk, MusicOpen, GameOver, hopeyou;
    public static void playMenuMove(){
        try{
            AudioInputStream in = AudioSystem.getAudioInputStream(new File("D:/bomberman-starter/res/sound/MenuMove.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(in);
            clip.start();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void playMenuSelect(){
        try{
            AudioInputStream in = AudioSystem.getAudioInputStream(new File("D:/bomberman-starter/res/sound/MenuSelect.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(in);
            clip.start();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void playBombDrop(){
        try{
            AudioInputStream in = AudioSystem.getAudioInputStream(new File("D:/bomberman-starter/res/sound/BombDrop.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(in);
            clip.start();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void playBombExplode(){
        try{
            AudioInputStream in = AudioSystem.getAudioInputStream(new File("D:/bomberman-starter/res/sound/BombExplode.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(in);
            clip.start();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void playVictory(){
        try{
            AudioInputStream in = AudioSystem.getAudioInputStream(new File("D:/bomberman-starter/res/sound/Victory.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(in);
            clip.start();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void playGameSong(){
        try{
            AudioInputStream in = AudioSystem.getAudioInputStream(new File("D:/bomberman-starter/res/sound/MusicOpen.wav"));
            //AudioInputStream in = AudioSystem.getAudioInputStream(new File("D:/bomberman-starter/res/sound/hopeyou.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(in);
            clip.start();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void gameOver(){
        try{
            AudioInputStream in = AudioSystem.getAudioInputStream(new File("D:/bomberman-starter/res/sound/GameOver.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(in);
            clip.start();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void bomberWalk(){
        try{
            AudioInputStream in = AudioSystem.getAudioInputStream(new File("D:/bomberman-starter/res/sound/walk.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(in);
            clip.start();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}

