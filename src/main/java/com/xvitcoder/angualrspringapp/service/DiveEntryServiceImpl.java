package com.xvitcoder.angualrspringapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.xvitcoder.angualrspringapp.beans.DiveEntry;

@Service("diveEntryService")
public class DiveEntryServiceImpl implements DiveEntryService {
    private static List<DiveEntry> divesList = new ArrayList<DiveEntry>();
    private static Long id = 0L;

    @Override
    public List<DiveEntry> getAllDives() {
        return divesList;
    }

    @Override
    public DiveEntry getDiveById(Long id) {
        return findDiveEntryById(id);
    }

    @Override
    public void addDive(DiveEntry diveEntry) {
    	System.out.println(diveEntry.toString());
        diveEntry.setId(++id);
        divesList.add(diveEntry);
    }

    @Override
    public void deleteDiveById(Long id) {
        DiveEntry foundDiveEntry = findDiveEntryById(id);
        if (foundDiveEntry != null) {
            divesList.remove(foundDiveEntry);
            id--;
        }
    }

    @Override
    public void deleteAll() {
        divesList.clear();
        id = 0L;
    }

    @Override
    public void updateDive(DiveEntry diveEntry) {
        DiveEntry foundDiveEntry = findDiveEntryById(diveEntry.getId());
        if (foundDiveEntry != null) {
            divesList.remove(foundDiveEntry);
            divesList.add(diveEntry);
        }
    }

    private DiveEntry findDiveEntryById(Long id) {
        for (DiveEntry DiveEntry : divesList) {
            if (DiveEntry.getId() == id) {
                return DiveEntry;
            }
        }

        return null;
    }
    
}
