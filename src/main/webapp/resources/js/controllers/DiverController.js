'use strict';

/**
 * DiverController
 * @constructor
 */
var DiverController = function($scope, $http) {
    $scope.fetchDiversList = function() {
        $http.get('divers/diverlist.json').success(function(diverList){
            $scope.divers = diverList;
        });
    }

    $scope.addNewDiver = function(newDiver) {
        $http.post('divers/addDiver/' + newDiver).success(function() {
            $scope.fetchDiversList();
        });
        $scope.diverName = '';
    }

    $scope.removeDiver = function(diver) {
        $http.delete('divers/removeDiver/' + diver).success(function() {
            $scope.fetchDiversList();
        });
    }

    $scope.removeAllDivers = function() {
        $http.delete('divers/removeAllDivers').success(function() {
            $scope.fetchDiversList();
        });

    };
    
    $scope.fetchLocsList = function() {
        $http.get('divers/locationsList.json').success(function(locsList){
            $scope.locations = locsList;
        });
    }

    $scope.fetchDiversList();
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