package com.angualrspringapp.service;

import java.util.List;


public interface DiverService {
    public List<String> getAllDivers();

    public void addDiver(String diver);

    public void deleteDiver(String diver);

    public void deleteAll();

	public List<String> getAllLocations();

//	public void autoCompleteCity();
}
