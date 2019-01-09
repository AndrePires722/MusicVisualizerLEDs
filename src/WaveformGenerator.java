import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.JPanel;

import javax.swing.Timer;

public class WaveformGenerator {
	static byte[] waveform;
	Scanner scanner;
	int index = 0;
	Scanner sc;
	Timer t;
	int max = 0, min = 0;
	Visualizer2 v2;

	public WaveformGenerator() {

		waveform = new byte[128];
		for (int i = 0; i < waveform.length; i++) {
			waveform[i] = 0;
		}
		v2 = new Visualizer2();
		// setSize(1200, 300);
	}

	public void createWaveform() {

		while (true) {
			
			
			
			
			
			// if(Main.player.clip.isActive()) {
			int totalFramesRead = 0;
			File fileIn = new File(Main.player.musicFile);
			waveform = new byte[1024];
			try {
				System.out.println("trying");
				if(!Main.player.streaming) {
					// fileIn is the same .wav file that the Clip object is playing
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(fileIn);
					int bytesPerFrame = audioInputStream.getFormat().getFrameSize();
					if (bytesPerFrame == AudioSystem.NOT_SPECIFIED) {
						// some audio formats may have unspecified frame size
						// in that case we may read any amount of bytes
						bytesPerFrame = 1;
					}
					int persecond = (int) Main.player.clip.getFormat().getSampleRate();
					// This is the number of bytes that should be played every 10ms
					int numBytes = (int) (persecond * (10 / 1000.0) * bytesPerFrame);
					numBytes = 1024;
					waveform = new byte[numBytes];

					try {
						int numBytesRead = 0;
						int numFramesRead = 0;
						// Try to read numBytes bytes from the file.
						long totalFrames = audioInputStream.getFrameLength();
						double totalSeconds = (double) totalFrames / persecond;
						long x = System.currentTimeMillis();
						int i = 0;
						while ((numBytesRead = audioInputStream.read(waveform)) != -1) {
							// System.out.println("frame "+i);i++;
							v2.updateEnergy();
							// if(!Main.player.clip.isActive())break;
							// Calculate the number of frames actually read.
							numFramesRead = numBytesRead / bytesPerFrame;
							totalFramesRead += numFramesRead;
							// System.out.println("frame "+i);i++;

							if (!Main.player.clip.isOpen())
								break;

							while ((System.currentTimeMillis() - x)
									/ 1000.0 < ((double) (totalFramesRead - numBytes * 50) / (double) totalFrames)
											* totalSeconds) {

							}

						}

					} catch (Exception ex) {
						// Handle the error...
						//ex.printStackTrace();
					}
				}else {
					int numFramesRead = 0;
					System.out.println("looping in stream");
					// Try to read numBytes bytes from the file.
					while ((Main.player.dataline.read(waveform, 0, waveform.length)) != -1) {
						v2.updateEnergy();
					}
				}
				
			} catch (Exception ep) {
				// Handle the error...
				//ep.printStackTrace();
			}
			/*
			 * System.out.println(Window.player.stream.getFrameLength());
			 * System.out.println(Window.player.stream.getFormat().getSampleRate());
			 */
		} // }
	}
}
