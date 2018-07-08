package io.ermdev.papershelf.psimg;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

@RestController
public class ImageController {

    private PsImgApplication.ApplicationProperty property;

    @Autowired
    public ImageController(PsImgApplication.ApplicationProperty property) {
        this.property = property;
    }

    @GetMapping("hello")
    public String getHello() {
        return "Hello rafael";
    }

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
                throw new PsImgException("chapterNumber is required and it can't be a negative value");
            }
            if (pageInfo.getPageNumber() == null || pageInfo.getPageNumber() <= -1) {
                throw new PsImgException("pageNumber is required and it can't be a negative value");
            }
            builder
                    .append(property.getBookDirectory())
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
                if (property.getServerName().charAt(property.getServerName().length() - 1) == '/' || property
                        .getServerName().charAt(property.getServerName().length() - 1) == '\\')
                    builder.delete(0, property.getBookDirectory().length());
                else
                    builder.delete(0, property.getBookDirectory().length() - 1);
                builder.insert(0, property.getServerName());

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
