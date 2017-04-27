/**
 * Created by Simona on 14.03.2017.
 */
app.controller('answerController', AnswerControllerFn);

AnswerControllerFn.$inject = ['answerService'];

function AnswerControllerFn(answerService) {
    var vm = this;

    vm.save = save;
    vm.clear = clear;
    vm.edit = edit;
    vm.remove = remove;

    vm.entity = {};
    vm.entities = [];

    loadAnswers();

    function loadAnswers() {
        answerService.getAll().then(function (data) {
            vm.entities = data;
        });
    }

    function save() {
        var promise = answerService.save(vm.entity);
        promise.then(successCallback, errorCallback);
        function successCallback(data) {
            clear();
            loadAnswers();
        }
        function errorCallback(data) {
        }
    }

    function clear() {
        vm.entity = {};
    }

    function edit(entity) {
        vm.entity = {};
        angular.extend(vm.entity, entity);
    }

    function remove(entity) {
        answerService
            .remove(entity)
            .then(function () {
                loadAnswers();
            });
    }

}
