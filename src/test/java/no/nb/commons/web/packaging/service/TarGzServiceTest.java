package no.nb.commons.web.packaging.service;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by andreasb on 16.09.15.
 */
public class TarGzServiceTest {


    @Test
    public void targzMultipeFilesTest() throws IOException {
        String filename = "ecd270f69cb8a9063306fcecd4b1a769.pdf";
        String tmpPath = FileUtils.getTempDirectoryPath();
        Resource resource = new ClassPathResource(filename);
        File file = new File(resource.getURI());

        TarGzService tarGzService = new TarGzService();
        InputStream inputStream = tarGzService.packIt(Arrays.asList(file));
        assertNotNull(inputStream);
    }
}
