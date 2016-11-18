var myApp = angular.module('blogApp', []);

myApp.service('dataService', function ($http) {

    this.getAllBlogPosts = function () {
        return $http.get('/fishregistration-1.0-SNAPSHOT/api/blog/');
    }

    this.addBlogPost = function (userId, blogText) {
        return $http.post('/fishregistration-1.0-SNAPSHOT/api/blog/' + userId + "/" + blogText);
    }

//    this.deleteFish = function (blogId) {
//        return $http.delete('/fishregistration-1.0-SNAPSHOT/api/blog/' + blogId);
//    }
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
//
//    $scope.deleteFish = function (id) {
//        dataService.deleteFish(id).then(function () {
//            $scope.fishes = $scope.getAllFish();
//        });
//    };
});