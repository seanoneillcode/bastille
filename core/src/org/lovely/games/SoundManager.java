package org.lovely.games;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;

import java.util.HashMap;
import java.util.Map;

public class SoundManager {

    LoadingManager loadingManager;
    Map<String, Sound> sounds;
    float soundEffectVolume;
    float musicVolume;

    SoundManager(LoadingManager loadingManager) {
        this.loadingManager = loadingManager;
        this.sounds = new HashMap<>();
        this.soundEffectVolume = 1.0f;
        this.musicVolume = 0.6f;
    }

    public void playSound(String name) {
        if (!sounds.containsKey(name)) {
            Sound sound = loadingManager.getSound(name);
            sounds.put(name, sound);
        }
        float pitch = MathUtils.random(0.8f, 1.2f);
        Sound sound = sounds.get(name);
        sound.play(soundEffectVolume, pitch, 0.5f);
    }

    public void playMusic(String name) {
        if (!sounds.containsKey(name)) {
            Sound sound = loadingManager.getSound(name);
            sounds.put(name, sound);
        }
        Sound sound = sounds.get(name);
        sound.play(musicVolume);
    }
}
