
package views.sounds;

import java.applet.Applet;
import java.applet.AudioClip;
import views.MainScreen;

public class AlertSounds {
    public AudioClip erroSong;
    public AudioClip alertSong;
    public AudioClip sucessSong;
    public AudioClip effectSong;
    
    public AlertSounds(){
        this.erroSong = Applet.newAudioClip(AlertSounds.class.getResource("error.wav"));
        this.alertSong = Applet.newAudioClip(AlertSounds.class.getResource("alert.wav"));
        this.sucessSong = Applet.newAudioClip(AlertSounds.class.getResource("sucess.wav"));
        this.effectSong = Applet.newAudioClip(AlertSounds.class.getResource("effect.wav"));
        erroSong.stop();
        alertSong.stop();
        sucessSong.stop();
        effectSong.stop();
    }
}
