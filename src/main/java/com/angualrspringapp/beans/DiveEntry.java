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
	
	private String locationImage;
	private String locationLang;
	private String locationExtLink;
	private String locationPhotoCollection;			
	
	public DiveEntry() { }
	
	public DiveEntry(long id, String name, Integer depth, String location, 
					 String diver, String buddy, Integer minutes, Double airTemp, 
					 Double waterTemp, Boolean valid, 
					 String locationImage, String locationLang, String locationExtLink, String locationPhotoCollection) {
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
		
		this.setLocationImage(locationImage);
		this.setLocationLang(locationLang);
		this.setLocationExtLink(locationExtLink);
		this.setLocationPhotoCollection(locationPhotoCollection);
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
	
	public String getLocationImage() {
		return locationImage;
	}

	public void setLocationImage(String locationImage) {
		this.locationImage = locationImage;
	}

	public String getLocationLang() {
		return locationLang;
	}

	public void setLocationLang(String locationLang) {
		this.locationLang = locationLang;
	}

	public String getLocationExtLink() {
		return locationExtLink;
	}

	public void setLocationExtLink(String locationExtLink) {
		this.locationExtLink = locationExtLink;
	}

	public String toString() {
		   return ReflectionToStringBuilder.toString(this);
		}

	public String getLocationPhotoCollection() {
		return locationPhotoCollection;
	}

	public void setLocationPhotoCollection(String locationPhotoCollection) {
		this.locationPhotoCollection = locationPhotoCollection;
	}

}
