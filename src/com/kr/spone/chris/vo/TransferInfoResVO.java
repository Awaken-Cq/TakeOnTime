package com.kr.spone.chris.vo;

public class TransferInfoResVO {

	
	String subwayId; //지하철호선ID
	String updnLine; //상하행선구분(2호선 : (내선:0,외선:1),상행,하행)
	String trainLineNm; //도착지방면(성수행 - 구로디지털단지방면)
	String subwayHeading; //내리는문방향(오른쪽,왼쪽)
	String statnFid; //이전지하철역ID
	String statnTid; //다음지하철역ID
	String statnId; //지하철역ID
	String statnNm; //지하철역명
	String ordkey; //도착예정열차순번
	String subwayList;
	String statnList;
	String barvlDt; //열차도착예정시간
	String btrainNo; //열차번호
	String bstatnId; //종착지하철역ID
	String bstatnNm; //종착지하철역명
	String recptnDt; //열차도착정보를 생성한 시각
	String arvlMsg2; //첫번째도착메세지(전역 진입, 전역 도착 등)
	String arvlMsg3; //두번째도착메세지(종합운동장 도착, 12분 후 (광명사거리) 등)
	String arvlCd; //도착코드(0:진입, 1:도착, 2:출발, 3:전역출발, 4:전역진입, 5:전역도착, 99:운행중)
	String arvlName; //도착상황(한글)
	String arrivalTime; //계산된 예상도착시간

	
	
	public String getArvlName() {
		return arvlName;
	}
	public void setArvlName(String arvlName) {
		this.arvlName = arvlName;
	}
	@Override
	public String toString() {
		return "TransferInfoResVO [subwayId=" + subwayId + ", updnLine=" + updnLine + ", trainLineNm=" + trainLineNm
				+ ", subwayHeading=" + subwayHeading + ", statnNm=" + statnNm + ", ordkey=" + ordkey + ", barvlDt="
				+ barvlDt + ", bstatnNm=" + bstatnNm + ", recptnDt=" + recptnDt + ", arvlMsg2=" + arvlMsg2
				+ ", arvlMsg3=" + arvlMsg3 + ", arvlCd=" + arvlCd + ", arrivalTime=" + arrivalTime + "]";
	}
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public String getSubwayId() {
		return subwayId;
	}
	public void setSubwayId(String subwayId) {
		this.subwayId = subwayId;
	}
	public String getUpdnLine() {
		return updnLine;
	}
	public void setUpdnLine(String updnLine) {
		this.updnLine = updnLine;
	}
	public String getTrainLineNm() {
		return trainLineNm;
	}
	public void setTrainLineNm(String trainLineNm) {
		this.trainLineNm = trainLineNm;
	}
	public String getSubwayHeading() {
		return subwayHeading;
	}
	public void setSubwayHeading(String subwayHeading) {
		this.subwayHeading = subwayHeading;
	}
	public String getStatnFid() {
		return statnFid;
	}
	public void setStatnFid(String statnFid) {
		this.statnFid = statnFid;
	}
	public String getStatnTid() {
		return statnTid;
	}
	public void setStatnTid(String statnTid) {
		this.statnTid = statnTid;
	}
	public String getStatnId() {
		return statnId;
	}
	public void setStatnId(String statnId) {
		this.statnId = statnId;
	}
	public String getStatnNm() {
		return statnNm;
	}
	public void setStatnNm(String statnNm) {
		this.statnNm = statnNm;
	}
	public String getOrdkey() {
		return ordkey;
	}
	public void setOrdkey(String ordkey) {
		this.ordkey = ordkey;
	}
	public String getSubwayList() {
		return subwayList;
	}
	public void setSubwayList(String subwayList) {
		this.subwayList = subwayList;
	}
	public String getStatnList() {
		return statnList;
	}
	public void setStatnList(String statnList) {
		this.statnList = statnList;
	}
	public String getBarvlDt() {
		return barvlDt;
	}
	public void setBarvlDt(String barvlDt) {
		this.barvlDt = barvlDt;
	}
	public String getBtrainNo() {
		return btrainNo;
	}
	public void setBtrainNo(String btrainNo) {
		this.btrainNo = btrainNo;
	}
	public String getBstatnId() {
		return bstatnId;
	}
	public void setBstatnId(String bstatnId) {
		this.bstatnId = bstatnId;
	}
	public String getBstatnNm() {
		return bstatnNm;
	}
	public void setBstatnNm(String bstatnNm) {
		this.bstatnNm = bstatnNm;
	}
	public String getRecptnDt() {
		return recptnDt;
	}
	public void setRecptnDt(String recptnDt) {
		this.recptnDt = recptnDt;
	}
	public String getArvlMsg2() {
		return arvlMsg2;
	}
	public void setArvlMsg2(String arvlMsg2) {
		this.arvlMsg2 = arvlMsg2;
	}
	public String getArvlMsg3() {
		return arvlMsg3;
	}
	public void setArvlMsg3(String arvlMsg3) {
		this.arvlMsg3 = arvlMsg3;
	}
	public String getArvlCd() {
		return arvlCd;
	}
	public void setArvlCd(String arvlCd) {
		this.arvlCd = arvlCd;
	}
	
	
	
	
}
