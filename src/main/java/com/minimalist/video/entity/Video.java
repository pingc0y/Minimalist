package com.minimalist.video.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Video implements Serializable,Comparable<Video> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;
    private String userName;
    private String assetsName;
    private String assetsUserName;
    private String path;
    private Date createTime;
    private Date updateTime;

    @Override
    public int compareTo(Video v) {
        return ((this.getCreateTime().getTime() > v.getCreateTime().getTime()) ? (-1)
                : ((this.getCreateTime().getTime() == v.getCreateTime().getTime())
                ? 1 : 0));

    }
}
