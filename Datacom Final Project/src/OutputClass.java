import java.io.FileWriter;
import java.io.IOException;

public class OutputClass {
	private FileWriter fwritter;
	OutputClass(){
		
	}
	public void setFileName(String filename) throws IOException{
		fwritter = new FileWriter(filename);
	}
	public void writeFile(String writeString) throws IOException{
		fwritter.write(writeString);
	}
	public void closeFile() throws IOException{
		fwritter.flush();
		fwritter.close();
	}
}
