var myApp = angular.module('chartApp', ['chart.js']);

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

myApp.controller('chartCtrl', function ($scope, dataService) {

    //Dummy data
//    dataService.addFish(5, 100, 10, 10, "Pike");
//    dataService.addFish(5, 100, 10, 10, "Pike");
//    dataService.addFish(5, 100, 10, 10, "Redfin");
//    dataService.addFish(5, 100, 10, 10, "Redfin");
//    dataService.addFish(5, 100, 10, 10, "Sea Trout");
//    dataService.addFish(5, 100, 10, 10, "Other");

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