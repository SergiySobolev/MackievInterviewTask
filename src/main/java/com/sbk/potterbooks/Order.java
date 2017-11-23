package com.sbk.potterbooks;

import java.util.*;
import java.util.stream.Collectors;

class Order {
    private Book[] books;
    private static int PRICE = 8;

    Order(Book... books) {
        this.books = books;
    }

    double calculateCost() {
        List<List<Book>> discountSeries = getDiscountSeries();
        return discountSeries.stream()
                .map(b -> {
                    if(b.size() < 4){
                        return PRICE*b.size()*(1 - (b.size()-1)*0.05);
                    } else if (b.size() == 4) {
                        return PRICE*b.size()*0.8;
                    } else {
                        return PRICE*b.size()*0.75;
                    }
                })
                .mapToDouble(Double::doubleValue)
                .sum();

    }

    List<List<Book>> getDiscountSeries() {
        List<List<Book>> discountSeries = new ArrayList<>();
        if(books.length > 0) {
            final Book[] lessBooks = Arrays.copyOf(books, books.length - 1);
            Book keyBook = books[books.length - 1];
            List<List<Book>> lessSeries = new Order(lessBooks).getDiscountSeries();
            Optional<List<Book>> seriesToAddOpt = findSeriesToAdd(lessSeries, keyBook);
            if(seriesToAddOpt.isPresent()) {
                discountSeries.addAll(addKeyBookAndReplace(lessSeries, seriesToAddOpt.get(), keyBook));
            } else {
                discountSeries.addAll(lessSeries);
                discountSeries.add(Collections.singletonList(keyBook));
            }
        }

        return discountSeries;
    }

    private List<List<Book>> addKeyBookAndReplace(List<List<Book>> lessSeries, List<Book> books, Book keyBook) {
        int place = lessSeries.indexOf(books);
        List<Book> newBooks = new ArrayList<>(books);
        newBooks.add(keyBook);
        List<List<Book>> replacedSeries = new ArrayList<>(lessSeries);
        replacedSeries.set(place, newBooks);
        return replacedSeries;
    }

    private Optional<List<Book>> findSeriesToAdd(List<List<Book>> lessSeries, Book keyBook) {
        List<List<Book>> lessSeriesWithoutKeyBook = lessSeries.stream()
                .filter(l -> !l.contains(keyBook))
                .sorted((o1, o2) -> o2.size() - o1.size())
                .collect(Collectors.toList());
        if(lessSeriesWithoutKeyBook.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(lessSeriesWithoutKeyBook.stream()
                .filter(l -> l.size() == 3)
                .findFirst()
                .orElse(lessSeriesWithoutKeyBook.get(0)));

    }

}
