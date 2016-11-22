var myApp = angular.module('myApp', ['ngRoute', 'ngMap', 'chart.js']);

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

myApp.service('dataService', function ($http) {

    this.getAllFish = function () {
        return $http.get('/fishregistration-1.0-SNAPSHOT/api/fish/');
    }

    this.addFish = function (weight, length, longitude, latitude, species, userId) {
        return $http.post('/fishregistration-1.0-SNAPSHOT/api/fish/' + weight +
            "/" + length + "/" + longitude + "/" + latitude + "/" + species +
            "/" + userId);
    }

    this.deleteFish = function (id) {
        return $http.delete('/fishregistration-1.0-SNAPSHOT/api/fish/' + id);
    }

    this.addUser = function (firstName, lastName) {
        return $http.post('/fishregistration-1.0-SNAPSHOT/api/users/' +
            firstName + "/" + lastName);
    }

    this.getAllBlogPosts = function () {
        return $http.get('/fishregistration-1.0-SNAPSHOT/api/blog/');
    }

    this.addBlogPost = function (userId, blogText) {
        return $http.post('/fishregistration-1.0-SNAPSHOT/api/blog/' + userId + "/" + blogText);
    }
});

myApp.controller('mapCtrl', function ($scope, dataService) {
    dataService.getAllFish().then(function (dataResponse) {
        $scope.fishes = dataResponse.data;
    });
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
            $scope.fishSpecies, $scope.userId).then(function () {
            $scope.fishes = $scope.getAllFish();
            $scope.fishWeight = '';
            $scope.fishLength = '';
            $scope.fishSpecies = '';
            $scope.userId = '';
        });
    };

    $scope.deleteFish = function (id) {
        dataService.deleteFish(id).then(function () {
            $scope.fishes = $scope.getAllFish();
        });
    };
});

myApp.controller('chartCtrl', function ($scope, dataService) {

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

myApp.controller('userRegistrationCtrl', function ($scope, dataService) {
    $scope.addUser = function () {
        dataService.addUser($scope.firstName, $scope.lastName).then(function () {
            $scope.firstName = '';
            $scope.lastName = '';
        });
    };
});

myApp.controller('blogCtrl', function ($scope, dataService) {

    dataService.getAllBlogPosts().then(function (dataResponse) {
        $scope.blogposts = dataResponse.data;
    });

    $scope.getAllBlogPosts = function () {
        dataService.getAllBlogPosts().then(function (dataResponse) {
            $scope.blogposts = dataResponse.data;
        });
    };

    $scope.addBlogPost = function () {
        dataService.addBlogPost($scope.userId, $scope.blogText).then(function () {
            $scope.blogposts = $scope.getAllBlogPosts();
            $scope.userId = '';
            $scope.blogText = '';
        });
    };
});