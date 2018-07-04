package io.ermdev.papershelf.psimg;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Locale;

@RestController
@RequestMapping("/upload")
public class ImageController {

    @Value("${ps.path}")
    private String path;

    @PostMapping(value = "/page", consumes = {"multipart/form-data"})
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file,
                                         @RequestParam(name = "title") String title,
                                         @RequestParam(name = "chapterNumber") Integer chapterNumber,
                                         @RequestParam(name = "pageNumber") Integer pageNumber) {
        try {
            final File dir = new File(path + title + File.separator + "ch" + chapterNumber);
            if (dir.exists() || dir.mkdirs()) {
                final File page = new File(dir.getPath() + File.separator + pageNumber + "." +
                        FilenameUtils.getExtension(file.getOriginalFilename()));
                final FileOutputStream fos = new FileOutputStream(page);
                fos.write(file.getBytes());
                fos.flush();
                fos.close();
                System.out.println(dir.getPath());
            } else {
                throw new PsImgException("");
            }
            System.out.println(file.getOriginalFilename());

            return ResponseEntity.ok().build();

        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
