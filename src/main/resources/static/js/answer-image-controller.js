/**
 * Created by user on 20.4.2017.
 */

app.controller('answerImageController', AnswerImageControllerFn);

AnswerImageControllerFn.$inject = ['answerImageService','questionService',   'diseaseService', 'levelService', '$http'];

function AnswerImageControllerFn(answerImageService, questionService,  diseaseService, levelService, $http) {
    var vm = this;

    vm.save = save;
    vm.clear = clear;
    vm.edit = edit;
    vm.remove = remove;

    vm.question = {};
    vm.questions = [];
    vm.imageStatus = [];
    vm.myAnswers = [];
    vm.levels = [];
    //vm.allImages = [];
    var file;
    var file1;
    var file2;
    var file3;

    loadQuestions();
    loadDiseases();
    loadMyAnswers();
    loadLevels();

    function loadQuestions() {
        questionService.getByQuestionType("MULTIPLE_IMAGE_SELECT").then(function (data) {
            vm.questions = data;
        });
    }

    function loadMyAnswers(){
        answerImageService.getAll().then(function (data) {
            vm.myAnswers = data;
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
        file  = document.getElementById('file').files[0];
        file1  = document.getElementById('file1').files[0];
        file2  = document.getElementById('file2').files[0];
        file3  = document.getElementById('file3').files[0];
        if(vm.question.questionType == undefined)
        {
            vm.question.questionType = "MULTIPLE_IMAGE_SELECT";
        }
        if(vm.question.id != undefined && file != undefined && file1 != undefined && file2 != undefined && file3 != undefined)
        {
            for(var i in vm.myAnswers)
            {
                if(vm.myAnswers[i].question.id == vm.question.id)
                {
                    answerImageService.remove(vm.myAnswers[i]);
                }
            }
        }
        if(vm.question.questionType=="MULTIPLE_IMAGE_SELECT")
        {
            var promise = questionService.save(vm.question);
            promise.then(successCallback, errorCallback);
            function successCallback(object) {

                if(file != undefined)
                {
                    var formData=new FormData();
                    formData.append("file",file);
                    formData.append("question", object.data.id);
                    formData.append("number", 0);
                    if (vm.imageStatus[0] == true)
                    {
                        formData.append("status", true);
                    }
                    $http({
                        method: 'POST',
                        url:  'http://localhost:7778/answerImage/',
                        headers: { 'Content-Type': undefined},
                        data:  formData
                    }).success(function(data, status) {
                        //alert("Success ... " + status);
                    }).error(function(data, status) {
                        // alert("Error ... " + status);
                    });
                }


                if(file1 != undefined)
                {
                    var formData=new FormData();
                    formData.append("file",file1);
                    formData.append("question", object.data.id);
                    formData.append("number", 1);
                    if (vm.imageStatus[1] == true)
                    {
                        formData.append("status", true);
                    }
                    $http({
                        method: 'POST',
                        url:  'http://localhost:7778/answerImage/',
                        headers: { 'Content-Type': undefined},
                        data:  formData
                    }).success(function(data, status) {
                        //alert("Success ... " + status);
                    }).error(function(data, status) {
                        // alert("Error ... " + status);
                    });
                }

                file2  = document.getElementById('file2').files[0];
                if(file2 != undefined)
                {
                    var formData=new FormData();
                    formData.append("file",file2);
                    formData.append("question", object.data.id);
                    formData.append("number", 2);
                    if (vm.imageStatus[2] == true)
                    {
                        formData.append("status", true);
                    }
                    $http({
                        method: 'POST',
                        url:  'http://localhost:7778/answerImage/',
                        headers: { 'Content-Type': undefined},
                        data:  formData
                    }).success(function(data, status) {
                        //alert("Success ... " + status);
                    }).error(function(data, status) {
                        // alert("Error ... " + status);
                    });
                }


                if(file3 != undefined)
                {
                    var formData=new FormData();
                    formData.append("file",file3);
                    formData.append("question", object.data.id);
                    formData.append("number", 3);
                    if (vm.imageStatus[3] == true)
                    {
                        formData.append("status", true);
                    }
                    $http({
                        method: 'POST',
                        url:  'http://localhost:7778/answerImage/',
                        headers: { 'Content-Type': undefined},
                        data:  formData
                    }).success(function(data, status) {
                        //alert("Success ... " + status);
                    }).error(function(data, status) {
                        // alert("Error ... " + status);
                    });
                }
                clear();
                loadMyAnswers();
                loadQuestions();
            }
            function errorCallback(data) {
            }

        }

    }

    function clear() {
        vm.question = {};
        vm.imageStatus = [];
        vm.images = [];
    }

    function edit(entity) {
        vm.question = {};
        angular.extend(vm.question, entity);
    }

    function remove(entity) {
        questionService.remove(entity)
            .then(function () {
                loadMyAnswers();
                loadQuestions();
            });
    }



}
