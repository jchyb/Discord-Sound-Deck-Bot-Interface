package Audio;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

public class IndexingAudioLoadResultHandler implements AudioLoadResultHandler {

    private final SoundAccessor accessor;
    private final int trackIndex;
    private String trackPath;

    public IndexingAudioLoadResultHandler(SoundAccessor accessor, int trackIndex, String path) {
        this.accessor = accessor;
        this.trackIndex = trackIndex;
        this.trackPath = path;
    }

    public void trackLoaded(AudioTrack track) {
        System.out.println("Track Loaded: "+ trackPath);
        accessor.setTrack(trackIndex, track);
    }
    public void playlistLoaded(AudioPlaylist audioPlaylist) {
        throw new UnsupportedOperationException();
    }
    public void noMatches() {
        System.out.println("Could not find (no matches): "+ trackPath);
    }
    public void loadFailed(FriendlyException e) {
        System.out.println("Loading failed: "+trackPath);
        e.printStackTrace();
    }
}