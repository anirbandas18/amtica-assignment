amticaApp.controller('registrationController', function($scope, $http, $window,
		cache) {
	$scope.register = function() {
		// parse registration form
		console.log('registration clicked');
		if (angular.equals($scope.password, $scope.confirmPassword)) {
			$scope.userForm.phone.countryCode = $scope.countryCode;
			$scope.userForm.phone.number = $scope.phoneNumber;
			var start = $scope.emailId.indexOf('@');
			$scope.userForm.email.name = $scope.emailId.substring(0, start);
			$scope.userForm.email.domain = $scope.emailId.substring(start + 1);
			$scope.userForm.password = $window.btoa($scope.password);
			var req = {
				method : 'POST',
				url : '/amtica/user',
				data: $scope.userForm
			};
			$http(req).then(function(response) {
				// redirect to sign up view on success
				var successResponse = {};
				successResponse.code = 'created';
				successResponse.message = 'user with ' + response.data.field + ': ' + response.data.value + '. please login to proceed';
				cache.put(successResponse, false);
				$window.location.href = '#!error';
			}, function(response) {
				// redirect to error view on failure
				console.log('error encountered: ');
				cache.put(response.data, true);
				$window.location.href = '#!error';
			});
		} else {
			$scope.errorMsg = "passwords don't match";
			$scope.invalid = true;
		}
	};
	$scope.clear = function() {
		resetForm();
	};
	var resetForm = function() {
		$scope.userForm = {};
		$scope.userForm.phone = {};
		$scope.userForm.email = {};
		$scope.countryCode = '';
		$scope.phoneNumber = '';
		$scope.emailId = '';
		$scope.invalid = false;
		$scope.password = '';
		$scope.confirmPassword = '';
	}
	$scope.$on('$viewContentLoaded', resetForm);
});
amticaApp.controller('errorController', function($scope, cache, $rootScope) {
	$rootScope.registrationActive = true;
	$rootScope.loginActive = true;
	$rootScope.userName = '';
	$scope.data = cache.get();
});
amticaApp.controller('dashboardController', function($scope, $cookies,
		$rootScope, $window, cache, $http) {
	$scope.logout = function() {
		var amticaCookie = $cookies.getObject('amtica-app');
		console.log(amticaCookie);
		var req = {
			method : 'DELETE',
			url : '/amtica/session',
			headers : {
				'AMTICA-SESSION' : amticaCookie.sessionId,
				'AMTICA-ACCESS' : amticaCookie.accessId
			}
		};
		console.log('logout clicked');
		$http(req).then(function(response) {
			console.log('post logout: ');
			$cookies.remove('amtica-app');
			console.log('cookie purged on local: amtica-app');
			$rootScope.userName = '';
			console.log('redirect to login');
			$rootScope.registrationActive = true;
			$rootScope.loginActive = true;
			$window.location.href = '#!login';
		}, function(response) {
			console.log('error encountered: ' + response.statusText);
			cache.put(response.data, true);
			$window.location.href = '#!error';
		});
	}
	var validateCookie = function() {
		var amticaCookie = $cookies.getObject('amtica-app');
		console.log('got cookie: ');
		console.log(amticaCookie);
		if (amticaCookie) {
			var req = {
				method : 'GET',
				url : '/amtica/session',
				headers : {
					'AMTICA-SESSION' : amticaCookie.sessionId,
					'AMTICA-ACCESS' : amticaCookie.accessId
				}
			};
			console.log('validating session');
			$http(req).then(function(response) {
				console.log(response.data);
				var successResponse = response.data;
				if (successResponse.value) {
					console.log('session is valid: ');
					$rootScope.registrationActive = false;
					$rootScope.loginActive = false;
					$rootScope.userName = amticaCookie.userName;
				} else {
					console.log('session invalid: ');
					$cookies.remove('amtica-app');
					console.log('cookie purged on local: amtica-app');
					$window.location.href = '#!error';
				}
			}, function(response) {
				$cookies.remove('amtica-app');
				console.log('cookie purged on local: amtica-app');
				console.log('error encountered: ' + response.statusText);
				cache.put(response.data, true);
				$window.location.href = '#!error';
			});
		} else {
			$window.location.href = '#!login';
		}
	}
	$scope.$on('$viewContentLoaded', validateCookie);
});
amticaApp.controller('loginController', function($scope, $http, $cookies,
		$window, cache, $rootScope) {
	$scope.login = function() {
		var unmasked = $scope.userName + ":" + $scope.password;
		var masked = $window.btoa(unmasked);
		var req = {
			method : 'POST',
			url : '/amtica/session',
			headers : {
				'Authorization' : masked,
				'X-Forwarded-For' : '10.1.2.3'
			}
		};
		console.log('login clicked');
		$http(req).then(function(response) {
			console.log('post login: ');
			$cookies.putObject('amtica-app', response.data);
			console.log('cookie set, redirecting to dashboard');
			$window.location.href = '#!home';
		}, function(response) {
			console.log('error encountered: ' + response.statusText);
			console.log(response);
			cache.put(response.data, true);
			$window.location.href = '#!error';
		});
	};
	$scope.clear = function() {
		console.log('clear clicked');
		$scope.userName = '';
		$scope.password = '';
	};
});