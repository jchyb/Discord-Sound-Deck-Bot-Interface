import Audio.AudioPlayerSendHandler;
import Audio.IndexingAudioLoadResultHandler;
import Audio.SoundAccessor;
import Control.KeyHandler;
import Control.MessageListener;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main{
    public static String BOT_TOKEN;// = "Nzc1NDc2MjAxMjMyOTI0NzMz.X6m4fg.M1lerZaV4De5Wz0epoUBHscARQ0";

    public static void main(String[] arguments) throws Exception {
        Configurations configs = new Configurations();

        XMLConfiguration config = configs.xml("config.XML");
        BOT_TOKEN = config.getString("token");
        List<String> paths = config.getList(String.class, "paths.path");
        List<Integer> keys = config.getList(Integer.class, "keys.key");
        System.out.println(""+BOT_TOKEN);
        System.out.println("Loaded Paths: " + paths.size());
        System.out.println("Loaded Keys: " + keys.size());
        if(keys.size() != paths.size()){
            System.out.println("WARNING");
            System.out.println("Amount of paths and keys read is different. Some sounds will not work.");
        }
        int soundsAmount = Math.min(paths.size(), keys.size());
        //TODO warnings and errors

        AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerLocalSource(playerManager);

        AudioPlayer player = playerManager.createPlayer();
        SoundAccessor accessor = new SoundAccessor(player, soundsAmount);
        AudioPlayerSendHandler sendHandler = new AudioPlayerSendHandler(player);

        KeyHandler keyHandler = new KeyHandler(accessor, keys);
        registerInput(keyHandler);

        MessageListener messageListener = new MessageListener(sendHandler, playerManager, accessor);

        for(int i = 0; i < Math.min(paths.size(), keys.size()); i++){
            //TODO Add checks and fails
            playerManager.loadItem(paths.get(i), new IndexingAudioLoadResultHandler(accessor, i));
        }

        JDA api = JDABuilder.createDefault(BOT_TOKEN).addEventListeners(messageListener).build();
    }
    public static void registerInput(KeyHandler keyHandler){
        LogManager.getLogManager().reset();
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        GlobalScreen.addNativeKeyListener(keyHandler);
        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }
    }
}
