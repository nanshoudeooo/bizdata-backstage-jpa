package com.bizdata.admin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.authz.permission.WildcardPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bizdata.admin.domain.Resource;
import com.bizdata.admin.repository.ResourceRepository;
import com.bizdata.admin.repository.RoleResourceRepository;
import com.bizdata.admin.service.ResourceService;
import com.bizdata.commons.constant.ResourceType;
import com.google.gson.Gson;

@Service
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	private ResourceRepository resourceRepository;

	@Autowired
	private RoleResourceRepository roleResourceRepository;

	@Transactional
	@Override
	public String insertResource(Resource resource) throws Exception {
		// 获取父元素
		Resource parentResource = resourceRepository.findOne(resource.getParent());
		// 设置新增节点类型
		switch (parentResource.getType()) {
		case ROOT:
			// 父节点是root节点
			// 则子节点类型肯定为column
			resource.setType(ResourceType.COLUMN);
			break;
		case COLUMN:
			// 父节点是栏目节点
			// 则子节点类型肯定为menu
			resource.setType(ResourceType.MENU);
			break;
		case MENU:
			// 父节点为菜单节点
			// 则子节点类型肯定为action
			resource.setType(ResourceType.ACTION);
			break;
		case ACTION:
			// 按钮节点
			// 按钮节点下面无法添加子节点
			break;
		default:
			break;
		}

		// 统一增加新节点层级
		resource.setLevel(parentResource.getLevel() + 1);

		// 设置当前子节点的父节点如果是叶子类型设置为不是叶子类型
		if (parentResource.getIsleaf()) {
			parentResource.setIsleaf(false);
			resourceRepository.save(parentResource);
		}
		Resource result_resource = resourceRepository.save(resource);
		return result_resource.getId();
	}

	@Override
	public String updateResource(Resource resource) throws Exception {
		Resource result_resource = resourceRepository.save(resource);
		return result_resource.getId();
	}

	/**
	 * 根据id递归删除资源与它的孩子们
	 *
	 * @param resourceId
	 *            资源id
	 * @throws Exception
	 */
	@Transactional
	@Override
	public void deleteResource(String resourceId) throws Exception {
		// 根据resourceId获取要删除的SysResource
		Resource result_resource = resourceRepository.findOne(resourceId);
		// 根据resourceid删除role_resource中记录
		roleResourceRepository.deleteRoleResource(resourceId);
		// 根据id删除resource中记录
		resourceRepository.deleteById(resourceId);
		// 根据父id字段查出孩子们
		List<Resource> childrens = findChildens(resourceId);
		System.out.println(childrens.size());
		for (Resource resource : childrens) {
			// 递归删除
			deleteResource(resource.getId());
		}
		// 根据parentId查询
		// int count =
		// resourceRepository.findCountByParentId(result_resource.getParent());
		int count = resourceRepository.findCountByParentId(resourceId);
		if (count < 1) {
			// 如果父节点下不存在子节点了，则设置父节点为叶子
			Resource parentSysResource = resourceRepository.findOne(result_resource.getParent());
			if (null != parentSysResource) {
				parentSysResource.setIsleaf(true);
				resourceRepository.save(parentSysResource);
			}
		}
	}

	@Transactional
	@Override
	public List<Resource> findAll() throws Exception {
		Sort sort = new Sort(Sort.Direction.ASC, "sortNum");
		return createFormatResourceList(resourceRepository.findAll(sort));
	}

	/**
	 * 创建符合jqgrid treegrid格式的资源列表
	 * 
	 * @param list
	 *            资源list
	 * @return List<Resource>
	 */
	private List<Resource> createFormatResourceList(List<Resource> list) {
		// 返回的list
		List<Resource> rootArray = new ArrayList<Resource>();
		for (Resource resource : list) {
			if (resource.getRoot()) {
				rootArray.add(resource);
				rootArray.addAll(getNextLevelResource(resource, list));
			}
		}
		return rootArray;
	}

	/**
	 * 递归查找获取下一级的resource
	 * 
	 * @return List<Resource>
	 */
	private List<Resource> getNextLevelResource(Resource currentNode, List<Resource> list) {
		List<Resource> temp_list = new ArrayList<Resource>();
		for (Resource newNode : list) {
			if (newNode.getParent() != null && newNode.getParent().compareTo(currentNode.getId()) == 0) {
				temp_list.add(newNode);
				temp_list.addAll(getNextLevelResource(newNode, list));
			}
		}
		return temp_list;
	}

	/**
	 * 判断该资源，当前用户是否有权限
	 *
	 * @param permissions
	 *            资源Set
	 * @param resource
	 *            当前资源
	 * @return boolean
	 */
	private boolean hasPermission(Set<String> permissions, Resource resource) {
		if (StringUtils.isEmpty(resource.getPermission())) {
			return true;
		}
		for (String permission : permissions) {
			WildcardPermission p1 = new WildcardPermission(permission);
			WildcardPermission p2 = new WildcardPermission(resource.getPermission());
			if (p1.implies(p2) || p2.implies(p1)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getUserMenus(Set<String> permissions) throws Exception {
		// 获取全部资源
		List<Resource> allResources = findAll();

		// 获取展示的栏目与菜单
		List<Resource> columns_menus = new ArrayList<Resource>();

		// 循环遍历取出栏目与菜单
		for (Resource resource : allResources) {
			// 判断是否根节点
			if (resource.getRoot()) {
				continue;
			}
			// 判断资源类型是否为栏目
			if (resource.getType() != ResourceType.COLUMN) {
				continue;
			}
			// 判断是否有权限
			if (!hasPermission(permissions, resource)) {
				continue;
			}
			// 遍历取出菜单
			for (Resource menu : allResources) {
				if (menu.getRoot()) {
					continue;
				}
				if (menu.getType() != ResourceType.MENU) {
					continue;
				}
				// 判断是否有权限
				if (!hasPermission(permissions, menu)) {
					continue;
				}
				// 判断此menu父id是否是资源Id
				if (menu.getParent().equals(resource.getId())) {
					resource.getMenus().add(menu);
				}
			}
			// 返回栏目与菜单
			columns_menus.add(resource);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("menus", columns_menus);
		Gson gson = new Gson();
		return gson.toJson(map);
	}

	/**
	 * 根据父id找到孩子们
	 *
	 * @param parent_id
	 *            父id
	 * @return List<Resource>
	 */
	@Override
	public List<Resource> findChildens(String parent_id) {
		return resourceRepository.findChildensByParentId(parent_id);
	}

	@Override
	public Resource findSysResource(String id) {
		return resourceRepository.findOne(id);
	}

}
