package com.ecobee.insulationrate.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InsulationRate {
	
	private String name;
	
	private String location;
	
	private float rValue;

}
