package com.bizdata.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bizdata.admin.domain.Organization;
import com.bizdata.admin.repository.OrganizationRepository;
import com.bizdata.admin.repository.UserRepository;
import com.bizdata.admin.service.OrganizationService;

@Service
public class OrganizationServiceImpl implements OrganizationService {

	@Autowired
	private OrganizationRepository organizationRepository;

	@Autowired
	private UserRepository userRepository;

	@Transactional
	@Override
	public String insertOrganization(Organization organization) {
		// 获取父元素
		Organization parentOrganization = organizationRepository.findOne(organization.getParent());
		// 统一增加新节点层级
		organization.setLevel(parentOrganization.getLevel() + 1);
		// 设置当前子节点的父节点如果是叶子类型设置为不是叶子类型
		if (parentOrganization.getIsleaf()) {
			parentOrganization.setIsleaf(false);
			organizationRepository.save(parentOrganization);
		}
		Organization result_organization = organizationRepository.save(organization);
		return result_organization.getId();
	}

	@Override
	public String updateOrganization(Organization organization) {
		Organization result_organization = organizationRepository.save(organization);
		return result_organization.getId();
	}

	@Transactional
	@Override
	public void deleteOrganization(String organizationId) {
		// 根据organizationId获取要删除的SysOrganization
		Organization delSysOrganization = organizationRepository.findOne(organizationId);
		// 根据id删除organization中记录
		organizationRepository.delete(organizationId);
		// 根据父id字段查出孩子们
		List<Organization> childrens = findChildens(organizationId);
		for (Organization sysOrganization : childrens) {
			// 递归删除
			deleteOrganization(sysOrganization.getId());
		}
		// 根据parentId查询
		int count = organizationRepository.countByParentId(delSysOrganization.getParent());
		if (count < 1) {
			// 如果父节点下不存在子节点了，则设置父节点为叶子
			Organization parentSysOrganization = organizationRepository.findOne(delSysOrganization.getParent());
			if (null != parentSysOrganization) {
				parentSysOrganization.setIsleaf(true);
				organizationRepository.save(parentSysOrganization);
			}
		}
	}

	@Override
	public Organization findOne(String organizationId) {
		return organizationRepository.findOne(organizationId);
	}

	@Override
	public List<Organization> findAll() {
		return createFormatOrganizationList(organizationRepository.findAll());
	}

	/**
	 * 创建符合jqgrid treegrid格式的资源列表
	 * 
	 * @param list
	 *            组织机构list
	 * @return List<Organization>
	 */
	private List<Organization> createFormatOrganizationList(List<Organization> list) {
		// 返回的list
		List<Organization> rootArray = new ArrayList<Organization>();
		for (Organization organization : list) {
			if (organization.getParent().equals("0")) {
				rootArray.add(organization);
				rootArray.addAll(getNextLevelOrganization(organization, list));
			}
		}
		return rootArray;
	}

	/**
	 * 递归查找获取下一级的Organization
	 * 
	 * @return List<Organization>
	 */
	private List<Organization> getNextLevelOrganization(Organization currentNode, List<Organization> list) {
		List<Organization> temp_list = new ArrayList<Organization>();
		for (Organization newNode : list) {
			if (newNode.getParent() != null && newNode.getParent().compareTo(currentNode.getId()) == 0) {
				temp_list.add(newNode);
				temp_list.addAll(getNextLevelOrganization(newNode, list));
			}
		}
		return temp_list;
	}

	@Override
	public List<Organization> findAllByParentId(String parent_id) {
		return organizationRepository.findAllByParentId(parent_id);
	}

	@Override
	public int CountByParentId(String parent_id) {
		return organizationRepository.countByParentId(parent_id);
	}

	@Override
	public List<Organization> findChildens(String parent_id) {
		return organizationRepository.findAllByParentId(parent_id);
	}

	@Override
	public boolean canDeleteOrganization(String id) {
		boolean state = true;
		List<String> ids = new ArrayList<>();
		ids.add(id);
		ids.addAll(getWantDeleteId(id));
		for (String temp_id : ids) {
			int count = userRepository.countByOrganizationId(temp_id);
			if (count > 0) {
				state = false;
				break;
			}
		}
		return state;
	}

	/**
	 * 获取需要删除的id的孩子的id
	 * 
	 * @return List<String>
	 */
	private List<String> getWantDeleteId(String id) {
		List<String> temp_ids = new ArrayList<>();
		// 根据父id字段查出孩子们
		List<Organization> childrens = findChildens(id);
		for (Organization sysOrganization : childrens) {
			temp_ids.add(sysOrganization.getId());
			temp_ids.addAll(getWantDeleteId(sysOrganization.getId()));
		}
		return temp_ids;
	}

}
