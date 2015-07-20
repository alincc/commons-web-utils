package no.nb.commons.web.sso.feign;

import java.util.Collection;

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
        String sso = getSsoFromParameterOrRequestHeader(template);
        addSsoToTemplateHeader(sso, template);
        String clientIp = getClientIpFromParameterOrRequestHeader(template);
        addClientIpToTemplateHeader(clientIp, template);
    }

    private String getSsoFromParameterOrRequestHeader(RequestTemplate template) {
        if (usingParameters(template)) {
            return getQueryValue(template, UserUtils.SSO_HEADER);
        } else {
            return UserUtils.getSsoToken(request);
        }
    }

    private String getClientIpFromParameterOrRequestHeader(
            RequestTemplate template) {
        if (usingParameters(template)) {
            return getQueryValue(template, UserUtils.REAL_IP_HEADER);
        } else {
            return UserUtils.getClientIp(request);
        }
    }

    private void addSsoToTemplateHeader(String sso, RequestTemplate template) {
        template.header(UserUtils.SSO_HEADER, sso);        
    }

    private void addClientIpToTemplateHeader(String clientIp, RequestTemplate template) {
        template.header(UserUtils.REAL_IP_HEADER, clientIp);
    }
    
    private boolean usingParameters(RequestTemplate template) {
        Collection<String> params = template.queries().get(UserUtils.REAL_IP_HEADER);
        if (params != null && !params.isEmpty()) {
            return true;    
        } else {
            return false;
        }
    }

    private String getQueryValue(RequestTemplate template, String headerName) {
        Collection<String> params = template.queries().get(headerName);
        if (params != null && !params.isEmpty()) {
            return params.iterator().next();    
        }
        return null;
    }

}
