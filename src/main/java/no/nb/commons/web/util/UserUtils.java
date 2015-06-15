package no.nb.commons.web.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.routines.InetAddressValidator;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

/**
 * 
 * @author ronnymikalsen
 *
 */
public class UserUtils {
    public static final String SSO_HEADER = "amsso";
    public static final String REAL_IP_HEADER = "X-Original-IP-Fra-Frontend";
    
    private UserUtils() {
        super();
    }
    
    public static String getClientIp() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();

        String clientIp = request.getHeader(UserUtils.REAL_IP_HEADER);
        
        if (originalIpIsNull(clientIp)) {
            clientIp = request.getRemoteAddr();
        }
        
        isValidAddress(clientIp);

        return clientIp;
    }

    public static String getSsoToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();

        String amsso = request.getHeader(UserUtils.SSO_HEADER);

        if (amsso != null) {
            return amsso;
        } else {
            Cookie amssoCookie = WebUtils.getCookie(request, UserUtils.SSO_HEADER);
            if (amssoCookie != null) {
                return amssoCookie.getValue();
            }
        }

        return null;
    }
    

    private static boolean originalIpIsNull(String clientIp) {
        return clientIp == null || clientIp.isEmpty();
    }

    private static void isValidAddress(String clientIp) {
        if (!InetAddressValidator.getInstance().isValidInet4Address(clientIp)
                || "127.0.0.1".equals(clientIp)) {
            throw new SecurityException("localhost or IPv6 is not supported ("
                    + clientIp + ")");
        }
    }


}
