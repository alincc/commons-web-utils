package no.nb.commons.web.sso.feign;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;

import no.nb.commons.web.util.UserUtils;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestTemplate;

/**
 * 
 * @author ronnymikalsen
 *
 */
public class SsoFeignInterceptorTest {

    @Test
    public void whenIpAndSsoTokenIsInRequestThenAddThemAsHeaders() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/v1/id1");
        String ip = "123.45.123.123";
        request.addHeader(UserUtils.REAL_IP_HEADER, ip);

        String amssoValue = "amsso1";
        Cookie cookie = new Cookie(UserUtils.SSO_HEADER, amssoValue);
        request.setCookies(cookie );
        
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        
        
        SsoFeignInterceptor interceptor = new SsoFeignInterceptor(request);
        RequestTemplate template = new RequestTemplate();
        interceptor.apply(template);
        
        assertEquals("Client IP should be \"123.45.123.123\"", ip, template.headers().get(UserUtils.REAL_IP_HEADER).iterator().next());
        assertEquals("SSO token should be \"amsso1\"", amssoValue, template.headers().get(UserUtils.SSO_HEADER).iterator().next());
    }

    @Test
    public void whenIpAndSsoTokenIsInParamsThenAddThemAsHeaders() {

        String ip = "123.45.123.123";
        String amssoValue = "amsso1";
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/v1/id1");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        
        SsoFeignInterceptor interceptor = new SsoFeignInterceptor(request);
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
