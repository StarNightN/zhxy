package com.atguigu.zhxy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author...Z.Yao..
 * @create 2022-11-17-19:01
 */
@TableName("tb_admin")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private String name;

    private Character gender;

    private String password;

    private String email;

    private String telephone;

    private String address;

    private String portraitPath;//头像的图片路径
}
