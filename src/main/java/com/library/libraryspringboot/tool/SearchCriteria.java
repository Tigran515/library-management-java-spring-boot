package com.library.libraryspringboot.tool;

import lombok.Data;

@Data
public class SearchCriteria {
    private String authorName;
    private String authorLname;
    private String authorSname;
    private String bookTitle;
    private String isbn;
    private String detail;

    public SearchCriteria() {
    }

    public SearchCriteria(String authorName, String authorLname, String authorSname, String bookTitle, String isbn) {
        this.authorName = authorName;
        this.authorLname = authorLname;
        this.authorSname = authorSname;
        this.bookTitle = bookTitle;
        this.isbn = isbn;
    }

    public SearchCriteria(String detail) {
        this.detail = detail;
    }
}
