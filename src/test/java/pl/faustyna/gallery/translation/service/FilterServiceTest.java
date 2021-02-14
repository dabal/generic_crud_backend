package pl.faustyna.gallery.translation.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

class FilterServiceTest {
    private Map<String, Object> map;
    private Set<String> list;
    private FilterService filterService;
    private List<Map<String, Object>> recordsList;

    @BeforeEach
    public void setUp() {
        filterService = new FilterService();
        map = new HashMap<>();
        map.put("key1", "test");
        map.put("key2", "test");
        map.put("key3", "test");

        list = new HashSet<>();
        list.add("key1");
        list.add("key2");
        list.add("key4");

        recordsList = new ArrayList<>();
        recordsList.add(new HashMap<>(map));
        recordsList.add(new HashMap<>(map));


    }


    @Test
    public void filterResultList_shouldPreserveListSizeAndRemoveOnlyKeysFromItems() {


        final FilterService filterService = new FilterService();
        final List<Map<String, Object>> resultList = filterService.filterResultList(recordsList, list);
        Assertions.assertEquals(recordsList.size(), resultList.size());
        for (final Map<String, Object> result : resultList
        ) {
            Assertions.assertTrue(result.keySet().contains("key1"));
            Assertions.assertTrue(result.keySet().contains("key2"));
            Assertions.assertFalse(result.keySet().contains("key3"));
            Assertions.assertFalse(result.keySet().contains("key4"));
        }
    }

    @Test
    public void filterSingleResult_shouldReturnRecordWithFilteredColumns() {

        final Map<String, Object> result;

        result = filterService.filterSingleResult(map, list);
        Assertions.assertTrue(result.keySet().contains("key1"));
        Assertions.assertTrue(result.keySet().contains("key2"));
        Assertions.assertFalse(result.keySet().contains("key3"));
        Assertions.assertFalse(result.keySet().contains("key4"));

    }
}