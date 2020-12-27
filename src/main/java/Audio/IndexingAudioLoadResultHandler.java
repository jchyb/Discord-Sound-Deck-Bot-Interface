package Audio;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

public class IndexingAudioLoadResultHandler implements AudioLoadResultHandler {

    private final SoundAccessor accessor;
    private final int trackIndex;

    public IndexingAudioLoadResultHandler(SoundAccessor accessor, int trackIndex) {
        this.accessor = accessor;
        this.trackIndex = trackIndex;
    }

    public void trackLoaded(AudioTrack track) {
        System.out.println("Track Loaded");
        accessor.setTrack(trackIndex, track);
    }
    public void playlistLoaded(AudioPlaylist audioPlaylist) {
        for(AudioTrack track : audioPlaylist.getTracks()){
            accessor.setTrack(trackIndex, track);
        }
        //TODO DELETE
    }
    public void noMatches() {
        System.out.println("no matches");
    } //TODO
    public void loadFailed(FriendlyException e) {
        e.printStackTrace();
    } //TODO
}