/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


angular.
module('vehicleList').
component('vehicleList', {
	templateUrl: 'vehicle-list/vehicle-list.template.html',
	controller: function VehicleListController($http) {
		var self = this;
		$http.get('api/vehicles/all', {cache:true}).then(function(response) {
			self.vehicles = response.data;
		});
	}
});