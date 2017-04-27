/**
 * Created by Simona on 15.03.2017.
 */
app.controller('diseaseController', DiseaseControllerFn);

DiseaseControllerFn.$inject = ['diseaseService'];

function DiseaseControllerFn(diseaseService) {
    var vm = this;

    vm.save = save;
    vm.clear = clear;
    vm.edit = edit;
    vm.remove = remove;

    vm.entity = {};
    vm.entities = [];

    loadDiseases();

    function loadDiseases() {
        diseaseService.getAll().then(function (data) {
            vm.entities = data;
        });
    }

    function save() {
        var promise = diseaseService.save(vm.entity);
        promise.then(successCallback, errorCallback);
        function successCallback(data) {
            clear();
            loadDiseases();
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
        diseaseService
            .remove(entity)
            .then(function () {
                loadDiseases();
            });
    }

}
