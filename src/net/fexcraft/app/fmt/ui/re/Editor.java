package net.fexcraft.app.fmt.ui.re;

import java.util.ArrayList;
import java.util.Arrays;

import net.fexcraft.app.fmt.FMTB;
import net.fexcraft.app.fmt.ui.NewElement;
import net.fexcraft.app.fmt.ui.UserInterface;
import net.fexcraft.app.fmt.utils.Settings;

public abstract class Editor extends NewElement {
	
	public static final ArrayList<Editor> EDITORS = new ArrayList<>();
	public static final String[] xyz = new String[]{ "x", "y", "z" };
	private NewElement[] containers;

	public Editor(String id, String stylegroup){
		super(null, id, stylegroup, false); EDITORS.add(this); this.setColor(0xff999999);
		this.setPosition(0, 0, -50).setSize(308, 0).setVisible(true).setBorder(0xff000000, 0xffffffff, 1, false, false, false, true);
		elements.addAll(Arrays.asList(containers = this.setupSubElements())); Button button;
		this.elements.add((button = new Button(this, "mb", "multiplicator", width - 8, 28, 4, 4, 0).setText("Multiplicator / Rate", 3, 4)).setHoverColor(0xffffffff, false));
		button.getElements().add(new TextField(button, "mt", "multiplicator:field", 70, button.width - 104, 1){
			@Override protected boolean processScrollWheel(int wheel){
				applyChange(FMTB.MODEL.multiply(wheel > 0 ? 2.0f : 0.5f)); return true;
			}
		}.setAsNumberfield(0, 1024, true).applyChange(FMTB.MODEL.rate));
		button.getElements().add(new Icon(button, "mr", "multiplicator:icon", 26, button.width - 30, 1){
			@Override
			protected boolean processButtonClick(int x, int y, boolean left){
				((TextField)root.getElement("mt")).applyChange(1); FMTB.MODEL.rate = 1f; return true;
			}
		}.setTexture("icons/group_delete", true));
		this.setHoverColor(0xffffffff, false); this.repos();
	}
	
	@Override
	public NewElement repos(){
		x = 0; y = UserInterface.TOOLBAR.height + UserInterface.TOOLBAR.border_width;
		height = FMTB.get().UI.height - y - UserInterface.TOOLBAR.border_width; if(Settings.bottombar()) height -= 26;
		clearVertexes(); for(NewElement elm : elements) elm.repos(); return this;
	}

	protected abstract NewElement[] setupSubElements();

	@Override
	protected boolean processButtonClick(int x, int y, boolean left){
		return false;
	}
	
	public void show(){
		hideAll(); this.visible = true;
	}
	
	public static void show(String id){
		for(Editor edit : EDITORS) edit.setVisible(edit.id.equals(id));
	}
	
	public static void hideAll(){
		for(Editor edit : EDITORS) edit.setVisible(false);
	}
	
	public static Editor get(String id){
		for(Editor edit : EDITORS) if(edit.id.equals(id)) return edit; return null;
	}

	public static void toggle(String string){ toggle(string, true); }

	public static void toggle(String string, boolean close){
		Editor edit = get(string); if(close && edit != null && edit.isVisible()) hideAll(); else show(string);
	}
	
	protected boolean processScrollWheel(int wheel){ return true; }

	public ArrayList<TextField> getFields(){
		ArrayList<TextField> fields = new ArrayList<>();
		for(NewElement elm : elements) if(elm instanceof TextField) fields.add((TextField)elm);
		return fields;
	}

	public static void toggleAll(){
		boolean anyvisible = false;
		for(Editor edit : EDITORS) if(edit.isVisible()){ anyvisible = true; break; }
		if(anyvisible) hideAll(); else show("general");
	}

	public static TextField getGlobalField(String string){
		for(TextField field : TextField.getAllFields()) if(field.getId().equals(string)) return field; return null;
	}

	public static void toggleContainer(int i){
		Editor editor = getVisibleEditor(); if(editor == null){
			if(i == 0) show("general_editor");
			if(i == 1) show("model_group_editor");
			if(i == 2) show("texture_editor");
			if(i == 3) show("preview_editor");
			return;
		}
		if(i < 0 || i >= editor.containers.length) return;
		//TODO editor.containers[i].setExpanded(!editor.containers[i].isExpanded());
	}

	private static Editor getVisibleEditor(){
		for(Editor edit : EDITORS) if(edit.isVisible()) return edit; return null;
	}

	public static boolean anyVisible(){
		for(Editor edit : EDITORS) if(edit.isVisible()) return true; return false;
	}

}
