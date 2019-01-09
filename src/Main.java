import java.awt.GridLayout;
import java.util.concurrent.Executor;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

public class Main {
	public static MusicPlayer player;
	public static String[] listData;
	public static boolean shuffled;
	public static boolean autoPlay;
	public static int startNum = -1;

	public static void main(String[] args) {
		if(args.length>=2) {
			startNum = Integer.parseInt(args[1]);
		}
		if (args.length > 0 && args[0].toLowerCase().equals("d")) {
			try {
				GPIOout LED = new GPIOout();
				for (int i = 1; i <= 100; i++) {
					LED.set(0, 0, i);
					Thread.sleep(25);
				}
				for (int i = 1; i <= 100; i++) {
					LED.set(0, i, 0);
					Thread.sleep(25);
				}
				for (int i = 1; i <= 100; i++) {
					LED.set(0, i, i);
					Thread.sleep(25);
				}
				for (int i = 1; i <= 100; i++) {
					LED.set(i, 0, 0);
					Thread.sleep(25);
				}
				for (int i = 1; i <= 100; i++) {
					LED.set(i, 0, i);
					Thread.sleep(25);
				}
				for (int i = 1; i <= 100; i++) {
					LED.set(i, i, i);
					Thread.sleep(25);
				}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.exit(0);
		}

		try {
			player = new MusicPlayer("");
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("yo");
		shuffled = false;
		autoPlay = false;
		if (args.length > 0 && args[0].equals("s")) {
			Main.player.stream();
		} else {
			Action3 a3 = new Action3();
			a3.startPlaying();
			(new Thread(new AutomaticSongPlayer())).start();
		}
		
		WaveformGenerator gen = new WaveformGenerator();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gen.createWaveform();
		// w.setVisible(true);
	}
}
