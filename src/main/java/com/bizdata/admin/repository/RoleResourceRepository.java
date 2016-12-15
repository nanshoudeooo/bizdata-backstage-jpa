package com.bizdata.admin.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bizdata.admin.domain.Role_Resource;
import com.bizdata.commons.utils.BaseRepository;

public interface RoleResourceRepository extends BaseRepository<Role_Resource, String> {

	/**
	 * 根据角色id与资源id删除关系
	 *
	 * @param roleid
	 *            角色id
	 * @param resourceid
	 *            资源id
	 * @return Role_Resource
	 */
	public Role_Resource findByRoleidAndResourceid(@Param("roleid") String roleid,
			@Param("resourceid") String resourceid);

	@Modifying
	@Query("delete Role_Resource t where t.resourceid =:resourceid")
	public void deleteRoleResource(@Param("resourceid") String resourceid);

	public void deleteByRoleid(String role_id);
	
}
