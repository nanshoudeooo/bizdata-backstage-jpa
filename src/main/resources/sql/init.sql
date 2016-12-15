-- 初始化组织机构根节点数据
INSERT INTO `admin_organization` VALUES ('1', b'1', b'0', '0', b'1', '组织机构', '0'),('2',b'1',b'1','1',b'1','南京商数','1');

-- 初始化资源数据
INSERT INTO `admin_resource` VALUES ('1', b'1', null, b'1', b'0', '0', b'1', '资源', '0', 'root', b'1', '-1', 'ROOT', null), ('10', b'1', '', b'1', b'1', '3', b'1', '角色删除', '7', 'sys:role:delete', b'0', '2', 'ACTION', ''), ('11', b'1', '', b'1', b'0', '2', b'1', '部门管理', '2', 'sys:organization:view', b'0', '3', 'MENU', '/organization'), ('12', b'1', '', b'1', b'1', '3', b'1', '部门新增', '11', 'sys:organization:create', b'0', '0', 'ACTION', ''), ('13', b'1', '', b'1', b'1', '3', b'1', '部门修改', '11', 'sys:organization:update', b'0', '1', 'ACTION', ''), ('14', b'1', '', b'1', b'1', '3', b'1', '部门删除', '11', 'sys:organization:delete', b'0', '2', 'ACTION', ''), ('15', b'1', '', b'1', b'0', '2', b'1', '资源管理', '2', 'sys:resource:view', b'0', '2', 'MENU', '/resource'), ('16', b'1', '', b'1', b'1', '3', b'1', '资源新增', '15', 'sys:resource:create', b'0', '0', 'ACTION', ''), ('17', b'1', '', b'1', b'1', '3', b'1', '资源修改', '15', 'sys:resource:update', b'0', '1', 'ACTION', ''), ('18', b'1', '', b'1', b'1', '3', b'1', '资源删除', '15', 'sys:resource:delete', b'0', '2', 'ACTION', ''), ('19', b'1', '', b'1', b'0', '2', b'1', '登录退出', '2', 'sys:loginlogout:view', b'0', '4', 'MENU', '/loginlogout'), ('2', b'1', 'glyphicon glyphicon-align-justify', b'1', b'0', '1', b'1', '系统管理', '1', 'sys:view', b'0', '99', 'COLUMN', ''), ('3', b'1', '', b'1', b'0', '2', b'1', '用户管理', '2', 'sys:user:view', b'0', '0', 'MENU', '/user'), ('4', b'1', '', b'1', b'1', '3', b'1', '用户新增', '3', 'sys:user:create', b'0', '0', 'ACTION', ''), ('5', b'1', '', b'1', b'1', '3', b'1', '用户修改', '3', 'sys:user:update', b'0', '1', 'ACTION', ''), ('6', b'1', '', b'1', b'1', '3', b'1', '用户删除', '3', 'sys:user:delete', b'0', '2', 'ACTION', ''), ('7', b'1', '', b'1', b'0', '2', b'1', '角色管理', '2', 'sys:role:view', b'0', '1', 'MENU', '/role'), ('8', b'1', '', b'1', b'1', '3', b'1', '角色新增', '7', 'sys:role:create', b'0', '0', 'ACTION', ''), ('9', b'1', '', b'1', b'1', '3', b'1', '角色修改', '7', 'sys:role:update', b'0', '1', 'ACTION', ''),('20', b'1', 'glyphicon', b'1', b'1', '3', b'1', '密码重置', '3', 'sys:password:reset', b'0', '3', 'ACTION', '');

-- 初始化角色数据
INSERT INTO `admin_role` VALUES ('1', '超级管理员', 'admin');

-- 初始化角色资源关系表数据
INSERT INTO `admin_role_resource` VALUES ('1', '2', '1'), ('10', '15', '1'), ('11', '16', '1'), ('12', '17', '1'), ('13', '18', '1'), ('14', '11', '1'), ('15', '12', '1'), ('16', '13', '1'), ('17', '14', '1'), ('18', '19', '1'), ('2', '3', '1'), ('3', '4', '1'), ('4', '5', '1'), ('5', '6', '1'), ('6', '7', '1'), ('7', '8', '1'), ('8', '9', '1'), ('9', '10', '1'),('19','20','1');

-- 初始化用户数据
INSERT INTO `admin_user` VALUES ('1', '2016-01-01 00:00:00', 'sdevil507@163.com', null, b'0', '2', 'aa75248bdef3ea31fd536f62a0c6b396', '2016-01-01 00:00:00','dde85dd09b74d038632e6d8c76938b59', 'admin');

-- 初始化用户角色关系表数据
INSERT INTO `admin_user_role` VALUES ('1', '1');
