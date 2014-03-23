'use strict';

var SimpleDiveLog = {};

var App = angular.module('SimpleDiveLog', ['SimpleDiveLog.filters', 'SimpleDiveLog.services', 'SimpleDiveLog.directives']);

// Declare app level module which depends on filters, and services
App.config(['$routeProvider', function ($routeProvider) {
    
    $routeProvider.when('/divers', {
        templateUrl: 'divers/layout',
        controller: DiverController
    });
    
    $routeProvider.when('/dives', {
        templateUrl: 'dives/layout',
        controller: DiveEntryController
    });
    
    $routeProvider.when('/divesfilter', {
        templateUrl: 'divesfilter/layout',
        controller: FilteredDiveEntryController
    });

    $routeProvider.otherwise({redirectTo: '/dives'});
}]);
