/**
 * Created by user on 20.4.2017.
 */
app.controller('levelController', LevelControllerFn);

LevelControllerFn.$inject = ['levelService'];

function LevelControllerFn(levelService) {
    var vm5 = this;

    vm5.save = save;
    vm5.clear = clear;
    vm5.edit = edit;
    vm5.remove = remove;

    vm5.entity = {};
    vm5.entities = [];

    loadLevels();

    function loadLevels() {
        levelService.getAll().then(function (data) {
            vm5.entities = data;
        });
    }

    function save() {
        var promise = levelService.save(vm5.entity);
        promise.then(successCallback, errorCallback);
        function successCallback(data) {
            clear();
            loadLevels();
        }
        function errorCallback(data) {
        }
    }

    function clear() {
        vm5.entity = {};
    }

    function edit(entity) {
        vm5.entity = {};
        angular.extend(vm5.entity, entity);
    }

    function remove(entity) {
        levelService
            .remove(entity)
            .then(function () {
                loadLevels();
            });
    }

}
