package com.example.freeswitchuser.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.freeswitchuser.dialTab.entity.DialTab;
import com.example.freeswitchuser.dialTab.service.IDialTabService;
import com.example.freeswitchuser.sip.entity.Sip;
import com.example.freeswitchuser.sip.service.ISipService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RestController
public class CommonController {

    @Autowired
    private ISipService iSipService;

    @Autowired
    private IDialTabService iDialTabService;

    @RequestMapping(value = "/directory",method = RequestMethod.POST,produces = MediaType.APPLICATION_XML_VALUE)
    public String directory(HttpServletRequest request){
        Map<String,String> map= new HashMap<String,String>();
        String action= request.getParameter("action");
        String sip ="";
        /**
         *根据参数action参数判断用户的动作信息
         *获取指定的sip帐号信息
        **/
        if("user_call".equals(action)){
            sip = request.getParameter("user");
        }else{
            sip = request.getParameter("sip_to_user");
        }
        /**
         *根据sip帐号信息，从数据库查询相应的用户信息
         **/
        QueryWrapper<Sip> queryWrapper = new QueryWrapper<Sip>();
        queryWrapper.eq("user",sip);
        Sip u = iSipService.getOne(queryWrapper);
        String res ="";
        try {
            /**
             *用户为空时直接返回注册失败的xm1*用户不为空时生成注册的xm信息
            **/
            if (u == null){
//                res = BeelFileTool.setfile("/conf/directory_err.xml",map);
                Path path = Paths.get("src"+ File.separator+"main"+ File.separator+"resources" + File.separator + "config" + File.separator
                                      + "directory_err.xml");
                res = new String(Files.readAllBytes(path));
            } else {
                /**
                 *使用sip信息，参数:sip to host(ip地址)以及数据库中获取的密码信息:pwd
                 *进行md5加密
                 *加密失败时返回错误信息
                 * * **/

//                String alHash = MD5.GetMD5code(sip + ":" + request.getParameter("sip to host") + ":" + u.getPwd());
                String alHash = DigestUtils.md5DigestAsHex((sip + ":" + request.getParameter("sip to host") + ":" + u.getPwd()).getBytes());
                if (alHash == null) {
//                    res = BeelFileTool.set_file("/conf/directory_err.xml", map);
                    Path path = Paths.get("src"+ File.separator+"main"+ File.separator+"resources" + File.separator + "config" + File.separator
                                          + "directory_err.xml");
//                    res = BeelFileTool.set_file("/conf/directory_err.xml", map);
                    res = new String(Files.readAllBytes(path));
                    return res;
                }

                map.put("alHash", alHash);
                map.put("password", u.getPwd());
                map.put("accountcode", sip);
                map.put("domain", request.getParameter("sip_to_host"));
                map.put("sip", sip);
                map.put("string", "${sofia_contact(${dialed_user}@${dialed_domain})}");
//                res = BeelFileTool.set_file("/conf/directory.xml",map);
                Path path = Paths.get("src"+ File.separator+"main"+ File.separator+"resources" + File.separator + "config" + File.separator
                                      + "directory.xml");
                    String template = new String(Files.readAllBytes(path));
                    /**
                     * 生成参数map，调动方法:
                     replaceargsNew，或者使用bee1工具类 * 根据模版生成注册的xm1信息
                     */
                    res = this.replaceArgsNew(template, map);
                }
            }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return res;
    }
    
    public  String replaceArgsNew(String template, Map<String, String> data) {

        // sb用来存储替换过的内容，它会把多次处理过的字符串按源字符串序 存储起来。

        StringBuffer sb = new StringBuffer();

           try{

                Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}");

                Matcher matcher = pattern.matcher(template);

               while(matcher.find()) {

                        String name = matcher.group(1);// 键名

                        String value = (String) data.get(name);// 键值

                    if(value != null ){

                            value = value.replace("\\", "\\\\\\");

                            value = value.replace("$", "\\$");

                            matcher.appendReplacement(sb, value);
                        }

                    }

                    matcher.appendTail(sb);

                } catch(Exception e){

                    e.printStackTrace();

                }

