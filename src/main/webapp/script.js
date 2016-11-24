var myApp = angular.module('myApp', ['ngRoute', 'ngMap', 'chart.js', 'ngResource']);

myApp.config(function($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl : 'pages/user_registration.html',
            controller : 'userRegistrationCtrl'
        })
        .when('/user_registration', {
            templateUrl : 'pages/user_registration.html',
            controller : 'userRegistrationCtrl'
        })
        .when('/map', {
            templateUrl : 'pages/map.html',
            controller : 'mapCtrl'
        })
        .when('/fish_registration', {
            templateUrl : 'pages/fish_registration.html',
            controller : 'registrationCtrl'
        })
        .when('/charts', {
            templateUrl : 'pages/charts.html',
            controller : 'chartCtrl'
        })
        .when('/blog', {
            templateUrl : 'pages/blog.html',
            controller : 'blogCtrl'
        });
});

myApp.factory('dataFactory', function ($resource) {

    var factory = {};

    factory.fish = $resource('/fishregistration-1.0-SNAPSHOT/api/fish/');

    factory.blogPosts = $resource('/fishregistration-1.0-SNAPSHOT/api/blog/');

    factory.user = $resource('/fishregistration-1.0-SNAPSHOT/api/users/');

    factory.userById = $resource('/fishregistration-1.0-SNAPSHOT/api/users/:userId');

    return factory;
})

myApp.service('dataService', function ($http) {

    this.addFish = function (weight, length, longitude, latitude, species, userId) {
        return $http.post('/fishregistration-1.0-SNAPSHOT/api/fish/' + weight +
            "/" + length + "/" + longitude + "/" + latitude + "/" + species +
            "/" + userId);
    }

    this.deleteFish = function (id) {
        return $http.delete('/fishregistration-1.0-SNAPSHOT/api/fish/' + id);
    }

    this.addBlogPost = function (userId, blogText) {
        return $http.post('/fishregistration-1.0-SNAPSHOT/api/blog/' + userId + "/" + blogText);
    }
});



myApp.controller('mapCtrl', function ($scope, dataFactory) {
    var fishes = dataFactory.fish.query(function() { $scope.fishes = fishes; });
});

myApp.controller('registrationCtrl', function ($scope, dataService, dataFactory) {

    var fishes = dataFactory.fish.query(function() { $scope.fishes = fishes; });

    //Get the current position. Will not change until the page is reloaded.
    navigator.geolocation.getCurrentPosition(function (position) {
        $scope.fishLatitude = position.coords.latitude;
        $scope.fishLongitude = position.coords.longitude;
    });

//        $scope.addUser = function () {
//            var user = new dataFactory.user;
//            user.firstName = $scope.firstName;
//            user.lastName = $scope.lastName;
//            user.$save().then( function () {
//                $scope.firstName = '';
//                $scope.lastName = '';
//            });
//        };

    $scope.addFish = function () {
    //Add a temp user
        var User = new dataFactory.userById;
            User.$get({userId:$scope.userId}, function (user) {
                var fish = new dataFactory.fish;
                fish.weight = $scope.fishWeight;
                fish.length = $scope.fishLength;
                fish.longitude = $scope.fishLongitude;
                fish.latitude = $scope.fishLatitude;
                fish.species = $scope.fishSpecies;
                fish.user = user;
                fish.$save().then( function () {
                    var fishes = dataFactory.fish.query(function() { $scope.fishes = fishes; });
                    $scope.fishWeight = '';
                    $scope.fishLength = '';
                    $scope.fishSpecies = '';
                    $scope.userId = '';
                });
            });
    };

    $scope.deleteFish = function (id) {
        dataService.deleteFish(id).then(function () {
            var fishes = dataFactory.fish.query(function() { $scope.fishes = fishes; });
        });
    };
});

myApp.controller('chartCtrl', function ($scope, dataFactory) {

     $scope.pielabels = ["Sea Trout", "Pike", "Redfin", "Other"];
     $scope.barlabels = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
                         "Aug", "Sep", "Oct", "Nov", "Dec"];

     //Create array from 1 to 12 symbolizing months
     monthNr = new Array(12).join().split(',')
                                   .map(function(item, index){
                                            return ++index;});

     var fishes = dataFactory.fish.query(function() {

         $scope.fishes = fishes;

         //Filter fish by species and return the number of each species in piedata
         $scope.piedata = $scope.pielabels.map(function (species) {
             return ($scope.fishes.filter(function (fish) {
                 return fish.species == species})).length});

         //Filter fish by month and return number of fish each month
         $scope.bardata = monthNr.map(function (month) {
             return ($scope.fishes.filter(function (fish) {
                 return parseInt(fish.date.substring(5, 7)) == month})).length});
     })
});

myApp.controller('userRegistrationCtrl', function ($scope, dataFactory) {
    $scope.addUser = function () {
        var user = new dataFactory.user;
        user.firstName = $scope.firstName;
        user.lastName = $scope.lastName;
        user.$save().then( function () {
            $scope.firstName = '';
            $scope.lastName = '';
        });
    };
});

myApp.controller('blogCtrl', function ($scope, dataService, dataFactory) {

    var blogPosts = dataFactory.blogPosts.query(function() { $scope.blogposts = blogPosts; });

    $scope.addBlogPost = function () {
        dataService.addBlogPost($scope.userId, $scope.blogText).then(function () {
            var blogPosts = dataFactory.blogPosts.query(function() { $scope.blogposts = blogPosts; });
            $scope.userId = '';
            $scope.blogText = '';
        });
    };
});