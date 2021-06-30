package entity;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Order implements Serializable {

	private String id;
	private String productName;
	private int mount;
	private String deliverTime;
	private String bidDeadline;
	private String receiver;
	private String receiverConMe;
	private String shippingAdd;
	private String status;

	private String belongDealerId;
	private ArrayList<Bid> bidInfor;
	private ArrayList<String> winFacName;
	private ArrayList<String> equipments;
	private ArrayList<Integer> resultMount;

	public Order(String id, String productName, int mount, String deliverTime, String bidDeadline, String receiver,
			String receiverConMe, String shippingAdd, String status, String belongDealerId, String winFacName) {
		this.id = id;
		this.productName = productName;
		this.mount = mount;
		this.deliverTime = deliverTime;
		this.bidDeadline = bidDeadline;
		this.receiver = receiver;
		this.receiverConMe = receiverConMe;
		this.shippingAdd = shippingAdd;
		this.status = status;
		this.belongDealerId = belongDealerId;
		this.winFacName = new ArrayList<>();
		this.bidInfor = new ArrayList<>();
		this.equipments = new ArrayList<>();
		this.resultMount = new ArrayList<>();
	}

	public ArrayList<Integer> getResultMount() {
		return resultMount;
	}

	public void setResultMount(ArrayList<Integer> resultMount) {
		this.resultMount = resultMount;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getMount() {
		return mount;
	}

	public void setMount(int mount) {
		this.mount = mount;
	}

	public String getDeliverTime() {
		return deliverTime;
	}

	public void setDeliverTime(String deliverTime) {
		this.deliverTime = deliverTime;
	}

	public String getBidDeadline() {
		return bidDeadline;
	}

	public void setBidDeadline(String bidDeadline) {
		this.bidDeadline = bidDeadline;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getReceiverConMe() {
		return receiverConMe;
	}

	public void setReceiverConMe(String receiverConMe) {
		this.receiverConMe = receiverConMe;
	}

	public String getShippingAdd() {
		return shippingAdd;
	}

	public void setShippingAdd(String shippingAdd) {
		this.shippingAdd = shippingAdd;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ArrayList<String> getWinFacName() {
		return winFacName;
	}

	public String getId() {
		return id;
	}

	public String getBelongDealerId() {
		return belongDealerId;
	}

	public ArrayList<Bid> getBidInfor() {
		return bidInfor;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", productName=" + productName + ", mount=" + mount + ", deliverTime=" + deliverTime
				+ ", bidDeadline=" + bidDeadline + ", receiver=" + receiver + ", receiverConMe=" + receiverConMe
				+ ", shippingAdd=" + shippingAdd + ", status=" + status + ", belongDealerId=" + belongDealerId
				+ ", bidInfor=" + bidInfor + ", winFacName=" + winFacName + "]";
	}

	public ArrayList<String> getEquipments() {
		return equipments;
	}

}
