# Discord-Sound-Deck-Bot-Interface
Software that allows you to create a discord bot, which plays your custom locally stored sound files in a discord sound channel with a press of a button.
This is meant to simulate a personal sound deck on discord. Press a keyboard key to instantly hear a chosen sound in a voice channel, played by a bot instance seperate from your own voice and account.

## Setup
Use discord developer portal (https://discord.com/developers/applications), create new application and give it a (possibly unique) name. Go to a bot tab and add one. You should be able to access a token, which is used by the app to access the created bot (see example config.xml file down below). Don't show it to anyone! Now, go to the OAuth tab. Mark the bot scope. You should already have a working link, which allows you to add this bot to your server. However, if you are going to ask somebody else to add the bot to their server, it would be a good idea to check additional bot permissions (to better inform them about it's use):
- Send Messeges
- Read Message History
- Connect
- Speak

## Usage
This is a java command line program with a single argument runned by:
	java Main CONFIG_FILE
where CONFIG_FILE is a path to a XML config file (f.e. config.xml).

### Example config.xml:

```xml
<?xml version="1.0" encoding="ISO-8859-1" ?>
<configuration>

<!--> Bot token generated from discord developer portal (example).  <-->
<token>QFiIOUYMeItlN0tTEwxRNzMz.X6m4fg.MullaV4De5Wz0epoUBHscARQ0</token>
<paths>
    <!--> List of paths to sound files. <-->
    <path>clap.wav</path>
    <path>seinfeld-theme.mp3</path>
    <path>oof.mp3</path>
</paths>
<keys>
    <!--> List of key values (in the same order as paths). You can obtain them from:
	  https://javadoc.io/static/com.1stleg/jnativehook/2.0.3/constant-values.html <-->
    <key>82</key>
    <key>79</key>
    <key>4</key>
</keys>
</configuration>
```
# Why do a 'bot interface' instead of a regular discord bot with a dedicated server (like groovy, for example)?

While the client-server approach would be more intuitive to use (no need to set up a seperate bot instance for every user), there are multiple reasons why the current implementation may be considered better:

- safety concerns

	As the software is using what is essentially a *key logger* to catch input, you would probably not want that data going to wrong hands. Since all of this data is processed locally, there is no need to worry about that (as you can confirm in the source code).
	
- latency

	As there are no middlemen that obtain and process the sound data (like the hypothetical server would), naturally there is going to be less latency all around.

Here, a simple approach like this ends up working surprisingly well.

# Used libraries
1. [JDA](https://github.com/DV8FromTheWorld/JDA) for discord integration.
2. [LavaPlayer](https://github.com/sedmelluq/lavaplayer) for sound file reformatting and streaming.
3. [JNativeHook](https://github.com/kwhat/jnativehook) for catching input.

 
