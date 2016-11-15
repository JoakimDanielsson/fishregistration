/**
 * Created by joakim on 2016-10-24.
 */

var myApp = angular.module('registrationApp', []);

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

myApp.controller('registrationCtrl', function ($scope, dataService) {

    dataService.getAllFish().then(function (dataResponse) {
        $scope.fishes = dataResponse.data;
    });

    //Get the current position. Will not change until the page is reloaded.
    navigator.geolocation.getCurrentPosition(function (position) {
        $scope.fishLatitude = position.coords.latitude;
        $scope.fishLongitude = position.coords.longitude;
    });

    $scope.getAllFish = function () {
        dataService.getAllFish().then(function (dataResponse) {
            $scope.fishes = dataResponse.data;
        });
    };

    $scope.addFish = function () {
        dataService.addFish($scope.fishWeight, $scope.fishLength,
            $scope.fishLongitude, $scope.fishLatitude,
            $scope.fishSpecies).then(function () {
            $scope.fishes = $scope.getAllFish();
            $scope.fishWeight = '';
            $scope.fishLength = '';
            $scope.fishSpecies = '';
        });
    };

    $scope.deleteFish = function (id) {
        dataService.deleteFish(id).then(function () {
            $scope.fishes = $scope.getAllFish();
        });
    };
});