(function () {
  'use strict';

  angular
    .module('app')
    .controller('CurrencyRateController', CurrencyRateController);

  CurrencyRateController.$inject = ['$rootScope', '$scope', '$cookies', '$routeParams', '$uibModal', '$location', 'ExchangeRateService', 'ErrorController'];

  function CurrencyRateController($rootScope, $scope, $cookies, $routeParams, $uibModal, $location, ExchangeRateService, ErrorController) {

    var ctrl = this;

    $scope.currencies = [];

    ctrl.$onInit = function() {
      //console.log('CurrencyRateController initialized');
      //ctrl.getAllCurrencies();
    };

    $scope.getAllCurrencies = function() {
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