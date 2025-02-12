package com.studentCrud.DAO;

import java.util.List;
import java.util.Optional;

public interface CrudRequests<T,updateT> {
    // T : type générique
    List<T> getAll(int page,int size);

    T getById(String id);

    void deleteById(String id);

    Optional<updateT> updateById(String id,updateT EntityToUpdate);

    Optional<T> save(T entity);

    Optional<List<T>> saveAll(List<T> entities);

}
