package sem.eind.server;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import sem.eind.model.BaseModel;

public class ClassParser {
	//TODO klasse die van een String een object kan parsen.

public final static String RESOURCEPATH = "./resources/initmodel/";
	
	public List<? extends BaseModel>  getObjectsFromFile(){
		ArrayList<? extends BaseModel> returnable=new ArrayList<>();
		String filename=RESOURCEPATH+this.getClass().toString()+".txt";
		File file = new File(filename);
	    if ( file.exists() && file.isFile() ) {
	        try {
	            RandomAccessFile r = new RandomAccessFile( filename, "r" );
	            String line;
	            while ( (line = r.readLine()) != null ) {
	              System.out.println(line);
	            }
	            r.close();
	        }
	        catch ( IOException io ) {
	            System.out.println("Fout bij het lezen van de file '" + filename + "'");
	        }
	    }
	    else {
	        System.out.println("File '" + filename + "' niet gevonden");
	    }
	return returnable;			
	}
	
}
