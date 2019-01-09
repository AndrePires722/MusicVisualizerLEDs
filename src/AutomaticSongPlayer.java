

public class AutomaticSongPlayer implements Runnable {

	@Override
	public void run() {
		System.out.println("Starting AutomaticSongPlayer");
		while(true){
			//since this is always looping, might as well do some other irrelevant processing
			
			//System.out.println("AutomaticSongPlayer checking...");
			if(Main.autoPlay){
				
				if(!Main.player.clip.isOpen()){
					
					Main.player.stop();

				}
				if(!Main.player.clip.isRunning()){
					Main.player.stop();
					if(Main.shuffled){
						System.out.println("Shuffled");
						Main.player.setSong(Action3.fileIndex.getRandom());
						
					}
					else{
						
						String next = Action3.fileIndex.getNext();
						System.out.println("Regular " + next);
						Main.player.setSong(next);
						System.out.println();
					}
					Main.player.play();
				}
				
			}else{
				System.out.println("They dont want me to play music automatically!");
			}
			
			
			// TODO Auto-generated method stub
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
		
	}
	

}
