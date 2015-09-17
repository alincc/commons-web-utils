package no.nb.commons.web.packaging.service;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.zip.GZIPOutputStream;

/**
 * Created by andreasb on 14.07.15.
 */
@Service
public class TarGzService implements PackageService {

    @Override
    public InputStream packIt(List<File> files) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // Wrap the output file stream in streams that will tar and gzip everything
        TarArchiveOutputStream taos = new TarArchiveOutputStream(new GZIPOutputStream(new BufferedOutputStream(baos)));
        taos.setLongFileMode(TarArchiveOutputStream.LONGFILE_GNU);
        taos.setBigNumberMode(TarArchiveOutputStream.BIGNUMBER_STAR);

        for (File file : files) {
            TarArchiveEntry entry = new TarArchiveEntry(file.getName());
            entry.setSize(file.length());
            taos.putArchiveEntry(entry);
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            IOUtils.copy(bis, taos);
            taos.closeArchiveEntry();
        }

        taos.close();
        baos.close();

        InputStream inputStream = new ByteArrayInputStream(baos.toByteArray());
        InputStream bufferedInputStream = new BufferedInputStream(inputStream);

        return bufferedInputStream;
    }
}
