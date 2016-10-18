'use strict';

/**
 * Config for the router
 */
define([
    "angularAMD",
    "./file-upload"
],function(angularAMD, fileUpload){
    angular.module('basic.module',[
    ]).config(['$stateProvider', '$urlRouterProvider',
        function ($stateProvider,   $urlRouterProvider) {

            var states = [];

            states.push({
                title: "文件上传",
                //sticky: false,
                name:"app.fileupload",
                url: '/fileupload',
                templateUrl: 'modules/basic/form_fileupload.html',
                //controllerUrl: "modules/basic/file-upload.js",
                controller:fileUpload,
                resolve: {
                    deps: ['$ocLazyLoad',
                        function( $ocLazyLoad){
                            return $ocLazyLoad.load('angularFileUpload');
                            //return $ocLazyLoad.load('angularFileUpload').then(
                            //    function(){
                            //
                            //    }
                            //);
                        }]
                },
                sticky: true
            });
            states.push({
                title: "帐号",
                //sticky: true,
                name:"app.profile",
                url: '/account/profile',
                templateUrl: 'modules/basic/account_profile.html'
                //controllerUrl: "modules/basic/file-upload.js",
            });

            angular.forEach(states, function(state){
                $stateProvider.state(state.name, angularAMD.route(state));
            });
        }
    ]);
});