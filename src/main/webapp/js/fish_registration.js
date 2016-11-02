/**
 * Created by joakim on 2016-10-24.
 */

var myApp = angular.module('myApp', ['chart.js', 'ngMap']);

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

    this.getNrBySpecies = function (species) {
        return $http.get('/fishregistration-1.0-SNAPSHOT/api/fish/' + species)
    }
});

myApp.controller('mapCtrl', function ($scope, dataService) {
    dataService.getAllFish().then(function (dataResponse) {
        $scope.fishes = dataResponse.data;
    });
});

myApp.controller('graphCtrl', function ($scope, dataService) {

    //Pie chart
    $scope.labels = ["Sea Trout", "Pike", "Redfin", "Other"];
    $scope.data = [0, 0, 0, 0];

    dataService.getNrBySpecies("Sea Trout").then(function (dataResponse) {
        $scope.data[0] = dataResponse.data;
    });
    dataService.getNrBySpecies("Pike").then(function (dataResponse) {
        $scope.data[1] = dataResponse.data;
    });
    dataService.getNrBySpecies("Redfin").then(function (dataResponse) {
        $scope.data[2] = dataResponse.data;
    });
    dataService.getNrBySpecies("Other").then(function (dataResponse) {
        $scope.data[3] = dataResponse.data;
    });
});

myApp.controller('registerCtrl', function ($scope, dataService) {
    $scope.fishes = [];

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