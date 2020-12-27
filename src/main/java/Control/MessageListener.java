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

    public MessageListener(AudioSendHandler audioSendHandler){
        this.audioSendHandler = audioSendHandler;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getAuthor().isBot()) return;
        Message message = event.getMessage();
        String content = message.getContentRaw();

        MessageChannel channel = event.getChannel();
        if (content.equals("!add-deck")) {
            if(event.getMember()==null){
                channel.sendMessage("Could not access member. Sorry!").queue();
                return;
            }
            if(event.getMember().getVoiceState() == null) {
                channel.sendMessage("Could not access voice state. Sorry!").queue();
                return;
            }
            VoiceChannel connectedChannel = event.getMember().getVoiceState().getChannel();
            if(connectedChannel == null){
                channel.sendMessage("Connect to a voice channel first (so I know where to connect).").queue();
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