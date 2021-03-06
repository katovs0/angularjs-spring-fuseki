'use strict';

/**
 * DiveEntryController
 * @constructor
 */
var DiveEntryController = function($scope, $http) {
    $scope.dive = {};
    $scope.editMode = false;

    $scope.fetchDivesList = function() {
        $http.get('dives/diveslist.json').success(function(diveList){
            $scope.dives = diveList;
        });
    }

    $scope.addNewDive = function(dive) {
        $scope.resetError();

        $http.post('dives/addDive', dive).success(function() {
            $scope.fetchDivesList();
            $scope.dive.name = '';
            $scope.dive.depth = '';

        	$scope.dive.location = '';
        	$scope.dive.diver = '';
        	$scope.dive.buddy = '';
        	$scope.dive.minutes = 0;
        	$scope.dive.airTemp = 0.0;
        	$scope.dive.waterTemp = 0.0;

            $scope.dive.valid = false;
        }).error(function() {
            $scope.setError('Could not add a new dive');
        });
    }

    $scope.updateDive = function(dive) {
        $scope.resetError();

        $http.put('dives/updateDive', dive).success(function() {
            $scope.fetchDivesList();
            $scope.dive.name = '';
            $scope.dive.depth = '';
            $scope.dive.valid = false;
            $scope.editMode = false;
        }).error(function() {
            $scope.setError('Could not update the dive');
        });
    }

    $scope.editDive = function(dive) {
        $scope.resetError();
        $scope.dive = dive;
        $scope.editMode = true;
    }

    $scope.removeDive = function(id) {
        $scope.resetError();

        $http.delete('dives/removeDive/' + id).success(function() {
            $scope.fetchDivesList();
        }).error(function() {
            $scope.setError('Could not remove dive');
        });
    }

    $scope.removeAllDives = function() {
        $scope.resetError();

        $http.delete('dives/removeAllDives').success(function() {
            $scope.fetchDivesList();
        }).error(function() {
            $scope.setError('Could not remove all dives');
        });

    };

    $scope.resetDiveForm = function() {
        $scope.resetError();
        $scope.dive = {};
        $scope.editMode = false;
    }

    $scope.resetError = function() {
        $scope.error = false;
        $scope.errorMessage = '';
    }

    $scope.setError = function(message) {
        $scope.error = true;
        $scope.errorMessage = message;
    }
    
    $scope.fetchLocsList = function() {
        $http.get('dives/locationsList.json').success(function(locsList){
            $scope.locations = locsList;
        });
    }
    
    $scope.fetchDiversList = function() {
        $http.get('divers/diverlist.json').success(function(diverList){
            $scope.divers = diverList;
        });
    }

    $scope.fetchDivesList();
    $scope.fetchLocsList();
    $scope.fetchDiversList();
    
    $scope.generateDivesList = function (locsList, divers) {
    	
    	 for (var i=0;i<locsList.length && i<=15;i++)
    	{
    		var dive={};
    		dive.name = 'Generated dive ' + i;
            dive.depth = i;
        	dive.location = locsList[i];
        	if(i<divers.length){
        		dive.diver = divers[i];
        	} else {
        		dive.diver = divers[Math.floor(Math.random()*11)];
        	}
        	
        	
        	
        	dive.buddy = 'buddy' + i;
        	dive.minutes = i * Math.floor(Math.random()*11) / 2;
        	dive.airTemp =  1.10 * Math.floor(Math.random()*11);
        	dive.waterTemp =  1.9 * Math.floor(Math.random()*11);
        	
        	$scope.addNewDive(dive);
    	}
    }
    
    

    $scope.predicate = 'name';
}