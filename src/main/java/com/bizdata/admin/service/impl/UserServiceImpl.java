package com.bizdata.admin.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bizdata.admin.domain.Role;
import com.bizdata.admin.domain.User;
import com.bizdata.admin.repository.OrganizationRepository;
import com.bizdata.admin.repository.RoleRepository;
import com.bizdata.admin.repository.UserRepository;
import com.bizdata.admin.service.RoleService;
import com.bizdata.admin.service.UserService;
import com.bizdata.commons.utils.JpaPageVO;
import com.bizdata.commons.utils.JpaSortVO;
import com.bizdata.commons.utils.JqgridSearchVO;
import com.bizdata.commons.utils.PasswordHelper;
import com.bizdata.framework.exception.JpaFindConditionException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private PasswordHelper passwordHelper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleService roleService;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private OrganizationRepository organizationRepository;

	@Transactional
	@Override
	public String insertUser(User user, String[] role_ids) {
		// 加密密码
		passwordHelper.encryptPassword(user);
		// 持久化用户角色
		if (role_ids != null && role_ids.length > 0) {
			Set<Role> roles = new HashSet<>();
			for (String roleid : role_ids) {
				roles.add(roleRepository.findOne(roleid));
			}
			user.setRoleList(roles);
		}
		// 持久化用户信息表
		User result_user = userRepository.save(user);
		return result_user.getId();
	}

	@Transactional
	@Override
	public String updateUser(User user, String[] role_ids) {
		String[] ignore;
		if (user.getPassword().length() != 32) {
			// 密码长度不为32时表示为新密码而不是原先md5加密的密码
			passwordHelper.encryptPassword(user);
			ignore = new String[] { "id", "create_time","lastLoginTime" ,"roleList" };
		} else {
			ignore = new String[] { "id", "create_time","lastLoginTime", "passwordUpdateTime","roleList" };
		}
		User temp_user = userRepository.findOne(user.getId());
		if (null == user.getSalt()) {
			user.setSalt(temp_user.getSalt());
		}
		BeanUtils.copyProperties(user, temp_user, ignore);
		// 执行用户角色关系修改
		temp_user.getRoleList().clear();
		if (role_ids != null && role_ids.length > 0) {
			for (String roleid : role_ids) {
				temp_user.getRoleList().add(roleRepository.findOne(roleid));
			}
		}
		User result_user = userRepository.save(temp_user);
		return result_user.getId();
	}

	@Transactional
	@Override
	public void deleteUser(String userId) {
		User user = userRepository.findOne(userId);
		user.getRoleList().clear();
		userRepository.delete(userId);
	}

	@Override
	public void changePassword(String userId, String newPassword) {
		User user = userRepository.findOne(userId);
		user.setPassword(newPassword);
		user.setPasswordUpdateTime(new Date());
		passwordHelper.encryptPassword(user);
		userRepository.save(user);
	}

	@Override
	public User findOne(String userId) {
		return userRepository.findOne(userId);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	/**
	 * 根据用户名查找用户
	 *
	 * @param username
	 *            用户名
	 * @return User
	 */
	@Override
	public User selectUserDetailByUsername(String username) {
		return userRepository.findUserByUsername(username);
	}

	/**
	 * 根据用户名查找其角色
	 *
	 * @param username
	 *            用户名
	 * @return Set<String>
	 */
	@Override
	public Set<String> findRoles(String username) {
		User user = userRepository.findUserByUsername(username);
		if (user == null) {
			return Collections.emptySet();
		}
		Set<String> roles = new HashSet<String>();
		for (Role role : user.getRoleList()) {
			roles.add(role.getRole());
		}
		return roles;
	}

	/**
	 * 根据用户名查找其权限
	 *
	 * @param username
	 *            用户名
	 * @return Set<String>
	 */
	@Override
	public Set<String> findPermissions(String username) {
		// 根据用户名获取用户
		User user = userRepository.findUserByUsername(username);
		if (user == null) {
			return Collections.emptySet();
		}
		// 返回对应的权限
		Set<String> permissions = roleService.findPermissions(new ArrayList<>(user.getRoleList()));
		return permissions;
	}

	@Transactional
	@Override
	public Page<User> findAllByPage(JpaPageVO pageVO, JpaSortVO sortVO, JqgridSearchVO searchVO)
			throws JpaFindConditionException {
		Page<User> users = null;
		// if (jpaPageSortWhereCondition.isSearch()) {
		// jpaPageSortWhereCondition.convert();
		// users =
		// userRepository.findAll(jpaPageSortWhereCondition.getSpecification(),
		// jpaPageSortWhereCondition.getPageAble());
		// } else {
		// users =
		// userRepository.findAll(jpaPageSortWhereCondition.getPageAble());
		// }

		users = userRepository.findAll(searchVO.getSpecifications(), pageVO.getPageable(sortVO.getCondition()));
		for (User user : users.getContent()) {
			user.setOrganizationName(organizationRepository.findOne(user.getOrganizationId()).getName());
		}
		return users;
	}

	@Override
	public boolean checkPassword(String username, String password) {
		// 根据用户名找出数据库记录
		User user = userRepository.findUserByUsername(username);
		// 判断旧密码输入是否正确
		if (passwordHelper.checkPassword(password, user.getPassword(), user.getCredentialsSalt())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void changePassword(String newPassword) {
		// 根据用户名找出数据库记录
		User user = userRepository.findUserByUsername(SecurityUtils.getSubject().getPrincipal().toString());
		user.setPassword(newPassword);
		user.setPasswordUpdateTime(new Date());
		passwordHelper.encryptPassword(user);
		userRepository.save(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bizdata.admin.service.UserService#updateLastUpdateTime(java.util.
	 * Date)
	 */
	@Override
	public void updateLastLoginTime(String username, Date date) {
		User user = userRepository.findUserByUsername(username);
		user.setLastLoginTime(date);
		userRepository.save(user);
	}

}
