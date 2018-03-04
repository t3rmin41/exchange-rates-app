(function () {
  'use strict';

  angular
    .module('app')
    .controller('CurrencyController', CurrencyController);

  CurrencyController.$inject = ['$rootScope', '$scope', '$cookies', '$routeParams', '$uibModal', '$location', 'ExchangeRateService', 'ErrorController'];

  function CurrencyController($rootScope, $scope, $cookies, $routeParams, $uibModal, $location, ExchangeRateService, ErrorController) {

    var ctrl = this;

    $scope.currencies = [];

    ctrl.$onInit = function() {
      //console.log('CurrencyController initialized');
      ctrl.getAllCurrencies();
    };

    ctrl.getAllCurrencies = function() {
      ExchangeRateService.getAllCurrencies(getCurrenciesSuccessCb, ErrorController.httpGetErroCb);
    }

    var getCurrenciesSuccessCb = function(data, status, headers) {
        $scope.currencies = data;
    }

    var getCurrenciesErrorCb = function(data, status, headers) {
      console.log(status);
    }
  }
})();