var myApp = angular.module('mapApp', ['ngMap']);

myApp.service('dataService', function ($http) {

    this.getAllFish = function () {
        return $http.get('/fishregistration-1.0-SNAPSHOT/api/fish/');
    }

    this.addFish = function (weight, length, longitude, latitude, species) {
        return $http.post('/fishregistration-1.0-SNAPSHOT/api/fish/' + weight +
            "/" + length + "/" + longitude + "/" + latitude + "/" + species);
    }

    this.deleteFish = function (id) {
        return $http.delete('/fishregistration-1.0-SNAPSHOT/api/fish/' + id)
    }
});

myApp.controller('mapCtrl', function ($scope, dataService) {
    dataService.getAllFish().then(function (dataResponse) {
        $scope.fishes = dataResponse.data;
    });
});