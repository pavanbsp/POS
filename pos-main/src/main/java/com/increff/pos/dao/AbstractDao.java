package com.increff.pos.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public abstract class AbstractDao {

    @PersistenceContext
    private EntityManager em;

    public <T> T select(Class<T> pojo, Integer id, String key) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cr = cb.createQuery(pojo);
        Root<T> root = cr.from(pojo);
        cr.select(root).where(cb.equal(root.get(key), id));

        TypedQuery<T> query = em.createQuery(cr);
        return getSingle(query);
    }

    public <T> List<T> selectMultiple(Class<T> pojo, Integer id, String key) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cr = cb.createQuery(pojo);
        Root<T> root = cr.from(pojo);
        cr.select(root).where(cb.equal(root.get(key), id));

        TypedQuery<T> query = em.createQuery(cr);
        List<T> results = query.getResultList();
        return results;
    }

    public <T> T selectSingle(Class<T> pojo, String name, String key) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cr = cb.createQuery(pojo);
        Root<T> root = cr.from(pojo);
        cr.select(root).where(cb.equal(root.get(key), name));

        TypedQuery<T> query = em.createQuery(cr);
        return getSingle(query);
    }

    public <T> List<T> selectMultiple(Class<T> pojo, String name, String key) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cr = cb.createQuery(pojo);
        Root<T> root = cr.from(pojo);
        cr.select(root).where(cb.equal(root.get(key), name));

        TypedQuery<T> query = em.createQuery(cr);
        List<T> results = query.getResultList();
        return results;
    }

    public <T> T selectSingle(Class<T> pojo, String name1, String key1, String name2, String key2) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cr = cb.createQuery(pojo);
        Root<T> root = cr.from(pojo);
        cr.select(root).where(cb.equal(root.get(key1), name1), cb.equal(root.get(key2), name2));

        TypedQuery<T> query = em.createQuery(cr);
        return getSingle(query);
    }

    public <T> T selectSingle(Class<T> pojo, int name1, String key1, int name2, String key2) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cr = cb.createQuery(pojo);
        Root<T> root = cr.from(pojo);
        cr.select(root).where(cb.equal(root.get(key1), name1), cb.equal(root.get(key2), name2));

        TypedQuery<T> query = em.createQuery(cr);
        return getSingle(query);
    }

    public <T> List<T> selectAll(Class<T> pojo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cr = cb.createQuery(pojo);
        Root<T> root = cr.from(pojo);
        cr.select(root);

        TypedQuery<T> query = em.createQuery(cr);
        List<T> results = query.getResultList();
        return results;
    }

    public <T> int delete(Class<T> pojo, Integer id, String key) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaDelete<T> cr = cb.createCriteriaDelete(pojo);
        Root<T> root = cr.from(pojo);
        cr.where(cb.equal(root.get(key), id));
        int result = em.createQuery(cr).executeUpdate();
        return result;
    }

    protected <T> T getSingle(TypedQuery<T> query) {
        return query.getResultList().stream().findFirst().orElse(null);
    }

    protected EntityManager em() {
        return em;
    }

}
