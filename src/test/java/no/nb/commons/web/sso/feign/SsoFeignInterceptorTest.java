package no.nb.commons.web.sso.feign;

import static org.junit.Assert.assertEquals;

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
    public void test() {
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
        
        System.out.println(ip);
        System.out.println(template.headers().get(UserUtils.REAL_IP_HEADER));
        assertEquals("Client IP should be \"123.45.123.123\"", ip, template.headers().get(UserUtils.REAL_IP_HEADER).iterator().next());
        assertEquals("SSO token should be \"amsso1\"", amssoValue, template.headers().get(UserUtils.SSO_HEADER).iterator().next());
    }

}
