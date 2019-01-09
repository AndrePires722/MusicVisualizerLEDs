
import java.io.File;
import java.util.ArrayList;

public class FileIndexer {
	String musicDir = System.getProperty("user.dir")+"/";
	public static ArrayList<String> fileNames;
	int length;
	int ID=0;
	public FileIndexer(){
		fileNames = new ArrayList<>();
		populateList();
	}
	
	private void populateList(){
		File folder = new File(musicDir);
		 System.out.println("Working Directory = " +
	              System.getProperty("user.dir"));
		File[] listOfFiles = folder.listFiles();
		length = new File(musicDir).list().length;
		Main.listData = new String[length];
		
		for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		        fileNames.add(musicDir+listOfFiles[i].getName());
		        Main.listData[i] = listOfFiles[i].getName();
		      } 
		    }
		
		System.out.println(length+" songs found");
		
		
	}
	public String getFile(String str){
		for(String x : fileNames){
			if(x.toLowerCase().contains(str.toLowerCase())){
				return x;
			}
		}
		return fileNames.get(0);
	}
	public String getNext(){
		ID++;
		ID%=length;
		System.out.println(ID+1);
		return fileNames.get(ID);
	}
	public String getRandom(){
		ID = (int)(Math.random()*length);
		String name = fileNames.get(ID);
		System.out.println("Selected song: "+name+" ID "+(ID+1));
		return name;
	}

}
