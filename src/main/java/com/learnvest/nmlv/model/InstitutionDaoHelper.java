package com.learnvest.nmlv.model;

import org.springframework.stereotype.Repository;

import com.learnvest.nmlv.model.Institution;
import com.learnvest.nmlv.util.SearchCriteria;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class InstitutionDaoHelper  {

    @PersistenceContext
    private EntityManager entityManager;

    //@Override
    public List<Institution> searchInstitution(final List<SearchCriteria> params) {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Institution> query = builder.createQuery(Institution.class);
        final Root<Institution> r = query.from(Institution.class);
        final Metamodel m = entityManager.getMetamodel();
        final EntityType<Institution> Inst_ = m.entity(Institution.class);
        

        Predicate predicate = builder.conjunction();
        builder.and(predicate, builder.equal(r.get("isActive"), true));        		        		

        for (final SearchCriteria param : params) {
            if (param.getOperation().equalsIgnoreCase(":")) {
                if (r.get(param.getKey()).getJavaType() == String.class) {
                    predicate = builder.and(predicate, builder.like(r.get(param.getKey()), "%" + param.getValue() + "%"));
                } else {
                    predicate = builder.and(predicate, builder.equal(r.get(param.getKey()), param.getValue()));
                }
            } else if (param.getOperation().equalsIgnoreCase("==")) {
                if (r.get(param.getKey()).getJavaType() == String.class) {
                    predicate = builder.and(predicate, builder.equal(r.get(param.getKey()), param.getValue()));
                }
            }
        }
        
        
        query.where(predicate);               

        return entityManager.createQuery(query).getResultList().
        		stream().
        		limit(10).
        		collect(Collectors.toList());
    }

}
