/**
 * Created by Simona on 14.03.2017.
 */
app.controller('answerController', AnswerControllerFn);

AnswerControllerFn.$inject = ['$filter','answerService'];

function AnswerControllerFn($filter,answerService) {
    var vm = this;

    vm.save = save;
    vm.clear = clear;
    vm.edit = edit;
    vm.remove = remove;
    vm.getData = getData;
    vm.numberOfPages = numberOfPages;
    vm.loadPages = loadPages;
    vm.currentPage = 0;
    vm.pageSize = 5;
    vm.page = 0;

    vm.entity = {};
    vm.entities = [];

    loadAnswers();
    numberOfPages();
    getData();
    loadPages();


    function loadPages()
    {
        var edge = vm.numberOfPages();
        var step = 1;
        var start = 0;

        // Create the array of numbers, stopping befor the edge.
        for (var ret = []; (edge - start) * step > 0; start += step) {
            ret.push(start);
        }
        return ret;
    }

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
    function getData() {
        return $filter('filter')(vm.entities);
    }
    function numberOfPages(){
        return  Math.ceil(vm.getData().length/vm.pageSize);
    }

}
app.filter('startFrom', function() {
    return function(input, start) {
        start = +start; //parse to int
        return input.slice(start);
    }
});