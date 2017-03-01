package com.zl.mvpdemo.model.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by ZL on 2017/2/22.
 */

public class GankData implements Serializable{
    private String _id;

    private String createdAt;

    private String desc;

    private Date publishedAt;

    private String source;

    private String type;

    private String url;

    private boolean used;

    private String who;

    private List<String> images;

    private int height;

    private int width;

    public void set_id(String _id){
        this._id = _id;
    }
    public String get_id(){
        return this._id;
    }
    public void setCreatedAt(String createdAt){
        this.createdAt = createdAt;
    }
    public String getCreatedAt(){
        return this.createdAt;
    }
    public void setDesc(String desc){
        this.desc = desc;
    }
    public String getDesc(){
        return this.desc;
    }
    public void setPublishedAt(Date publishedAt){
        this.publishedAt = publishedAt;
    }
    public Date getPublishedAt(){
        return this.publishedAt;
    }
    public void setSource(String source){
        this.source = source;
    }
    public String getSource(){
        return this.source;
    }
    public void setType(String type){
        this.type = type;
    }
    public String getType(){
        return this.type;
    }
    public void setUrl(String url){
        this.url = url;
    }
    public String getUrl(){
        return this.url;
    }
    public void setUsed(boolean used){
        this.used = used;
    }
    public boolean getUsed(){
        return this.used;
    }
    public void setWho(String who){
        this.who = who;
    }
    public String getWho(){
        return this.who;
    }
    public void setHeight(int height){
        this.height = height;
    }
    public int getHeight(){
        return this.height;
    }
    public void setWidth(int width){
        this.width = width;
    }
    public int getWidth(){
        return this.width;
    }
    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
