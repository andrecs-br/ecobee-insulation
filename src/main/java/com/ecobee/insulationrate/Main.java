package com.ecobee.insulationrate;

import java.util.Scanner;

import com.ecobee.insulationrate.domain.InsulationRate;
import com.ecobee.insulationrate.services.InsulationService;

public class Main {
	
	public static void main(String... args) {
		
		process();
		
	}
	
	private static void process() {
		
		Scanner scanner = new Scanner(System.in);

		InsulationRate rate;

		InsulationService service = new InsulationService();
		
		String name;
		
		String location;
		
		Float rValue;

		String line = scanner.nextLine();

		long startTime = System.currentTimeMillis();

		//now we will store data
		while (line != null && !line.trim().equals("")) {
			
			//putting line into rate object
			name = line.split("\"\\s")[0].substring(1);
			location = line.split("\"\\s")[1].substring(1);
			rValue = Float.valueOf(line.split("\"\\s")[2]);
			rate = new InsulationRate(name, location, rValue);
			
			service.store(rate);
			
			line = scanner.nextLine();
		}

		line = scanner.nextLine();

		//now we will do queries
		while (line != null && !line.trim().equals("")) {
			
			name = line.split("\"\\s")[0].substring(1);
			location = line.split("\"\\s")[1].substring(1, line.split("\"\\s")[1].length()-1);
			
			int score = service.calculateScoreQuery(name, location);
			
			System.out.println(line + " " + score);
			
			line = scanner.nextLine();
		}
	
		scanner.close();

		long endTime = System.currentTimeMillis();
		
		long execTimeInMillis = endTime - startTime;
		
		String str = String.format("Execution time in minutes:seconds : %d:%02d.%03d", (execTimeInMillis) / (60 * 1000), ((execTimeInMillis) / 1000) % 60, ((execTimeInMillis) % 1000));
		
		System.out.println(str);
		
	}

}
