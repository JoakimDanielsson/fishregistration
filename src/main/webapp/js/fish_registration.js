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
});

myApp.controller('mapCtrl', function ($scope, dataService) {
    dataService.getAllFish().then(function (dataResponse) {
        $scope.fishes = dataResponse.data;
    });
});

myApp.controller('graphCtrl', function ($scope, dataService) {

    //Dummy data
//    dataService.addFish(5, 100, 10, 10, "Pike").then();
//    dataService.addFish(5, 100, 10, 10, "Pike").then();
//    dataService.addFish(5, 100, 10, 10, "Redfin").then();
//    dataService.addFish(5, 100, 10, 10, "Redfin").then();
//    dataService.addFish(5, 100, 10, 10, "Sea Trout").then();
//    dataService.addFish(5, 100, 10, 10, "Other").then();

     $scope.pielabels = ["Sea Trout", "Pike", "Redfin", "Other"];
     $scope.barlabels = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
                         "Aug", "Sep", "Oct", "Nov", "Dec"];

     //Create array from 1 to 12 symbolizing months
     monthNr = new Array(12).join().split(',')
                                   .map(function(item, index){
                                            return ++index;});

     dataService.getAllFish().then(function (dataResponse) {
         $scope.fishes = dataResponse.data;

         //Filter fish by species and return the number of each species in piedata
         $scope.piedata = $scope.pielabels.map(function (species) {
             return ($scope.fishes.filter(function (fish) {
                 return fish.species == species})).length});

         //Filter fish by month and return number of fish each month
         $scope.bardata = monthNr.map(function (month) {
             return ($scope.fishes.filter(function (fish) {
                 return parseInt(fish.date.substring(5, 7)) == month})).length});
     });
});

myApp.controller('registerCtrl', function ($scope, dataService) {

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