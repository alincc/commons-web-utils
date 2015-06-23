package no.nb.commons.web.xforwarded.feign;

import java.util.Collection;

import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * 
 * @author ronnymikalsen
 *
 */
@Component
public class XForwardedParamsFeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        setHeader(template, XForwardedFeignInterceptor.X_FORWARDED_HOST);
        setHeader(template, XForwardedFeignInterceptor.X_FORWARDED_PORT);
    }

    private void setHeader(RequestTemplate template, String headerName) {
        Collection<String> params = template.queries().get(headerName);
        if (params != null && params.size() > 0) {
            template.header(headerName,  params.iterator().next());    
        }

    }
}