           return sb.toString(); // 加一个空行（结束行）
    }

    @RequestMapping(value = "/dail",method = RequestMethod.POST,produces = MediaType.APPLICATION_XML_VALUE)
    public String dail(HttpServletRequest request){
        try {
            String Hunt_Caller_ID_Number = request.getParameter("Hunt-Caller-ID-Number");//月的号码
            String variable_sip_to_user = request.getParameter("Caller-Destination-Number");//呼入ip
            String variable_sip_contact_host = request.getParameter("variable_sip_contact_host");
            String ret="";
            QueryWrapper<Sip> queryWrapper = new QueryWrapper<Sip>();
            queryWrapper.eq("user",variable_sip_to_user);
            Sip sip_user = iSipService.getOne(queryWrapper);
            /**
             *若被叫为sip号码，呼叫送到相应的sip账户
             */
            if(sip_user!=null) {
                Map<String, String> paramMap = new HashMap<String, String>();
                String effective_caller_id_number = Hunt_Caller_ID_Number;
                String data = "user/" + variable_sip_to_user;
                paramMap.put("call prefix", "");
                paramMap.put("effective caller id number", effective_caller_id_number);
                paramMap.put("data", data);
                Path path = Paths.get("src"+ File.separator+"main"+ File.separator+"resources" + File.separator + "config" + File.separator
                                      + "dial.xml");
                String template = new String(Files.readAllBytes(path));
//                    ret = BeelFileTool.set_file("/conf/dial.xml", paramMap);
                ret = this.replaceArgsNew(template, paramMap);
                return ret;
            }
            //获取拔号方案列表
            QueryWrapper<DialTab> queryWrapper1 = new QueryWrapper<>();
            List<DialTab> dialList = iDialTabService.list(queryWrapper1);
            /**
             *循环查询拨号方案列表信息
             若主叫号码，同相应的拨号方案设定的主叫号码
             则呼叫送到指定拨号方案设定的落地网关言
             */
            for (int i=0;i<dialList.size();i++){
                String call_number=dialList.get(i).getCallNumber();
                if(StringUtils.isEmpty(call_number)) {
                    continue;
                }
                if (call_number.equals(Hunt_Caller_ID_Number)) {
                    String gateway = dialList.get(i).getGateway();
                    if (StringUtils.isEmpty(gateway)) {
                        continue;
                    }
                    String call_prefix = dialList.get(i).getCallPrefix();
                    String tranfer_prefix = dialList.get(i).getTranferPrefix();
                    Map<String, String> paramMap = new HashMap<String, String>();
                    String data = "sofia/gateway/" + gateway + "/" + tranfer_prefix + "$1";
                    paramMap.put("effective_caller_id_number", Hunt_Caller_ID_Number);
                    paramMap.put("call_prefix", call_prefix);
                    paramMap.put("data", data);
//                    ret = BeelFileTool.set_file("/conf/dial.xml", paramMap);
                    Path path = Paths.get("src"+ File.separator+"main"+ File.separator+"resources" + File.separator + "config" + File.separator
                                          + "dial.xml");
                    String template = new String(Files.readAllBytes(path));
                    ret = this.replaceArgsNew(template, paramMap);
                    return ret;
                }
            }
            for(int i=0;i<dialList.size();i++){
                String call_prefix = dialList.get(i).getCallPrefix();
                if(variable_sip_to_user.startsWith(call_prefix)){
                    String gateway=dialList.get(i).getGateway();
                    if (StringUtils.isEmpty(gateway)){
                        continue;
                    }
                    String tranfer_prefix = dialList.get(i).getTranferPrefix();
                    Map<String,String> paramMap=new HashMap<String,String>();
                    String data="sofia/qateway/"+gateway+"/"+tranfer_prefix+"$1";
                    paramMap.put ("effective caller id number", Hunt_Caller_ID_Number);
                    paramMap.put("call_prefix",call_prefix);
                    paramMap.put("data",data);
//                    ret=BeelFileTool.set_file("/conf/dial.xml",paramMap);
                    Path path = Paths.get("src"+ File.separator+"main"+ File.separator+"resources" + File.separator + "config" + File.separator
                                          + "dial.xml");
                    String template = new String(Files.readAllBytes(path));
                    ret = this.replaceArgsNew(template, paramMap);
                    return ret;
                }
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return  "";
    }
}
