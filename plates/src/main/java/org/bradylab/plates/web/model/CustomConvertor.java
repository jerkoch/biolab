package org.bradylab.plates.web.model;

import org.dozer.DozerConverter;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class CustomConvertor extends DozerConverter<String, Key> {

	public CustomConvertor() {
		super(String.class, Key.class);
	}

	public Key convertTo(String source, Key destination) {
		if (source != null) {
			return KeyFactory.stringToKey(source);
		}
		return null;
	}

	public String convertFrom(Key source, String destination) {
		if (source != null) {
			return KeyFactory.keyToString(source);
		}
		return null;
	}
}
