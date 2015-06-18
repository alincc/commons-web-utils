package no.nb.commons.web.sso.feign;

import javax.servlet.http.HttpServletRequest;

import no.nb.commons.web.util.UserUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
public class SsoFeignInterceptor implements RequestInterceptor {

    private final HttpServletRequest request;

    @Autowired
    public SsoFeignInterceptor(HttpServletRequest request) {
        this.request = request;
    }
    
    @Override
    public void apply(RequestTemplate template) {
        addAmssoToRequest(template);
        addClientIpToRequest(template);
    }

    private void addClientIpToRequest(RequestTemplate template) {
        template.header(UserUtils.REAL_IP_HEADER, UserUtils.getClientIp(request));
    }

    private void addAmssoToRequest(RequestTemplate template) {
        template.header(UserUtils.SSO_HEADER, UserUtils.getSsoToken(request));
    }

}
