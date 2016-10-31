/**
 * Created by joakim on 2016-10-24.
 */

var myApp = angular.module('myApp', ['chart.js', 'ngMap']);

myApp.service('dataService', function ($http) {

    this.getAllFish = function () {
        return $http.get('/fishregistration-1.0-SNAPSHOT/api/fish/');
    }

    this.addFish = function (weight, length, longitude, latitude, species) {
        return $http.post('/fishregistration-1.0-SNAPSHOT/api/fish/' + weight + "/" +
                          length + "/" + longitude + "/" + latitude + "/" +
                          species);
    }

    this.deleteFish = function (id) {
        return $http.delete('/fishregistration-1.0-SNAPSHOT/api/fish/' + id)
    }

    this.getNrBySpecies = function (species) {
        return $http.get('/fishregistration-1.0-SNAPSHOT/api/fish/' + species)
    }
});

myApp.controller('fishCtrl', function ($scope, dataService) {
    $scope.fishes = [];

    // $scope.fishWeight = 10;
    // $scope.fishLength = 120;
    // $scope.fishLongitude = 12.23;
    // $scope.fishLatitude = 57.76;
    // $scope.fishSpecies = 'Pike';

    //Pie chart
    $scope.labels = ["Sea Trout", "Pike", "Redfin", "Other"];
    $scope.data = [0, 0, 0, 0];


    $scope.getAllFish = function () {
        dataService.getAllFish().then(function (dataResponse) {
            $scope.fishes = dataResponse.data;
            $scope.updatePieChart();
        });
    };

    $scope.addFish = function () {
        dataService.addFish($scope.fishWeight, $scope.fishLength,
                            $scope.fishLongitude, $scope.fishLatitude,
                            $scope.fishSpecies).then(function () {
            $scope.fishes = $scope.getAllFish();
            $scope.fishWeight = '';
            $scope.fishLength = '';
            $scope.fishLongitude = '';
            $scope.fishLatitude = '';
            $scope.fishSpecies = '';
        });
    };

    $scope.deleteFish = function (id) {
        dataService.deleteFish(id).then(function () {
            $scope.fishes = $scope.getAllFish();
        });
    };

    $scope.updatePieChart = function () {
        dataService.getNrBySpecies("Sea Trout").then(function(dataResponse) {
            $scope.data[0] = dataResponse.data;
        });
        dataService.getNrBySpecies("Pike").then(function(dataResponse) {
            $scope.data[1] = dataResponse.data;
        });
        dataService.getNrBySpecies("Redfin").then(function(dataResponse) {
            $scope.data[2] = dataResponse.data;
        });
        dataService.getNrBySpecies("Other").then(function(dataResponse) {
            $scope.data[3] = dataResponse.data;
        });
    }
});