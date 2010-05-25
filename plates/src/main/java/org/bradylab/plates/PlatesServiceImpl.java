package org.bradylab.plates;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.bradylab.plates.model.DataPoint;
import org.bradylab.plates.model.Label;
import org.bradylab.plates.model.Plate;
import org.bradylab.plates.model.Result;
import org.bradylab.plates.persistence.LabelDao;
import org.bradylab.plates.persistence.PlateDao;
import org.bradylab.plates.persistence.ResultDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.appengine.api.datastore.Key;

@Service
@Transactional
public class PlatesServiceImpl {

	@Autowired
	private PlateDao plateDao;
	
	@Autowired
	private ResultDao resultDao;
	
	@Autowired
	private LabelDao labelDao;
	
	public List<Plate> getPlates() {
		return plateDao.loadAll("name");
	}
	
	public Plate savePlate(Plate plate) {
		return plateDao.save(plate);
	}
	
	public Plate savePlate(String name, int width, int height, int subDivide) {
		Plate plate = new Plate();
		plate.setCreationTime(new Date());
		plate.setName(name);
		plate.setWidth(width);
		plate.setHeight(height);
		plate.setSubDivide(subDivide);
		return plateDao.save(plate);
	}
	
	public List<Result> getResults(Plate plate) {
		return resultDao.getResultsForPlate(plate);
	}
	
	public Result storeResult(Long plateId, Set<DataPoint> points) {
		Result result = new Result();
		result.setCreationTime(new Date());
		result.setDataPoints(points);
		Plate plate = plateDao.load(plateId);
		plate.getResults().add(result);
		plateDao.save(plate);
		return result;
	}
	
	public Label saveLabel(Label label) {
		return labelDao.save(label);		
	}
	
	public Boolean deleteLabel(Key key) {
		labelDao.deleteById(key.getId());
		return true;
	}
	
	public List<Label> getLabels() {
		return labelDao.loadAll("name");
	}	
}
