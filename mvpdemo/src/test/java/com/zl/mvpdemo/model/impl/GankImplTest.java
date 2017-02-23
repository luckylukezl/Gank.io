package com.zl.mvpdemo.model.impl;

import com.zl.mvpdemo.model.model.IGankModel;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ZL on 2017/2/22.
 */
public class GankImplTest {
    @Test
    public void getGankData() throws Exception {
        IGankModel model = new GankImpl();
        model.getGankData("Android",1);
    }

}