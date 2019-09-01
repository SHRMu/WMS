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
    private Integer goodID;

    /**
     *  检测货物批次
     */
    private Integer batch;

    /**
     *  入库仓库ID
     */
    private Integer repositoryID;

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

    public Integer getGoodID() {
        return goodID;
    }

    public void setGoodID(Integer goodID) {
        this.goodID = goodID;
    }

    public Integer getBatch() {
        return batch;
    }

    public void setBatch(Integer batch) {
        this.batch = batch;
    }

    public Integer getRepositoryID() {
        return repositoryID;
    }

    public void setRepositoryID(Integer repositoryID) {
        this.repositoryID = repositoryID;
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
                ", goodID=" + goodID +
                ", batch=" + batch +
                ", repositoryID=" + repositoryID +
                ", passed=" + passed +
                ", scratch=" + scratch +
                ", damage=" + damage +
                ", time=" + time +
                ", personInCharge='" + personInCharge + '\'' +
                '}';
    }
}
