package custom

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import com.sun.net.httpserver.HttpsParameters
import com.kms.katalon.core.testobject.impl.HttpTextBodyContent
import com.kms.katalon.core.testobject.impl.HttpUrlEncodedBodyContent

import internal.GlobalVariable
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import java.util.stream.Collectors

public class CollectionApi {
	public static mapToStringBuilder(Map data) {
		String resultData = data.collect { key, value ->
			"$key=${URLEncoder.encode(value.toString(), 'UTF-8')}"
		}.join('&')

		println resultData
		return resultData
	}

	public static String requestBuilder(Map bodyContent) {

		String requestBody

		requestBody = bodyContent.keySet().stream()
				.map({mapKey -> '"' + mapKey + '"' + ": " + '"' + bodyContent.get(mapKey) + '"'})
				.collect(Collectors.joining(", ", "{ ", " }"))

		return requestBody
	}

	public static hitApi(String url, String endpoint, ArrayList header, String method, String body) {
		RequestObject request
		ResponseObject response

		request = new RequestObject()
		request.setRestUrl( url + endpoint)
		request.setHttpHeaderProperties(header)
		request.setRestRequestMethod(method)
		request.setBodyContent(new HttpTextBodyContent(body))

		response = WS.sendRequest(request)

		KeywordUtil.logInfo("URL : " + request.getRestUrl())
		KeywordUtil.logInfo("Request Body : " + body)
		WS.comment("Response Body: " +response.responseBodyContent)

		return response
	}

	public static setHeader(String typeAuth,String token, String contentType) {
		'Set Header & Authorization Token'
		ArrayList<TestObjectProperty> header = new ArrayList<TestObjectProperty>()
		header.add(new TestObjectProperty("Authorization", ConditionType.EQUALS, typeAuth +" " + token))
		header.add(new TestObjectProperty("Accept", ConditionType.EQUALS, "*/*"))

		if (contentType == 'json') {
			header.add(new TestObjectProperty("Content-Type", ConditionType.EQUALS, "application/json"))
		}
		else if(contentType == 'form-data') {
			header.add(new TestObjectProperty("Content-Type", ConditionType.EQUALS, "application/form-data"))
		}
		else if(contentType == 'url-encode') {
			header.add(new TestObjectProperty("Content-Type", ConditionType.EQUALS, "application/x-www-form-urlencoded"))
		}
		else if(contentType == 'text') {
			header.add(new TestObjectProperty("Content-Type", ConditionType.EQUALS, "text/plain"))
		}
		else {
			KeywordUtil.logInfo("Content-Type Empty String" )
		}

		return header
	}
}