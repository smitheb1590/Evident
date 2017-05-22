import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;


public class EvidentDemo {
	public static final String YEAR = "YEAR";
	public static final String QUARTER = "QUARTER";
	public static final String MONTH = "MONTH";
	public static final String WEEK = "WEEK";
	public static final String DAY = "DAY";
	public static final String HOUR = "HOUR";
	
	public static final String TOTAL_SALARY = "A";
	public static final String TOTAL_SALARY_BY_ROLE = "B";
	public static final String TOTAL_SALARY_BY_TIME = "C";
	public static final String TOTAL_SALARY_BY_ROLE_BY_TIME = "D";
	
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		NumberFormat usCurrency = NumberFormat.getCurrencyInstance(Locale.US);
		String timeUnit = YEAR;
		String calculationSelection;
		boolean isTotalOnly = false;
		
		if(args.length < 2){
			System.out.println("Invalid number of arguments. Proper format has two arguments!");
		}
		else{
			
			CSVParser csvParser = new CSVParser();
			TreeMap<String, BigDecimal> salaryMap = csvParser.parseFile(args[0]);
			calculationSelection = args[1];
			
			if (TOTAL_SALARY.equalsIgnoreCase(calculationSelection)  || TOTAL_SALARY_BY_TIME.equalsIgnoreCase(calculationSelection)) {
				isTotalOnly = true;
			}
			
			if(TOTAL_SALARY_BY_TIME.equalsIgnoreCase(calculationSelection) || TOTAL_SALARY_BY_ROLE_BY_TIME.equals(calculationSelection)){
				timeUnit = MONTH;
			}
			
			if(isTotalOnly) {
				if(YEAR.equalsIgnoreCase(timeUnit)){
					System.out.println("Total Salary: " + usCurrency.format(salaryMap.get(CSVParser.TOTAL_SALARY_KEY)));
				}
				else{
					BigDecimal totalSalaryValue = calculateByTimeUnit(salaryMap.get(CSVParser.TOTAL_SALARY_KEY), timeUnit);
					System.out.println("Total Salary: " + usCurrency.format(totalSalaryValue) + " per " + timeUnit);
				}
			}
			else{
				iterateMap(salaryMap, timeUnit);
			}
		}
	}
    
	private static <K, V> void iterateMap(Map<K, V> map, String timeUnit){
		NumberFormat usCurrency = NumberFormat.getCurrencyInstance(Locale.US);
		
		map.remove(CSVParser.TOTAL_SALARY_KEY);
		
		for (Map.Entry<K, V> entry : map.entrySet()) {
		System.out.println("Role: " + entry.getKey());
		
		BigDecimal salaryKey = new BigDecimal(entry.getValue().toString());
		if(YEAR.equalsIgnoreCase(timeUnit)){
				System.out.println("Total Salary: " + usCurrency.format(salaryKey.doubleValue()));	
		}
		else{
			salaryKey = calculateByTimeUnit(salaryKey, timeUnit);
			System.out.println("Total Salary: " + usCurrency.format(salaryKey.doubleValue()) + " per " + timeUnit);
		}
		
		System.out.println();
	   
		}
	}
	
	
	private static BigDecimal calculateByTimeUnit(BigDecimal salaryKey, String timeUnit){
		BigDecimal time;
		
		switch (timeUnit){
		case QUARTER: time = new BigDecimal("4");
				break;
		case MONTH: time = new BigDecimal("12");
				break;
		case WEEK: time = new BigDecimal("52");
				break;
		case DAY: time = new BigDecimal("365");
		        break;
		case HOUR: time = new BigDecimal(365 * 24);
				break;
		default: time = new BigDecimal("1");
				break;
		}
		
		return salaryKey.divide(time, 2, BigDecimal.ROUND_HALF_EVEN);
	}
}
