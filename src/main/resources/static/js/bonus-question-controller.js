/**
 * Created by Simona on 04.03.2017.
 */
app.controller('bonusQuestionController', BonusQuestionControllerFn);

BonusQuestionControllerFn.$inject = ['$filter','questionService', 'answerService', 'questionAnswerService'];

function BonusQuestionControllerFn($filter, questionService, answerService, questionAnswerService) {
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

    vm.proba = [];
    vm.question = {};
    vm.questions = [];
    vm.possibleAnswers = [];
    vm.answers = [];
    vm.answerStatus = [];
    vm.questionAnswer = {};
    vm.myAnswers = [];


    loadQuestions();
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

    function loadQuestions() {
        questionService.getByPage("BONUS",0).then(function (data) {
            vm.page =0;
            vm.myAnswers = [];
            vm.questions = data.content;

            for(var $i in vm.questions)
            {
                questionAnswerService.getByQuestionId(vm.questions[$i].id).then(function (data)
                {
                    for(var $j in data)
                    {
                        vm.myAnswers.push(data[$j]);
                    }
                });
            }
        });
    }


    function loadAnswers() {
        answerService.getAll().then(function (data) {
            vm.possibleAnswers = data;
        });
    }


    function save() {
        if(vm.question.questionType == undefined)
        {
            vm.question.questionType = "BONUS";
        }
        if(vm.question.id != undefined)
        {
            for(var i in vm.myAnswers)
            {
                if(vm.myAnswers[i].question.id == vm.question.id)
                {
                    questionAnswerService.remove(vm.myAnswers[i]);
                }
            }
        }
        if(vm.question.questionType=="BONUS")
        {
            var promise = questionService.save(vm.question);
            promise.then(successCallback, errorCallback);
            function successCallback(object) {
                for(var i in vm.answers)
                {
                    if(vm.answers[i]==true)
                    {
                        vm.questionAnswer = {};
                        vm.questionAnswer.question = object.data;
                        vm.questionAnswer.answer = vm.possibleAnswers[i];
                        if(vm.answerStatus[i])
                        {
                            vm.questionAnswer.status = true;
                        }
                        questionAnswerService.save(vm.questionAnswer);
                    }
                }
                clear();

                loadQuestions();

            }
            function errorCallback(data) {
            }

        }

    }

    function clear() {
        vm.question = {};
        vm.answers = [];
        vm.answerStatus = [];
    }

    function edit(entity) {
        vm.question = {};
        angular.extend(vm.question, entity);
        for(var i in vm.myAnswers)
        {
            if(vm.myAnswers[i].question.id == entity.id)
            {
                for(var j in vm.possibleAnswers)
                {
                    if(vm.possibleAnswers[j].id == vm.myAnswers[i].answer.id)
                    {
                        vm.answers[j] = true;
                        if(vm.myAnswers[i].status == true)
                        {
                            vm.answerStatus[j] = true;
                        }
                    }
                }
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
    return $filter('filter')(vm.questions);
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