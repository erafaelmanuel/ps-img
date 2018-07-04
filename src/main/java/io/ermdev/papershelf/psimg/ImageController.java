package io.ermdev.papershelf.psimg;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

@RestController
@RequestMapping("/upload")
public class ImageController {

    @Value("${ps.book-dir}")
    private String bookDirectory;

    @PostMapping(value = "/page", consumes = {"multipart/form-data"})
    public ResponseEntity<?> addImage(@RequestParam("file") MultipartFile file, PageInfo pageInfo) {
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
            final StringBuilder builder = new StringBuilder();
            builder
                    .append(bookDirectory)
                    .append(pageInfo.getTitle().toLowerCase().replace(" ", "_"))
                    .append(File.separator)
                    .append(pageInfo.getBookId())
                    .append(File.separator)
                    .append("chapter_1")
                    .append(pageInfo.getChapterNumber());
            final File dir = new File(builder.toString());
            if (dir.exists() || dir.mkdirs()) {
                builder
                        .append(File.separator)
                        .append(pageInfo.getPageNumber())
                        .append(".")
                        .append(FilenameUtils.getExtension(file.getOriginalFilename()));

                final FileOutputStream fos = new FileOutputStream(new File(builder.toString()));
                fos.write(file.getBytes());
                fos.flush();
                fos.close();
            } else {
                throw new PsImgException("Unable to upload a file");
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
