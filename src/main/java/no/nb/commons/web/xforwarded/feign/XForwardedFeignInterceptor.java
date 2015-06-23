package no.nb.commons.web.xforwarded.feign;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
public class XForwardedFeignInterceptor implements RequestInterceptor {
    public static final String X_FORWARDED_HOST = "X-Forwarded-Host";
    public static final String X_FORWARDED_PORT = "X-Forwarded-Port";
    public static final String X_FORWARDED_SSL = "X-Forwarded-Ssl";
    public static final String X_FORWARDED_PROTO = "X-Forwarded-Proto";

    private final HttpServletRequest request;

    @Autowired
    public XForwardedFeignInterceptor(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void apply(RequestTemplate template) {
        template.header(XForwardedFeignInterceptor.X_FORWARDED_HOST,
                request.getHeader(XForwardedFeignInterceptor.X_FORWARDED_HOST));
        template.header(XForwardedFeignInterceptor.X_FORWARDED_PORT,
                request.getHeader(XForwardedFeignInterceptor.X_FORWARDED_PORT));
        template.header(XForwardedFeignInterceptor.X_FORWARDED_SSL,
                request.getHeader(XForwardedFeignInterceptor.X_FORWARDED_SSL));
        template.header(XForwardedFeignInterceptor.X_FORWARDED_PROTO,
                request.getHeader(XForwardedFeignInterceptor.X_FORWARDED_PROTO));
    }

}
