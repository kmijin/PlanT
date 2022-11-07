package com.example.plant01.store;

public class store_Goods {
    String goodsImg, storeName, goodsTitle, goodsReview, goodsPrice;

    public store_Goods(String s){}

    public store_Goods(String goodsImg, String storeName, String goodsTitle, String goodsReview, String goodsPrice) {
        this.goodsImg = goodsImg;
        this.storeName = storeName;
        this.goodsTitle = goodsTitle;
        this.goodsReview = goodsReview;
        this.goodsPrice = goodsPrice;
    }
    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsPrice) {
        this.goodsImg = goodsImg;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public String getGoodsReview() {
        return goodsReview;
    }

    public void setGoodsReview(String goodsReview) {
        this.goodsReview = goodsReview;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

}
