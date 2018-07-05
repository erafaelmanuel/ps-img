package io.ermdev.papershelf.psimg;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

@RestController
public class ImageController {

    @Value("${ps.book-dir}")
    private String bookDirectory;

    @Value("${ps.server}")
    private String server;

    @PostMapping(value = "/page", consumes = {"multipart/form-data"})
    public ResponseEntity<?> addImagePage(@RequestParam("file") MultipartFile file, PageInfo pageInfo) {
        final StringBuilder builder = new StringBuilder();
        final String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        final Image image = new Image();
        try {
            if (StringUtils.isEmpty(pageInfo.getTitle())) {
                throw new PsImgException("title is required and it can't be empty");
            }
            if (StringUtils.isEmpty(pageInfo.getBookId())) {
                throw new PsImgException("bookId is required and it can't be empty");
            }
            if (pageInfo.getChapterNumber() == null || pageInfo.getChapterNumber() <= -1) {
                throw new PsImgException("chapterNumber is required and it can't have a negative number value");
            }
            if (pageInfo.getPageNumber() == null || pageInfo.getPageNumber() <= -1) {
                throw new PsImgException("pageNumber is required and it can't have a negative number value");
            }
            builder
                    .append(bookDirectory)
                    .append(pageInfo.getTitle().toLowerCase().replace(" ", "_"))
                    .append(File.separator)
                    .append(pageInfo.getBookId())
                    .append(File.separator)
                    .append("chapter_")
                    .append(pageInfo.getChapterNumber());
            final File dir = new File(builder.toString());
            if (dir.exists() || dir.mkdirs()) {
                builder
                        .append(File.separator)
                        .append(pageInfo.getPageNumber())
                        .append(".")
                        .append(extension);

                final File imageFile = new File(builder.toString());
                final FileOutputStream fos = new FileOutputStream(imageFile);

                fos.write(file.getBytes());
                fos.flush();
                fos.close();
                if (server.charAt(server.length() - 1) == '/' || server.charAt(server.length() - 1) == '\\') {
                    builder.delete(0, bookDirectory.length());
                } else {
                    builder.delete(0, bookDirectory.length() - 1);
                }
                builder.insert(0, server);

                image.setUrl(builder.toString().replace("\\", "/"));
                image.setDimension(ImageUtil.getImageDimension(imageFile.getPath()));
                image.setExtension(extension.toUpperCase());
                return ResponseEntity.ok().body(image);
            } else {
                throw new PsImgException("Unable to upload the file");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
