package de.demarks.wms.domain;

/**
 * 仓库库存
 * 
 * @author Shouran
 *
 */
public class Storage {

	private Integer goodsID;// 货物ID
	private String goodsName;// 货物名称
	private String goodsSize;// 货物规格
	private String goodsType;// 货物类型
	private double goodsWeight;// 货物重量
	private Integer batchID; //批次ID
	private String batchCode; //批次编号
	private Integer repositoryID;// 仓库ID
	private Long number;// 待检测


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

	public String getGoodsSize() {
		return goodsSize;
	}

	public void setGoodsSize(String goodsSize) {
		this.goodsSize = goodsSize;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public double getGoodsWeight() {
		return goodsWeight;
	}

	public void setGoodsWeight(double goodsWeight) {
		this.goodsWeight = goodsWeight;
	}

	public Integer getRepositoryID() {
		return repositoryID;
	}

	public void setRepositoryID(Integer repositoryID) {
		this.repositoryID = repositoryID;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
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

	@Override
	public String toString() {
		return "Storage{" +
				"goodsID=" + goodsID +
				", goodsName='" + goodsName + '\'' +
				", goodsSize='" + goodsSize + '\'' +
				", goodsType='" + goodsType + '\'' +
				", goodsWeight=" + goodsWeight +
				", batchID=" + batchID +
				", batchCode='" + batchCode + '\'' +
				", repositoryID=" + repositoryID +
				", number=" + number +
				'}';
	}
}
