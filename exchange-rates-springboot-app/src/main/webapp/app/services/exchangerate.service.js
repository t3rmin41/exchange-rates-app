(function() {
  'use strict';

  angular
    .module('app')
    .factory('ExchangeRateService', ExchangeRateService);
  
  ExchangeRateService.$inject = ['$http', '$location'];
  
  function ExchangeRateService($http, $location) {
    
    var service = {};

    service.getAllCurrencies = function(success, error) {
      $http({
        url: '/currencies/all',
        method: 'GET',
        headers : {
          "Content-Type" : "application/json;charset=UTF-8",
          "Accept" : "application/json;charset=UTF-8"
        }
      }).success(success).error(error);
    };

    return service;
  }
  
})();