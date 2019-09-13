package de.demarks.wms.domain;


import java.util.Date;

/**
 * 入库记录
 *
 * @author Shouran
 */
public class StockInDO {

    /**
     * 入库记录
     */
    private Integer id;

    /**
     * 包裹号
     */
    private Integer packetID;

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

    private Integer batchID;

    private String batchCode;

    /**
     * 入库仓库ID
     */
    private Integer repositoryID;

    /**
     * 入库数量
     */
    private long number;

    /**
     * 入库日期
     */
    private Date time;

    /**
     * 入库经手人
     */
    private String personInCharge;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPacketID() {
        return packetID;
    }

    public void setPacketID(Integer packetID) {
        this.packetID = packetID;
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

    public Integer getRepositoryID() {
        return repositoryID;
    }

    public void setRepositoryID(Integer repositoryID) {
        this.repositoryID = repositoryID;
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
        return "StockInDO{" +
                "id=" + id +
                ", packetID=" + packetID +
                ", customerID=" + customerID +
                ", customerName='" + customerName + '\'' +
                ", goodsID=" + goodsID +
                ", goodName='" + goodName + '\'' +
                ", batchID=" + batchID +
                ", batchCode='" + batchCode + '\'' +
                ", repositoryID=" + repositoryID +
                ", number=" + number +
                ", time=" + time +
                ", personInCharge='" + personInCharge + '\'' +
                '}';
    }
}
