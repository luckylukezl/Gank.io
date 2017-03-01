package com.zl.mvpdemo.presenter.presenter;

import java.util.List;

/**
 * Created by ZL on 2017/3/1.
 */

public interface IDBPresenter<T> {
    void saveToDB(T t);
    void getDatas(List<T> t);
    void getData(T t);
    void deleteData(T t);
}
