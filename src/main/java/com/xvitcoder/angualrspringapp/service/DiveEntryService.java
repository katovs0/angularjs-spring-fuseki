package com.xvitcoder.angualrspringapp.service;

import java.util.List;

import com.xvitcoder.angualrspringapp.beans.DiveEntry;

public interface DiveEntryService {
	
    public List<DiveEntry> getAllDives();

    public DiveEntry getDiveById(Long id);

    public void addDive(DiveEntry diveEntry);

    public void deleteDiveById(Long id);

    public void deleteAll();

    public void updateDive(DiveEntry diveEntry);
}
