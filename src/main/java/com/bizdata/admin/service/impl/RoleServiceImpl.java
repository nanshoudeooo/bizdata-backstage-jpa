package com.bizdata.admin.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bizdata.admin.domain.Resource;
import com.bizdata.admin.domain.Role;
import com.bizdata.admin.domain.Role_Resource;
import com.bizdata.admin.repository.ResourceRepository;
import com.bizdata.admin.repository.RoleRepository;
import com.bizdata.admin.repository.RoleResourceRepository;
import com.bizdata.admin.service.RoleService;
import com.bizdata.framework.exception.RoleException;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private RoleResourceRepository roleResourceRepository;

	@Autowired
	private ResourceRepository resourceRepository;

	@Override
	public String insertRole(Role role) {
		Role result_role = roleRepository.save(role);
		return result_role.getId();
	}

	@Override
	public String updateRole(Role role) {
		Role temp_role = roleRepository.findOne(role.getId());
		role.setResourceList(temp_role.getResourceList());
		Role result_role = roleRepository.save(role);
		return result_role.getId();
	}

	@Transactional
	@Override
	public boolean deleteRole(String roleId) {
		// 根据ID查询出角色
		Role role = roleRepository.findOne(roleId);
		if (role.getUserList().size() > 0) {
			// 角色下有用户,不允许删除
			return false;
		} else {
			role.getResourceList().clear();
			role.getUserList().clear();
			roleRepository.delete(roleId);
			return true;
		}
	}

	@Override
	public Role findOne(String roleId) {
		return roleRepository.findOne(roleId);
	}

	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	/**
	 * 根据角色获取对应的资源
	 *
	 * @param list
	 *            角色列表
	 * @return Set<String>
	 */
	@Override
	public Set<String> findPermissions(List<Role> list) {
		Set<String> resourceSet = new HashSet<String>();
		for (Role role : list) {
			// 根据角色id获取角色对象，并同时将资源信息转换为list
			role = roleRepository.findOne(role.getId());
			if (role != null && role.getResourceList() != null) {
				// 执行一下资源id的去重
				for (Resource res : role.getResourceList()) {
					resourceSet.add(res.getPermission());
				}
			}
		}
		return resourceSet;
	}

	@Override
	public void addRelation(Role_Resource roleResource) {
		roleResourceRepository.save(roleResource);
	}

	@Transactional
	@Override
	public void disassociate(Role_Resource roleResource) throws RoleException {
		// 判断是否是删除超级管理员角色的系统功能关系，若是则不能删除
		if (roleResource.getRoleid().equals("1")) {
			Resource res = resourceRepository.findOne(roleResource.getResourceid());
			if (res.getIsInitialized() != null && res.getIsInitialized().equals(true)) {
				throw new RoleException("超级管理员角色与初始化的系统资源关联关系不可以解除。");
			}
		}
		Role_Resource result_role_resource = roleResourceRepository.findByRoleidAndResourceid(roleResource.getRoleid(),
				roleResource.getResourceid());
		roleResourceRepository.delete(result_role_resource);
	}

}
