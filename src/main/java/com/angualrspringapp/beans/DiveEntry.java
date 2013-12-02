package com.angualrspringapp.beans;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class DiveEntry {
	
	private Long id;
	private String idString;
	private String name;
	private Integer depth;
	
	private String location;
	private String diver;
	private String buddy;
	private Integer minutes;
	private Double airTemp;
	private Double waterTemp;
	
	private Boolean valid;
	
	public DiveEntry() { }
	
	public DiveEntry(long id, String name, Integer depth, String location, String diver, String buddy, Integer minutes, Double airTemp, Double waterTemp, Boolean valid) {
		this.setId(id);
		this.setName(name);
		this.setLocation(location);
		this.setDiver(diver);
		this.setDepth(depth);
		
		this.setAirTemp(airTemp);
		this.setBuddy(buddy);
		this.setDiver(diver);
		this.setLocation(location);
		this.setMinutes(minutes);
		this.setWaterTemp(waterTemp);
		
		this.setValid(valid);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdString() {
		return idString;
	}

	public void setIdString(String idString) {
		this.idString = idString;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDiver() {
		return diver;
	}

	public void setDiver(String diver) {
		this.diver = diver;
	}

	public Integer getDepth() {
		return depth;
	}

	public String getBuddy() {
		return buddy;
	}

	public void setBuddy(String buddy) {
		this.buddy = buddy;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	public Integer getMinutes() {
		return minutes;
	}

	public void setMinutes(Integer minutes) {
		this.minutes = minutes;
	}

	public Double getAirTemp() {
		return airTemp;
	}

	public void setAirTemp(Double airTemp) {
		this.airTemp = airTemp;
	}

	public Double getWaterTemp() {
		return waterTemp;
	}

	public void setWaterTemp(Double waterTemp) {
		this.waterTemp = waterTemp;
	}

	public Boolean isValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}
	
	public String toString() {
		   return ReflectionToStringBuilder.toString(this);
		}

}
