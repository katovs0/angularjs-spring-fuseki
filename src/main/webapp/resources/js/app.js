'use strict';

var AngularSpringApp = {};

var App = angular.module('AngularSpringApp', ['AngularSpringApp.filters', 'AngularSpringApp.services', 'AngularSpringApp.directives']);

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

    $routeProvider.otherwise({redirectTo: '/dives'});
}]);
