package de.demarks.wms.domain;

public class Packet {
    private String trace;
    private Integer repositoryID; //包裹所属仓库
    private String desc; //包裹描述

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

    public Integer getRepositoryID() {
        return repositoryID;
    }

    public void setRepositoryID(Integer repositoryID) {
        this.repositoryID = repositoryID;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Packet{" +
                "trace='" + trace + '\'' +
                ", repositoryID=" + repositoryID +
                ", desc='" + desc + '\'' +
                '}';
    }
}
