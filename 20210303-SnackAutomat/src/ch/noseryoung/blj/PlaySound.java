package ch.noseryoung.blj;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Created by lazar on 10.03.2021.
 * Project name: 20210303-SnackAutomat
 **/

public class PlaySound {
    Long currentFrame;
    Clip clip;
    String status;

    AudioInputStream audioInputStream;
    static String filePath;

    public PlaySound()
            throws UnsupportedAudioFileException,
            IOException, LineUnavailableException
    {
        filePath = "Music/Dalasa-Jingle.wav";
        audioInputStream =
                AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void play() {
        clip.start();
        status = "play";
    }
}
