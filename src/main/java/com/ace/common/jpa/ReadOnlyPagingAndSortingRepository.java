package com.ace.common.jpa;

/**
 * Created by bamboo on 17-11-29.
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author bamboo
 */
@NoRepositoryBean
public abstract interface ReadOnlyPagingAndSortingRepository<T, ID extends Serializable>
        extends JpaRepository<T, ID> {
    //@RestResource(exported = false)
    @Override
    public abstract <S extends T> S save(S paramS);

    //    @RestResource(exported = false)
    //    @Override
    //    public abstract <S extends T> Iterable<S> save(Iterable<S> paramIterable);

    //@RestResource(exported = false)
    @Override
    public abstract <S extends T> List<S> save(Iterable<S> paramIterable);

    //@RestResource(exported = false)
    @Override
    public abstract void flush();

    //@RestResource(exported = false)
    @Override
    public abstract <S extends T> S saveAndFlush(S paramS);

    // @RestResource(exported = false)
    @Override
    public abstract void delete(ID paramID);

    //@RestResource(exported = false)
    @Override
    public abstract void delete(T paramT);

    //@RestResource(exported = false)
    @Override
    public abstract void delete(Iterable<? extends T> paramIterable);

    //@RestResource(exported = false)
    @Override
    public abstract void deleteAll();
}