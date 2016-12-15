package com.bizdata.admin.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bizdata.admin.domain.Resource;
import com.bizdata.admin.service.ResourceService;
import com.bizdata.commons.constant.BizdataResponseStatus;
import com.google.gson.Gson;

/**
 * 资源操作Controller
 *
 * @version 1.0
 *
 * @author sdevil507
 */
@Controller
@RequestMapping("/admin/resource")
public class AdminResourceController {

	@Autowired
	private ResourceService resourceService;

	/**
	 * 返回资源管理list页面 对应templates下的/admin/resource/resource_list
	 *
	 * @return ModelAndView
	 */
	@RequiresPermissions("sys:resource:view")
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list() {
		return new ModelAndView("admin_page/resource/resource_list");
	}

	/**
	 * 返回资源管理的json数据
	 *
	 * @return String类型json
	 * @throws Exception
	 */
	@RequiresPermissions("sys:resource:view")
	@RequestMapping(value = "/resourceList", method = RequestMethod.GET)
	@ResponseBody
	public String resourceList() throws Exception {
		Map<String, Object> resourceMap = new HashMap<String, Object>();
		resourceMap.put("rows", resourceService.findAll());
		return new Gson().toJson(resourceMap);
	}

	/**
	 * 添加资源操作
	 *
	 * @param resource
	 *            资源实体
	 * @return json
	 */
	@RequiresPermissions("sys:resource:create")
	@ResponseBody
	@RequestMapping(value = "/addResource", method = RequestMethod.POST)
	public String addResource(Resource resource) {
		String json = "";
		try {
			resourceService.insertResource(resource);
			json = BizdataResponseStatus.COMMON_SUCCESS.getResult("新增资源成功");
		} catch (Exception e) {
			e.printStackTrace();
			json = BizdataResponseStatus.COMMON_ERROR.getResult("新增资源失败");
		}
		return json;
	}

	/**
	 * 删除资源
	 *
	 * @param id
	 *            删除资源的id
	 * @return json
	 */
	@RequiresPermissions("sys:resource:delete")
	@ResponseBody
	@RequestMapping(value = "/deleteResource", method = RequestMethod.POST)
	public String deleteResource(String id) {
		String json = "";
		try {
			resourceService.deleteResource(id);
			json = BizdataResponseStatus.COMMON_SUCCESS.getResult("删除资源成功");
		} catch (Exception e) {
			e.printStackTrace();
			json = BizdataResponseStatus.COMMON_ERROR.getResult("删除资源失败");
		}
		return json;
	}

	/**
	 * 更新资源
	 *
	 * @param resource
	 *            资源实体
	 * @return json
	 */
	@RequiresPermissions("sys:resource:update")
	@ResponseBody
	@RequestMapping(value = "/updateResource", method = RequestMethod.POST)
	public String updateResource(Resource resource) {
		String json = "";
		try {
			// 因为jqgrid update操作不传递type，所以从原数据获取
			Resource old_resource = resourceService.findSysResource(resource.getId());
			resource.setType(old_resource.getType());
			resource.setIsleaf(old_resource.getIsleaf());
			resourceService.updateResource(resource);
			json = BizdataResponseStatus.COMMON_SUCCESS.getResult("更新资源成功");
		} catch (Exception e) {
			e.printStackTrace();
			json = BizdataResponseStatus.COMMON_ERROR.getResult("更新资源失败");
		}
		return json;
	}
}
