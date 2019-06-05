package com.ecobee.insulationrate.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.FileWriter;
import java.io.PrintWriter;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.ecobee.insulationrate.domain.InsulationRate;
import com.ecobee.insulationrate.services.InsulationService;

public class InsulationServiceVolumeTesting {

	//List<InsulationRate> rates = new ArrayList<InsulationRate>();
	
	InsulationService service = new InsulationService();
	
	@Before
	public void setUp() throws Exception {
		//setting rates 1000000
		for (int i = 0; i < 1000000; i++) {
			service.store(new InsulationRate("John Doe " + i, "Canada/Ontario/Toronto", 1f));
		}
		
	}

	@Test
	public void shouldReturn10Because1000000EqualsRegister() {
		//when
		int score = service.calculateScoreQuery("John Doe 0", "Canada/Ontario/Toronto");
		//then
		assertThat(score, is(equalTo(10)));
	
	}
	
	@Test
	@Ignore
	public void generateData() throws Exception { 
		
		FileWriter arq = new FileWriter("teste.txt");
	    PrintWriter gravarArq = new PrintWriter(arq);
	 

		//setting rates 1000000
		for (int i = 0; i < 200000; i++) {
			gravarArq.printf("\"John Doe " + (i + 1) + "\" \"Canada/Ontario/Toronto\" 1.5\n");
			gravarArq.printf("\"Samanta Smith " + (i + 1) + "\" \"Canada/Ontario/London\" 3.7\n");
			gravarArq.printf("\"Adam Xin  " + (i + 1) + "\" \"Canada/British Columbia/Vancouver\" 2.110\n");
			gravarArq.printf("\"Monica Taylor  " + (i + 1) + "\" \"Canada/Ontario/Toronto\" 2.110\n");
			gravarArq.printf("\"Alicia Yazzie  " + (i + 1) + "\" \"US/Arizona/Phoenix\" 5.532\n");
			gravarArq.printf("\"Mohammed Zadeh " + (i + 1) + "\" \"Canada/Ontario/Toronto\" 1.43\n");
		}

	    arq.close();
		
	}

}
