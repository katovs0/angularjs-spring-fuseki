package com.simpledivelog.service;

import java.util.List;

import com.simpledivelog.beans.DiveEntry;
import com.simpledivelog.beans.SearchFilter;

public interface DiveEntryService {
	
    public List<DiveEntry> getAllDives();

//    public DiveEntry getDiveById(Long id);

    public void addDive(DiveEntry diveEntry);

//    public void deleteDiveById(Long id);

    public void deleteAllDives();

    public void updateDive(DiveEntry diveEntry);

	void deleteDiveById(String id);

	public List<String> getAllLocations();

	public List<DiveEntry> getFilteredDives();

	public List<DiveEntry> getSearchFilteredDives(SearchFilter filter);
}
