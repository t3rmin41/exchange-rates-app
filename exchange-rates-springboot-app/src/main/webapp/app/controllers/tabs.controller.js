(function () {
    'use strict';

    angular
        .module('app')
        .controller('TabsController', TabsController);

    TabsController.$inject = ['$rootScope', '$scope',  '$cookies', '$location', '$sce'];

    function TabsController($rootScope, $scope, $cookies, $location, $sce) {

      var ctrl = this;

      $scope.tabs = [ 
                      { id: '0', title: 'Currencies', path: 'app/views/currency.html', ctrl : 'CurrencyController', reloadEvent: 'CurrencyReload' }
                     ];
      
      $scope.currentTab = $scope.tabs[0];
      $rootScope.currentTab = $scope.currentTab;
      
      $scope.setCurrentTab = function(i) {
        $scope.currentTab = $scope.tabs[i];
        $rootScope.currentTab = $scope.currentTab;
        $rootScope.httpErrorMessage = null;
      }

      $scope.templateUrl = function(index) {
        return $scope.tabs[index].path;
      }
      
    }
})();