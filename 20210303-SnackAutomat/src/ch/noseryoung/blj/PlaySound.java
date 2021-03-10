package ch.noseryoung.blj;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

/**
 * Created by lazar on 10.03.2021.
 * Project name: 20210303-SnackAutomat
 **/

public class PlaySound {
    Clip clip;

    AudioInputStream audioInputStream;
    static String filePath;

    public PlaySound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        filePath = "Music/Dalasa-Jingle.wav";
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void playMusic() {
        clip.start();
    }

    public void stopMusic() {
        clip.stop();
    }
}
