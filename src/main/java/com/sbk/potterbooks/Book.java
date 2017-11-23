package com.sbk.potterbooks;

public enum Book {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5);

    private final int n;

    Book(int n) {
        this.n = n;
    }

    static Book getBookByN(int n) {
        for(Book book : values()) {
            if(book.n == n) {
                return book;
            }
        }
        return null;
    }
}
