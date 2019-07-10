package com.dodoca.create_image.pojo;

import java.util.Date;

public class AloneActivityRecode {
    private Integer id;

    private Integer merchantId;

    private Integer shopId;

    private Integer goodsId;

    private Integer productId;

    private Integer stockLock;

    private Integer aloneId;

    private String actType;

    private Date createdAt;

    private Date finishAt;

    private Date updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getStockLock() {
        return stockLock;
    }

    public void setStockLock(Integer stockLock) {
        this.stockLock = stockLock;
    }

    public Integer getAloneId() {
        return aloneId;
    }

    public void setAloneId(Integer aloneId) {
        this.aloneId = aloneId;
    }

    public String getActType() {
        return actType;
    }

    public void setActType(String actType) {
        this.actType = actType == null ? null : actType.trim();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getFinishAt() {
        return finishAt;
    }

    public void setFinishAt(Date finishAt) {
        this.finishAt = finishAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}