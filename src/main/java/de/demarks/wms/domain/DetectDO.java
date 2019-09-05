package de.demarks.wms.domain;

import java.util.Date;

public class DetectDO {

    /**
     *  检测记录
     */
    private Integer id;

    /**
     *  检测货物ID
     */
    private Integer goodsID;

    /**
     *  检测货物名称
     */
    private String goodsName;

    /**
     *  检测货物批次ID
     */
    private Integer batchID;

    /**
     *  检测货物批次编号
     */
    private String batchCode;

    /**
     *  入库仓库ID
     */
    private Integer repositoryID;

    /**
     *  检测总数
     */
    private long number;

    /**
     *  良品数量
     */
    private long passed;

    /**
     *  划痕数量
     */
    private long scratch;

    /**
     *  故障数量
     */
    private long damage;

    /**
     *  检测日期
     */
    private Date time;

    /**
     *  入库经手人
     */
    private String personInCharge;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoodsID() {
        return goodsID;
    }

    public void setGoodsID(Integer goodsID) {
        this.goodsID = goodsID;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
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

    public long getPassed() {
        return passed;
    }

    public void setPassed(long passed) {
        this.passed = passed;
    }

    public long getScratch() {
        return scratch;
    }

    public void setScratch(long scratch) {
        this.scratch = scratch;
    }

    public long getDamage() {
        return damage;
    }

    public void setDamage(long damage) {
        this.damage = damage;
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
        return "DetectDO{" +
                "id=" + id +
                ", goodsID=" + goodsID +
                ", goodsName='" + goodsName + '\'' +
                ", batchID=" + batchID +
                ", batchCode='" + batchCode + '\'' +
                ", repositoryID=" + repositoryID +
                ", number=" + number +
                ", passed=" + passed +
                ", scratch=" + scratch +
                ", damage=" + damage +
                ", time=" + time +
                ", personInCharge='" + personInCharge + '\'' +
                '}';
    }
}
