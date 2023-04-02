package com.wbm.forum.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 
 * @TableName like
 */
@TableName(value ="`like`")//like 为sql关键字，mp不加``会报错
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Like implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    @TableField(value = "pid")
    private Integer pid;

    /**
     * 
     */
    @TableField(value = "cid")
    private Integer cid;

    /**
     * 
     */
    @TableField(value = "sub_id")
    private Integer subId;

    /**
     * 
     */
    @TableField(value = "uid")
    private Integer uid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}