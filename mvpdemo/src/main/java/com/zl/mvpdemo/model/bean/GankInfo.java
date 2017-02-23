package com.zl.mvpdemo.model.bean;

import java.util.List;

/**
 * Created by ZL on 2017/2/19.
 */

public class GankInfo<T> {
    private boolean error;

    private T results ;

    public void setError(boolean error){
        this.error = error;
    }
    public boolean getError(){
        return this.error;
    }
    public void setResults(T results){
        this.results = results;
    }
    public T getResults(){
        return this.results;
    }
}
