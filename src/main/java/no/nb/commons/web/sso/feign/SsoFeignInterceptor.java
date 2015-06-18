package no.nb.commons.web.sso.feign;

import no.nb.commons.web.util.UserUtils;

import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
public class SsoFeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        addAmssoToRequest(template);
        addClientIpToRequest(template);
    }

    private void addClientIpToRequest(RequestTemplate template) {
        template.header(UserUtils.REAL_IP_HEADER, UserUtils.getClientIp());
    }

    private void addAmssoToRequest(RequestTemplate template) {
        template.header(UserUtils.SSO_HEADER, UserUtils.getSsoToken());
    }

}
