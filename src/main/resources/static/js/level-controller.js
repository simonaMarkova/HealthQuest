/**
 * Created by user on 20.4.2017.
 */
app.controller('levelController', LevelControllerFn);

LevelControllerFn.$inject = ['$filter','levelService'];

function LevelControllerFn($filter,levelService) {
    var vm5 = this;

    vm5.save = save;
    vm5.clear = clear;
    vm5.edit = edit;
    vm5.remove = remove;
    vm5.getData = getData;
    vm5.numberOfPages = numberOfPages;
    vm5.loadPages = loadPages;
    vm5.currentPage = 0;
    vm5.pageSize = 5;
    vm5.page = 0;

    vm5.entity = {};
    vm5.entities = [];

    loadLevels();
    numberOfPages();
    getData();
    loadPages();

    function loadPages()
    {
       var edge = vm5.numberOfPages();
        var step = 1;
        var start = 0;

        // Create the array of numbers, stopping befor the edge.
        for (var ret = []; (edge - start) * step > 0; start += step) {
            ret.push(start);
        }
        return ret;
    }

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

    function getData() {
        return $filter('filter')(vm5.entities);
    }
    function numberOfPages(){
        return  Math.ceil(vm5.getData().length/vm5.pageSize);
    }

}
app.filter('startFrom', function() {
    return function(input, start) {
        start = +start; //parse to int
        return input.slice(start);
    }
});