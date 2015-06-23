package no.nb.commons.web.sso.feign;

import java.util.Collection;

import no.nb.commons.web.util.UserUtils;

import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * 
 * @author ronnymikalsen
 *
 */
@Component
public class SsoParamsFeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        setHeader(template, UserUtils.REAL_IP_HEADER);
        setHeader(template, UserUtils.SSO_HEADER);
    }

    private void setHeader(RequestTemplate template, String headerName) {
        Collection<String> params = template.queries().get(headerName);
        if (params != null && params.size() > 0) {
            template.header(headerName,  params.iterator().next());    
        }

    }
}
