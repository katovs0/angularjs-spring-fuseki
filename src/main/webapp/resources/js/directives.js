'use strict';

/* Directives */

var AppDirectives = angular.module('SimpleDiveLog.directives', []);

AppDirectives.directive('appVersion', ['version', function (version) {
    return function (scope, elm, attrs) {
        elm.text(version);
    };
}]);

//AppDirectives.directive('jq:slider', function() {
//    return function(scope, elm) {
//      var currentScope = this;
//      elm.slider({
//            range: true,
//            min: 0,
//            max: 500,
//            values: [ 75, 300 ],
//            slide: function( event, ui ) {
//                $( "#amount" ).val( "$" + ui.values[ 0 ] + " - $" + ui.values[ 1 ] );
//                currentScope.years = ui.values[0];            
//                currentScope.$apply();
//            }
//        });
//    };
//});

AppDirectives.directive('slider', function() {
    return {
        restrict:'E',
        //replace: true,
        //scope: true,
        link:function(scope,element,attrs)
		{
      element.slider(
      {
        range: true,
        min: 1 /*scope.min*/,
        max: 10000000 /*scope.max*/,
        values: [ 1 /*scope.max*/, 10000000 /*scope.min*/],
        step: 5000
       });
      
      //element.html('<div>fuilula!!!</div>');
		}
	};
});