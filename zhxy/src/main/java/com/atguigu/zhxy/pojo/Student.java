package com.atguigu.zhxy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author...Z.Yao..
 * @create 2022-11-17-19:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_student")
public class Student {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private String sno;

    private String name;

    private Character gender;

    private String password;

    private String email;

    private String telephone;

    private String address;

    private String introducation;

    private String portraitPath;

    private String clazzName;
}
