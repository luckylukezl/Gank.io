package com.zl.mvpdemo.model.bean.dao.imagesDB;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by ZL on 2017/2/28.
 */

@Entity
public class ImagesDB {
    @Id
    private Long id;

    @Property(nameInDb = "image_id")
    private int image_id;

    @Property(nameInDb = "image_url")
    @Unique
    private String image_url;

    @Generated(hash = 1858224333)
    public ImagesDB(Long id, int image_id, String image_url) {
        this.id = id;
        this.image_id = image_id;
        this.image_url = image_url;
    }

    @Generated(hash = 1157765470)
    public ImagesDB() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getImage_id() {
        return this.image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public String getImage_url() {
        return this.image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
