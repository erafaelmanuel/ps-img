package io.ermdev.papershelf.psimg;

import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class ImageUtil {

    public static Dimension getImageDimension(final String path) throws IOException {
        final String suffix = FilenameUtils.getExtension(path);
        final Iterator<ImageReader> iter = ImageIO.getImageReadersBySuffix(suffix);
        if (iter.hasNext()) {
            ImageReader reader = iter.next();
            try {
                final ImageInputStream stream = new FileImageInputStream(new File(path));
                reader.setInput(stream);

                final int width = reader.getWidth(reader.getMinIndex());
                final int height = reader.getHeight(reader.getMinIndex());
                final Dimension result = new Dimension(width, height);
                return result;
            } catch (Exception e) {
                throw new IOException(e.getMessage());
            } finally {
                reader.dispose();
            }
        }
        throw new IOException("No reader found for given format: " + suffix);
    }
}
