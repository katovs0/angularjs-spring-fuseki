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
    	
        return FusekiAdapter.getAllDivers();
    }

    @Override
    public void addDiver(String diver) {
        
        FusekiAdapter.storeDiver(diver);
    }

    @Override
    public void deleteDiver(String diver) {
        
        FusekiAdapter.removeDiver(diver);
    }

    @Override
    public void deleteAll() {
    	
    	FusekiAdapter.removeAllDivers();
    }
    
    
    public List<String> getAllLocations() {
    	
    	return FusekiAdapter.getAllLocations();
    	
    }
    
   
}
