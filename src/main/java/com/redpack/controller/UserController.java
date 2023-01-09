package com.redpack.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.redpack.common.R;
import com.redpack.entity.User;
import com.redpack.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@Slf4j
@RequestMapping("/user")
@Api(tags = "user接口")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 员工登录
     * @param request 传给页面的数据
     * @param user 传过来的用户
     * @return
     */
    @ApiOperation("登录")
    @PostMapping("/login")
    public R<User> login(HttpServletRequest request, @RequestBody User user){

        //1、将页面提交的密码password进行md5加密处理
        String password = user.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //2、根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getName,user.getName());
        User u = userService.getOne(queryWrapper);

        //3、如果没有查询到则返回登录失败结果
        if(u == null){
            return R.error("账户不存在");
        }

        //4、密码比对，如果不一致则返回登录失败结果
        if(!u.getPassword().equals(password)){
            return R.error("登录失败");
        }

        //5、查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if(u.getStatus() == 0){
            return R.error("账号已禁用");
        }

        //6、登录成功，将员工id存入Session并返回登录成功结果
        request.getSession().setAttribute("user",u.getId());
        return R.success(u);
    }

    /**
     * 用户登出
     * @param request
     */
    @ApiOperation("用户登出")
    @PostMapping("/logout")
    public void logout(HttpServletRequest request){
        request.getSession().removeAttribute("user");
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @ApiOperation("用户注册")
    @PostMapping("/register")
    public R<String> register(@RequestBody User user){
        //接受输入进来的账户和密码
        String name = user.getName();
        String password = user.getPassword();
        //MD5加密
        user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));

        //检查账户是否和数据库里的账户冲突
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getName,name);
        User one = userService.getOne(queryWrapper);

        //如果冲突则返回注册失败
        if (one != null){
            return R.error("账号已存在");
        }

        //如果不冲突注册成功，对数据库insert
        user.setStatus(1);
        userService.save(user);

        return R.success("注册成功");
    }

    /**
     * 分页
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @ApiOperation("用户分页")
    @GetMapping("/page")
    public R<Page> page(Integer page, Integer pageSize, String name){
        log.info("page = {},pageSize = {},name = {}" ,page,pageSize,name);

        //构造分页构造器
        Page pageInfo = new Page(page,pageSize);

        //构造条件构造器
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name),User::getName,name);
        queryWrapper.orderByDesc(User::getUpdateTime);

        //执行查询
        userService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * 员工查询
     * @param searchUser
     * @return
     */
    @ApiOperation("条件查询员工")
    @PostMapping("/list")
    public R<List<User>> search(@RequestBody User searchUser){

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();

        //模糊查询
        queryWrapper.like(User::getName,searchUser.getName())
                .like(searchUser.getStatus()!=null,User::getStatus,searchUser.getStatus());

        List<User> list = userService.list(queryWrapper);
        return R.success(list);
    }

    /**
     * 员工信息修改
     * 报错一次：前端传过来的值即将消失；解决方案为把Get方式改为Post
     * @param updateUser
     * @return
     */
    @ApiOperation("员工修改")
    @PostMapping("/update")
    public R<String> update(@RequestBody User updateUser){
        if (updateUser == null){
            return R.error("修改失败!");
        }
        userService.updateById(updateUser);
        return R.success("修改成功");
    }

    /**
     * 根据id删除员工
     * @param id
     * @return
     */
    @ApiOperation("员工删除")
    @DeleteMapping("/delete")
    public R<String> delete(@RequestBody Long id){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId,id);
        User user = userService.getOne(queryWrapper);
        if (user == null){
            return R.error("删除失败");
        }
        userService.removeById(id);
        return R.success("删除成功");
    }

    /**
     * 根据id数组删除多名员工
     * @param ids
     * @return
     */
    @ApiOperation("员工批量删除")
    @DeleteMapping("/deletes")
    public R<String> delete(@RequestBody Long[] ids){
        for (Long id : ids){
            LambdaQueryWrapper<User> idswrapper = new LambdaQueryWrapper<>();
            idswrapper.eq(User::getId,id);
            User user = userService.getOne(idswrapper);
            if (user == null){
                return R.error("删除失败");
            }
            userService.removeById(id);
        }
        return R.success("删除成功");
    }

    @ApiOperation("发送红包方法")
    @PostMapping("/send")
    public R<String> sendRedpack(String sender,String recipient){
        //发送红包
        return null;
    }
}
