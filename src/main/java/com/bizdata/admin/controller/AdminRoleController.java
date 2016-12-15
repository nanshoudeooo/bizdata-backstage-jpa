package com.bizdata.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bizdata.admin.domain.Role;
import com.bizdata.admin.domain.Role_Resource;
import com.bizdata.admin.service.RoleService;
import com.bizdata.commons.constant.BizdataResponseStatus;
import com.bizdata.commons.utils.GsonExclusionStrategy;
import com.bizdata.framework.exception.RoleException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 用户角色Controller
 *
 * @version 1.0
 *
 * @author sdevil507
 */
@Controller
@RequestMapping("/admin/role")
public class AdminRoleController {

	@Autowired
	private RoleService roleService;

	/**
	 * 侧边栏打开角色管理路径 返回对应的list.jsp页面
	 *
	 * @return ModelAndView
	 */
	@RequiresPermissions("sys:role:view")
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list() {
		return new ModelAndView("admin_page/role/role_list");
	}

	/**
	 * 返回对应的角色json数据
	 *
	 * @param role
	 *            角色实体
	 * @return 对应角色json
	 */
	@RequiresPermissions("sys:role:view")
	@RequestMapping(value = "/roleList", method = RequestMethod.GET)
	@ResponseBody
	public String roleList(Role role) {
		Map<String, Object> roleMap = new HashMap<String, Object>();
		List<Role> roleList = roleService.findAll();
		roleMap.put("rows", roleList);
		String[] ex = { "roleList" };
		Gson gson = new GsonBuilder().setExclusionStrategies(new GsonExclusionStrategy(ex)).create();
		return gson.toJson(roleMap);
	}

	/**
	 * 新增角色
	 *
	 * @param role
	 *            角色实体
	 * @return json
	 */
	@RequiresPermissions("sys:role:create")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public String create(Role role) {
		String json = "";
		try {
			roleService.insertRole(role);
			json = BizdataResponseStatus.COMMON_SUCCESS.getResult("新增角色成功");
		} catch (Exception e) {
			e.printStackTrace();
			json = BizdataResponseStatus.COMMON_ERROR.getResult("添加角色失败,请确认角色名称不重复！");
		}
		return json;
	}

	/**
	 * 更新角色
	 *
	 * @param role
	 *            角色实体
	 * @return json
	 */
	@RequiresPermissions("sys:role:update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String update(Role role) {
		String json = "";
		try {
			roleService.updateRole(role);
			json = BizdataResponseStatus.COMMON_SUCCESS.getResult("更新角色成功");
		} catch (Exception e) {
			e.printStackTrace();
			json = BizdataResponseStatus.COMMON_ERROR.getResult("修改角色失败,请确认角色名不重复!");
		}
		return json;
	}

	/**
	 * 角色删除
	 *
	 * @param id
	 *            角色id
	 * @return json
	 */
	@RequiresPermissions("sys:role:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public String delete(String id) {
		String json = "";
		if (id.equals("1")) {
			json = BizdataResponseStatus.COMMON_ERROR.getResult("admin为超级管理员角色,初始化数据不可以删除！");
			return json;
		}
		try {
			boolean state = roleService.deleteRole(id);
			if (state) {
				json = BizdataResponseStatus.COMMON_SUCCESS.getResult("删除角色成功");
			} else {
				json = BizdataResponseStatus.COMMON_ERROR.getResult("角色下存在用户,不允许删除！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			json = BizdataResponseStatus.COMMON_ERROR.getResult("删除角色失败");
		}
		return json;
	}

	/**
	 * 添加角色与资源之间的关系
	 *
	 * @param roleResource
	 *            角色资源关系实体
	 * @return json
	 */
	@ResponseBody
	@RequestMapping(value = "/relation/role/{roleid}/resource/{resourceid}", method = RequestMethod.POST)
	public String addRelation(Role_Resource roleResource) {
		String json = "";
		try {
			roleService.addRelation(roleResource);
			json = BizdataResponseStatus.COMMON_SUCCESS.getResult("角色资源关系建立成功");
		} catch (Exception e) {
			e.printStackTrace();
			json = BizdataResponseStatus.COMMON_ERROR.getResult("角色资源关系建立失败");
		}
		return json;
	}

	/**
	 * 解除角色与资源间的关系
	 *
	 * @param roleResource
	 *            角色资源关系实体
	 * @return json
	 */
	@ResponseBody
	@RequestMapping(value = "/disassociate/role/{roleid}/resource/{resourceid}", method = RequestMethod.POST)
	public String disassociate(Role_Resource roleResource) {
		String json = "";
		if (roleResource.getRoleid() == null || roleResource.getResourceid() == null) {
			json = BizdataResponseStatus.COMMON_ERROR.getResult("参数异常，请重试");
			return json;
		}
		try {
			roleService.disassociate(roleResource);
			json = BizdataResponseStatus.COMMON_SUCCESS.getResult("解除角色资源关系成功");
		} catch (RoleException b) {
			b.printStackTrace();
			json = BizdataResponseStatus.COMMON_ERROR.getResult("b.getMessage()");
		} catch (Exception e) {
			e.printStackTrace();
			json = BizdataResponseStatus.COMMON_ERROR.getResult("解除角色资源关系成功");
		}
		return json;
	}
}
