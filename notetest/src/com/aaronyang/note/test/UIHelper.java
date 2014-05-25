package com.aaronyang.note.test;

import com.aaronyang.note.test.elements.ElementLogin;
import com.aaronyang.note.test.elements.ElementMain;

/**
 * elements manager,operator manager
 * @author hiworld
 *
 */
public class UIHelper {
	private ElementLogin elementLogin;
	private ElementMain  elementMain;
	private SoloWrapper soloWrapper = null;
	
	public UIHelper(SoloWrapper soloWrapper){
		this.soloWrapper = soloWrapper;
	}
	
	public  ElementLogin getElementLogin(){
		if(elementLogin == null){
			elementLogin = new ElementLogin(soloWrapper);
			return elementLogin;
		}
		return elementLogin;
	}

	public  ElementMain getElementMain(){
		if(elementLogin == null){
			elementMain =  new ElementMain(soloWrapper);
			return elementMain;
		}
		return elementMain;
	}
	
	public SoloWrapper getSoloWrapper(){
		return soloWrapper;
	}
}
