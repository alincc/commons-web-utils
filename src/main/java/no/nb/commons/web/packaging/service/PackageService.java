package no.nb.commons.web.packaging.service;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by andreasb on 14.07.15.
 */
public interface PackageService {
    InputStream packIt(List<File> files) throws IOException;
}
