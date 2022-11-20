package com.atguigu.zhxy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

/**
 * @author...Z.Yao..
 * @create 2022-11-17-19:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_clazz")
public class Clazz {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private String name;

    private Integer number;

    private String introducation;

    private String headmaster;

    private String email;

    private String telephone;

    private String gradeName;
}
