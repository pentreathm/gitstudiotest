//import static org.junit.Assert.*;

//import org.junit.Test;
//import org.mule.api.MuleMessage;
//import org.mule.api.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;
//import org.mule.transport.NullPayload;
//import com.vatit.blaze.esb.dragon.dto.*;


public class TestLogin extends FunctionalTestCase {
	protected String getConfigResources(){
		return "./src/main/app/eduards_flows.xml";
	}
	
//	@Test
//	public void testLogin() throws Exception {
//		String url = "http://localhost:8081/login";
//		MuleClient client = muleContext.getClient();
//		//client.dispatch(url, null, null);
//		MuleMessage result = client.request(url, RECEIVE_TIMEOUT);
//		System.out.println(result.getPayload(Fault.class));
//		assertNotNull(result);
//		assertNull(result.getExceptionPayload());
//		assertFalse(result.getPayload() instanceof NullPayload);
//		
//	}
}
