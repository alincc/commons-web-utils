package no.nb.commons.web.packaging.service;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by andreasb on 14.07.15.
 */
@Service
public class ZipService implements PackageService {

    @Override
    public InputStream packIt(List<File> files) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(baos);
        ZipOutputStream zos = new ZipOutputStream(bos);
        for(File file : files) {
            ZipEntry ze = new ZipEntry(file.getName());
            zos.putNextEntry(ze);
            zos.write(FileUtils.readFileToByteArray(file));
            zos.closeEntry();
        }
        zos.close();
        bos.close();
        baos.close();

        InputStream inputStream = new ByteArrayInputStream(baos.toByteArray());
        InputStream bufferedInputStream = new BufferedInputStream(inputStream);

        return bufferedInputStream;
    }

}
