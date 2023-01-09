package com.redpack.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.redpack.common.R;
import com.redpack.entity.Record;
import com.redpack.service.RecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/record")
@Api(tags = "红包record接口")
public class RecordController {

    @Autowired
    private RecordService recordService;

    /**
     * 分页
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @ApiOperation("分页")
    @GetMapping("/page")
    public R<Page> page(Integer page, Integer pageSize, String name){
        log.info("page = {},pageSize = {},name = {}" ,page,pageSize,name);

        //构造分页构造器
        Page pageInfo = new Page(page,pageSize);

        //构造条件构造器
        LambdaQueryWrapper<Record> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Record::getSendTime);

        //执行查询
        recordService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * 红包记录查询
     * @param searchRecord
     * @return
     */
    @ApiOperation("模糊查询")
    @PostMapping("/list")
    public R<List<Record>> search(@RequestBody Record searchRecord){

        LambdaQueryWrapper<Record> queryWrapper = new LambdaQueryWrapper<>();

        //模糊查询
        queryWrapper.like(Record::getSender,searchRecord.getSender())
                    .like(Record::getRecipient,searchRecord.getRecipient())
                    .like(searchRecord.getStatus()!=null,Record::getStatus,searchRecord.getStatus());

        List<Record> list = recordService.list(queryWrapper);
        return R.success(list);
    }

    /**
     * 红包信息修改
     * 报错:前端传过来的值即将消失 ; 解决方案:把Get改为Post
     * @param updateRecord
     * @return
     */
    @ApiOperation("红包记录修改")
    @PostMapping("/update")
    public R<String> update(@RequestBody Record updateRecord){
        if (updateRecord == null){
            return R.error("修改失败!");
        }
        recordService.updateById(updateRecord);
        return R.success("修改成功");
    }

    /**
     * 根据id删除记录
     * @param id
     * @return
     */
    @ApiOperation("红包记录删除")
    @DeleteMapping("/delete")
    public R<String> delete(@RequestBody Long id){
        LambdaQueryWrapper<Record> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Record::getId,id);
        Record user = recordService.getOne(queryWrapper);
        if (user == null){
            return R.error("删除失败");
        }
        recordService.removeById(id);
        return R.success("删除成功");
    }

    /**
     * 根据id数组删除多条记录
     * @param ids
     * @return
     */
    @ApiOperation("记录批量删除")
    @DeleteMapping("/deletes")
    public R<String> delete(@RequestBody Long[] ids){
        for (Long id : ids){
            LambdaQueryWrapper<Record> idswrapper = new LambdaQueryWrapper<>();
            idswrapper.eq(Record::getId,id);
            Record user = recordService.getOne(idswrapper);
            if (user == null){
                return R.error("删除失败");
            }
            recordService.removeById(id);
        }
        return R.success("删除成功");
    }

    @ApiOperation("发送红包方法(即新增)")
    @PostMapping("/send")
    public R<String> sendRedpack(String sender,String recipient){
        //新增红包记录
        Record record = new Record();
        record.setSender(sender);
        record.setRecipient(recipient);
        record.setStatus(1);
        recordService.save(record);
        return R.success("发送成功");
    }
}
