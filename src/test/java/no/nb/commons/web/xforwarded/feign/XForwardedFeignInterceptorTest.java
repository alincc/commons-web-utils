package no.nb.commons.web.xforwarded.feign;

import static org.junit.Assert.assertEquals;
import no.nb.commons.web.sso.feign.SsoFeignInterceptor;
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
public class XForwardedFeignInterceptorTest {

    @Test
    public void testApply() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/v1/id1");
        String ip = "123.45.123.123";
        request.addHeader(UserUtils.REAL_IP_HEADER, ip);
        
        String xHost = "123.45.123.123";
        request.addHeader(XForwardedFeignInterceptor.X_FORWARDED_HOST, xHost);

        String xPort = "8080";
        request.addHeader(XForwardedFeignInterceptor.X_FORWARDED_PORT, xPort);

        String proto = "proto";
        request.addHeader(XForwardedFeignInterceptor.X_FORWARDED_PROTO, proto);

        String ssl = "ssl";
        request.addHeader(XForwardedFeignInterceptor.X_FORWARDED_SSL, ssl);

        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        
        XForwardedFeignInterceptor interceptor = new XForwardedFeignInterceptor(request);
        RequestTemplate template = new RequestTemplate();
        interceptor.apply(template);
        
        assertEquals("X-Forwarded-Host should be \"123.45.123.123\"", xHost, template.headers().get(XForwardedFeignInterceptor.X_FORWARDED_HOST).iterator().next());
        assertEquals("X-Forwarded-Port should be \"8080\"", xPort, template.headers().get(XForwardedFeignInterceptor.X_FORWARDED_PORT).iterator().next());
        assertEquals("X-Forwarded-Proto should be \"proto\"", proto, template.headers().get(XForwardedFeignInterceptor.X_FORWARDED_PROTO).iterator().next());
        assertEquals("X-Forwarded-Ssl should be \"ssl\"", ssl, template.headers().get(XForwardedFeignInterceptor.X_FORWARDED_SSL).iterator().next());
    }

}
