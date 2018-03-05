(function () {
  'use strict';

  angular
    .module('app')
    .controller('CurrencyController', CurrencyController);

  CurrencyController.$inject = ['$rootScope', '$scope', '$cookies', '$routeParams', '$uibModal', '$location', 'ExchangeRateService', 'ErrorController'];

  function CurrencyController($rootScope, $scope, $cookies, $routeParams, $uibModal, $location, ExchangeRateService, ErrorController) {

    var ctrl = this;

    $scope.dataLoaded = true;
    $scope.currencies = [];
    $scope.errorMessage = undefined;
    $scope.errors = undefined;

    ctrl.$onInit = function() {
      //console.log('CurrencyController initialized');
      ctrl.getAllCurrencies();
    };

    ctrl.getAllCurrencies = function() {
      $scope.dataLoaded = false;
      ExchangeRateService.getAllCurrencies(getCurrenciesSuccessCb, getCurrenciesErrorCb);
    }

    var getCurrenciesSuccessCb = function(data, status, headers) {
        $scope.errorMessage = undefined;
        $scope.errors = undefined;
        $scope.dataLoaded = true;
        $scope.currencies = data;
    }

    var getCurrenciesErrorCb = function(data, status, headers) {
      //console.log(status);
      $scope.dataLoaded = true;
      $scope.errorMessage = data.error;
      $scope.errors = {};
      angular.forEach(data.errors, function(error, index){
        $scope.errors[error.field] = error.errorMessage;
      });
    }
  }
})();