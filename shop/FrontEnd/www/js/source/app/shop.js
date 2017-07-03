/**
 * Created by Administrator on 2017/7/2.
 */
angular.module('shop',['ionic','shop.service'])
/**
 * home page js
 */
  .controller('ShopCtrl', ['$scope', '$timeout', '$state', '$rootScope', '$ionicLoading','ShopAPI','$ionicHistory', function($scope, $timeout, $state, $rootScope,$ionicLoading,ShopAPI,$ionicHistory) {
    $scope.shakeClass="animated infinite shake";

      $scope.orderItems=[];
    $scope.navtoroot = function(statename,stateParams){
      $ionicHistory.nextViewOptions({
        historyRoot: true
      });
      $state.go(statename,stateParams);
    };

    var init = function(){
      $scope.orderItems=[];
      $ionicLoading.show({
        template: '<ion-spinner></ion-spinner>',
        delay: 100
      });

        ShopAPI.findAll(
            function(suc){
                $scope.ProductInfos = suc.ProductInfos;
                var txt = JSON.stringify(suc.ProductInfos);
                //alert(txt);
                for(var i=0; i < $scope.ProductInfos.length; i++){
                    var id=$scope.ProductInfos[i].productNo ;
                    var item={};
                    item.productNo=id;
                    //item.purchasedNumber=0;

                    $scope.orderItems.push(item);
                    //  alert(id);
                }

                $ionicLoading.hide();
            },function(err){
                $timeout(function(){$scope.shakeClass=""}, 3000);
                alert("Server not responsed now,please try later");
            });

    };

    $scope.order = function(){
       var amount=0;
      for(var i=0; i < $scope.orderItems.length; i++){
        var item=$scope.orderItems[i];
        if(isNaN(item.purchasedNumber)||item.purchasedNumber=="")
        {
          $scope.errorMsg="Please input correct number ";
          alert( $scope.errorMsg);
          return ;
        }
        amount+=item.purchasedNumber;
      }
      if(amount<=0)
      {
        $scope.errorMsg="Please buy something :}";
        alert( $scope.errorMsg);
        return ;
      }
      ShopAPI.order($scope.orderItems,
        function(suc){
          $scope.ProductInfos = suc.ProductInfos;
          $scope.SummaryInfo = suc.SummaryInfo;
          $scope.orderItems= suc.SummaryInfo;
          $scope.OrderNo= suc.OrderNo;
          var orderNo=$scope.OrderNo;
          var error=suc.error;
          if(error)
          {
            alert(error);
          }
          else
          {
            $scope.navtoroot('summary',({orderNo: orderNo,data:$scope.SummaryInfo }));
          }

        },function(err){
          $timeout(function(){$scope.shakeClass=""}, 3000);
          alert("Server not responsed now,please try later");
        });


    };
    var initSummany = function(orderNo){
      $ionicLoading.show({
        template: '<ion-spinner></ion-spinner>',
        delay: 100
      });
      ShopAPI.queryOrder(orderNo,
        function(suc){
          $scope.SummaryInfo = suc.orderItems;
          $ionicLoading.hide();
        },function(err){
          $timeout(function(){$scope.shakeClass=""}, 3000);
          alert("Server not responsed now,please try later");
        });

    };
    $scope.home = function(){
      $scope.navtoroot('app',null);
    };
    //init
    var orderNo=$state.params.orderNo;
    if(orderNo)
    {
      initSummany(orderNo);
    }
    else {
      init();
    }

    $rootScope.$on('$stateChangeStart',
      function(event, toState, toParams, fromState, fromParams){
        if(toState.name  === "summary"){

        }
        if(toState.name  === "app"){
          init();
        }
      });

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

    var QueryOrder = $resource(urlConfig.REST_END_POINT+'/queryOrder/:orderNo',{orderNo:'@orderNo'},
      {
        'query': {
          method:'GET',
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
      queryOrder: function(orderNo,success,error){
        QueryOrder.query({orderNo:orderNo},
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