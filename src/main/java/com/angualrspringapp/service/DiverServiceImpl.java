package com.angualrspringapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.angualrspringapp.external.FusekiAdapter;

@Service("diverService")
public class DiverServiceImpl implements DiverService {
    private static List<String> diverList = new ArrayList<String>();

    @Override
    public List<String> getAllDivers() {
        return diverList;
    }

    @Override
    public void addDiver(String diver) {
        diverList.add(diver);
    }

    @Override
    public void deleteDiver(String diver) {
        if (diverList.contains(diver)) {
            diverList.remove(diver);
        }
    }

    @Override
    public void deleteAll() {
        diverList.clear();
    }
    
    
    public List<String> getAllLocations() {
    	
    	return FusekiAdapter.getAllLocations();
    	
    }
    
   
}
