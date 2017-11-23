package com.sbk.potterbooks;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.sbk.potterbooks.Book.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderTest {

    @Nested
    @DisplayName("Testing calculateCost method")
    class OrderCalculateCostTest {
        @Test
        @DisplayName("Calculate cost should return 8 when only one book of any type in order")
        void shouldReturn8_whenOnlyOneBookInOrder() {
            assertEquals(8, new Order(ONE).calculateCost());
        }

        @Test
        @DisplayName("Calculate cost should return 15.2 when only two book of any type in order")
        void shouldReturn15_2_whenThreeBooksInOrder() {
            assertEquals(15.2, new Order(ONE, TWO).calculateCost());
        }

        @Test
        @DisplayName("Calculate cost should return 21,6 when only three book of any type in order")
        void shouldReturn21_6_whenTwoBooksInOrder() {
            assertEquals(21.6, new Order(ONE, TWO, THREE).calculateCost());
        }

        @Test
        @DisplayName("Calculate cost should return 25,6 when only four book of any type in order")
        void shouldReturn25_6_whenTwoBooksInOrder() {
            assertEquals(25.6, new Order(ONE, TWO, THREE, FOUR).calculateCost());
        }

        @Test
        @DisplayName("Calculate cost should return 51.2 when two series of four books of any type in order")
        void shouldReturn51_2_whenTwoBooksInOrder() {
            assertEquals(51.2, new Order(ONE, TWO, THREE, FOUR, ONE, TWO, THREE, FIVE).calculateCost());
        }
    }

    @Nested
    @DisplayName("Testing getDiscountSeries method")
    class OrderDiscountSeriesTest {

        @Test
        @DisplayName("Should return one series with one element when only one book of any type in order")
        void shouldReturnEmptySeries_whenNoBooksInOrder() {
            assertEquals(new ArrayList<List<Book>>(), new Order().getDiscountSeries());
        }

        @Test
        @DisplayName("Should return one series with one element when only one book of any type in order")
        void shouldReturnOneSeriesWithOneElement_whenOnlyOneBookInOrder() {
            assertEquals(buildExpectedResult(new int[][]{{1}}),
                    new Order(ONE).getDiscountSeries());
        }

        @Test
        @DisplayName("Should return two series with one element when two same books in order")
        void shouldReturnTwoSingletonSeries_whenTwoSameBooksInOrder() {
            assertEquals(buildExpectedResult(new int[][]{{2}, {2}}),
                    new Order(TWO, TWO).getDiscountSeries());
        }

        @Test
        @DisplayName("Should return two series when two ONE and one TWO  books in order")
        void shouldReturnTwoSeries_when2plus1BooksInOrder() {
            assertEquals(buildExpectedResult(new int[][]{{1,2},{2}}), new Order(ONE, TWO, TWO).getDiscountSeries());
        }

        @Test
        @DisplayName("Should return three series when two ONE and one TWO and one THREE books in order")
        void shouldReturnThreeSeries_when2plus1BooksInOrder() {
            assertEquals(buildExpectedResult(new int[][]{{1,2,3},{2}}), new Order(ONE, TWO, TWO, THREE).getDiscountSeries());
        }

        @Test
        @DisplayName("Should return [1,2,3,4] + [2] series when ONE, TWO, TWO, THREE, FOUR in order")
        void shouldReturnThreeSeries_when2plus1BooksInOrder1() {
            assertEquals(buildExpectedResult(new int[][]{{1,2,3,4},{2}}), new Order(ONE, TWO, TWO, THREE, FOUR).getDiscountSeries());
        }

        @Test
        @DisplayName("Should return [1,2,3,4] + [2,3] series when ONE, TWO, TWO, THREE, FOUR in order")
        void shouldReturnThreeSeries_when2plus1BooksInOrder2() {
            assertThat(buildExpectedResult(new int[][]{{1,2,3,4},{2,3}}),
                            containsInAnyOrder(new Order(ONE, TWO, TWO, THREE, FOUR, THREE).getDiscountSeries().toArray()));
        }

        @Test
        @DisplayName("Should return [1,2,3,4] + [2,3] + [2] series when ONE, TWO, TWO, THREE, FOUR, TWO in order")
        void shouldReturnThreeSeries_when2plus1BooksInOrder3() {
            List<List<Book>> expectedR = buildExpectedResult(new int[][]{{1,2,3,4},{2,3},{2}});
            assertThat(expectedR,
                    containsInAnyOrder(new Order(ONE, TWO, TWO, THREE, FOUR, THREE,TWO).getDiscountSeries().toArray()));
        }

        @Test
        @DisplayName("Should return [1,2,3,4] + [2,3] + [2] + [2] series when ONE, TWO, TWO, THREE, FOUR, TWO, TWO in order")
        void shouldReturnThreeSeries_when2plus1BooksInOrder4() {
            assertThat(buildExpectedResult(new int[][]{{1,2,3,4},{2,3},{2},{2}}),
                    containsInAnyOrder(new Order(ONE, TWO, TWO, THREE, FOUR, THREE,TWO, TWO).getDiscountSeries().toArray()));
        }

        @Test
        @DisplayName("Should return [1,2,3,4] + [2,3] + [2,3] + [2] series when ONE, TWO, TWO, THREE, FOUR, TWO, TWO in order")
        void shouldReturnThreeSeries_when2plus1BooksInOrder5() {
            assertThat(buildExpectedResult(new int[][]{{1,2,3,4},{2,3},{2,3},{2}}),
                    containsInAnyOrder(new Order(ONE, TWO, THREE, FOUR, TWO, THREE, TWO, THREE, TWO).getDiscountSeries().toArray()));
        }

        @Test
        @DisplayName("Should return [1,2,3,4] + [2,3] + [2,3] + [2] series when ONE, TWO, TWO, THREE, FOUR, TWO, TWO in order")
        void shouldReturnThreeSeries_when2plus1BooksInOrder6() {
            assertThat(buildExpectedResult(new int[][]{{1, 2, 3, 4}, {2, 3}, {2, 3}, {2}}),
                    containsInAnyOrder(new Order(ONE, TWO, TWO, THREE, FOUR, THREE, TWO, TWO, THREE).getDiscountSeries().toArray()));
        }

        @Test
        @DisplayName("Should return [1,2,3,4] + [1,2,3,4,5] series when ONE, TWO, THREE, FOUR, FIVE, ONE, TWO, THREE, FOUR in order")
        void shouldReturnThreeSeries_when2plus1BooksInOrder7() {
            assertThat(buildExpectedResult(new int[][]{{1, 2, 3, 4, 5}, {1,2,3,4}}),
                    containsInAnyOrder(new Order(ONE, TWO, THREE, FOUR, FIVE, ONE, TWO, THREE, FOUR).getDiscountSeries().toArray()));
        }

        @Test
        @DisplayName("Should return [1,2,3,4] + [1,2,3,5] series when ONE, TWO, THREE, FIVE, ONE, TWO, THREE, FOUR in order")
        void shouldReturnThreeSeries_when2plus1BooksInOrder8() {
            assertThat(buildExpectedResult(new int[][]{{1, 2, 3, 5}, {1,2,3,4}}),
                    containsInAnyOrder(new Order(ONE, TWO, THREE, FIVE, ONE, TWO, THREE, FOUR).getDiscountSeries().toArray()));
        }

        @Test
        @DisplayName("Should return [1,2,3,4] + [1,2,3,5] + [1] series when ONE, TWO, THREE, FIVE, ONE, TWO, THREE, FOUR, ONE in order")
        void shouldReturnThreeSeries_when2plus1BooksInOrder9() {
            assertThat(buildExpectedResult(new int[][]{{1, 2, 3, 5}, {1,2,3,4}, {1}}),
                    containsInAnyOrder(new Order(ONE, TWO, THREE, FIVE, ONE, TWO, THREE, FOUR, ONE).getDiscountSeries().toArray()));
        }

        @Test
        @DisplayName("Should return [1,2,3,4] + [1,2,3,5] + [1,2,3,4] series when ONE, TWO, THREE, FIVE, ONE, TWO, THREE, FOUR, ONE in order")
        void shouldReturnThreeSeries_when2plus1BooksInOrder10() {
            assertThat(buildExpectedResult(new int[][]{{1, 2, 3, 5}, {1,2,3,4}, {1,2,3,5}}),
                    containsInAnyOrder(new Order(ONE, TWO, THREE, FIVE, ONE, TWO, THREE, FOUR, ONE, TWO, THREE, FIVE).getDiscountSeries().toArray()));
        }


        private List<List<Book>> buildExpectedResult(int[][] books) {
            return Arrays.stream(books)
                    .map(this::intsToBooks)
                    .collect(Collectors.toList());
        }

        private List<Book> intsToBooks(int[] ar) {
            return Arrays.stream(ar)
                    .mapToObj(Book::getBookByN)
                    .collect(Collectors.toList());
        }

    }

}