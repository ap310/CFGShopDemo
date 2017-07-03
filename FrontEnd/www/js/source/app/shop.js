/**
 * Created by Administrator on 2017/7/2.
 */
angular.module('shop',['ionic','shop.service'])
/**
 * 入口主页
 */
  .controller('ShopCtrl', ['$scope', '$timeout', '$state', '$rootScope', '$ionicLoading','ShopAPI', function($scope, $timeout, $state, $rootScope,$ionicLoading,ShopAPI) {
    $scope.shakeClass="animated infinite shake";

      $scope.orderItems=[];
    var init = function(){
      $ionicLoading.show({
        template: '<ion-spinner></ion-spinner>',
        delay: 100
      });


        ShopAPI.findAll(
            function(suc){

                $scope.ProductInfos = suc.ProductInfos;
               // var txt = JSON.stringify(suc.ProductInfos);
              //  alert(txt);
                for(var i=0; i < $scope.ProductInfos.length; i++){
                    var id=$scope.ProductInfos[i].productId ;
                    var item={};
                    item.productId=id;
                    item.purchasedNumber=0;
                    $scope.orderItems.push(item);
                    //  alert(id);
                }

                $ionicLoading.hide();
            },function(err){
                $timeout(function(){$scope.shakeClass=""}, 3000);
                alert("服务器未响应，获取品类信息失败，请联系管理员说稍后再试");
            });

    };
    //init
    init();
      $scope.order = function(){

          ShopAPI.order($scope.orderItems,
              function(suc){

              },function(err){
                  $timeout(function(){$scope.shakeClass=""}, 3000);
                  alert("服务器未响应，获取品类信息失败，请联系管理员说稍后再试");
              });


      };
  }])

angular.module('shop.service',['ngResource'])
  .service('ShopAPI', ['$resource', 'urlConfig',function($resource, urlConfig){
      var Order = $resource(urlConfig.REST_END_POINT+'/order',null,
          {
              'save': {
                  method:'POST',
                  isArray:false,
                  timeout: 60000
              }
          },null
      );
    var products = $resource(urlConfig.REST_END_POINT+'/show',null,
      {
        'query': {
          method:'GET',
          isArray:false,
          timeout: 10000
        }
      },null
    );
    return {
        order: function(orderItems,success,error){
            Order.save(orderItems,
                function(suc) {
                    success(suc);
                },function(err){
                    error(err);
                });
        },
      findAll: function(success, error){
            products.query(function(suc){
            success(suc);
          },error);

      }
    }
  }]);