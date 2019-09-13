package de.demarks.wms.domain;

/**
 * 预报包裹库存
 * 
 * @author huanyingcool
 *
 */
public class PacketStorage {

	private Integer packetID;
	private String packetTrace;
	private String packetStatus;
	private Integer goodsID;// 货物ID
	private String goodsName;// 货物名称
	private Integer customerID;
	private String customerName;
	private Integer repositoryID;// 仓库ID
	private Long number; //预报数量
	private Long storage; //未到货数量

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

	public String getPacketStatus() {
		return packetStatus;
	}

	public void setPacketStatus(String packetStatus) {
		this.packetStatus = packetStatus;
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
				", packetStatus='" + packetStatus + '\'' +
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
