/**
 * Created by Simona on 04.03.2017.
 */
var app = angular.module('app', ['ngRoute','ngResource']);
app.config(function($routeProvider){
    $routeProvider
        .when('/questionAnswer',{
            templateUrl: '/views/question.answer.html',
            controller: 'questionController',
            controllerAs: 'vm'
        })
        .when('/answer',{
            templateUrl: '/views/answer.html',
            controller: 'answerController',
            controllerAs: 'vm1'
        })
        .when('/disease',{
            templateUrl: '/views/disease.html',
            controller: 'diseaseController',
            controllerAs: 'vm3'
        })
        .when('/questionConnecting',{
            templateUrl: '/views/question.connecting.html',
            controller: 'questionConnectingController',
            controllerAs: 'vm4'
        })
        .when('/level',{
            templateUrl:'/views/level.html',
            controller:'levelController',
            controllerAs:'vm5'
        })
        .when('/questionImage',{
            templateUrl:'/views/question.image.html',
            controller:'questionImageController',
            controllerAs:'vm6'
        })
        .when('/answerImage', {
            templateUrl:'/views/answer.image.html',
            controller:'answerImageController',
            controllerAs:'vm7'
        })
        .otherwise({
            redirectTo: '/'
        });
});

