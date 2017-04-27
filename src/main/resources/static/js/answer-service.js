/**
 * Created by Simona on 08.03.2017.
 */
app.factory('answerService', AnswerServiceFn);

AnswerServiceFn.$inject = ['$http', '$q'];

/* @ngInject */
function AnswerServiceFn($http, $q) {

    var URL = 'http://localhost:7778/answer/';

    var service = {
        save: saveFn,
        update: updateFn,
        getById: getByIdFn,
        getAll: getAllFn,
        remove: removeFn
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

}