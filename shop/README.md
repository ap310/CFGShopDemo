本项目为一个简单的购物类JAVA服务程序，使用Spring boot框架 前端使用 AngularJs+Ionic 框架，欢迎大家进行补充完善。  

问题  

--Reason shy you uesed specific framework and libraries for the front-end and backend。  

使用spring boot框架 的好处在于其在保留spring框架各种功能的同时 大大简化新Spring应用的项目搭建以及开发，通过简单配置即可获得不断的完善支持很多丰富的功能能。  

使用 AngularJs+Ionic 框架在于其功能 是一个比较完善的前端MVC框架，包含模板，数据双向绑定，路由，模块化，服务，过滤器，依赖注入等所有功能；而且支持跨平台端功能。其实如果时间更充裕使用React可能跨平台性能更好。
  前后端项目和代码分离便于维护和开发，之间通过Restful进行交互，具有很好的可扩充性。  
  
--How the persistend layer could be implemented?  

使用Spring-data-jpa模块并使用数据库进行存储（Oracle,SQL Server）.  

Spring-data-jpa 借助于Hibernate 可使开发者屏蔽不同的数据库平台带来的SQL语句的差异性，具有代码简洁高效的优点，同时支持数据缓存。当然我们也可以使用Redis进行数据缓存。  


--How long did it take you to create the code?  

分散时间进行了开发，总共花了5个小时多小时，后面美化优化代码花了一个小时左右。  


在此声明：项目代码肯定有不完善的地方由于时间的关系 并没有完成单元测试的部分对应订单类和支付类并没有实现。本人从事开发十几年也深感技术在不断发展，要写好代码需要不断的努力和学习，完美的代码需要一生不断的追求。
          希望各位领导和同事能给予指点。