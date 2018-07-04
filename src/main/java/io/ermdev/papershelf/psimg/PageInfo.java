package io.ermdev.papershelf.psimg;

import org.springframework.web.bind.annotation.RequestParam;

public class PageInfo {

    private String title;
    private String bookId;
    private Integer chapterNumber;
    private Integer pageNumber;

    public PageInfo(@RequestParam("title") String title,
                    @RequestParam("bookId") String bookId,
                    @RequestParam("chapterNumber") Integer chapterNumber,
                    @RequestParam("pageNumber") Integer pageNumber) {
        this.title = title;
        this.bookId = bookId;
        this.chapterNumber = chapterNumber;
        this.pageNumber = pageNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public Integer getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(Integer chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public String toString() {
        return "PageInfo{" +
                "title='" + title + '\'' +
                ", bookId='" + bookId + '\'' +
                ", chapterNumber=" + chapterNumber +
                ", pageNumber=" + pageNumber +
                '}';
    }
}
