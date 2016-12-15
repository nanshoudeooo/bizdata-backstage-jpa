package com.bizdata.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bizdata.admin.domain.Organization;
import com.bizdata.commons.utils.BaseRepository;

public interface OrganizationRepository extends BaseRepository<Organization, String>{

	/**
	 * 根据父id查询出子节点个数
	 *
	 * @param parent id
	 * @return int
	 */
	@Query("select count(1) from Organization where parent= :parent") 
	public int countByParentId(@Param("parent") String parent);
	
	/**
	 * 根据父id查询子节点
	 *
	 * @param parent 资源父id
	 * @return List<Resource>
	 */
	@Query("from Organization where parent= :parent") 
	public List<Organization> findAllByParentId(@Param("parent") String parent);
	
}
