package com.zl.mvpdemo.model.bean.dao.gankDataDb;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.util.Date;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by ZL on 2017/2/28.
 */

@Entity
public class GankDataDB {
    @Id
    private Long id;

    @Property(nameInDb = "desc")
    private String desc;

    @Property(nameInDb = "publishedAt")
    private Date publishedAt;


    @Property(nameInDb = "type")
    private String type;

    @Property(nameInDb = "url")
    @Unique
    private String url;


    @Property(nameInDb = "who")
    private String who;

    @Property(nameInDb = "image_id")
    private int image_id;

    @Generated(hash = 455113080)
    public GankDataDB(Long id, String desc, Date publishedAt, String type,
            String url, String who, int image_id) {
        this.id = id;
        this.desc = desc;
        this.publishedAt = publishedAt;
        this.type = type;
        this.url = url;
        this.who = who;
        this.image_id = image_id;
    }

    @Generated(hash = 149686239)
    public GankDataDB() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getPublishedAt() {
        return this.publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWho() {
        return this.who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public int getImage_id() {
        return this.image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

}
