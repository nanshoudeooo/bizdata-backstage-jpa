package com.bizdata.admin.service;

import java.util.List;

import com.bizdata.admin.domain.Organization;


/**
 * 组织机构接口
 *
 * @version 1.0
 *
 * @author sdevil507
 */
public interface OrganizationService {
	/**
	 * 创建组织机构
	 *
	 * @param organization 
	 * @return 组织机构id
	 */
	public String insertOrganization(Organization organization);

	
	/**
	 * 更新组织机构
	 *
	 * @param organization 更新组织机构
	 * @return 组织机构id
	 */
	public String updateOrganization(Organization organization);

	/**
	 * 删除组织机构
	 *
	 * @param organizationId 组织机构id
	 */
	public void deleteOrganization(String organizationId);
	
	/**
	 * 查找组织机构
	 *
	 * @param organizationId 组织机构id
	 * @return 组织机构实体
	 */
	Organization findOne(String organizationId);

	/**
	 * 查找组织机构列表
	 *
	 * @return List<Organization>
	 */
	List<Organization> findAll();

	
	/**
	 * 根据条件查询
	 *
	 * @param parent_id 组织机构id
	 * @return List<Organization>
	 */
	List<Organization> findAllByParentId(String parent_id);

	/**
	 * 获取直系子节点个数
	 *
	 * @param parent_id 组织机构id
	 * @return int
	 */
	public int CountByParentId(String parent_id);

	/**
	 * 根据父id找到孩子们
	 *
	 * @param parent_id 父id
	 * @return List<Organization>
	 */
	public List<Organization> findChildens(String parent_id);

	/**
	 * 判断组织结构是否可以删除(规则：如果部门下存在用户则无法删除)
	 *
	 * @param id 组织机构id
	 * @return int
	 */
	public boolean canDeleteOrganization(String id);
	
}
