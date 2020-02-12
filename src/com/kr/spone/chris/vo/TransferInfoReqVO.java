package com.kr.spone.chris.vo;

/**
 * 
 * <pre>
 *
 *
 *
 * </pre>
 *
 * @project TakeOnTime
 * @service 
 * @author 노정탁
 * @since 2020. 1. 31.
 * @version
 * <pre>
 * ----------------------------------------------------------
 * [Modification Information]
 * ----------------------------------------------------------
 *  수정일자        |  수정자  |  변경사유
 *  2020. 1. 31.   |  노정탁  |  최초작성
 * ----------------------------------------------------------
 * </pre>
 */
	/**
	 * String key : 인증키 
	 * String type : 요청파일타입 : xml, xmlf(xml파일), xls(엑셀파일), json(json파일)
	 * String service : 서비스명
	 * int start_index : 요청시작위치 정수
	 * int end_index : 요청종료위치 정수
	 * String statnNm : 지하철 역명
	 */
public class TransferInfoReqVO {
	
	final String url = "http://swopenAPI.seoul.go.kr/api/subway";
	final String key = "4f795246486e6a74383961524c4447"; 
	String type;
	final String service = "realtimeStationArrival";
	int start_index;
	int end_index;
	final String statnNm = "청량리";
	
	@Override
	public String toString() {
		return "TransferInfoReqVO [url=" + url + ", key=" + key + ", type=" + type + ", service=" + service
				+ ", start_index=" + start_index + ", end_index=" + end_index + ", statnNm=" + statnNm + "]";
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getStart_index() {
		return start_index;
	}
	public void setStart_index(int start_index) {
		this.start_index = start_index;
	}
	public int getEnd_index() {
		return end_index;
	}
	public void setEnd_index(int end_index) {
		this.end_index = end_index;
	}
	public String getUrl() {
		return url;
	}
	public String getKey() {
		return key;
	}
	public String getService() {
		return service;
	}
	public String getStatnNm() {
		return statnNm;
	}
}
