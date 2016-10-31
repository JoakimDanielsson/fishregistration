/**
 * Created by joakim on 2016-10-18.
 */

var myApp = angular.module('myApp', []);

myApp.service('dataService', function ($http) {

    this.getUsers = function () {
        return $http.get('/fishregistration-1.0-SNAPSHOT/api/users/');
    }

    this.addUser = function (name) {
        return $http.post('/fishregistration-1.0-SNAPSHOT/api/users/' + name);
    }

    this.deleteUser = function (id) {
        return $http.delete('/fishregistration-1.0-SNAPSHOT/api/users/' + id)
    }
});

myApp.controller('AngularJSCtrl', function ($scope, dataService) {
    $scope.users = [];

    $scope.getUsers = function () {
        dataService.getUsers().then(function (dataResponse) {
            $scope.users = dataResponse.data;
        });
    };

    $scope.addUser = function () {
        dataService.addUser($scope.userToAdd).then(function () {
            $scope.users = $scope.getUsers();
            $scope.userToAdd = '';
        });
    };

    $scope.deleteUser = function (id) {
        dataService.deleteUser(id).then(function () {
            $scope.users = $scope.getUsers();
        });
    };
});