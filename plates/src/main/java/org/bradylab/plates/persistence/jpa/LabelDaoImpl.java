package org.bradylab.plates.persistence.jpa;

import org.bradylab.plates.model.Label;
import org.bradylab.plates.persistence.LabelDao;
import org.springframework.stereotype.Component;

@Component
public class LabelDaoImpl extends BaseDaoImpl<Label, Long> implements LabelDao {

}
