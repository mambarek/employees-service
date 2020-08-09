package com.it2go.micro.employeesservice.search;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.Data;
import org.hibernate.criterion.MatchMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
public class PredicateBuilder<T> {

  private static final Logger logger = LoggerFactory.getLogger(PredicateBuilder.class);

  private static char ESCAPE_CHAR = '!';

  private static String escape(String value) {
    return value
        .replace("!", ESCAPE_CHAR + "!")
        .replace("%", ESCAPE_CHAR + "%")
        .replace("_", ESCAPE_CHAR + "_");
  }

  private Map<String, Object> paramMap = new HashMap<>();
  private  List<Predicate> predicates = new ArrayList<>();

  public static <T> PredicateBuilder<T> createPredicates(CriteriaBuilder cb, Root<T> root, Group filterGroup){
    PredicateBuilder<T> pb = new PredicateBuilder<>();

    for (Rule rule : filterGroup.getRules()) {
      Predicate rulePredicate = null;

      // unique operator needs no value
      switch (rule.getOp()) {
        case NULL: {
          rulePredicate = cb.isNull(root.get(rule.getField()));
          break;
        }
        case NOT_NULL: {
          rulePredicate = cb.isNotNull(root.get(rule.getField()));
          break;
        }
      }

      if(rule.getData() != null && !rule.getData().isEmpty()){
        final Object value;
        boolean isDate = "date".equals(rule.getType().toLowerCase());
        boolean isNumber = "number".equals(rule.getType().toLowerCase());

        String escapedValue = escape(rule.getData());
        final Expression path;
        final ParameterExpression namedParameter;
        if (isDate) {
          namedParameter = cb.<Date>parameter(Date.class, rule.getField());
          path = root.<Date>get(rule.getField());
          Date date = null;
          try {
            LocalDate localDate = LocalDate.parse(rule.getData());
            date = Date.valueOf(localDate);
          } catch (Exception e) {
            logger.error(e.getMessage());
          }

          value = date;
        } else if (isNumber) {
          namedParameter = cb.<Number>parameter(Number.class, rule.getField());
          path = root.<Number>get(rule.getField());
          Number number = null;
          try {
            number = Double.parseDouble(rule.getData());
          } catch (Exception e) {
            logger.error(e.getMessage());
          }

          value = number;
        } else {
          namedParameter = cb.<String>parameter(String.class, rule.getField());
          path = root.<String>get(rule.getField());
          value = rule.getData();
        }

        pb.paramMap.put(rule.getField(), value);

        switch (rule.getOp()) {
          case EQUAL: {
            rulePredicate = cb.equal(path, namedParameter);
            break;
          }
          case NOT_EQUAL: {
            rulePredicate = cb.notEqual(path, namedParameter);
            break;
          }
          case LESS: {
            rulePredicate = cb.lessThan(path, namedParameter);
            break;
          }
          case LESS_OR_EQUAL: {
            rulePredicate = cb.lessThanOrEqualTo(path, namedParameter);
            break;
          }
          case GREATHER: {
            rulePredicate = cb.greaterThan(path, namedParameter);
            break;
          }
          case GREATHER_OR_EQUAL: {
            rulePredicate = cb.greaterThanOrEqualTo(path, namedParameter);
            break;
          }
          case CONTAINS: {
            pb.paramMap.put(rule.getField(), MatchMode.ANYWHERE.toMatchString(escapedValue));
            rulePredicate = cb.like(path, namedParameter, ESCAPE_CHAR);
            break;
          }
          case NOT_CONTAINS: {
            pb.paramMap.put(rule.getField(), MatchMode.ANYWHERE.toMatchString(escapedValue));
            rulePredicate = cb.notLike(path, namedParameter, ESCAPE_CHAR);
            break;
          }
          case ENDS_WITH: {
            pb.paramMap.put(rule.getField(), MatchMode.END.toMatchString(escapedValue));
            rulePredicate = cb.like(path, namedParameter, ESCAPE_CHAR);
            break;
          }
          case NOT_ENDS_WITH: {
            pb.paramMap.put(rule.getField(), MatchMode.END.toMatchString(escapedValue));
            rulePredicate = cb.notLike(path, namedParameter, ESCAPE_CHAR);
            break;
          }
          case BEGINS_WITH: {
            pb.paramMap.put(rule.getField(), MatchMode.START.toMatchString(escapedValue));
            rulePredicate = cb.like(path, namedParameter, ESCAPE_CHAR);
            break;
          }
          case NOT_BEGIN_WITH: {
            pb.paramMap.put(rule.getField(), MatchMode.START.toMatchString(escapedValue));
            rulePredicate = cb.notLike(path, namedParameter, ESCAPE_CHAR);
            break;
          }
          case IN: {
            final Expression<Integer> locate = cb.locate(namedParameter, path);
            rulePredicate = cb.gt(locate, 0);
            break;
          }
          case NOT_IN: {
            final Expression<Integer> locate = cb.locate(namedParameter, path);
            rulePredicate = cb.equal(locate, 0);
            break;
          }
          case BETWEEN: {
            break;
          }
        }
      }

    }

    return pb;
  }
}
