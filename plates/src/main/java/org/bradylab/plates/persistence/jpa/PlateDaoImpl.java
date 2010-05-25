package org.bradylab.plates.persistence.jpa;

import org.bradylab.plates.model.Plate;
import org.bradylab.plates.persistence.PlateDao;
import org.springframework.stereotype.Component;

@Component
public class PlateDaoImpl extends BaseDaoImpl<Plate, Long> implements PlateDao {

}
