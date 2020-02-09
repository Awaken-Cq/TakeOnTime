package com.kr.spone.chris.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.XMLConstants;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.kr.spone.chris.service.TransferInfoService;
import com.kr.spone.chris.vo.TransferInfoReqVO;
import com.kr.spone.chris.vo.TransferInfoResVO;

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
 * 
 *          <pre>
 * ----------------------------------------------------------
 * [Modification Information]
 * ----------------------------------------------------------
 *  수정일자        |  수정자  |  변경사유
 *  2020. 1. 31.   |  노정탁  |  최초작성
 * ----------------------------------------------------------
 *          </pre>
 */

/*
 * 서울시 교통정보과[TOPIS]에서 제공하는 정보를 이용한 지하철 실시간 도착정보 조회 API 입니다. 역조회시 참조: 응암 ->
 * 응암순환(상선), 공릉 -> 공릉(서울산업대입구), 춘의 -> 춘의역, 남한산성입구 -> 남한산성입구(성남법원, 검찰청), 대모산입구 ->
 * 대모산, 천호 -> 천호(풍납토성), 몽촌토성 -> 몽천토성(평화의문) 서울시 이외의 역구간은 미제공 됩니다.(예, 광명, 서동탄, 춘천
 * 등)
 * 
 * 주의사항 : 원천에서 데이터가 수집 및 가공되어 서비스되는 과정에서 시간 차가 발생할 수 있습니다. 출력값 중 recptnDt(열차
 * 도착정보를 생성한 시각)는 데이터가 생성된 시간을 의미하며 현재시각과 recptnDt의 차이 만큼 열차가 더 진행한 것으로 보정해서
 * 사용해야 합니다. 예시) 현재시간이 10시 5분 30초이고, recptnDt가 10시 3분 30초인경우 2분간의 시차가 발생하므로
 * 도착정보는 2분씩 당겨지거나 1개의 역을 더 진행한것으로 판단
 */

public class TransferInfoController {
	
	
	
	public static void main(String[] args) {
		
		TransferInfoService transferInfoService = new TransferInfoService();
		
		System.out.println("Welcome to TOT");
		
		List<TransferInfoResVO> transInfoVOList;
		TransferInfoReqVO requestVO;
		

		requestVO = new TransferInfoReqVO();

	 	//start call api
	 	requestVO.setType("xml");
	 	requestVO.setStart_index(0);
	 	requestVO.setEnd_index(20);
	 
	 	String requestUrl = requestVO.getUrl() + "/"
 						+ requestVO.getKey() + "/"
 						+ requestVO.getType() + "/"
 						+ requestVO.getService() + "/"
 						+ requestVO.getStart_index() + "/"
 						+ requestVO.getEnd_index() + "/"
 						+ requestVO.getStatnNm();

	 	String result = transferInfoService.requestTransferInfo(requestUrl);		
	 	//end call api
		
		
		//start parsing process
		transInfoVOList = new ArrayList<TransferInfoResVO>();
		
		//파싱
		transInfoVOList = transferInfoService.parseXmlToJson(result);
		System.out.println(transInfoVOList.size());
		
		//중복제거
		transInfoVOList = transferInfoService.dataDeduplication(transInfoVOList);
		System.out.println(transInfoVOList.size());
		
		//시차계산
		transInfoVOList = transferInfoService.calculrateParallax(transInfoVOList);
		System.out.println(transInfoVOList.size());
		
		//end parsing process
		
		/*
		//start print process
		System.out.println("========역명 : " + transInfoVOList.get(0).statnNm  + "========");
		System.out.println("========상하 : " + transInfoVOList.get(0).updnLine  + "========");
		int i = 0;
		for (TransferInfoResVO vo : transInfoVOList) {
			i++;
			System.out.println("========도착예정열차 " + i + "========");
			System.out.println("========열차 정보 : " + vo.trainLineNm  + "========");
			System.out.println("========열차 종착역 : " + vo.bstatnNm  + "========");
			System.out.println("========열차 위치 : " + vo.arvlMsg3  + "========");
			System.out.println("========열차 상태 : " + vo.arvlName  + "========");
			
			if (Integer.valueOf(vo.arvlCd) < 6) {
				System.out.println("========열차 도착시간 : " + vo.arrivalTime + "전========");
				
			}
		
			
		}
			*/
		//end print process

	}
}
