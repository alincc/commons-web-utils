package no.nb.commons.web.sso.feign;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import no.nb.commons.web.util.UserUtils;

import org.junit.Test;

import feign.RequestTemplate;

/**
 * 
 * @author ronnymikalsen
 *
 */
public class SsoParamsFeignInterceptorTest {

    @Test
    public void whenIpAndSsoTokenIsInParamsThenAddThemAsHeaders() {

        String ip = "123.45.123.123";
        String amssoValue = "amsso1";
        
        SsoParamsFeignInterceptor interceptor = new SsoParamsFeignInterceptor();
        RequestTemplate template = new RequestTemplate();
        Map<String, Collection<String>> queries = new HashMap<>();
        queries.put(UserUtils.REAL_IP_HEADER, Arrays.asList(ip));
        queries.put(UserUtils.SSO_HEADER, Arrays.asList(amssoValue));
        template.queries(queries);
        
        interceptor.apply(template);
        
        assertEquals("Client IP should be \"123.45.123.123\"", ip, template.headers().get(UserUtils.REAL_IP_HEADER).iterator().next());
        assertEquals("SSO token should be \"amsso1\"", amssoValue, template.headers().get(UserUtils.SSO_HEADER).iterator().next());
    }

}
