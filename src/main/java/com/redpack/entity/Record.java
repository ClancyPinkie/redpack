package com.redpack.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Record implements Serializable {
    public static final long serialVersionUID = 2L;

    private Long id;
    private String sender;
    private String recipient;
    private String money;
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime sendTime;


}
