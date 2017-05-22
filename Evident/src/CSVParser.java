import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.IllegalArgumentException;
import java.math.BigDecimal;
import java.util.TreeMap;


public class CSVParser {
	public static final String COMMA_DELIMITER = ",";
	public static final String FILE_EXTENSION = ".csv";
	public static final String TOTAL_SALARY_KEY = "Total";
	
	public CSVParser(){
		
	}
	
	
	public TreeMap<String, BigDecimal> parseFile(String file) {
		String line = "";
		String[] employeeInformation = null;
		String employeeName;
		String employeeDOB;
		String employeeRole;
		BigDecimal employeeSalary;
		TreeMap<String, BigDecimal> totalSalaryMap = new TreeMap<String, BigDecimal>(); 
		
		
		if (file == null) {
			throw new NullPointerException("Parameter file was null!");
		}
		
		if(!(file.endsWith(FILE_EXTENSION))){
			throw new IllegalArgumentException(file + " is not a csv file.");
		}
		
		try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file)))  {
			totalSalaryMap.put(TOTAL_SALARY_KEY, new BigDecimal("0.00"));
			while( (line = bufferedReader.readLine()) != null) {
				employeeInformation = line.split(COMMA_DELIMITER);
				if(employeeInformation.length == 4) {
					employeeName = employeeInformation[0];
					employeeDOB = employeeInformation[1];
					employeeSalary = new BigDecimal(employeeInformation[2]);
					employeeRole = employeeInformation[3];
					
					
					totalSalaryMap.put(TOTAL_SALARY_KEY, totalSalaryMap.get(TOTAL_SALARY_KEY).add(employeeSalary));
					totalSalaryMap.put(employeeRole, totalSalaryMap.getOrDefault(employeeRole, new BigDecimal("0.00")).add(employeeSalary));
				}
				else {
					throw new IllegalArgumentException("CSV file is improperly formatted!");
				}
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return totalSalaryMap;
	}
}
