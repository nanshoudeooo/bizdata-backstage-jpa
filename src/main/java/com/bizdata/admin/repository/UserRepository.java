package com.bizdata.admin.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bizdata.admin.domain.User;
import com.bizdata.commons.utils.BaseRepository;

/**
 * 用户Repository
 *
 * @version 1.0
 *
 * @author sdevil507
 */
public interface UserRepository extends BaseRepository<User, String> {

	/**
	 * 根据用户名查找用户
	 *
	 * @param username 用户名
	 * @return User
	 */
	@Query("from User where username= :username")
	public User findUserByUsername(@Param("username") String username);

	/**
	 * 根据组织机构id查询用户个数
	 *
	 * @param organizationId 组织机构id
	 * @return List<User>
	 */
	@Query("select count(1) from User where organizationId=:organizationId") 
	public int countByOrganizationId(@Param("organizationId") String organizationId);
}
