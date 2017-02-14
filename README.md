##1.0.5
###2017-2-14
1. 升级spring boot至1.5.1GA版本

##1.0.4
###2016-12-8
1. 修改user_list.html文件中checkPassword校验密码规则,至少8个字符，包含至少一个大写字母、至少一个小写字母和至少一个阿拉伯数字。
2. 修改user_list.html文件,修复用户锁定状态一直处于禁用的bug，由原先的0或1状态值改为了true或false。
3. 新增一个用户重置密码的权限操作，原先逻辑是admin直接可以在编辑用户时更改密码,此时会查询出用户的md5加密过的密码！这种方式会影响到密码的规则判断并不友好！现在处理方式为,如果没有重置密码权限则修改用户信息时，无法修改用户的密码。有重置密码权限,重置按钮会显示，并点击后进行重置。
4. 新增select2相关js与css文件
5. 将用户与角色改为多对多,其实原先后台已经实现。现在更改了页面,将模态框中select类型的框,置为select2类型,具体参照user_list.html中role相关代码！
6. 修改outer.html中修改密码规则为至少8个字符，包含至少一个大写字母、至少一个小写字母和至少一个阿拉伯数字。
7. 因为select模式被取代,隐藏可能出现角色未赋予就提交情况，针对此情况作了验证,角色必须选择！

###2016-12-9
1. 用户信息增加email字段,为必填字段,方便拓展发送邮件等相关功能。
2. 用户信息增加lastLoginTime字段,方便拓展关于账号有效期等相关功能。
3. 修改用户信息create_time字段名为createTime,防止jpa中关于下划线的错误。
4. 用户信息增加passwordUpdate字段,用于记录最后修改密码时间,方便拓展密码时效功能。
5. 修改LoginFilter,UserService,UserServiceImpl增加了记录用户最后登录时间逻辑。
6. 修改UserServiceImpl中updateUser方法,加入了修改密码时间更新。

###2016-12-12
1. 修改role_list.html,将原先资源树每次都需要reload逻辑,改为重置为初始状态,减少请求并解决setTimeout如果时间设置过短,则资源树中条目已选中但不显示问题。该问题主要表现在,网络情况不好时，角色点击事件发生后,延迟500ms，但是此时资源数据却并未加载完成，导致无法标记选中。

##1.0.3
###2016-11-14
1. 	修改ehcache-shiro.xml文件,删除dtd验证方式，因为该方式会导致无法启动！更改为xsd验证方式！
2. 修改readmt.txt为README.md方便修改记录做markdown展示！

###2016-12-4
1. 修改ShiroConfiguration配置文件中注释与调整了结构
2. 新增ehcache-shiro-cluster.xml,ehcache的集群配置文件！如果应用该文件,则需要修改ShrioConfiguration中文件导入(目前没有做开关,因为后续版本会取消encache实现),对应前端nginx负载均衡,需要设置为ip_hash策略。

##1.0.2
###2016-11-06
1. 去除原先json数据接口返回工具类JsonResultUtils，更改为BizdataResponseStatus枚举实现
2. 因为1的变动，对应修改Controller中返回与对应模板html中相关判断
	
###2016-11-09
1. 修改pom将spring boot 从1.3.6升级至1.4.1
2. 因为1.4.1版本改变，修改BackStageApplication中ErrorPage所属包
3. 增加测试Valid顺序,目前只增加了First,Second,ValidSequence接口
4. 新增JpaFindConditionException基础异常，用于描述JPA查询条件异常
5. 新增JpaSortVO用于单独控制排序条件
6. GlobalExceptionHandler类中新增捕获JpaFindConditionException异常处理
7. 修改Spring boot的test配置为1.4.1中的配置，参照BackStageApplicationTests
8. 新增JpaPageVO用于单独控制分页条件
9. 修改PageConditionException继承JpaFindConditionException，用于查询时统一捕获相关异常
		
###2016-11-11
1. 新增SearchConjunction枚举，用于多条件中AND,OR操作
2. 增加SearchOperator中注释
3. 新增search包，用于封装多条件操作
4. 新增BizdataSpecification类，用于连接各个表达式
5. 新增LogicalExpression类，用于操作AND,OR之类的逻辑功能
6. 新增SimpleExpression类，用于简单表达式功能
7. 新增SearchConditionAssembler类，用于组装各个表达式
8. 新增JpaPageVO类，用于接收分页参数
9. 新增JpaSortVO类，用于接收排序参数
10. 新增JqgridSearchVO类，用于封装jqgrid查询
11. 修改SearchConditionException继承自JpaFindConditionException异常处理
12. 修改BaseEntity不再继承JpaPageSortWhereCondition类
13. 删除JpaPageSortWhereCondition类
14. 修改代码中涉及到分页、排序、搜索的代码，转为使用JpaPageVO、JpaSortVO、JqgridSearchVO接收参数