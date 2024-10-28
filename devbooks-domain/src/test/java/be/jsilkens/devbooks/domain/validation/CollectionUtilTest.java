package be.jsilkens.devbooks.domain.validation;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CollectionUtilTest {

    @Test
    void givenTwoLists_whenJoining_thenListsJoined() {
        // Given
        List<String> first = Arrays.asList("A", "B");
        List<String> second = Arrays.asList("C", "D");

        // When
        List<String> result = CollectionUtil.join(first, second);

        //Then
        assertThat(result).containsExactly("A", "B", "C", "D");
    }

    @Test
    void givenOneList_whenJoiningSecond_thenReturnOneList() {
        // Given
        List<String> second = Arrays.asList("X", "Y");

        // When
        List<String> result = CollectionUtil.join(null, second);

        // Then
        assertThat(result).containsExactly("X", "Y");
    }

    @Test
    void givenOneList_whenJoiningFirst_thenReturnOneList() {
        // Given
        List<String> first = Arrays.asList("1", "2");

        // When
        List<String> result = CollectionUtil.join(first, null);

        // Then
        assertThat(result).containsExactly("1", "2");
    }

    @Test
    void whenJoiningNulls_thenReturnEmptyList() {
        // Given none

        // When
        List<String> result = CollectionUtil.join(null, null);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void givenOneElementAndOneList_whenJoining_thenElementsJoined() {
        // Given
        String first = "Item1";
        List<String> second = Arrays.asList("Item2", "Item3");

        // When
        List<String> result = CollectionUtil.join(first, second);

        // Then
        assertThat(result).containsExactly("Item1", "Item2", "Item3");
    }

    @Test
    void givenListWithOnlyOneElement_whenJoiningSecond_thenReturnOneList() {
        List<String> second = List.of("OnlyItem");

        List<String> result = CollectionUtil.join(null, second);

        assertThat(result).containsExactly("OnlyItem");
    }

    @Test
    void givenListWithOnlyOneElement_whenJoiningFirst_thenReturnOneList() {
        String first = "SoloItem";

        List<String> result = CollectionUtil.join(first, null);

        assertThat(result).containsExactly("SoloItem");
    }

}