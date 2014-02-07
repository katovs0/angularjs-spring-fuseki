'use strict';

/**
 * CarController
 * @constructor
 */
var CarController = function($scope, $http) {
    $scope.fetchCarsList = function() {
        $http.get('cars/carlist.json').success(function(carList){
            $scope.cars = carList;
        });
    }

    $scope.addNewCar = function(newCar) {
        $http.post('cars/addCar/' + newCar).success(function() {
            $scope.fetchCarsList();
        });
        $scope.carName = '';
    }

    $scope.removeCar = function(car) {
        $http.delete('cars/removeCar/' + car).success(function() {
            $scope.fetchCarsList();
        });
    }

    $scope.removeAllCars = function() {
        $http.delete('cars/removeAllCars').success(function() {
            $scope.fetchCarsList();
        });

    };
    
    $scope.fetchLocsList = function() {
        $http.get('cars/locationsList.json').success(function(locsList){
            $scope.locations = locsList;
        });
    }

    $scope.fetchCarsList();
    $scope.fetchLocsList();
    
//    $scope.cities = function(cityName) {
//    return $http.jsonp("http://gd.geobytes.com/AutoCompleteCity?callback=JSON_CALLBACK &filter=US&q="+cityName).then(function(response){
//      return limitToFilter(response.data, 15);
//    });
//  };
//
//    $scope.selected = undefined;
//    $scope.states = [];
//    
//    $scope.onedit = function(){
//        $scope.states = [];
//    
//        for(var i = 0; i < Math.floor((Math.random()*10)+1); i++){
//          var value = "";
//    
//          for(var j = 0; j < i; j++){
//            value += j;
//          }
//          $scope.states.push(value);
//        }
//      }
}