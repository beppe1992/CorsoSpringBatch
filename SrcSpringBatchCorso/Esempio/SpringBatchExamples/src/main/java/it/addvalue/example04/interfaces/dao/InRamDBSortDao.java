package it.addvalue.example04.interfaces.dao;

import it.addvalue.example04.interfaces.key.SortingKeyGenerator;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

public interface InRamDBSortDao<T>
{

    void insertRowToSort(final String key,
                         final T obj);
    
    public void insertRowToSort(final List<T> items, SortingKeyGenerator keyGenerator);
    
    List<T> extractSortedRows();
    
    void extractAndWriteRows(ItemWriter<T> writer, int bufferSize);

    void clear();

    void rowInsertionComplete();

}