package com.library.libraryspringboot.Tool;

import org.apache.commons.lang3.StringUtils;

public class SearchCriteria {
    private String authorName;
    private String authorLname;
    private String authorSname;
    private String bookTitle;
    private String ISBN;

    public String getAuthorName() {
        return authorName;
    }

    public String getAuthorLname() {
        return authorLname;
    }

    public String getAuthorSname() {
        return authorSname;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getISBN() {
        return ISBN;
    }

    public boolean isEmpty() { //@TODO manage
        if (StringUtils.isBlank(authorName) && StringUtils.isBlank(authorLname) && StringUtils.isBlank(authorSname) && StringUtils.isBlank(bookTitle) && StringUtils.isBlank(ISBN)) {
            return true;
        }
        return false;
    }

    public boolean hasAuthorCriteria() { // @TODO move
//        if (StringUtils.isBlank(authorName) || StringUtils.isBlank(authorLname) || StringUtils.isBlank(authorSname)) {
        if (authorName != null || authorLname != null || authorSname != null) {
            return true;
        }
        return false;
    }

    public boolean hasBookCriteria() { // @TODO move
        if (bookTitle.isBlank() || ISBN.isBlank()) {
            return false;
        }
        return true;
    }
}
