/**
 * Created by Simona on 04.03.2017.
 */
app.factory('questionService', QuestionServiceFn);

QuestionServiceFn.$inject = ['$http', '$q'];

/* @ngInject */
function QuestionServiceFn($http, $q) {

    var URL = '/question/';

    var service = {
        save: saveFn,
        update: updateFn,
        getById: getByIdFn,
        getAll: getAllFn,
        remove: removeFn,
        getByQuestionType: getByQuestionTypeFn,
        getByPage: getByPageFn
    };
    return service;

    function saveFn(entity) {
        var deferred = $q.defer();
        $http.post(URL, entity)
            .then(
                function (response) {
                    deferred.resolve(response);
                },
                function(errResponse){
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function getByQuestionTypeFn(type) {
        var deferred = $q.defer();
        $http.get(URL+"type/" +type)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function updateFn(entity) {
        var deferred = $q.defer();
        $http.put(URL+entity.id, entity)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function getByIdFn(entityId) {
        var deferred = $q.defer();
        $http.get(URL + entityId)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function getAllFn() {
        var deferred = $q.defer();
        $http.get(URL)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function removeFn(entity) {
        var deferred = $q.defer();
        $http.delete(URL+entity.id)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function getByPageFn(type, page) {
        var deferred = $q.defer();
        $http.get(URL+'page/'+type +'/'+page)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

}