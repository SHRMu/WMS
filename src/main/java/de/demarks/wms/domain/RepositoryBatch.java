package de.demarks.wms.domain;

import java.util.Date;

/**
 * 批次信息
 * @author Shouran
 *
 */
public class RepositoryBatch {

    private Integer id; // 批次ID
    private Integer number; // 批次数
    private String status; // 批次状态
    private String desc;// 批次描述
    private Date time; //批次开始时间
    private Integer repositoryBelongID; // 批次所属仓库

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getRepositoryBelongID() {
        return repositoryBelongID;
    }

    public void setRepositoryBelongID(Integer repositoryBelongID) {
        this.repositoryBelongID = repositoryBelongID;
    }

    @Override
    public String toString() {
        return "RepositoryBatch{" +
                "id=" + id +
                ", number=" + number +
                ", status='" + status + '\'' +
                ", desc='" + desc + '\'' +
                ", time=" + time +
                ", repositoryBelongID=" + repositoryBelongID +
                '}';
    }
}
