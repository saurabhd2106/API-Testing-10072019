package tests;

import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.restApi.RestClient;

public class GetAPITest extends BaseTest {

	@Test
	public void verifyGetUsersApi() throws Exception {
		extentTest = extentReport.createTest("First API - Verify Get Users API");

		restClient = new RestClient();

		CloseableHttpResponse httpResponse = restClient.get(uri);
		
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		
		SoftAssert softassert = new SoftAssert();

		softassert.assertEquals(statusCode, statuscode_200Ok);

		// ---------------------------------------------------------------------

		String httpResponseSting = EntityUtils.toString(httpResponse.getEntity());

		JSONObject httpResponseJson = new JSONObject(httpResponseSting);

		System.out.println("JSON Response : " + httpResponseJson);

		// --------------------------------------------------------------------

		Header[] headersArray = httpResponse.getAllHeaders();

		HashMap<String, String> allHeaders = new HashMap<>();

		for (Header header : headersArray) {
			allHeaders.put(header.getName(), header.getValue());
		}
		
		System.out.println("Headers array : "+ allHeaders);
		
		softassert.assertTrue(allHeaders.containsKey("Content-Type"));
		softassert.assertEquals(allHeaders.get("Content-Type"), "application/json; charset=utf-8");
		
		softassert.assertAll();
	}

}
