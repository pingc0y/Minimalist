package com.minimalist.assetsMy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.minimalist.assets.entity.Assets;
import com.minimalist.assetsUser.entity.AssetsUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssetsMy extends Assets implements Serializable {
    private static final long serialVersionUID = 1L;
    private String assetsId;
    private String assetsUserId;


}
