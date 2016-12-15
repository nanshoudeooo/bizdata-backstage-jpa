package com.bizdata.admin.service;

import java.util.List;
import java.util.Set;

import com.bizdata.admin.domain.Resource;

/**
 * 资源接口
 *
 * @version 1.0
 *
 * @author sdevil507
 */
public interface ResourceService {

	/**
	 * 新增资源
	 *
	 * @param resource
	 *            资源实体
	 * @return 新增资源的id
	 * @throws Exception
	 */
	public String insertResource(Resource resource) throws Exception;

	/**
	 * 更新资源
	 *
	 * @param resource
	 *            资源实体
	 * @return 更新资源id
	 */
	public String updateResource(Resource resource) throws Exception;

	/**
	 * 删除资源
	 *
	 * @param resourceId
	 *            资源id
	 * @throws Exception
	 */
	public void deleteResource(String resourceId) throws Exception;

	/**
	 * 查询所有资源
	 *
	 * @return List<Resource>
	 * @throws Exception
	 */
	public List<Resource> findAll() throws Exception;
	
	/**
	 * 根据用户权限得到菜单
	 *
	 * @param permissions 权限set
	 * @return 菜单json
	 */
	public String getUserMenus(Set<String> permissions) throws Exception;
	
	/**
	 * 根据父id获取孩子节点
	 *
	 * @param parent_id 父节点id
	 * @return List<Resource>
	 */
	public List<Resource> findChildens(String parent_id);
	
	/**
	 * 根据id获取SysResource元素
	 * @param id 资源id
	 * @return Resource
	 */
	public Resource findSysResource(String id);
}
