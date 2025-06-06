package com.example.freeswitchuser.dialTab.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.freeswitchuser.common.Result;
import com.example.freeswitchuser.dialTab.entity.DialTab;
import com.example.freeswitchuser.dialTab.service.IDialTabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

 /**
 * @Description: 播打日志
 * @Author: jeecg-boot
 * @Date:   2025-06-04
 * @Version: V1.0
 */
@RestController
@RequestMapping("/dialTab")
public class DialTabController {
	@Autowired
	private IDialTabService dialTabService;
	
	/**
	 * 分页列表查询
	 *
	 * @param dialTab
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(DialTab dialTab,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<DialTab> queryWrapper = new QueryWrapper<>();
		Page<DialTab> page = new Page<DialTab>(pageNo, pageSize);
		IPage<DialTab> pageList = dialTabService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 * 添加
	 *
	 * @param dialTab
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody DialTab dialTab) {
		dialTabService.save(dialTab);
		return Result.OK("添加成功！");
	}
	
	/**
	 * 编辑
	 *
	 * @param dialTab
	 * @return
	 */
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<?> edit(@RequestBody DialTab dialTab) {
		dialTabService.updateById(dialTab);
		return Result.OK("编辑成功!");
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		dialTabService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.dialTabService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		DialTab dialTab = dialTabService.getById(id);
		return Result.OK(dialTab);
	}


}
