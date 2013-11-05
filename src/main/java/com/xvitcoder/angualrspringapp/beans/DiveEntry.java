package com.xvitcoder.angualrspringapp.beans;

public class DiveEntry {
	
	private Long id;
	private String name;
//	private String location;
//	private String diver;
	private Integer depth;
	private Boolean valid;
	
	public DiveEntry() { }
	
	public DiveEntry(long id, String name, Integer depth, Boolean valid) {
		this.setId(id);
		this.setName(name);
//		this.setLocation(location);
//		this.setDiver(diver);
		this.setDepth(depth);
		this.setValid(valid);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public String getLocation() {
//		return location;
//	}
//
//	public void setLocation(String location) {
//		this.location = location;
//	}
//
//	public String getDiver() {
//		return diver;
//	}
//
//	public void setDiver(String diver) {
//		this.diver = diver;
//	}

	public Integer getDepth() {
		return depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	public Boolean isValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

}
