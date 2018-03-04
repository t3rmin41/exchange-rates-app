(function () {
  'use strict';

  angular
    .module('app')
    .controller('CurrencyRateController', CurrencyRateController);

  CurrencyRateController.$inject = ['$rootScope', '$scope', '$cookies', '$routeParams', '$filter', '$timeout', '$uibModal', '$location', 'ExchangeRateService', 'ErrorController'];

  function CurrencyRateController($rootScope, $scope, $cookies, $routeParams, $filter, $timeout, $uibModal, $location, ExchangeRateService, ErrorController) {

    var ctrl = this;

    $scope.currencyRates = [];
    
    $scope.datePicked = new Date();

    $scope.opened = true;
    
    $scope.status = {
        opened: false
    }

    $scope.open = function($event) {
        $event.preventDefault();
        $event.stopPropagation();
        $scope.opened = true;
        $scope.status.opened = true;
        //$scope.$parent.opened = true;
        //$timeout(function () {
        //    $scope.opened = true;
        //});
        //
    };

    ctrl.$onInit = function() {
      //console.log('CurrencyRateController initialized');
    };

    ctrl.getAllCurrencyRateChanges = function() {
      var formattedDate = $scope.datePicked;
      ExchangeRateService.getAllCurrencyRateChangesForDate(formattedDate, getCurrencyRatesSuccessCb, getCurrencyRatesErrorCb);
    }

    var getCurrencyRatesSuccessCb = function(data, status, headers) {
        $scope.currencyRates = data;
    }

    var getCurrencyRatesErrorCb = function(data, status, headers) {
      console.log(status);
    }
  }
})();