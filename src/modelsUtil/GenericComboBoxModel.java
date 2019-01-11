/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelsUtil;

import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author focuswts
 */
public class GenericComboBoxModel<T> extends DefaultComboBoxModel {
	private static final long serialVersionUID = 3163209468787065554L;
	private List<T> data;
	private T nullObj;
	public GenericComboBoxModel(List<T> list) {
		super(list.toArray());
		data = list;
	}
	public GenericComboBoxModel(List<T> list, T nullObj) {
		super(list.toArray());
		this.nullObj = nullObj;
		data = new ArrayList<T>();
		insertElementAt(nullObj, 0);
		data.add(nullObj);
		data.addAll(list);
		setSelectedObject(null);
	}
	public GenericComboBoxModel() {
		data = new ArrayList<T>();
	}
	public void addObject(T obj) {
		addElement(obj);
		data.add(obj);
		setSelectedObject(obj);
	}
	public void removeObject(T obj) {
		removeElement(obj);
		data.remove(obj);
	}
	public void setData(List<T> list) {
		removeAllElements();
		data.clear();
		if (nullObj != null) {
			data = new ArrayList<T>();
			data.add(nullObj);
			data.addAll(list);
		}else {
			data = list;
		}
		for (T obj : data) {
			addElement(obj);
		}
	}
	public List<T> getData() {
		return data;
	}
	public void setSelectedObject(T obj) {
		if (obj == null) {
			setSelectedItem(nullObj);
		}else{
			setSelectedItem(obj);
		}
	}
	@SuppressWarnings("unchecked")
	public T getSelectedObject() {
		return (T)getSelectedItem();
	}
	public void setNullObject(T obj) {
		nullObj = obj;
		if (!data.isEmpty()) {
			data.add(0, nullObj);
		}
	}
	public void clear() {
		data.clear();
		removeAllElements();
		if (nullObj != null)
			data.add(nullObj);
		setSelectedObject(null);
	}
}
