import org.junit.*;

import controllers.financeinfo.FinanceInfo;
import controllers.financeinfo.FinanceInfoManager;
import controllers.financeinfo.QuoteInfo;

import java.util.*;
import play.test.*;
import models.*;

public class FinanceInfoManagerTest extends UnitTest {

    @Test
    public void testGetFinanceInfo() {
    	FinanceInfo financeInfo = FinanceInfoManager.getFinanceInfo("yhoo");
    	assertEquals("Yahoo! Inc.", financeInfo.getCompanyName());
    	assertEquals("YHOO", financeInfo.getSymbol());
    	
    	
    	 financeInfo = FinanceInfoManager.getFinanceInfo("nonexistant");
    	 assertNull(financeInfo);
    }

    
    @Test
    public void testGetQuoteInfo() {
    	QuoteInfo quoteInfo = FinanceInfoManager.getQuoteInfo("yhoo");
    	assertEquals("Yahoo! Inc.", quoteInfo.getCompanyName());
    	assertEquals("YHOO", quoteInfo.getSymbol());
    	
    	
    	quoteInfo = FinanceInfoManager.getQuoteInfo("nonexistant");
    	 assertNull(quoteInfo);
    }
    
    @Test
    public void testGetMultipleQuoteInfo() {
    	String[] array = {"YHOO","MSFT","GOOG"};
    	Map<String,QuoteInfo> quoteInfos = FinanceInfoManager.getQuoteInfo(array);
    	
    	assertEquals(3, quoteInfos.size());
    	
    	

    }
}
