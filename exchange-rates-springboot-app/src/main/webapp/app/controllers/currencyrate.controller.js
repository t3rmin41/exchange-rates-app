(function () {
  'use strict';

  angular
    .module('app')
    .controller('CurrencyRateController', CurrencyRateController);

  CurrencyRateController.$inject = ['$rootScope', '$scope', '$cookies', '$routeParams', '$filter', '$timeout', '$uibModal', '$location', 'ExchangeRateService', 'ErrorController'];

  function CurrencyRateController($rootScope, $scope, $cookies, $routeParams, $filter, $timeout, $uibModal, $location, ExchangeRateService, ErrorController) {

    var ctrl = this;

    ctrl.opened = false;
    
    $scope.dataLoaded = true;
    $scope.currencyRates = [];
    $scope.datePicked = "";
    $scope.datePickedFrom = "";
    $scope.datePickedTo = "";

    $scope.errorMessage = undefined;
    $scope.errors = undefined;

    $scope.opened = true;
    
    $scope.status = {
        opened: false
    }

    $scope.myDate = new Date();
    
    ctrl.open = function($event) {
        $event.preventDefault();
        $event.stopPropagation();
        $scope.opened = true;
        $scope.status.opened = true;
        ctrl.opened = true;
        console.log("Datepicker clicked")
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
      $scope.dataLoaded = false;
      var formattedDate = $scope.datePicked;
      var formattedDateFrom = $scope.datePickedFrom;
      var formattedDateTo = $scope.datePickedTo;
      //ExchangeRateService.getAllCurrencyRateChangesForDate(formattedDate, getCurrencyRatesSuccessCb, getCurrencyRatesErrorCb);
      ExchangeRateService.getAllCurrencyRateChangesForDifferentDates(formattedDateFrom, formattedDateTo, getCurrencyRatesSuccessCb, getCurrencyRatesErrorCb);
    }

    var getCurrencyRatesSuccessCb = function(data, status, headers) {
        $scope.errorMessage = undefined;
        $scope.errors = undefined;
        $scope.dataLoaded = true;
        $scope.currencyRates = data;
    }

    var getCurrencyRatesErrorCb = function(data, status, headers) {
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