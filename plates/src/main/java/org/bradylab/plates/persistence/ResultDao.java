/**
 * ============================================================================
 * 
 * IBM Confidential
 *
 * (C) Copyright IBM Corp. 2009.
 *
 * The source code for this program is not published or otherwise divested of
 * its trade secrets, irrespective of what has been deposited with the U.S.
 * Copyright office.
 * 
 * ============================================================================
 */

package org.bradylab.plates.persistence;

import java.util.List;

import org.bradylab.plates.model.Plate;
import org.bradylab.plates.model.Result;

public interface ResultDao extends BaseDao<Result, Long> {
	
	public List<Result> getResultsForPlate(Plate plate);
}
