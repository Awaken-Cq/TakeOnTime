package com.kr.spone.chris.takeontime.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import com.kr.spone.chris.takeontime.vo.TransferInfoResVO;

public class TransferInfoService {
	
	public TransferInfoService(){} 
	
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMddHHmmss", Locale.KOREA);
	
	public static String requestTransferInfo(String requestUrl) {
		CloseableHttpClient httpClient = HttpClients.createMinimal();
		StringBuilder sb = new StringBuilder();
		try {
			//HttpGet httpGet = new HttpGet("http://swopenAPI.seoul.go.kr/api/subway/sample/xml/realtimeStationArrival/0/5/서울");
			HttpGet httpGet = new HttpGet(requestUrl);
			//end setting process

				//start execute process
				CloseableHttpResponse response =  httpClient.execute(httpGet);
			
					
					System.out.println("status line : " + response.getStatusLine());
					
					HttpEntity entity = response.getEntity();
					
					BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
					
					String readLine;
					while((readLine = reader.readLine()) != null) {
						
						sb.append(readLine);
						
					}
				
					
					//start close process
					EntityUtils.consume(entity);
					response.close();
					
				
		
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
			
		} finally {
			
			try {
				httpClient.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//end close process
		
		return new String(sb);
	}
	
	
	/**
	 * 
	 * @param result
	 * @return transInfoVOList
	 */
	public static List<TransferInfoResVO> parseXmlToJson(String result) {
		List<TransferInfoResVO> transInfoVOList = new ArrayList<TransferInfoResVO>(); 
		TransferInfoResVO transInfoVO;
		//System.out.println(result.toString());
		
		try {
			
			JSONObject resultObj = XML.toJSONObject(result);
			JSONObject body = resultObj.getJSONObject("realtimeStationArrival");
			JSONArray rows = body.getJSONArray("row");
			
			int len = rows.length();
			
			for (int i = 0; i < len; i++ ) {
				JSONObject obj = (JSONObject)rows.get(i);
				
				//호선
				if ((Integer) obj.get("subwayId") != 1001) {
					continue;
				}
				//상하행선 구분
				if ( !obj.get("updnLine").equals("하행")) {
					continue;
				}
				
				transInfoVO = new TransferInfoResVO();
				
				transInfoVO.setSubwayId(obj.get("subwayId").toString());
				transInfoVO.setUpdnLine((String)obj.get("updnLine"));
				transInfoVO.setTrainLineNm((String)obj.get("trainLineNm"));
				transInfoVO.setStatnNm((String)obj.get("statnNm"));
				transInfoVO.setOrdkey((String)obj.get("ordkey"));
				transInfoVO.setBstatnNm(String.valueOf(obj.get("bstatnNm")));
				transInfoVO.setBtrainNo(String.valueOf(obj.get("btrainNo")));
				transInfoVO.setBarvlDt(obj.get("barvlDt").toString());
				transInfoVO.setRecptnDt(obj.get("recptnDt").toString());
				transInfoVO.setArvlCd(obj.get("arvlCd").toString());
				transInfoVO.setArvlMsg2((String)obj.get("arvlMsg2"));
				transInfoVO.setArvlMsg3((String)obj.get("arvlMsg3"));
				
				transInfoVOList.add(transInfoVO);
			}
		
		
		} catch (Exception e) {
			
			System.out.println("데이터를 가져오는 동안 에러가 발생하였습니다. 다시 시도해주세요.");
		}
		
		return transInfoVOList;
	}
	
	
	
	/**
	 * 
	 * @param beforeTransInfoVOList
	 * 1. 생성시간과 현재시간의 시차로 인해 이미 열차가 도착 또는 지나가 버린 경우
	 * - 리스트에 넣지 않는다.
	 * 
	 * @return afterTransInfoVOList
	 */
	public static List<TransferInfoResVO> calculrateParallax (List<TransferInfoResVO> beforeTransInfoVOList) {

		List<TransferInfoResVO> afterTransInfoVOList = new ArrayList<TransferInfoResVO>();
		
		Date date = new Date();
		
		try {

			Date currentDate = dateFormat.parse(dateFormat.format(date));
			
			for (TransferInfoResVO vo : beforeTransInfoVOList) {

				String recptnDt =  vo.getRecptnDt().replaceAll("\\D", "");
				String requestDateStr = recptnDt.substring(0, 14);
				Date requestDate = dateFormat.parse(requestDateStr);
				String beforeStation = "";
				
				if (vo.getBarvlDt().equals("0")) {
					//도착시간이 정해지지않음
					
					beforeStation = vo.getArvlMsg2().replaceAll("\\D", "");
					if (!beforeStation.equals("")) {
						//정류장 번호로 세팅
						String updateBarvlDt = String.valueOf(Integer.valueOf(beforeStation) * 120);
						
						vo.setBarvlDt(updateBarvlDt);
					} else {
						//곧 도착하는 상황
						//TOBE 전역 전전역 도착 등의 상황
						
					}
					
					//초 세팅 완료
					
				} else {
					
					//도착시간이 정해짐
					
					if (currentDate.getTime() - requestDate.getTime() > (Integer.valueOf(vo.getBarvlDt())*1000)) {
						//이미 지나감
						continue;
					}
					
					//남은 시간(아직 안지나감)
				}
				
				//시차적용
				//시차(초) = 현재시간 - 요청시간
				int parallax = (int)((currentDate.getTime()-requestDate.getTime())/1000);
				
				vo.setBarvlDt(String.valueOf(Integer.valueOf(vo.getBarvlDt()) - parallax));
				
				if (Integer.valueOf(vo.getBarvlDt()) <= 0) {
					//도착했거나 이미 떠남
					continue;
				}
				vo.setArrivalTime(Integer.valueOf(vo.getBarvlDt())/60 + "분 " + Integer.valueOf(vo.getBarvlDt())%60 + "초");

				
				if (parallax > 60) {
					
					if (!beforeStation.equals("")){
						
						vo.setArvlMsg2("[" + (Integer.valueOf(beforeStation) - 1) + "]번째 전역");
					}
					
				}
				
				if (vo.getArvlCd() == "99") {	//운행중
					
					
				} else {
					
					//TODO 역코드와 역명 매핑시 도착코드 직접 조작
					//도착코드(0:진입, 1:도착, 2:출발, 3:전역출발, 4:전역진입, 5:전역도착, 99:운행중)
					switch (vo.getArvlCd()) {
					case "0" :
						vo.setArvlName("진입");
						break;
					case "1" :
						vo.setArvlName("도착");
						break;
					case "2" :
						vo.setArvlName("출발");
						break;
					case "3" :
						vo.setArvlName("전역 출발");
						break;
					case "4" :
						vo.setArvlName("전역 진입");
						break;
					case "5" :
						vo.setArvlName("전역 도착");
						break;
					
					default:
						vo.setArvlName("운행중");
						break;
					
					}
				
				}
				
				afterTransInfoVOList.add(vo);
			
			}
			
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
	
		return afterTransInfoVOList;
	}
	
	/**
	 * 
	 * @param beforeTransInfoVOList
	 * 
	 * 1. 열차번호가 같은 중복 정보가 있는 경우(같은 열차의 정보가 두번 들어옴)
	 * - 열차번호가 같고 생성시간이 다를경우
	 * - 생성시간이 늦은 정보를 add한다.
	 * 
	 * @return afterTransInfoVOList
	 */
	public List<TransferInfoResVO> dataDeduplication (List<TransferInfoResVO> beforeTransInfoVOList) {

		boolean checkOverLap;
		
		List<TransferInfoResVO> afterTransInfoVOList = new ArrayList<TransferInfoResVO>();
		
		
		for ( TransferInfoResVO originVO : beforeTransInfoVOList) {
			
			checkOverLap = false;
			
			for ( TransferInfoResVO cloneVO : beforeTransInfoVOList) {
				
				if (originVO.getBtrainNo().equals(cloneVO.getBtrainNo()) && !originVO.getRecptnDt().equals(cloneVO.getRecptnDt())) {
				
					//중복데이터 발생
					long originRecptnDt =  Long.valueOf((originVO.getRecptnDt().replaceAll("\\D", "")));
					long cloneRecptnDt =  Long.valueOf((cloneVO.getRecptnDt().replaceAll("\\D", "")));
					
					//데이터 생성일자로 비교
					if (originRecptnDt < cloneRecptnDt) {
						//더 최신 데이터가 있으므로 스킵
						checkOverLap = true;
						
					} else {
						//현재 데이터 추가
					}
				}
				
			}
			
			if (checkOverLap == true) {
				
				continue;
			}
			
			afterTransInfoVOList.add(originVO);
			
		}
		
		return afterTransInfoVOList;
	}

}
