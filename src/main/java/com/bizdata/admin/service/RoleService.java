package com.bizdata.admin.service;

import java.util.List;
import java.util.Set;

import com.bizdata.admin.domain.Role;
import com.bizdata.admin.domain.Role_Resource;
import com.bizdata.framework.exception.RoleException;

/**
 * 角色接口
 *
 * @version 1.0
 *
 * @author sdevil507
 */
public interface RoleService {

	
	/**
	 * 新建角色
	 *
	 * @param role 角色实体
	 * @return 新增实体id
	 */
	public String insertRole(Role role);

	/**
	 * 更新角色
	 *
	 * @param role 角色实体
	 * @return 更新实体id
	 */
	public String updateRole(Role role);

	/**
	 * 删除角色
	 *
	 * @param roleId 角色id
	 */
	public boolean deleteRole(String roleId);

	/**
	 * 根据角色ID查找角色
	 *
	 * @param roleId 角色id
	 * @return Role
	 */
	public Role findOne(String roleId);

	/**
	 * 查找所有角色
	 *
	 * @return List<Role>
	 */
	public List<Role> findAll();

	
	/**
	 * 根据角色得到权限字符串列表
	 *
	 * @param list 角色列表
	 * @return Set<String>
	 */
	Set<String> findPermissions(List<Role> list);

	
	/**
	 * 在角色资源关系表中，插入新的关系记录
	 *
	 * @param roleResource roleResource实体
	 */
	public void addRelation(Role_Resource roleResource);

	/**
	 * 在角色资源关系表中，删除一条关系记录
	 *
	 * @param roleResource roleResource实体
	 * @throws RoleException
	 */
	public void disassociate(Role_Resource roleResource)
			throws RoleException;
}
