package no.nb.commons.web.xforwarded.feign;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import feign.RequestTemplate;

/**
 * 
 * @author ronnymikalsen
 *
 */
public class XForwardedParamsFeignInterceptorTest {

    @Test
    public void testApply() {

        XForwardedParamsFeignInterceptor interceptor = new XForwardedParamsFeignInterceptor();
        RequestTemplate template = new RequestTemplate();
        
        String xHost = "123.45.123.123";
        String xPort = "8080";
        
        Map<String, Collection<String>> queries = new HashMap<>();
        queries.put(XForwardedFeignInterceptor.X_FORWARDED_HOST, Arrays.asList(xHost));
        queries.put(XForwardedFeignInterceptor.X_FORWARDED_PORT, Arrays.asList(xPort));
        template.queries(queries);
        
        interceptor.apply(template);
        
        assertEquals("X-Forwarded-Host should be \"123.45.123.123\"", xHost, template.headers().get(XForwardedFeignInterceptor.X_FORWARDED_HOST).iterator().next());
        assertEquals("X-Forwarded-Port should be \"8080\"", xPort, template.headers().get(XForwardedFeignInterceptor.X_FORWARDED_PORT).iterator().next());
    }

}
