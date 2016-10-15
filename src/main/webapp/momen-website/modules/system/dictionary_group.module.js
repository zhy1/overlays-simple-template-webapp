'use strict';

/**
 * @author  tanxinzheng
 * @date    2016-10-15 14:41:37
 * @version 1.0.0
 */
define([
    "angularAMD",
    "./dictionary_group.api",
    "./dictionary_group"
],function(angularAMD, DictionaryGroupRest, dictionaryGroup){
    angular.module('dictionary_group.module',[
        "DictionaryGroup.REST"
    ]).config(['$stateProvider', '$urlRouterProvider',
        function ($stateProvider,   $urlRouterProvider) {

            var states = [];

            states.push({
                title: "数据字典组",
                name: 'app.dictionaryGroup',
                url: '/dictionaryGroup',
                views: {
                    'dictionaryGroup': angularAMD.route({
                        controller: dictionaryGroup,
                        //controllerUrl: "system/dictionary_group.js",
                        templateUrl: 'modules/system/dictionary_group.html'
                    })
                },
                sticky: true
            });

            angular.forEach(states, function(state){
                $stateProvider.state(state.name, angularAMD.route(state));
            });
        }
    ]);
});