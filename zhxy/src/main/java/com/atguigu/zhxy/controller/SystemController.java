package com.atguigu.zhxy.controller;

import com.atguigu.zhxy.pojo.Admin;
import com.atguigu.zhxy.pojo.LoginForm;
import com.atguigu.zhxy.pojo.Student;
import com.atguigu.zhxy.pojo.Teacher;
import com.atguigu.zhxy.service.AdminService;
import com.atguigu.zhxy.service.StudentService;
import com.atguigu.zhxy.service.TeacherService;
import com.atguigu.zhxy.util.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author...Z.Yao..
 * @create 2022-11-18-15:16
 *
 */
@RestController
@RequestMapping("/sms/system")
@Api(tags = "系统控制器")
public class SystemController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    //http://localhost:9002/sms/system/updatePwd/admin/123456
    //修改密码
    @PostMapping("/updatePwd/{oldPwd}/{newPwd}")
    @ApiOperation("修改密码接口")
    public Result updatePwd(@RequestHeader("token") String token,
                            @PathVariable("oldPwd") String oldPwd,
                            @PathVariable("newPwd") String newPwd){

        boolean expiration = JwtHelper.isExpiration(token);
        if(expiration){
            //token过期
            return Result.ok().message("token失效，请重新登录后修改密码");
        }
        //获取用户id和用户类型
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);
        //查询用户
        oldPwd = MD5.encrypt(oldPwd);
        newPwd = MD5.encrypt(newPwd);

        switch (userType){
            case 1:
                LambdaQueryWrapper<Admin> admWrapper = new LambdaQueryWrapper<>();
                admWrapper.eq(Admin::getId,userId.intValue());
                admWrapper.eq(Admin::getPassword, oldPwd);
                Admin admin = adminService.getOne(admWrapper);
                if(admin != null){
                    admin.setPassword(newPwd);
                    adminService.saveOrUpdate(admin);
                }else{
                    return Result.fail().message("原密码有误");
                }
                break;
            case 2:
                LambdaQueryWrapper<Student> stuWrapper = new LambdaQueryWrapper<>();
                stuWrapper.eq(Student::getId,userId.intValue());
                stuWrapper.eq(Student::getPassword, oldPwd);
                Student student = studentService.getOne(stuWrapper);
                if(student != null){
                    student.setPassword(newPwd);
                    studentService.saveOrUpdate(student);
                }else{
                    return Result.fail().message("原密码有误");
                }
                break;
            case 3:
                LambdaQueryWrapper<Teacher> tchWrapper = new LambdaQueryWrapper<>();
                tchWrapper.eq(Teacher::getId,userId.intValue());
                tchWrapper.eq(Teacher::getPassword, oldPwd);
                Teacher teacher = teacherService.getOne(tchWrapper);
                if(teacher != null){
                    teacher.setPassword(newPwd);
                    teacherService.saveOrUpdate(teacher);
                }else{
                    return Result.fail().message("原密码有误");
                }
                break;
        }
        return Result.ok();
    }


    @PostMapping("/headerImgUpload")
    @ApiOperation("头像上传接口")
    public Result headerImgUpload(
            @ApiParam("上传的头像图片") @RequestPart MultipartFile multipartFile,
            HttpServletRequest request){
        //设置新的文件名，防止文件名重复
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        String originalFilename = multipartFile.getOriginalFilename();
        int i = originalFilename.lastIndexOf(".");
        String newFileName = uuid + originalFilename.substring(i);
        //保存文件 将文件发送到第三方或者独立的图片服务器上
        String portraitPath = "D:/IDEA.workplace/zhxy/src/main/resources/public/upload/".concat(newFileName);
        try {
            multipartFile.transferTo(new File(portraitPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //响应图片的路径
        String path = "upload/".concat(newFileName);
        return Result.ok(path);
    }

    //通过前端请求过来的token，查询相对应的对象，返回登录对象和登录类型给前端
    @ApiOperation("通过前端请求过来的token，查询相对应的对象，返回登录对象和登录类型给前端")
    @GetMapping("/getInfo")
    public Result getInfo(@ApiParam("前端传过来的token") @RequestHeader("token") String token){//将请求头中对应的数据放在对象中
        //校验token是否过期
        boolean expiration = JwtHelper.isExpiration(token);
        if(expiration){
            return Result.build(null,ResultCodeEnum.TOKEN_ERROR);
        }
        //从token中解析用户id和用户类型
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);
        Map<String ,Object> map = new LinkedHashMap();
        switch (userType){
            case 1:
                Admin admin = adminService.getAdminById(userId);
                map.put("userType",userType);
                map.put("user",admin);
                break;
            case 2:
                Student student = studentService.getStudentById(userId);
                map.put("userType",userType);
                map.put("user",student);
                break;
            case 3:
                Teacher teacher = teacherService.getTeacherById(userId);
                map.put("userType",userType);
                map.put("user",teacher);
                break;
        }

        return Result.ok(map);
    }

    //通过前端传过来的用户名，密码，登录类型，验证码，生成包含用户id和用户类型的token返回给前端
    @PostMapping("/login")
    @ApiOperation("通过前端传过来的用户名，密码，登录类型，验证码，生成包含用户id和用户类型的token返回给前端")
    public Result login(@ApiParam("登录表单信息") @RequestBody LoginForm loginForm,@ApiParam("Http请求") HttpServletRequest request){//将前端传来的数据对应到loginForm对象中
        //验证码校验
        //将前端传来的验证码与之前传入session中的验证码进行校验
        HttpSession session = request.getSession();
        String sessionVerificode = (String)session.getAttribute("verificode");
        String loginFormVerifiCode = loginForm.getVerifiCode();
        if("".equals(sessionVerificode) || null == sessionVerificode){
            return Result.fail().message("验证码失效，请刷新后重试！");
        }
        if(!sessionVerificode.equals(loginFormVerifiCode)){
            return Result.fail().message("验证码有误，请小心输入后重试！");
        }
        //从session域中移除当前验证码
        session.removeAttribute("verificode");
        //用户类型校验

        //准备一个map存放响应的数据
        Map<String, Object> map = new LinkedHashMap<>();
        switch (loginForm.getUserType()){
            case 1:
                try {
                    Admin admin = adminService.login(loginForm);
                    if(admin != null){
                        //如果用户不等于null，将用户类型和用户id转换为密文，以token的名称向客户端返回
                        String token = JwtHelper.createToken(admin.getId().longValue(), 1);
                        map.put("token",token);
                    }else {
                        throw new RuntimeException("用户名或者密码有误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            case 2:
                try {
                    Student student = studentService.login(loginForm);
                    if(student != null){
                        //如果用户不等于null，将用户类型和用户id转换为密文，以token的名称向客户端返回
                        String token = JwtHelper.createToken(student.getId().longValue(), 2);
                        map.put("token",token);
                    }else {
                        throw new RuntimeException("用户名或者密码有误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            case 3:
                try {
                    Teacher teacher = teacherService.login(loginForm);
                    if(teacher != null){
                        //如果用户不等于null，将用户类型和用户id转换为密文，以token的名称向客户端返回
                        String token = JwtHelper.createToken(teacher.getId().longValue(), 3);
                        map.put("token",token);
                    }else {
                        throw new RuntimeException("用户名或者密码有误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
        }
        return Result.fail().message("查无此用户");
    }


    //生成验证码和验证码图片
    @GetMapping("/getVerifiCodeImage")
    @ApiOperation("生成验证码和验证码图片")
    public void getVerifiCodeImage(HttpServletRequest request, HttpServletResponse  response){
        //获取图片
        BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        //获取图片上的验证码
        String verificode = new String ( CreateVerifiCodeImage.getVerifiCode());
        //将验证码文本放入session域，为下一次验证做准备
        HttpSession session = request.getSession();
        session.setAttribute("verificode",verificode);
        //将验证码响应给浏览器
        try {
            ImageIO.write(verifiCodeImage,"JPEG",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
