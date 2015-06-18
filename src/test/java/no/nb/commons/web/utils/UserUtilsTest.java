package no.nb.commons.web.utils;

import static org.junit.Assert.*;

import javax.servlet.http.Cookie;

import no.nb.commons.web.util.UserUtils;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 
 * @author ronnymikalsen
 *
 */
public class UserUtilsTest {

    @Test
    public void whenIpFromFrontendThenUseIt() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/v1/id1");
        String ip = "123.45.123.123";
        request.addHeader(UserUtils.REAL_IP_HEADER, ip);
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        
        assertEquals(ip, UserUtils.getClientIp(request));
    }

    @Test
    public void whenIpFromFrontendIsNullThenUseRemoteAddress() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/v1/id1");
        String remoteAddr = "123.45.123.123";
        request.setRemoteAddr(remoteAddr);
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        
        assertEquals(remoteAddr, UserUtils.getClientIp(request));
    }

    @Test
    public void whenIpFromFrontendIsEmptyThenUseRemoteAddress() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/v1/id1");
        String ip = "";
        request.addHeader(UserUtils.REAL_IP_HEADER, ip);
        String remoteAddr = "123.45.123.123";
        request.setRemoteAddr(remoteAddr);
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        
        assertEquals(remoteAddr, UserUtils.getClientIp(request));
    }

    @Test(expected = SecurityException.class)
    public void whenIp6ThenThrowSecurityException() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/v1/id1");
        String remoteAddr = "2001:0db8:85a3:0000:0000:8a2e:0370:7334";
        request.setRemoteAddr(remoteAddr);
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        
        UserUtils.getClientIp(request);
    }

    @Test(expected = SecurityException.class)
    public void whenIpIsLocalhostThenThrowSecurityException() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/v1/id1");
        String remoteAddr = "127.0.0.1";
        request.setRemoteAddr(remoteAddr);
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        
        UserUtils.getClientIp(request);
    }

    @Test
    public void whenTokenIsInHeaderThenUseIt() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/v1/id1");
        String amsso = "amsso1";
        request.addHeader(UserUtils.SSO_HEADER, amsso);
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        
        assertEquals(amsso, UserUtils.getSsoToken(request));

    }

    @Test
    public void whenTokenIsInCookieThenUseIt() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/v1/id1");
        String amssoValue = "amsso1";
        Cookie cookie = new Cookie(UserUtils.SSO_HEADER, amssoValue);
        request.setCookies(cookie );
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        
        assertEquals(amssoValue, UserUtils.getSsoToken(request));

    }

    @Test
    public void whenTokenIsMissingThenReturnNull() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/v1/id1");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        
        assertNull(UserUtils.getSsoToken(request));

    }

}
