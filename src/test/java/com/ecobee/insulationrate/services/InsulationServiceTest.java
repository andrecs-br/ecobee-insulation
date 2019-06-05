package com.ecobee.insulationrate.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.ecobee.insulationrate.domain.InsulationRate;
import com.ecobee.insulationrate.services.InsulationService;

public class InsulationServiceTest {
	
	InsulationService service;

	@Before
	public void setUp() throws Exception {
		service = new InsulationService();
	}

	@Test
	public void shouldStoreARecord() {
		
		//given
		InsulationRate rate = new InsulationRate("John Doe", "Canada/Ontario/Toronto", 1.5f);
		//when
		service.store(rate);
		//then
		assertThat(service.getTotalRecords(), is(equalTo(1)));
	}

	@Test
	public void shouldCalculateQueryExactLocation1Record() {
		
		//given
		InsulationRate rate = new InsulationRate("John Doe", "Canada/Ontario/Toronto", 1.5f);
		//when
		service.store(rate);
		int score = service.calculateScoreQuery("John Doe", "Canada/Ontario/Toronto");
		//then
		assertThat(score, is(equalTo(10)));
	}

	@Test
	public void shouldCalculateQueryExactLocation3LowerRecord() {
		
		//given
		InsulationRate rate = new InsulationRate("John Doe", "Canada/Ontario/Toronto", 1.5f);
		InsulationRate rate1 = new InsulationRate("John Doe 2", "Canada/Ontario/Toronto", 5f);
		InsulationRate rate2 = new InsulationRate("John Doe 3", "Canada/Ontario/Toronto", 6f);
		
		//when
		service.store(rate);
		service.store(rate1);
		service.store(rate2);
		
		int score = service.calculateScoreQuery("John Doe", "Canada/Ontario/Toronto");
		//then
		assertThat(score, is(equalTo(4)));
	}

	@Test
	public void shouldCalculateQueryExactLocation3MiddleRecord() {
		
		//given
		InsulationRate rate = new InsulationRate("John Doe", "Canada/Ontario/Toronto", 1.5f);
		InsulationRate rate1 = new InsulationRate("John Doe 2", "Canada/Ontario/Toronto", 5f);
		InsulationRate rate2 = new InsulationRate("John Doe 3", "Canada/Ontario/Toronto", 6f);
		
		//when
		service.store(rate);
		service.store(rate1);
		service.store(rate2);
		
		int score = service.calculateScoreQuery("John Doe 2", "Canada/Ontario/Toronto");
		//then
		assertThat(score, is(equalTo(7)));
	}

	@Test
	public void shouldCalculateQueryExactLocation3NameNotFound() {
		
		//given
		InsulationRate rate = new InsulationRate("John Doe", "Canada/Ontario/Toronto", 1.5f);
		InsulationRate rate1 = new InsulationRate("John Doe 2", "Canada/Ontario/Toronto", 5f);
		InsulationRate rate2 = new InsulationRate("John Doe 3", "Canada/Ontario/Toronto", 6f);
		
		//when
		service.store(rate);
		service.store(rate1);
		service.store(rate2);
		
		int score = service.calculateScoreQuery("John Doe 5", "Canada/Ontario/Toronto");
		//then
		assertThat(score, is(equalTo(1)));
	}

	@Test
	public void shouldCalculateQueryLocationNotFound() {
		
		//given
		InsulationRate rate = new InsulationRate("John Doe", "Canada/Ontario/Toronto", 1.5f);
		InsulationRate rate1 = new InsulationRate("John Doe 2", "Canada/Ontario/Toronto", 5f);
		InsulationRate rate2 = new InsulationRate("John Doe 3", "Canada/Ontario/Toronto", 6f);
		
		//when
		service.store(rate);
		service.store(rate1);
		service.store(rate2);
		
		int score = service.calculateScoreQuery("John Doe", "US");
		//then
		assertThat(score, is(equalTo(1)));
	}

	@Test
	public void shouldCalculateQueryExactLocation3RegisterHigherRecord() {
		
		//given
		InsulationRate rate = new InsulationRate("John Doe", "Canada/Ontario/Toronto", 1.5f);
		InsulationRate rate1 = new InsulationRate("John Doe 2", "Canada/Ontario/Toronto", 5f);
		InsulationRate rate2 = new InsulationRate("John Doe 3", "Canada/Ontario/Toronto", 6f);
		
		//when
		service.store(rate);
		service.store(rate1);
		service.store(rate2);
		
		int score = service.calculateScoreQuery("John Doe 3", "Canada/Ontario/Toronto");
		//then
		assertThat(score, is(equalTo(10)));
	}
	
	@Test
	public void shouldCalculateQueriesExamplesPassedByEcoobee() {
		
		//given
		InsulationRate rate = new InsulationRate("John Doe", "Canada/Ontario/Toronto", 1.5f);
		InsulationRate rate1 = new InsulationRate("Samanta Smith", "Canada/Ontario/London", 3.7f);
		InsulationRate rate2 = new InsulationRate("Adam Xin", "Canada/British Columbia/Vancouver", 2.110f);
		InsulationRate rate3 = new InsulationRate("Monica Taylor", "Canada/Ontario/Toronto", 2.110f);
		InsulationRate rate4 = new InsulationRate("Alicia Yazzie", "US/Arizona/Phoenix", 5.532f);
		InsulationRate rate5 = new InsulationRate("Mohammed Zadeh", "Canada/Ontario/Toronto", 1.43f);
		
		//when
		service.store(rate);
		service.store(rate1);
		service.store(rate2);
		service.store(rate3);
		service.store(rate4);
		service.store(rate5);
		
		int score = service.calculateScoreQuery("John Doe", "Canada");
		int score1 = service.calculateScoreQuery("John Doe", "Canada/Ontario");
		int score2 = service.calculateScoreQuery("Alicia Yazzie", "US/Arizona");

		//then
		assertThat(score, is(equalTo(4)));
		assertThat(score1, is(equalTo(5)));
		assertThat(score2, is(equalTo(10)));

	}

	@Test
	public void shouldReturn1on95Percent() {
		
		//given
		//when
		int score = service.calculateScore(90, 100);

		//then
		assertThat(score, is(equalTo(1)));

	}

}
