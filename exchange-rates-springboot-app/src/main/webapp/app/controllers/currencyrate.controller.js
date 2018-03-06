(function () {
  'use strict';

  angular
    .module('app')
    .controller('CurrencyRateController', CurrencyRateController);

  CurrencyRateController.$inject = ['$rootScope', '$scope', '$cookies', '$routeParams', '$filter', '$timeout', '$uibModal', '$location', 'ExchangeRateService', 'ErrorController'];

  function CurrencyRateController($rootScope, $scope, $cookies, $routeParams, $filter, $timeout, $uibModal, $location, ExchangeRateService, ErrorController) {

    var ctrl = this;

    ctrl.opened = false;
    
    $scope.currencyRates = [];
    
    $scope.dataLoaded = true;

    var firstDay = new Date(2014, 0, 1);
    $scope.dateFormat = 'yyyy-MM-dd';
    
    $scope.datePickedFrom = new Date(2014, 0, 1);
    $scope.dateFromOptions = {
      formatYear: 'yy',
      startingDay: 1,
      minDate: firstDay,
      maxDate: new Date(2014, 11, 31),
      showWeeks: false
    };
    $scope.dateFromPopup = {
            opened: false
    };
    ctrl.openDateFrom = function($event) {
      $event.preventDefault();
      $event.stopPropagation();
      $scope.dateFromPopup.opened = !$scope.dateFromPopup.opened;
    };
    
    $scope.datePickedTo = new Date(2014, 1, 1);
    $scope.dateToOptions = {
      formatYear: 'yy',
      startingDay: 1,
      minDate: firstDay,
      maxDate: new Date(2014, 11, 31),
      showWeeks: false
    };
    
    $scope.dateToPopup = {
      opened: false
    };
    ctrl.openDateTo = function() {
      $scope.dateToPopup.opened = !$scope.dateToPopup.opened;
    };

    ctrl.changeDateToMinDate = function(dateFrom) {
        if (null != dateFrom && undefined != dateFrom) {
            var dateToMinDate = new Date(dateFrom);
            $scope.dateToOptions.minDate = dateToMinDate;
            $scope.datePickedTo = dateToMinDate;
        }
    };

    $scope.errorMessage = undefined;
    $scope.errors = undefined;

    ctrl.$onInit = function() {
      //console.log('CurrencyRateController initialized');
    };

    ctrl.getAllCurrencyRateChanges = function() {
      $scope.dataLoaded = false;
      var formattedDateFrom = toJSONLocal($scope.datePickedFrom);
      var formattedDateTo = toJSONLocal($scope.datePickedTo);
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
    
    var toJSONLocal = function(date) {
      var local = new Date(date);
      local.setMinutes(date.getMinutes() - date.getTimezoneOffset());
      return local.toJSON().slice(0, 10);
    }

  }
})();