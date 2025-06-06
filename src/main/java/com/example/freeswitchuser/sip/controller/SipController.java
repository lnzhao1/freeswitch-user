package com.example.freeswitchuser.sip.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.freeswitchuser.common.Result;
import com.example.freeswitchuser.sip.entity.Sip;
import com.example.freeswitchuser.sip.service.ISipService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Api(tags="播打日志")
@RestController
@RequestMapping("/sip")
public class SipController {
	@Autowired
	private ISipService sipService;
	
	/**
	 * 分页列表查询
	 *
	 * @param sip
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value="播打日志-分页列表查询", notes="播打日志-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(Sip sip,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Sip> queryWrapper = new QueryWrapper<>();
		Page<Sip> page = new Page<Sip>(pageNo, pageSize);
		IPage<Sip> pageList = sipService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 * 添加
	 *
	 * @param sip
	 * @return
	 */
	@ApiOperation(value="播打日志-添加", notes="播打日志-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody Sip sip) {
		sipService.save(sip);
		return Result.OK("添加成功！");
	}
	
	/**
	 * 编辑
	 *
	 * @param sip
	 * @return
	 */
	@ApiOperation(value="播打日志-编辑", notes="播打日志-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<?> edit(@RequestBody Sip sip) {
		sipService.updateById(sip);
		return Result.OK("编辑成功!");
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value="播打日志-通过id删除", notes="播打日志-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		sipService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@ApiOperation(value="播打日志-批量删除", notes="播打日志-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.sipService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value="播打日志-通过id查询", notes="播打日志-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		Sip sip = sipService.getById(id);
		return Result.OK(sip);
	}

}
