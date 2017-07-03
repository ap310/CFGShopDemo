/**
 * Created by Sean Wang on 2015/12/3.
 */
angular.module('redirect',['account.service','wechat','agreement'])
  .controller('redirectCtrl',['$stateParams', '$state', '$ionicHistory', 'agreementConstants', 'CommonAuthenticationService', 'Wechat',function($stateParams, $state, $ionicHistory, agreementConstants, CommonAuthenticationService, Wechat){

    var isQQBrowser = !!Wechat.isQQBrowser();
    //init and redirect
    if($stateParams.providerUserId !== undefined&&$stateParams.providerUserId !== 'null'){
      Wechat.setOpenId($stateParams.providerUserId);
    }
    if($stateParams.xAuthToken != undefined){
      CommonAuthenticationService.setXAuthToken($stateParams.xAuthToken);
      CommonAuthenticationService.setMobile($stateParams.mobile);
    }
    $ionicHistory.nextViewOptions({
      historyRoot: true
    });
    if($stateParams.state === 'agreements'){
      $state.go('app.agreement.list',{toPropose:'0',paygo:'0',user:'1'});
    }else if($stateParams.state === 'agreementsToPropose'){
      $state.go('app.agreement.list',{toPropose:'1',paygo:'0',user:'1'});
    }else if($stateParams.state === 'coupons') {
      if(isQQBrowser === true){
        $state.go('app.coupon.listQQx5');
      } else {
        $state.go('app.coupon.list');
      }
    }else if($stateParams.state === 'account') {
      $state.go('app.account.show');
    }else if($stateParams.state === 'transactions') {
      if(isQQBrowser === true){
        $state.go('app.transaction.listQQx5');
      } else {
        $state.go('app.transaction.list');
      }
    }else if($stateParams.state === 'simulate') {
      $state.go('app.proposal.start',{agreementId:agreementConstants.AGREEMENT_ID_SIMULATE});
    }else if($stateParams.state === 'dxk'){
      $state.go('app.account.salesOrComm');
    } else {
      $state.go('app.portal.category');
    }
  }]);