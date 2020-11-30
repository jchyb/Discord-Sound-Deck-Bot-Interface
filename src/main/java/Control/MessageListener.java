package Control;

import Audio.SoundAccessor;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

public class MessageListener extends ListenerAdapter {

    private final AudioSendHandler audioSendHandler;

    //debug
    private final AudioPlayerManager playerManager;
    private final SoundAccessor accessor;

    public MessageListener(AudioSendHandler audioSendHandler, AudioPlayerManager playerManager, SoundAccessor accessor){
        this.audioSendHandler = audioSendHandler;
        this.playerManager = playerManager;
        this.accessor = accessor;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getAuthor().isBot()) return;
        Message message = event.getMessage();
        String content = message.getContentRaw();

        MessageChannel channel = event.getChannel();
        if (content.equals("!add-deck")) {
            if(event.getMember().getVoiceState() == null) {
                channel.sendMessage("Could not access voice state. Sorry!").queue();
                return;
            }
            VoiceChannel connectedChannel = event.getMember().getVoiceState().getChannel();
            if(connectedChannel == null){
                channel.sendMessage("Connect to a voice channel first.").queue();
                return;
            }

            connect(event.getGuild().getAudioManager(), connectedChannel);
            channel.sendMessage("Alright! Connected to " + connectedChannel.getName() + "!").queue();
        }
    }

    private void connect(AudioManager audioManager, VoiceChannel connectedChannel) {
        audioManager.setSendingHandler(audioSendHandler);
        audioManager.openAudioConnection(connectedChannel);
    }
}