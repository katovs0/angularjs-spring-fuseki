package com.angualrspringapp.beans;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class SearchFilter {
	
	private Double minAirTemp;
	private Double maxAirTemp;
	private Double minWaterTemp;
	private Double maxWaterTemp;
	private Double minDepth;
	private Double maxDepth;
	private Double minTime;
	private Double maxTime;
	
	private String diverName;
	private String location;
	
	public SearchFilter() { }
	
	public SearchFilter(Double minAirTemp, Double maxAirTemp, Double minWaterTemp, Double maxWaterTemp, Double minDepth, Double maxDepth, Double minTime, Double maxTime, String diverName, String location) {
		this.setMaxAirTemp(maxAirTemp);
		this.setMinAirTemp(minAirTemp);
		this.setMinWaterTemp(minWaterTemp);
		this.setMaxWaterTemp(maxWaterTemp);
		this.setMinDepth(minDepth);
		this.setMaxDepth(maxDepth);
		this.setMinTime(minTime);
		this.setMaxTime(maxTime);
		
		this.setDiverName(diverName);
		this.setLocation(location);
	}

	

	public Double getMinAirTemp() {
		return minAirTemp;
	}

	public void setMinAirTemp(Double minAirTemp) {
		this.minAirTemp = minAirTemp;
	}

	public Double getMaxAirTemp() {
		return maxAirTemp;
	}

	public void setMaxAirTemp(Double maxAirTemp) {
		this.maxAirTemp = maxAirTemp;
	}

	public Double getMinWaterTemp() {
		return minWaterTemp;
	}

	public void setMinWaterTemp(Double minWaterTemp) {
		this.minWaterTemp = minWaterTemp;
	}

	public Double getMaxWaterTemp() {
		return maxWaterTemp;
	}

	public void setMaxWaterTemp(Double maxWaterTemp) {
		this.maxWaterTemp = maxWaterTemp;
	}

	public Double getMinDepth() {
		return minDepth;
	}

	public void setMinDepth(Double minDepth) {
		this.minDepth = minDepth;
	}

	public Double getMaxDepth() {
		return maxDepth;
	}

	public void setMaxDepth(Double maxDepth) {
		this.maxDepth = maxDepth;
	}

	public Double getMinTime() {
		return minTime;
	}

	public void setMinTime(Double minTime) {
		this.minTime = minTime;
	}

	public Double getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(Double maxTime) {
		this.maxTime = maxTime;
	}

	public String getDiverName() {
		return diverName;
	}

	public void setDiverName(String diverName) {
		this.diverName = diverName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String toString() {
		   return ReflectionToStringBuilder.toString(this);
		}

}
