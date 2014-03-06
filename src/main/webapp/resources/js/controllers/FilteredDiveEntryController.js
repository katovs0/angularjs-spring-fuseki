'use strict';

/**
 * FilteredDiveEntryController
 * @constructor
 */
var FilteredDiveEntryController = function($scope, $http) {
    $scope.dive = {};
    $scope.filter = {};
    $scope.params = {};
    $scope.editMode = false;

    $scope.fetchDivesList = function() {
        $http.get('divesfilter/divesfilterlist.json').success(function(diveList){
            $scope.dives = diveList;
        });
    }
    
    $scope.searchFilteredDives = function(filter) {
        $scope.resetError();


        $http.get('divesfilter/searchDivesList.json', 
        			{params: {minAirTemp:$scope.filter.minAirTemp,maxAirTemp:$scope.filter.maxAirTemp,
                			minWaterTemp:$scope.filter.minWaterTemp,maxWaterTemp:$scope.filter.maxWaterTemp,                			
                			minDepth:$scope.filter.minDepth,maxDepth:$scope.filter.maxDepth,
                			minTime:$scope.filter.minTime,maxTime:$scope.filter.maxTime,

                			diverName:$scope.filter.diverName,
                			location:$scope.filter.location}} ).success(function(diveList) {
        	$scope.dives = diveList;
        	
//            $scope.filter.minAirTemp = 0.0;
//            $scope.filter.maxAirTemp = 0.0;

        }).error(function() {
            $scope.setError('Could search for filtered dives');
        });
    }
    
     $scope.fetchDiversList = function() {
        $http.get('divers/diverlist.json').success(function(diverList){
            $scope.divers = diverList;
        });
    }
     
    $scope.fetchLocsList = function() {
        $http.get('dives/locationsList.json').success(function(locsList){
            $scope.locations = locsList;
        });
    }

    $scope.resetError = function() {
        $scope.error = false;
        $scope.errorMessage = '';
    }

    $scope.setError = function(message) {
        $scope.error = true;
        $scope.errorMessage = message;
    }
    
    $scope.fetchDiversList();
    $scope.fetchLocsList();
    

    $scope.predicate = 'name';
}