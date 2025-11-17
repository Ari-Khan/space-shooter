/**
 * Sound.java
 * Author: Ari Khan
 * Version: 1.0.0
 * Date: 2025-11-16
 * Description: A class to handle sound effects in the game.
*/

// Import necessary packages
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import java.io.File;

// Class representing a sound effect
public class Sound {
    // File object for the sound file
    private final File file;

    // Constructor to initialize the sound with a file path
    public Sound(String filePath) {
        this.file = new File(filePath);
    } // End of constructor

    // Method to play the sound
    public void play() {
        // Try-catch block to handle sound playback
        try {
            // Open audio stream
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            Clip c = AudioSystem.getClip();
            c.open(audioInputStream);

            // Start playing the sound
            c.start();

            // Add a listener to close the clip when done
            c.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    c.close();
                }
            });
        // Catch any exceptions during sound playback
        } catch (Exception e) {
            System.out.println("Sound error: " + e.getMessage());
        } // End of try-catch
    } // End of play method
} // End of Sound class