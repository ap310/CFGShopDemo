/**
 * Created by Administrator on 2017/7/2.
 */
angular.module('app',[])
  .controller('AppCtrl', ['$scope', '$ionicModal', '$rootScope', '$state', '$ionicHistory', '$ionicLoading',
      , '$timeout', '$ionicPopup',
    function($scope, $ionicModal, $rootScope, $state, $ionicHistory,$ionicLoading, $timeout) {

      //rootScope helpers
      /**
       * 页面跳转
       */
      $rootScope.navToRoot = function(statename,config){
        $ionicHistory.nextViewOptions({
          historyRoot: true
        });
        $state.go(statename,config);
      };
      /**
       * 回到首页
       */
      $rootScope.navToIndex = function(){
        $rootScope.navToRoot('app.shop');
      };

      //403 已登录用户访问未授权资源
      $scope.$on('event:auth-forbidden',function(){
        console.log("Auth forbidden");
        $ionicLoading.hide();
        $scope.modal.show();
      });

      //Debug 页面跳转
      $rootScope.$on('$stateChangeStart',
        function(event, toState, toParams, fromState, fromParams){
          console.log(fromState.name+" to "+toState.name);
        });
    }]);