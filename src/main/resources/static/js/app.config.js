/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

angular.
  module('dgApp').
  config(['$routeProvider',
    function config($routeProvider) {
      $routeProvider.
        when('/vehicles', {
          template: '<vehicle-list></vehicle-list>'
        }).
        when('/vehicles/:vehicleId', {
          template: '<vehicle-detail></vehicle-detail>'
        }).
        otherwise('/vehicles');
    }
  ]);