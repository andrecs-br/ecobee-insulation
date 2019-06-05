package com.ecobee.insulationrate.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ecobee.insulationrate.domain.InsulationRate;

public class InsulationService {
	
	/* I am using just one hashmap to store the entire data, because we will have 1000000 registers.
	 * Another possibility is: store hashmaps separated by location, but in this case we will consume 3x more memory, because
	 * a register will be stored in Country Record, Country + Province record as well as Country + Province + City record.
	 * However, in this case we will have a better performance in queries.
	 */
	private Map<String, Float> data = new HashMap<String, Float>();
	
	/*Just to store the names to take the rValue in querie*/
	private Map<String, Float> nameDataSet = new HashMap<String, Float>();

	public int getTotalRecords() {
		return data.size();
	}
	
	public void store(InsulationRate rate) {
		//generating the hashmap key with name and location
		String key = getKey(rate.getName(), rate.getLocation());
		data.putIfAbsent(key, rate.getRValue());
		nameDataSet.putIfAbsent(rate.getName(), rate.getRValue());
	
	}
	
	public int calculateScoreQuery(String name, String location) {

		int score = 0;
		//filtering by location and ordering by value
		Map<String, Float> locationSubset = data
			.entrySet()
			.stream()
			.filter(entry -> entry.getKey().startsWith(location))
			.sorted(Map.Entry.comparingByValue())
			.collect(Collectors.toMap(key -> key.getKey(), value -> value.getValue()));
		
		int size = locationSubset.size();

		int position = getMapPosition(locationSubset, nameDataSet.get(name));
		
		if (position == -1) { //not found
			score = 1;
		} else {
			score = calculateScore(position, size);
		}
		return score;
	}
	
	private String getKey(String name, String location) {
		return location + "||" + name;
	}
	
	private int getMapPosition(Map<String, Float> map, Float value) {
		
		if (value != null) {
			List<Float> mapList = new ArrayList<Float>(map.values());
			
			for (int i = 0; i < mapList.size(); i++) {
				if (mapList.get(i).equals(value)) {
					return (i);
				}
			}
		}
		return -1;
		
	}
	
	public int calculateScore(int position, int size) {
		return new BigDecimal((10 - ((float)(position)/(float)size) * 10)).setScale(0, RoundingMode.UP).intValue();
	}

}
