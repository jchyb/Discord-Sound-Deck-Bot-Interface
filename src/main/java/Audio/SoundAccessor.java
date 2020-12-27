package Audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

public class SoundAccessor {
    private final int soundsAmount;
    private AudioTrack[] tracks;
    private final AudioPlayer player;

    public SoundAccessor(AudioPlayer player, int soundsAmount){
        this.player = player;
        this.soundsAmount = soundsAmount;
        tracks = new AudioTrack[soundsAmount];
    }
    public void setTrack(int trackIndex, AudioTrack track){
        this.tracks[trackIndex] = track;
    }
    public void play(int trackIndex){
        player.playTrack(tracks[trackIndex]);
        tracks[trackIndex] = tracks[trackIndex].makeClone();
    }
}
