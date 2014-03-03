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
    
}