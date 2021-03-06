package com.example.demo.dao;

import com.example.demo.models.TestNews;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;


@Repository
public class TestNewsDataAccessService {

    @PersistenceContext
    private EntityManager em;

    static final String STATEMENT_SQLMAP1 = "Statement-SQL-MappingDetails";

//    public List<TestNews> findComplainsById(@Param("id") Integer id) {
      public List<TestNews> findNews() {
        Query querydetails = em.createNativeQuery(
//                "SELECT  v.id, v.answer, c.description, m.srNo  FROM vitalinfo4 v JOIN complains2 c ON v.complain_id=c.id JOIN complain_metadatascaps1 m ON c.metadata_id=m.id WHERE (v.question like '%ADDRESS OF THE ISSUE OCCURRING LOCATION%') AND (c.id=:id)  ",
                "SELECT  s.news_id, s.title, s.body, c.summerized_article, s.date, l.category, l.subcategory01 FROM scraped_news_articles s JOIN summerized_news c ON s.news_id=c.news_id JOIN classified_news l ON s.news_id=l.news_id",
                STATEMENT_SQLMAP1);
//        querydetails.setParameter("id", id);
        return querydetails.getResultList();
    }

    public List<TestNews> findSummarizedNewsById(@Param("id") Integer id) {
        Query querydetailsbyid = em.createNativeQuery(
//                "SELECT  v.id, v.answer, c.description, m.srNo  FROM vitalinfo4 v JOIN complains2 c ON v.complain_id=c.id JOIN complain_metadatascaps1 m ON c.metadata_id=m.id WHERE (v.question like '%ADDRESS OF THE ISSUE OCCURRING LOCATION%') AND (c.id=:id)  ",
                "SELECT  s.news_id, s.title, s.body, c.summerized_article, s.date, l.category, l.subcategory01 FROM scraped_news_articles s JOIN summerized_news c ON s.news_id=c.news_id JOIN classified_news l ON s.news_id=l.news_id WHERE c.news_id=:id",
                STATEMENT_SQLMAP1);
        querydetailsbyid.setParameter("id", id);
        return querydetailsbyid.getResultList();
    }

    @SqlResultSetMapping(name= STATEMENT_SQLMAP1, classes = {
            @ConstructorResult(targetClass = TestNews.class,
                    columns = {
                            @ColumnResult(name="news_id",type = Integer.class),
                            @ColumnResult(name="title", type = String.class),
                            @ColumnResult(name="body", type = String.class),
                            @ColumnResult(name="summerized_article", type = String.class),
                            @ColumnResult(name="date", type = String.class),
                            @ColumnResult(name="category", type = String.class),
                            @ColumnResult(name="subcategory01", type = String.class)
                    }
            )
    })  @Entity class SQLMappingCfgEntityy{@Id Integer id;}
//    @Entity class SQLMappingCfgEntityy{@Id Integer id;} // <- workaround

}
