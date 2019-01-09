
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Action3 {        
	public static FileIndexer fileIndex = new FileIndexer();
	public void startPlaying() {     
		
		if(Main.player.clip.isOpen())
			Main.player.stop();
		Main.shuffled = true;
		Main.player.setSong(fileIndex.getRandom());
		Main.player.play();
		Main.autoPlay = true;
		
	}
}
