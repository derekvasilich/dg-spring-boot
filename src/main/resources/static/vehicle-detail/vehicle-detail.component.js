/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

angular.
module('vehicleDetail').
component('vehicleDetail', {
	templateUrl: 'vehicle-detail/vehicle-detail.template.html',
	controller: function VehicleDetailController($http, $routeParams) {
		var self = this;
		var id = '';
		$http.get('api/vehicles/'+$routeParams.vehicleId, {cache:true}).then(function(response) {
			self.vehicle = response.data;
		});
	}
});