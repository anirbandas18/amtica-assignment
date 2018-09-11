amticaApp.controller('indexController', function($scope, $http) {
   $scope.login = function() {
	   // redirect to login view
	  console.log('login clicked'); 
	  $scope.myWelcome = '';
	  $http({
	        method : "GET",
	        url : "/amtica/user/test"
	    }).then(function (response) {
	    	$scope.myWelcome = response.data;
	    }, function (response) {
	    	$scope.myWelcome = response.statusText;
	    });
   } ;
   $scope.register = function() {
	   // redirect to sign up view
		  console.log('registration clicked'); 
	   } ;
});
amticaApp.controller('loginController', function($scope) {
   $scope.login = function() {
	  console.log('login clicked'); 
   } ;
   $scope.register = function() {
		  console.log('registration clicked'); 
	   } ;
});