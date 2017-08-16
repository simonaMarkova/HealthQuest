/**
 * Created by Simona on 30.03.2017.
 */
app.controller('questionConnectingController', QuestionConnectingControllerFn);

QuestionConnectingControllerFn.$inject = ['$filter','questionService', 'diseaseService', 'questionConnectingService', 'levelService'];

function QuestionConnectingControllerFn($filter,questionService, diseaseService, questionConnectingService, levelService) {
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
    vm.connectingEntities = [];
    vm.phraseOne = [];
    vm.phraseTwo = [];
    vm.connectingEntity = {};
    vm.levels = [];

    vm.page = 0;

    loadQuestions();
    loadDiseases();
    loadLevels();
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

    function loadQuestions() {
        questionService.getByPage("CONNECTING_PHRASES",0).then(function (data) {
            vm.page =0;
            vm.connectingEntities = [];
            vm.entities = data.content;

            for(var $i in vm.entities)
            {
                questionConnectingService.getByQuestionId(vm.entities[$i].id).then(function (data)
                {
                    for(var $j in data)
                    {
                        vm.connectingEntities.push(data[$j]);
                    }
                });
            }
        });
    }

    function loadDiseases() {
        diseaseService.getAll().then(function (data) {
            vm.possibleDiseases = data;
        })
    }


    function loadLevels() {
        levelService.getAll().then(function (data) {
            vm.levels = data;
        })
    }

    function save() {
        if(vm.entity.questionType == undefined)
        {
            vm.entity.questionType = "CONNECTING_PHRASES";
        }
        if(vm.entity.id != undefined)
        {
            for(var $i in vm.connectingEntities)
            {
                if(vm.connectingEntities[$i].question.id == vm.entity.id)
                {
                    questionConnectingService.remove(vm.connectingEntities[$i]);
                }
            }
        }
        if(vm.entity.questionType == "CONNECTING_PHRASES")
        {
            var promise = questionService.save(vm.entity);
            promise.then(successCallback, errorCallback);
            function successCallback(object) {

                if((vm.phraseOne[0] != '' && vm.phraseTwo[0] != '') && (vm.phraseOne[0] != null && vm.phraseTwo[0] != null))
                {
                    vm.connectingEntity = {};
                    vm.connectingEntity.phraseOne = vm.phraseOne[0];
                    vm.connectingEntity.phraseTwo = vm.phraseTwo[0];
                    vm.connectingEntity.question = object.data;

                    questionConnectingService.save(vm.connectingEntity);
                }
                if((vm.phraseOne[1] != '' && vm.phraseTwo[1] != '') && (vm.phraseOne[1] != null && vm.phraseTwo[1] != null))
                {
                    vm.connectingEntity = {};
                    vm.connectingEntity.phraseOne = vm.phraseOne[1];
                    vm.connectingEntity.phraseTwo = vm.phraseTwo[1];
                    vm.connectingEntity.question = object.data;

                    questionConnectingService.save(vm.connectingEntity);
                }
                if((vm.phraseOne[2] != '' && vm.phraseTwo[2] != '') && (vm.phraseOne[2] != null && vm.phraseTwo[2] != null))
                {
                    vm.connectingEntity = {};
                    vm.connectingEntity.phraseOne = vm.phraseOne[2];
                    vm.connectingEntity.phraseTwo = vm.phraseTwo[2];
                    vm.connectingEntity.question = object.data;

                    questionConnectingService.save(vm.connectingEntity);
                }
                if((vm.phraseOne[3] != '' && vm.phraseTwo[3] != '') && (vm.phraseOne[3] != null && vm.phraseTwo[3] != null))
                {
                    vm.connectingEntity = {};
                    vm.connectingEntity.phraseOne = vm.phraseOne[3];
                    vm.connectingEntity.phraseTwo = vm.phraseTwo[3];
                    vm.connectingEntity.question = object.data;

                    questionConnectingService.save(vm.connectingEntity);
                }
                clear();
                loadQuestions();
            }
            function errorCallback(data) {
            }

        }
    }

    function clear() {
        vm.entity = {};
        vm.phraseOne = [];
        vm.phraseTwo = [];
    }

    function edit(entity) {
        vm.entity = {};
        angular.extend(vm.entity, entity);
        var j = 0;
        for(var $i in vm.connectingEntities)
        {
            if(vm.connectingEntities[$i].question.id == entity.id)
            {
                vm.phraseOne[j] = vm.connectingEntities[$i].phraseOne;
                vm.phraseTwo[j] = vm.connectingEntities[$i].phraseTwo;
                j++;
            }
        }
    }

    function remove(entity) {
        questionService.remove(entity)
            .then(function () {
                loadQuestions();
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