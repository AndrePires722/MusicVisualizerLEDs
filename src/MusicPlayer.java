

import java.io.*;
import javax.sound.sampled.*;
import javax.sound.sampled.AudioFileFormat.*;

public class MusicPlayer{
	String musicFile;
	AudioInputStream stream;
    AudioFormat format;
    DataLine.Info info;
    long timeStarted;
    static Clip clip;
	boolean streaming = false;
	public static TargetDataLine dataline;
	public static SourceDataLine speakers;
	public static Line inputLine;
	public MusicPlayer(String savefile) throws LineUnavailableException {
		musicFile = savefile;
		info = new DataLine.Info(Clip.class, format);
	    clip = (Clip) AudioSystem.getLine(info);
	}
	
	public void play(){
		try {
		    File yourFile = new File(musicFile);
		    stream = AudioSystem.getAudioInputStream(yourFile);
		    format = stream.getFormat();
		    clip.open(stream);
		    timeStarted = System.currentTimeMillis();
		    clip.start();
		}
		catch (Exception e) {
		    System.out.println("No song selected");
		}
	}
	public void stream() {
		try {
			Mixer.Info[] infolist = AudioSystem.getMixerInfo();
			Mixer thisone = null;
			System.out.printf("%s lines found on this system:%n",infolist.length);
			for (int i = 0;i<infolist.length;i++) {
				System.out.println(infolist[i].getName());
			}
			if(Main.startNum==-1)
			for (int i = 0; i < infolist.length; i++) {
				if (infolist[i].getName().toLowerCase().contains("port device")) {
					System.out.println("--> chose " + infolist[i].getName());
					thisone = AudioSystem.getMixer(infolist[i]);

					i = infolist.length;
				}
			}else thisone = AudioSystem.getMixer(infolist[Main.startNum]);
			System.out.println("Setting dataline...");
			AudioFormat format = new AudioFormat(22050f, 16, 1, true, true);
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("winter.wav"));
		    format = audioInputStream.getFormat();
		    System.out.println("using format: "+format.toString());
			if (thisone == null) {
				System.out.println("lol jk its null");
				return;
			}
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
			
			dataline = (TargetDataLine) thisone.getLine(info);
			dataline.open(format, 1024);
			dataline.start();
			
			
			
			System.out.println("using :" + dataline);
			System.out.println("streaming. . .");
			streaming = true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void setSong(String fileName){
		musicFile = fileName;
	}
	public void stop(){
		clip.close();
	}
	public void pause() throws InterruptedException{
		clip.wait();
	}
}