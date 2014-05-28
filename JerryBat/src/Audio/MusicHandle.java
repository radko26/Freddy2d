package Audio;

import java.util.HashMap;
import java.util.Map;

public class MusicHandle {
	
	private HashMap<String,AudioPlayer> audioPlaying;
	
	public MusicHandle(){
		audioPlaying = new HashMap<>();
	}

	
	public void add(AudioPlayer a, String s){
		audioPlaying.put(s, a);
	}
	public void play(String s){
		audioPlaying.get(s).play();
	}
	public void stop(){
		for (Map.Entry<String,AudioPlayer> entry : audioPlaying.entrySet()) {
		    audioPlaying.get(entry.getKey()).close();
		}
	}

}
