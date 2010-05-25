package org.bradylab.plates.web;

import java.util.ArrayList;
import java.util.List;

import org.bradylab.plates.PlatesServiceImpl;
import org.bradylab.plates.model.Label;
import org.bradylab.plates.web.model.WebLabel;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.appengine.api.datastore.KeyFactory;

@Controller
@RequestMapping("/labels")
public class LabelController {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private PlatesServiceImpl service;

	@Autowired
	private DozerBeanMapper mapper;

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public List<WebLabel> getLabels() {
		List<WebLabel> webLabels = new ArrayList<WebLabel>();
		for (Label src : service.getLabels()) {
			webLabels.add(mapper.map(src, WebLabel.class));
		}
		return webLabels;
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public WebLabel saveLabel(@RequestParam(required = false) String id, @RequestParam String name) {
		Label label = new Label();
		label.setName(name);
		if (id != null) {
			label.setId(KeyFactory.stringToKey(id));
		}
		return mapper.map(service.saveLabel(label), WebLabel.class);
	}

	@ResponseBody
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public Boolean deleteLabel(@PathVariable String id) {
		return service.deleteLabel(KeyFactory.stringToKey(id));
	}

}
