import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.IllegalArgumentException;
import java.util.TreeMap;


public class CSVParser {
	public static final String COMMA_DELIMITER = ",";
	public static final String FILE_EXTENSION = ".csv";
	public static final String TOTAL_SALARY_KEY = "Total";
	
	public CSVParser(){
		
	}
	
	
	public TreeMap<String, Double> parseFile(String file) {
		String line = "";
		String[] employeeInformation = null;
		String employeeName;
		String employeeDOB;
		String employeeRole;
		double employeeSalary;
		TreeMap<String, Double> tmap = new TreeMap<String, Double>(); //Bad Generic BAD!!! 
		
		
		if (file == null) {
			throw new NullPointerException("Parameter file was null!");
		}
		
		if(!(file.endsWith(FILE_EXTENSION))){
			throw new IllegalArgumentException(file + " is not a csv file.");
		}
		
		try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file)))  {
			tmap.put(TOTAL_SALARY_KEY, 0.0);
			while( (line = bufferedReader.readLine()) != null) {
				employeeInformation = line.split(COMMA_DELIMITER);
				if(employeeInformation.length == 4) {
					employeeName = employeeInformation[0];
					employeeDOB = employeeInformation[1];
					employeeSalary = Double.parseDouble(employeeInformation[2]);
					employeeRole = employeeInformation[3];
					
					
					tmap.put(TOTAL_SALARY_KEY, tmap.get(TOTAL_SALARY_KEY) + employeeSalary);
					tmap.put(employeeRole, tmap.getOrDefault(employeeRole, 0.0) + employeeSalary);
				}
				else {
					throw new IllegalArgumentException("CSV file is improperly formatted!");
				}
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return tmap;
	}
}
