package com.example.demo.dao;

import com.example.demo.models.CategoricalNews;
import com.example.demo.models.NewsAll;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class NewsAllDataAccessService {

    @PersistenceContext
    private EntityManager em;

    static final String STATEMENT_SQLMAP1 = "Statement-SQL-Mapping2";

    public List<NewsAll> findNews() {
        Query querysummary = em.createNativeQuery(
                "SELECT s.news_id, s.title, s.body, c.category FROM scraped_news_articles s JOIN classified_news c ON s.news_id=c.news_id ",
                STATEMENT_SQLMAP1);
//        querysummary.setParameter("id", id);
        return querysummary.getResultList();
    }

    @SqlResultSetMapping(name= STATEMENT_SQLMAP1, classes = {
            @ConstructorResult(targetClass = NewsAll.class,
                    columns = {
                            @ColumnResult(name="news_id",type = Integer.class),
                            @ColumnResult(name="title",type = String.class),
                            @ColumnResult(name="body",type = String.class),
                            @ColumnResult(name="category",type = String.class),

                    }
            )
    }) @Entity class SQLMappingCfgEntityy{@Id int id;} // <- workaround
}
