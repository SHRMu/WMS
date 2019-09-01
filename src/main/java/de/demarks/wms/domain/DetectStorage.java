package de.demarks.wms.domain;

public class DetectStorage {

    private Integer goodsID;// 货物ID
    private Integer batch; // 货物批次
    private Integer repositoryID;// 仓库ID
    private long passed; // 良品数
    private long scratch; // 划痕数
    private long damage; // 故障数

    public Integer getGoodsID() {
        return goodsID;
    }

    public void setGoodsID(Integer goodsID) {
        this.goodsID = goodsID;
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


    @Override
    public String toString() {
        return "DetectStorage{" +
                "goodsID=" + goodsID +
                ", batch=" + batch +
                ", repositoryID=" + repositoryID +
                ", passed=" + passed +
                ", scratch=" + scratch +
                ", damage=" + damage +
                '}';
    }
}
