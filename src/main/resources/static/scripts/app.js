var amticaApp = angular.module('amticaApp',['ngRoute','ngCookies']);
amticaApp.config(function($routeProvider, $locationProvider) {
    $routeProvider
    .when("/signup", {
        templateUrl : "/amtica/views/signup.html",
        controller : "registrationController"
    })
    .when("/home", {
        templateUrl : "/amtica/views/dashboard.html",
        controller : "dashboardController"
    })
    .when("/login", {
        templateUrl : "/amtica/views/login.html",
        controller : "loginController"
    })
    .when("/error", {
        templateUrl : "/amtica/views/error.html",
        controller : "errorController"
    });
});
amticaApp.service('cache', function() {
	var data = {};
    this.put = function (x, isError) {
    	data = angular.copy(x);
        data.isError = {};
        data.isError = isError;
    }
    this.get = function() {
    	return data;
    }
});
amticaApp.run(function($rootScope, $cookies, $window) {
    var amticaCookie = $cookies.getObject('amtica-app');
    if(amticaCookie) {
    	$window.location.href = '#!home';
    } else {
    	$rootScope.userName = '';
        $rootScope.registrationActive = true;
        $rootScope.loginActive = true;
    }
})