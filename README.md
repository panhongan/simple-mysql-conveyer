# simple-mysql-conveyer 简单的mysql数据访问器  

【背景】
基于Mybatis访问数据库，存在低效的问题：  
1）服务接口实现 -> Java Mapper文件 —> Mybatis XML文件，开发成本高。  
2）上述过程无通用性，每一份数据都需要定制化提供Java Mapper文件和Mybatis XML文件，无可复用性。  
  
  
【目标】  
改变现有的数据访问模式，为了能更快进行业务迭代，推出通用的mysql数据访问框架：simple-mysql-conveyer  


【原理】  
1）根据Mybatis的原理，Java Bean可转换为Sql语句；  
2）Java对象是树形结构，Sql语句也是树形结构，可对应。  
  
【应用示例】  
module : demo  
包含了JavaBean/Thrift/ProtoBuf三种数据序列化方式 应用当前框架。  
  
【下一步】  
支持更多数据库类型  
