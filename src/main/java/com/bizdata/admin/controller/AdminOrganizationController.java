package com.bizdata.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bizdata.admin.domain.Organization;
import com.bizdata.admin.service.OrganizationService;
import com.bizdata.commons.constant.BizdataResponseStatus;
import com.google.gson.Gson;

/**
 * 组织机构操作Controller
 *
 * @version 1.0
 *
 * @author sdevil507
 */
@Controller
@RequestMapping("/admin/organization")
public class AdminOrganizationController {

	@Autowired
	private OrganizationService organizationService;

	@RequiresPermissions("sys:organization:view")
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index() {
		return new ModelAndView("admin_page/organization/organization_list");
	}

	/**
	 * 返回部门list json数据
	 *
	 * @return json类型部门数据
	 */
	@RequiresPermissions("sys:organization:view")
	@RequestMapping(value = "/organizationList", method = RequestMethod.GET)
	@ResponseBody
	public String organizationList() {
		Map<String, Object> organizationMap = new HashMap<String, Object>();
		organizationMap.put("rows", organizationService.findAll());
		return new Gson().toJson(organizationMap);
	}

	/**
	 * 添加部门
	 * 
	 * @return json
	 */
	@RequiresPermissions("sys:organization:create")
	@ResponseBody
	@RequestMapping(value = "/addOrganization", method = RequestMethod.POST)
	public String addOrganization(Organization sysOrganization) {
		String json = "";
		try {
			organizationService.insertOrganization(sysOrganization);
			json = BizdataResponseStatus.COMMON_SUCCESS.getResult("新增部门成功");
		} catch (Exception e) {
			e.printStackTrace();
			json = BizdataResponseStatus.COMMON_ERROR.getResult("新增部门失败");
		}
		return json;
	}

	/**
	 * 部门更新操作
	 * 
	 * @param sysOrganization
	 * @return json
	 */
	@RequiresPermissions("sys:organization:update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String update(Organization sysOrganization) {
		String json = "";
		try {
			organizationService.updateOrganization(sysOrganization);
			json = BizdataResponseStatus.COMMON_SUCCESS.getResult("更新部门成功");
		} catch (Exception e) {
			e.printStackTrace();
			json = BizdataResponseStatus.COMMON_ERROR.getResult("更新部门失败");
		}
		return json;
	}

	/**
	 * 根据id删除部门
	 *
	 * @param id
	 *            部门id
	 * @return json类型操作反馈
	 */
	@RequiresPermissions("sys:organization:delete")
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(String id) {
		String json = "";
		try {
			if (organizationService.canDeleteOrganization(id)) {
				// 可以删除
				organizationService.deleteOrganization(id);
				json = BizdataResponseStatus.COMMON_SUCCESS.getResult("删除部门成功");
			} else {
				// 部门下存在人员
				json = BizdataResponseStatus.COMMON_ERROR.getResult("部门或子部门下存在人员，不允许删除！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			json = BizdataResponseStatus.COMMON_ERROR.getResult("删除部门失败");
		}
		return json;
	}

	/**
	 * 获取相同父项的组织机构 并包装到TreeModel中给页面提供树形展示
	 *
	 * @param id
	 *            组织机构id
	 * @return json类型树形数据
	 */
	@RequiresPermissions("sys:organization:view")
	@RequestMapping(value = "/brotherOrganization/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String brotherOrganization(@PathVariable("id") String id) {
		String json = "";
		try {
			List<Organization> orgList = organizationService.findAllByParentId(id);
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			if (orgList != null) {
				for (Organization org : orgList) {
					Map<String, Object> map = new HashMap<String, Object>();
					int count = organizationService.CountByParentId(org.getId());
					if (count > 0) {
						map.put("type", "folder");
					} else {
						map.put("type", "item");
					}
					map.put("id", org.getId() + "");
					map.put("text", org.getName());
					list.add(map);
				}
			}
			json = new Gson().toJson(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
}
