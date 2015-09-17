package no.nb.commons.web.packaging.factory;

import no.nb.commons.web.packaging.exception.UnsupportedPackageFormatException;
import no.nb.commons.web.packaging.service.PackageService;
import no.nb.commons.web.packaging.service.TarGzService;
import no.nb.commons.web.packaging.service.ZipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by andreasb on 15.09.15.
 */
@Component
public class PackageFactory {

    private final ZipService zipService;
    private final TarGzService tarGzService;

    @Autowired
    public PackageFactory(ZipService zipService, TarGzService tarGzService) {
        this.zipService = zipService;
        this.tarGzService = tarGzService;
    }

    public PackageService getPackageService(String format) {
        if ("zip".equalsIgnoreCase(format)) {
            return zipService;
        }
        else if ("tar.gz".equalsIgnoreCase(format)) {
            return tarGzService;
        }

        throw new UnsupportedPackageFormatException("Package format '" + format + "' is not supported");
    }
}
