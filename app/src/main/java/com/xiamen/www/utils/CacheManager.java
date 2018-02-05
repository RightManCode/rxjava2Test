package com.xiamen.www.utils;

import com.xiamen.www.bean.FoodListBean;

/**
 * Created by admin on 2018/2/5.
 */

public class CacheManager {
    private static CacheManager instance;
    private FoodListBean footListJson;

    private CacheManager() {
    }

    public static CacheManager getInstance() {
        if (instance == null) {
            instance = new CacheManager();
        }
        return instance;
    }

    public FoodListBean getFoodListData() {
        return this.footListJson;
    }

    public void setFoodListData(FoodListBean data) {
        this.footListJson = data;
    }
}
