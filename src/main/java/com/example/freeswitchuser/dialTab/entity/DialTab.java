package com.example.freeswitchuser.dialTab.entity;

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
@TableName("dial_tab")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="dial_tab对象", description="播打日志")
public class DialTab {
    
	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
	private Integer id;
	/**被叫前缀*/
    @ApiModelProperty(value = "被叫前缀")
	private String callPrefix;
	/**网关名称*/
    @ApiModelProperty(value = "网关名称")
	private String gateway;
	/**转接前缀*/
    @ApiModelProperty(value = "转接前缀")
	private String tranferPrefix;
	/**主叫号码*/
    @ApiModelProperty(value = "主叫号码")
	private String callNumber;
	/**creatAt*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "creatAt")
	private Date creatAt;
}
