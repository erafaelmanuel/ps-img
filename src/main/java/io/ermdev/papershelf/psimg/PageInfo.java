package io.ermdev.papershelf.psimg;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestParam;

@Configuration
public class PageInfo {

    @Value("${ps.path}")
    private String path;

    private String title;
    private String chapterNumber;
    private String pageNumber;

    public PageInfo() {}

    public PageInfo(@RequestParam(name = "title") String title,
                    @RequestParam(name = "chapterNumber") String chapterNumber,
                    @RequestParam(name = "pageNumber") String pageNumber) {
        this();
        this.title = title;
        this.chapterNumber = chapterNumber;
        this.pageNumber = pageNumber;
    }

    @Override
    public String toString() {
        return "PageInfo{" +
                "title='" + title + '\'' +
                ", chapterNumber='" + chapterNumber + '\'' +
                ", pageNumber='" + pageNumber + '\'' +
                '}';
    }
}
