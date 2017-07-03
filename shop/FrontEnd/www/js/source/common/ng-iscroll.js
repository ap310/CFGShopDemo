/**
 * Created by cattom on 2015/11/27.
 */
angular.module('ng-iscroll',[])
  .service('iScrollAccessor', function() {
    var iScrollInternal;
    return {
      set: function(iScroll) {
        iScrollInternal = iScroll;
      },
      get: function() {
        return iScrollInternal;
      }
    };
  })
  .directive('iScroll', ['iScrollAccessor',function (iScrollAccessor) {
    return {
      restrict: 'A',
      link: function (scope, element) {
        var scroll = new IScroll(element[0], {
          click: true,
          bounce: true,
          preventDefaultException: {
            tagName: /^(INPUT|TEXTAREA|BUTTON|SELECT)$/,
            isContentEditable: /^true$/
          }
        });

        iScrollAccessor.set(scroll);

        element.on('$destroy', function() {
          scroll.destroy();
          scroll = null;
        });
      }
    };
  }])
  .directive('iScrollRefresh', ['iScrollAccessor', '$timeout',function (iScrollAccessor, $timeout) {
    return {
      restrict: 'A',
      link: function (scope) {
        if (scope.$last) {
          $timeout(function() {
            var scroll = iScrollAccessor.get();
            scroll.refresh();
          });
        }
      }
    };
  }]);