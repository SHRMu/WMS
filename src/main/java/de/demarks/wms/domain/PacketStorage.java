package de.demarks.wms.domain;

/**
 * 仓库库存
 * 
 * @author Shouran
 *
 */
public class PacketStorage {

	private Integer packetID;
	private String packetTrace;
	private Integer goodsID;// 货物ID
	private String goodsName;// 货物名称
	private Integer customerID;
	private String customerName;
	private Integer repositoryID;// 仓库ID
	private Long number; //预报数量
	private Long storage; //到货后未处理数量

	public Integer getPacketID() {
		return packetID;
	}

	public void setPacketID(Integer packetID) {
		this.packetID = packetID;
	}

	public String getPacketTrace() {
		return packetTrace;
	}

	public void setPacketTrace(String packetTrace) {
		this.packetTrace = packetTrace;
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

	public Long getStorage() {
		return storage;
	}

	public void setStorage(Long storage) {
		this.storage = storage;
	}

	@Override
	public String toString() {
		return "PacketStorage{" +
				"packetID=" + packetID +
				", packetTrace='" + packetTrace + '\'' +
				", goodsID=" + goodsID +
				", goodsName='" + goodsName + '\'' +
				", customerID=" + customerID +
				", customerName='" + customerName + '\'' +
				", repositoryID=" + repositoryID +
				", number=" + number +
				", storage=" + storage +
				'}';
	}
}
