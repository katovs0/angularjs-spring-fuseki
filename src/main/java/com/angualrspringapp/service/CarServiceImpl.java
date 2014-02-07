package com.angualrspringapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.angualrspringapp.external.FusekiAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: xvitcoder
 * Date: 12/20/12
 * Time: 11:14 PM
 */
@Service("carService")
public class CarServiceImpl implements CarService {
    private static List<String> carList = new ArrayList<String>();

    @Override
    public List<String> getAllCars() {
        return carList;
    }

    @Override
    public void addCar(String car) {
        carList.add(car);
    }

    @Override
    public void deleteCar(String car) {
        if (carList.contains(car)) {
            carList.remove(car);
        }
    }

    @Override
    public void deleteAll() {
        carList.clear();
    }
    
    
    public List<String> getAllLocations() {
    	
    	return FusekiAdapter.getAllLocations();
    	
    }
    
   
}
