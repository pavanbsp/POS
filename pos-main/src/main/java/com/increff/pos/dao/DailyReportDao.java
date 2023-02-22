package com.increff.pos.dao;

import com.increff.pos.pojo.DailyReportPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class DailyReportDao extends AbstractDao {
    @Transactional
    public void insertDailyReport(DailyReportPojo dailyReportPojo) {
        em().persist(dailyReportPojo);
    }

    public DailyReportPojo selectDailyReport(String date) {
        CriteriaBuilder cb = em().getCriteriaBuilder();
        CriteriaQuery<DailyReportPojo> cr = cb.createQuery(DailyReportPojo.class);
        Root<DailyReportPojo> root = cr.from(DailyReportPojo.class);
        cr.select(root).where(cb.equal(root.get("date"), date));

        TypedQuery<DailyReportPojo> query = em().createQuery(cr);
        return getSingle(query);
    }

    public List<DailyReportPojo> selectAllDailyReports() {
        return selectAll(DailyReportPojo.class);
    }

    public List<DailyReportPojo> selectByFromDate(String from, String to) {
        CriteriaBuilder cb = em().getCriteriaBuilder();
        CriteriaQuery<DailyReportPojo> cr = cb.createQuery(DailyReportPojo.class);
        Root<DailyReportPojo> root = cr.from(DailyReportPojo.class);
        cr.select(root).where(cb.greaterThanOrEqualTo(root.get("date"), from), cb.lessThanOrEqualTo(root.get("date"), to));

        TypedQuery<DailyReportPojo> query = em().createQuery(cr);
        List<DailyReportPojo> results = query.getResultList();
        return results;
    }
}
