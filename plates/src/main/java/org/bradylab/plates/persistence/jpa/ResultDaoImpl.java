package org.bradylab.plates.persistence.jpa;

import java.util.List;

import javax.persistence.Query;

import org.bradylab.plates.model.Plate;
import org.bradylab.plates.model.Result;
import org.bradylab.plates.persistence.ResultDao;
import org.springframework.stereotype.Component;

@Component
public class ResultDaoImpl extends BaseDaoImpl<Result, Long> implements ResultDao {

	@SuppressWarnings("unchecked")
	public List<Result> getResultsForPlate(Plate plate) {
		String queryStr = "SELECT * FROM Plate p WHERE p.id = :plateId";
		Query query = getEntityManager().createQuery(queryStr).setParameter("plateId", plate.getId());
		return query.getResultList();
	}
}
