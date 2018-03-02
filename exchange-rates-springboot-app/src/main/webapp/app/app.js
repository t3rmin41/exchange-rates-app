(function () {
    'use strict';
    angular
      .module('app', ['ngRoute', 'ngCookies', 'ui.bootstrap'])
      .config([ '$routeProvider', function($routeProvider) {
        $routeProvider
        .when('/', {
            templateUrl: 'app/views/home.html',
            controller: 'HomeController'
        })
        .otherwise({
          redirectTo : '/'
        });
      } ])
})();