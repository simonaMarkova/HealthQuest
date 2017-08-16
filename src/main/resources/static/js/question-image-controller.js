
app.controller('questionImageController', QuestionImageControllerFn);

QuestionImageControllerFn.$inject = ['$filter','questionImageService','questionService', 'answerService', 'questionAnswerService', 'diseaseService', 'levelService', '$http'];

function QuestionImageControllerFn($filter,questionImageService, questionService, answerService, questionAnswerService, diseaseService, levelService, $http) {
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

    vm.images = [];
    vm.question = {};
    vm.questions = [];
    vm.possibleAnswers = [];
    vm.answers = [];
    vm.answerStatus = [];
    vm.questionAnswer = {};
    vm.myAnswers = [];
    vm.levels = [];

    vm.page=0;

    loadQuestions();
    loadAnswers();
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
        questionService.getByPage("IMAGE_SELECT",0).then(function (data) {
            vm.page =0;
            vm.myAnswers = [];
            vm.images = [];
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

    function loadDiseases() {
        diseaseService.getAll().then(function (data) {
            vm.possibleDiseases = data;
        });
    }

    function loadLevels() {
        levelService.getAll().then(function (data) {
            vm.levels = data;
        })
    }

    function save() {
        if(vm.question.questionType == undefined)
        {
            vm.question.questionType = "IMAGE_SELECT";
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
        if(vm.question.questionType=="IMAGE_SELECT")
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

                var  file  = document.getElementById('file').files[0];
                if(file != undefined)
                {
                    var formData=new FormData();
                    formData.append("file",file);
                    formData.append("question", object.data.id);
                    $http({
                        method: 'POST',
                        url:  '/questionImage/',
                        headers: { 'Content-Type': undefined},
                        data:  formData
                    }).success(function(data, status) {
                        //alert("Success ... " + status);
                    }).error(function(data, status) {
                        // alert("Error ... " + status);
                    });
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