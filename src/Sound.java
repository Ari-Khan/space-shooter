/**
 * Sound.java
 * Author: Ari Khan
 * Version: 1.0.0
 * Date: 2025-11-16
 * Description: A class to handle sound effects in the game.
*/

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import java.io.File;

public class Sound {
    private final File file;

    public Sound(String filePath) {
        this.file = new File(filePath);
    }

    public void play() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            Clip c = AudioSystem.getClip();
            c.open(audioInputStream);

            c.start();

            c.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    c.close();
                }
            });

        } catch (Exception e) {
            System.out.println("Sound error: " + e.getMessage());
        }
    }
}
