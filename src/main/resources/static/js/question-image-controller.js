
app.controller('questionImageController', QuestionImageControllerFn);

QuestionImageControllerFn.$inject = ['questionImageService','questionService', 'answerService', 'questionAnswerService', 'diseaseService', 'levelService', '$http'];

function QuestionImageControllerFn(questionImageService, questionService, answerService, questionAnswerService, diseaseService, levelService, $http) {
    var vm = this;

    vm.save = save;
    vm.clear = clear;
    vm.edit = edit;
    vm.remove = remove;


    vm.images = [];
    vm.question = {};
    vm.questions = [];
    vm.possibleAnswers = [];
    vm.answers = [];
    vm.answerStatus = [];
    vm.questionAnswer = {};
    vm.myAnswers = [];
    vm.levels = [];

    loadQuestions();
    loadAnswers();
    loadDiseases();
    loadMyAnswers();
    loadLevels();
    loadImages();

    function loadImages() {
        questionImageService.getAll().then(function (data) {
           vm.images = data;
        });
    }
    function loadQuestions() {
        questionService.getByQuestionType("IMAGE_SELECT").then(function (data) {
            vm.questions = data;
        });
    }

    function loadMyAnswers(){
        questionAnswerService.getAll().then(function (data) {
            vm.myAnswers = data;
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
                        url:  'http://localhost:7778/questionImage/',
                        headers: { 'Content-Type': undefined},
                        data:  formData
                    }).success(function(data, status) {
                        //alert("Success ... " + status);
                    }).error(function(data, status) {
                        // alert("Error ... " + status);
                    });
                }

                clear();
                loadImages();
                loadMyAnswers();
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
                loadMyAnswers();
                loadQuestions();
                loadImages();
            });
    }



}
