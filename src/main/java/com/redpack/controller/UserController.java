package com.redpack.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.redpack.common.R;
import com.redpack.entity.User;
import com.redpack.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

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
            return R.error("登录失败");
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
            return R.error("注册失败");
        }

        //如果不冲突注册成功，对数据库insert
        userService.save(user);

        return R.success("注册成功");
    }

    /**
     * 设置用户状态
     * @param user
     * @return
     */
    @ApiOperation("设置用户状态")
    @PostMapping("/status")
    public R<String> set_status(@RequestBody User user){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId,user.getId());
        User one = userService.getOne(queryWrapper);
        if (one.getStatus() == 1){
            one.setStatus(0);
            userService.updateById(one);
            return R.success("已禁用");
        }else{
            one.setStatus(1);
            userService.updateById(one);
            return R.success("已启用");
        }
//        return R.error("状态设置失败");
    }

    /**
     * 员工信息修改
     * @param user
     * @return
     */
    @ApiOperation("员工修改")
    @GetMapping("/update")
    public R<String> update(@RequestBody User user){
        userService.updateById(user);
        return null;
    }

    /**
     * 根据id删除员工
     * @param id
     * @return
     */
    @ApiOperation("员工删除")
    @DeleteMapping("/delete")
    public R<String> delete(Long id){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId,id);
        User user = userService.getOne(queryWrapper);
        if (user == null){
            return R.error("删除失败");
        }
        userService.removeById(id);
        return R.success("删除成功");
    }
}