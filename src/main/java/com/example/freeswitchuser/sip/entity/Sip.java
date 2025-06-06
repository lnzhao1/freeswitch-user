package com.example.freeswitchuser.sip.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Description: 播打日志
 * @Author: jeecg-boot
 * @Date:   2025-06-04
 * @Version: V1.0
 */
@Data
@TableName("sip")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="sip对象", description="播打日志")
public class Sip {
    
	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
	private Integer id;
	/**用户名*/
    @ApiModelProperty(value = "用户名")
	private String user;
	/**密码*/
    @ApiModelProperty(value = "密码")
	private String pwd;
	/**creatAt*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "creatAt")
	private Date creatAt;
}
