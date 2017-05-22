import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

public class EvidentDemo {
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		if(args.length < 1){
			System.out.println("Invalid number of arguments. Proper format has two arguments!");
		}
		else{
		CSVParser csvParser = new CSVParser();
		
		iterateMap(csvParser.parseFile(args[0]));
		}
	}
    
	private static <K, V> void iterateMap(Map<K, V> map){

		for (Map.Entry<K, V> entry : map.entrySet()) {
		System.out.println("Role: " + entry.getKey());
		System.out.println("Total Salary: " + entry.getValue());
		System.out.println();
	   
		}
	}
}
