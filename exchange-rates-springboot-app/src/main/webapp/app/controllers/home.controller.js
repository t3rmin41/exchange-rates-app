(function () {
  'use strict';

  angular
    .module('app')
    .controller('HomeController', HomeController);

  HomeController.$inject = ['$rootScope', '$scope', '$cookies', '$route', '$routeParams', '$location', 'ErrorController'];

  function HomeController($rootScope, $scope, $cookies, $route, $routeParams, $location, ErrorController) {

    var ctrl = this;

    ctrl.$onInit = function() {
        console.log("HomeController initialized");
    };

  }
})();