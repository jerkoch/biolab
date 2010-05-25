package org.bradylab.plates.web;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.bradylab.plates.PlatesServiceImpl;
import org.bradylab.plates.model.DataPoint;
import org.bradylab.plates.model.Plate;
import org.bradylab.plates.web.model.WebPlate;
import org.bradylab.plates.web.model.WebResult;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/plates")
public class PlateController {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private PlatesServiceImpl service;

	@Autowired
	private DozerBeanMapper mapper;
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public List<WebPlate> getPlates() {
		List<WebPlate> webPlates = new ArrayList<WebPlate>();
		for (Plate src : service.getPlates()) {
			webPlates.add(mapper.map(src, WebPlate.class));
		}
		return webPlates;
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public WebPlate savePlate(@ModelAttribute WebPlate webPlate) {
		Plate plate = mapper.map(webPlate, Plate.class);
		return mapper.map(service.savePlate(plate), WebPlate.class);
	}

	@ResponseBody
	@RequestMapping(value = "/{id}/result", method = RequestMethod.POST)
	public WebResult saveResult(@RequestParam(required = false) List<String> point, @PathVariable("id") Long plateId) {
		Set<DataPoint> dpSet = new HashSet<DataPoint>();
		if (point != null) {
			for (String p : point) {
				dpSet.add(new DataPoint(StringUtils.substringBefore(p, "-"), StringUtils.substringAfter(p, "-")));
			}
		}
		return mapper.map(service.storeResult(plateId, dpSet), WebResult.class);
	}
}
