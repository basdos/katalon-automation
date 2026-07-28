package auth
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testcase.TestCaseFactory
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable

import org.openqa.selenium.WebElement
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By

import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import com.kms.katalon.core.webui.driver.DriverFactory

import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty

import com.kms.katalon.core.mobile.helper.MobileElementCommonHelper
import com.kms.katalon.core.util.KeywordUtil

import com.kms.katalon.core.webui.exception.WebElementNotFoundException

import cucumber.api.java.en.And
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import custom.CollectionApi as collectionApi
import groovy.json.JsonSlurper as JsonSlurper


class apiTest {

	Map requestBody, responseBody
	ResponseObject response
	String endpoint, body, getApi, query
	ArrayList<Map<String, Object>> result

	@Given("user set api request login")
	def setApiLogin() {
		requestBody = new HashMap<String, String>()

		requestBody.put("username", "emilys")
		requestBody.put("password", "emilyspass")

		body = collectionApi.requestBuilder(requestBody)
	}

	@When("user hit request login")
	def hitRequestLogin() {
		'get endpoint'
		endpoint = "/auth/login"

		'prepare header'
		ArrayList<TestObjectProperty> header = new ArrayList<TestObjectProperty>()
		header.add(new TestObjectProperty("Content-Type", ConditionType.EQUALS, "application/json"))

		'hit api'
		response = collectionApi.hitApi(GlobalVariable.baseUrlApi, endpoint, header, "POST", body)

		JsonSlurper slurper = new JsonSlurper()
		responseBody = slurper.parseText(response.getResponseBodyContent())
	}

	@Then("user verify status code and response body")
	def verifyStatusCodeResponseBody() {
		'Verify Response Login'
		WS.verifyResponseStatusCode(response, 200)
		WS.verifyElementPropertyValue(response, 'gender', 'female')
		WS.verifyElementPropertyValue(response, 'firstName', 'Emily')
		WS.verifyElementPropertyValue(response, 'lastName', 'Johnson')
		KeywordUtil.logInfo("Access Token Login : " + responseBody.accessToken)
		
	}
}