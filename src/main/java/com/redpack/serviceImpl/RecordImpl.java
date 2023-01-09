package com.redpack.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redpack.entity.Record;
import com.redpack.mapper.RecordMapper;
import com.redpack.service.RecordService;
import org.springframework.stereotype.Service;

@Service
public class RecordImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {
}
