package de.demarks.wms.domain;


import java.util.Date;

/**
 * 出库记录
 *
 * @author huanyingcool
 */
public class StockOutDO {

    /**
     * 出库记录ID
     */
    private Integer id;

    /**
     * 包裹运单号
     */
    private String packet;

    /**
     * 批次ID
     */
    private Integer batchID;

    /**
     * 批次编号
     */
    private String batchCode;

    /**
     * 客户ID
     */
    private Integer customerID;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 商品ID
     */
    private Integer goodsID;

    /**
     * 商品名称
     */
    private String goodName;

    /**
     * 出库仓库ID
     */
    private Integer repositoryID;

    /**
     * 商品出库数量
     */
    private long number;

    /**
     * 出库日期
     */
    private Date time;

    /**
     * 出库经手人
     */
    private String personInCharge;// 经手人

    public Integer getRepositoryID() {
        return repositoryID;
    }

    public void setRepositoryID(Integer repositoryID) {
        this.repositoryID = repositoryID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPacket() {
        return packet;
    }

    public void setPacket(String packet) {
        this.packet = packet;
    }

    public Integer getBatchID() {
        return batchID;
    }

    public void setBatchID(Integer batchID) {
        this.batchID = batchID;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getGoodsID() {
        return goodsID;
    }

    public void setGoodsID(Integer goodsID) {
        this.goodsID = goodsID;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getPersonInCharge() {
        return personInCharge;
    }

    public void setPersonInCharge(String personInCharge) {
        this.personInCharge = personInCharge;
    }

    @Override
    public String toString() {
        return "StockOutDO{" +
                "id=" + id +
                ", packet='" + packet + '\'' +
                ", batchID=" + batchID +
                ", batchCode='" + batchCode + '\'' +
                ", customerID=" + customerID +
                ", customerName='" + customerName + '\'' +
                ", goodsID=" + goodsID +
                ", goodName='" + goodName + '\'' +
                ", repositoryID=" + repositoryID +
                ", number=" + number +
                ", time=" + time +
                ", personInCharge='" + personInCharge + '\'' +
                '}';
    }
}
