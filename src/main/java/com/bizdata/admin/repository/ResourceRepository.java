package com.bizdata.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bizdata.admin.domain.Resource;
import com.bizdata.commons.utils.BaseRepository;

public interface ResourceRepository extends BaseRepository<Resource, String>{

	/**
	 * 根据父id查询子节点个数
	 *
	 * @return int 子节点个数
	 */
	@Query("select count(1) from Resource where parent= :parent") 
	public int findCountByParentId(@Param("parent") String parent_resource_id);
	
	
	/**
	 * 根据父id查询子节点
	 *
	 * @param parent_resource_id 资源父id
	 * @return List<Resource>
	 */
	@Query("from Resource where parent= :parent") 
	public List<Resource> findChildensByParentId(@Param("parent") String parent_resource_id);
	
	public void deleteById(String id);
}
