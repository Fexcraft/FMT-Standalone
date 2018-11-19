package net.fexcraft.app.fmt.ui.generic;

import java.awt.Desktop;
import java.io.File;

import org.lwjgl.input.Mouse;

import net.fexcraft.app.fmt.FMTB;
import net.fexcraft.app.fmt.porters.PorterManager;
import net.fexcraft.app.fmt.ui.Element;
import net.fexcraft.app.fmt.ui.UserInterface;
import net.fexcraft.app.fmt.ui.editor.Editor;
import net.fexcraft.app.fmt.ui.generic.FileChooser.AfterTask;
import net.fexcraft.app.fmt.ui.tree.RightTree;
import net.fexcraft.app.fmt.utils.GGR;
import net.fexcraft.app.fmt.utils.HelperCollector;
import net.fexcraft.app.fmt.utils.ImageHelper;
import net.fexcraft.app.fmt.utils.SaveLoad;
import net.fexcraft.app.fmt.utils.SessionHandler;
import net.fexcraft.app.fmt.utils.Settings;
import net.fexcraft.app.fmt.utils.TextureManager;
import net.fexcraft.app.fmt.utils.TextureManager.Texture;
import net.fexcraft.app.fmt.wrappers.BoxWrapper;
import net.fexcraft.app.fmt.wrappers.CylinderWrapper;
import net.fexcraft.app.fmt.wrappers.ShapeboxWrapper;
import net.fexcraft.app.fmt.wrappers.TurboList;
import net.fexcraft.lib.common.math.RGB;

public class Toolbar extends Element {
	
	private RGB subhover = new RGB(218, 232, 104);
	
	public Toolbar(){
		super(null, "toolbar");
		this.height = 30;
		//
		String[] buttons = new String[]{ "Files", "Utils", "Editor", "Shapeditor", "Shapelist", "Textures", "Helpers", "Settings", "Account", "Exit"};
		for(int i = 0; i < buttons.length; i++){
			final int j = i;
			this.elements.put(buttons[i].toLowerCase(), new Button(this, buttons[i].toLowerCase(), 100, 26, 2 + (j * 102), 2){
				@Override
				protected boolean processButtonClick(int x, int y, boolean left){
					if(left){
						switch(this.id){
							case "exit":{ SaveLoad.checkIfShouldSave(true); return true; }
							case "camera":{ Mouse.setGrabbed(true); return true; }
							case "shapelist":{ RightTree.toggle("modeltree", true); return true; }
							case "shapeditor":{ Editor.show("general_editor"); return true; }
							case "helpers":{ RightTree.toggle("helpertree", true); return true; }
						}
					}
					return true;
				}
				@Override
				public void setupSubmenu(){
					switch(this.id){
						case "files":{
							this.elements.put("menu", new Menulist(this, "menu", 104, 200, (j * 102), 28){
								@Override
								public void addButtons(){
									this.elements.put("new", new Button(this, "new", 100, 26, 2, 2, subhover){
										@Override
										protected boolean processButtonClick(int x, int y, boolean left){
											SaveLoad.openNewModel(); return true;
										}
									}.setText("New", false));
									//
									this.elements.put("open", new Button(this, "open", 100, 26, 2, 30, subhover){
										@Override
										protected boolean processButtonClick(int x, int y, boolean left){
											SaveLoad.openModel(); return true;
										}
									}.setText("Open..", false));
									//
									this.elements.put("save", new Button(this, "save", 100, 26, 2, 58, subhover){
										@Override
										protected boolean processButtonClick(int x, int y, boolean left){
											SaveLoad.saveModel(false); return true;
										}
									}.setText("Save", false));
									//
									this.elements.put("save_as", new Button(this, "save_as", 100, 26, 2, 86, subhover){
										@Override
										protected boolean processButtonClick(int x, int y, boolean left){
											SaveLoad.saveModel(true); return true;
										}
									}.setText("Save As..", false));
									//
									this.elements.put("export", new Button(this, "export", 100, 26, 2, 114, subhover){
										@Override
										protected boolean processButtonClick(int x, int y, boolean left){
											PorterManager.handleExport(); return true;
										}
									}.setText("Export >>", false));
									//
									this.elements.put("import", new Button(this, "import", 100, 26, 2, 142, subhover){
										@Override
										protected boolean processButtonClick(int x, int y, boolean left){
											PorterManager.handleImport(); return true;
										}
									}.setText("Import <<", false));
									this.elements.put("exit", new Button(this, "exit", 100, 26, 2, 170, subhover){
										@Override protected boolean processButtonClick(int x, int y, boolean left){ SaveLoad.checkIfShouldSave(true); return true; }
									}.setText("Exit", false));
								}
							});
							break;
						}
						case "utils":{
							this.elements.put("menu", new Menulist(this, "menu", 104, 200, (j * 102), 28){
								@Override
								public void addButtons(){
									this.elements.put("undo", new Button(this, "undo", 100, 26, 2, 2, subhover){
										@Override
										protected boolean processButtonClick(int x, int y, boolean left){
											//TODO
											return true;
										}
									}.setText("Undo", false));
									//
									this.elements.put("redo", new Button(this, "redo", 100, 26, 2, 30, subhover){
										@Override
										protected boolean processButtonClick(int x, int y, boolean left){
											//TODO
											return true;
										}
									}.setText("Redo", false));
									//
									/*this.elements.put("movesel", new Button(this, "movesel", 100, 26, 2, 58, subhover){
										@Override
										protected boolean processButtonClick(int x, int y, boolean left){
											//TODO
											return true;
										}
									}.setText("Move Sel.", false));
									//
									this.elements.put("delsel", new Button(this, "delsel", 100, 26, 2, 86, subhover){
										@Override
										protected boolean processButtonClick(int x, int y, boolean left){
											//TODO
											return true;
										}
									}.setText("Delete Sel.", false));*/
									//
									this.elements.put("create_gif", new Button(this, "create_gif", 100, 26, 2, 58, subhover){
										@Override
										protected boolean processButtonClick(int x, int y, boolean left){
											ImageHelper.createGif(false); return true;
										}
									}.setText("Create Gif", false));
									//
									this.elements.put("screenshot", new Button(this, "screenshot", 100, 26, 2, 86, subhover){
										@Override
										protected boolean processButtonClick(int x, int y, boolean left){
											ImageHelper.takeScreenshot(true); return true;
										}
									}.setText("Screenshot", false));
								}
							});
							break;
						}
						case "editor":{
							this.elements.put("menu", new Menulist(this, "menu", 104, 200, (j * 102), 28){
								@Override
								public void addButtons(){
									this.elements.put("floor", new Button(this, "floor", 100, 26, 2, 2, subhover){
										@Override public void setupSubmenu(){ return; }
										@Override protected boolean processButtonClick(int x, int y, boolean left){ Settings.toggleFloor(); return true; }
									}.setText("Toggle Floor", false));
									//
									this.elements.put("lines", new Button(this, "lines", 100, 26, 2, 30, subhover){
										@Override public void setupSubmenu(){ return; }
										@Override protected boolean processButtonClick(int x, int y, boolean left){ Settings.toggleLines(); return true; }
									}.setText("Line/Border", false));
									//
									this.elements.put("cube", new Button(this, "cube", 100, 26, 2, 58, subhover){
										@Override public void setupSubmenu(){ return; }
										@Override protected boolean processButtonClick(int x, int y, boolean left){ Settings.toggleCube(); return true; }
									}.setText("Center Cube", false));
									//
									this.elements.put("demo", new Button(this, "demo", 100, 26, 2, 86, subhover){
										@Override public void setupSubmenu(){ return; }
										@Override protected boolean processButtonClick(int x, int y, boolean left){ Settings.toggleDemo(); return true; }
									}.setText("Demo Model", false));
									this.elements.put("reset", new Button(this, "reset", 100, 26, 2, 114, subhover){
										@Override public void setupSubmenu(){ return; }
										@Override
										protected boolean processButtonClick(int x, int y, boolean left){
											FMTB.ggr = new GGR(FMTB.get(), 0, 4, 4); FMTB.ggr.rotation.xCoord = 45;
											return true;
										}
									}.setText("Reset Cam.", false));
								}
							});
							break;
						}
						case "shapeditor":{
							this.elements.put("menu", new Menulist(this, "menu", 104, 200, (j * 102), 28){
								@Override
								public void addButtons(){
									this.elements.put("general", new Button(this, "general", 100, 26, 2, 2, subhover){
										@Override
										protected boolean processButtonClick(int x, int y, boolean left){
											Editor.toggle("general_editor"); return true;
										}
									}.setText("General", false));
									//
									this.elements.put("shapebox", new Button(this, "shapebox", 100, 26, 2, 30, subhover){
										@Override
										protected boolean processButtonClick(int x, int y, boolean left){
											Editor.toggle("shapebox_editor"); return true;
										}
									}.setText("Shapebox", false));
									//
									this.elements.put("cylinder", new Button(this, "cylinder", 100, 26, 2, 58, subhover){
										@Override
										protected boolean processButtonClick(int x, int y, boolean left){
											Editor.toggle("cylinder_editor"); return true;
										}
									}.setText("Cylinder", false));
									//
									this.elements.put("group", new Button(this, "group", 100, 26, 2, 86, subhover){
										@Override
										protected boolean processButtonClick(int x, int y, boolean left){
											Editor.toggle("group_editor"); return true;
										}
									}.setText("Group", false));
									//
									this.elements.put("model", new Button(this, "model", 100, 26, 2, 114, subhover){
										@Override
										protected boolean processButtonClick(int x, int y, boolean left){
											Editor.toggle("model_editor"); return true;
										}
									}.setText("Model", false));
								}
							});
							break;
						}
						case "shapelist":{
							this.elements.put("menu", new Menulist(this, "menu", 124, 200, (j * 102), 28){
								@Override
								public void addButtons(){
									this.elements.put("add_box", new Button(this, "add_box", 120, 26, 2, 2, subhover){
										@Override
										protected boolean processButtonClick(int x, int y, boolean left){
											FMTB.MODEL.add(new BoxWrapper(FMTB.MODEL));
											this.parent.visible = false; return true;
										}
									}.setText("Add Box/Cube", false));
									//
									this.elements.put("add_shapebox", new Button(this, "add_shapebox", 120, 26, 2, 30, subhover){
										@Override
										protected boolean processButtonClick(int x, int y, boolean left){
											FMTB.MODEL.add(new ShapeboxWrapper(FMTB.MODEL));
											this.parent.visible = false; return true;
										}
									}.setText("Add Shapebox", false));
									//
									this.elements.put("add_cylinder", new Button(this, "add_cylinder", 120, 26, 2, 58, subhover){
										@Override
										protected boolean processButtonClick(int x, int y, boolean left){
											FMTB.MODEL.add(new CylinderWrapper(FMTB.MODEL));
											this.parent.visible = false; return true;
										}
									}.setText("Add Cylinder", false));
									//
									this.elements.put("add_group", new Button(this, "add_group", 120, 26, 2, 86, subhover){
										@Override
										protected boolean processButtonClick(int x, int y, boolean left){
											String string = "group" + FMTB.MODEL.getCompound().size();
											if(FMTB.MODEL.getCompound().containsKey(string)){
												string = "group0" + FMTB.MODEL.getCompound().size();
												FMTB.MODEL.getCompound().put(string, new TurboList(string));
											}
											else{
												FMTB.MODEL.getCompound().put(string, new TurboList(string));
											}
											RightTree.show("modeltree");
											this.parent.visible = false; return true;
										}
									}.setText("Add Group", false));
								}
							});
							break;
						}
						case "textures":{
							this.elements.put("menu", new Menulist(this, "menu", 104, 200, (j * 102), 28){
								@Override
								public void addButtons(){
									this.elements.put("select", new Button(this, "select", 100, 26, 2, 2, subhover){
										@Override
										protected boolean processButtonClick(int x, int y, boolean left){
											UserInterface.FILECHOOSER.show(new String[]{ "Select a texture file." }, new File("./textures"), new AfterTask(){
												@Override
												public void run(){
													String name = file.getPath(); TextureManager.loadTextureFromFile(name, file); FMTB.MODEL.setTexture(name);
													//
													/*Texture tex = TextureManager.getTexture(name, true); if(tex == null) return;
													if(tex.getWidth() > FMTB.MODEL.textureX) FMTB.MODEL.textureX = tex.getWidth();
													if(tex.getHeight() > FMTB.MODEL.textureY) FMTB.MODEL.textureY = tex.getHeight();*/
												}
											}, false, true);
											return true;
										}
									}.setText("Select", false));
									//
									this.elements.put("edit", new Button(this, "edit", 100, 26, 2, 30, subhover){
										@Override
										protected boolean processButtonClick(int x, int y, boolean left){
											if(FMTB.MODEL.texture == null) return true;
											Texture texture = TextureManager.getTexture(FMTB.MODEL.texture, true);
											if(texture == null) return true;
											try{
												if(System.getProperty("os.name").toLowerCase().contains("windows")) {
													String cmd = "rundll32 url.dll,FileProtocolHandler " + texture.getFile().getCanonicalPath();
													Runtime.getRuntime().exec(cmd);
												}
												else{ Desktop.getDesktop().edit(texture.getFile()); }
											}
											catch(Exception e){
												e.printStackTrace();
											}
											return true;
										}
									}.setText("Edit (extern)", false));
									//
									this.elements.put("remove", new Button(this, "remove", 100, 26, 2, 58, subhover){
										@Override
										protected boolean processButtonClick(int x, int y, boolean left){
											if(FMTB.MODEL.texture != null && TextureManager.getTexture(FMTB.MODEL.texture, true) != null){
												TextureManager.removeTexture(FMTB.MODEL.texture);
											}
											return true;
										}
									}.setText("Remove", false));
									//
									this.elements.put("generate", new Button(this, "generate", 100, 26, 2, 86, subhover){
										@Override
										protected boolean processButtonClick(int x, int y, boolean left){
											//TODO
											return true;
										}
									}.setText("Generate", false));
									//
									this.elements.put("autopos", new Button(this, "autopos", 100, 26, 2, 114, subhover){
										@Override
										protected boolean processButtonClick(int x, int y, boolean left){
											//TODO
											return true;
										}
									}.setText("AutoPosition", false));
								}
							});
							break;
						}
						case "helpers":{
							this.elements.put("menu", new Menulist(this, "menu", 134, 200, (j * 102), 28){
								@Override
								public void addButtons(){
									/*this.elements.put("reload", new Button(this, "reload", 130, 26, 2, 2, subhover){
										@Override protected boolean processButtonClick(int x, int y, boolean left){ HelperCollector.reload(); return true; }
									}.setText("Reload List", false));*/
									this.elements.put("view", new Button(this, "view", 130, 26, 2, 2, subhover){
										@Override protected boolean processButtonClick(int x, int y, boolean left){ RightTree.show("helpertree"); return true; }
									}.setText("View Loaded", false));
									//
									this.elements.put("open", new Button(this, "open", 130, 26, 2, 30, subhover){
										@Override
										protected boolean processButtonClick(int x, int y, boolean left){
											UserInterface.FILECHOOSER.show(new String[]{ "Select a Preview/Helper file." }, new File("./helpers"), new AfterTask(){
												@Override public void run(){ HelperCollector.load(file, porter); }
											}, false, false);
											return true;
										}
									}.setText("Open New", false));
									//
									this.elements.put("clear", new Button(this, "clear", 130, 26, 2, 58, subhover){
										@Override
										protected boolean processButtonClick(int x, int y, boolean left){
											HelperCollector.LOADED.clear(); return true;
										}
									}.setText("Clear All", false));
									//
									/*for(int i = 0; i < 10; i++){
										if(i >= HelperCollector.getMap().size()) break; int j = i;
										String name = HelperCollector.getMap().keySet().toArray()[j].toString();
										this.elements.put("helper" + i, new Button(this, "helper" + i, 130, 26, 2, 86 + (i * 28), subhover){
											@Override
											protected boolean processButtonClick(int x, int y, boolean left){
												HelperCollector.load(name);
												return true;
											}
										}.setText(name.length() > 16 ? name.substring(0, 12) + "..." : name, false));
									}*/
								}
							});
							break;
						}
						case "settings":{
							this.elements.put("menu", new Menulist(this, "menu", 104, 200, (j * 102), 28){
								@Override
								public void addButtons(){
									this.elements.put("controls", new Button(this, "controls", 100, 26, 2, 2, subhover){
										@Override
										protected boolean processButtonClick(int x, int y, boolean left){
											//TODO
											return true;
										}
									}.setText("Controls", false));
									//
									this.elements.put("console", new Button(this, "console", 100, 26, 2, 30, subhover){
										@Override
										protected boolean processButtonClick(int x, int y, boolean left){
											//TODO
											return true;
										}
									}.setText("Console", false));
									//
									this.elements.put("Help", new Button(this, "Help", 100, 26, 2, 58, subhover){
										@Override
										protected boolean processButtonClick(int x, int y, boolean left){
											//TODO
											return true;
										}
									}.setText("Help", false));
								}
							});
							break;
						}
						case "account":{
							this.elements.put("menu", new Menulist(this, "menu", 104, 200, (j * 102), 28){
								@Override
								public void addButtons(){
									this.elements.put("license", new Button(this, "license", 100, 26, 2, 2, subhover){
										@Override
										protected boolean processButtonClick(int x, int y, boolean left){
											//TODO
											return true;
										}
									}.setText("License", false));
									//
									this.elements.put("session", new Button(this, "session", 100, 26, 2, 30, subhover){
										@Override
										protected boolean processButtonClick(int x, int y, boolean left){
											SessionHandler.tryLogin(true); return true;
										}
									}.setText("Log In/Out", false));
									//
									this.elements.put("register", new Button(this, "register", 100, 26, 2, 58, subhover){
										@Override
										protected boolean processButtonClick(int x, int y, boolean left){
											//TODO
											return true;
										}
									}.setText("Register", false));
								}
							});
							break;
						}
						case "exit":{
							/*this.elements.put("menu", new Menulist(this, "menu", 154, 200, (j * 102), 28){
								@Override
								public void addButtons(){
									this.elements.put("surr", new Button(this, "surr", 150, 26, 2, 2, subhover){
										@Override
										protected boolean processButtonClick(int x, int y, boolean left){
											this.enabled = false; return true;
										}
									}.setText("Are you sure? (Y/N)", false));
								}
							});*/
							break;
						}
					}
				}
				
			}.setText(buttons[i], true));
		}
	}

	@Override
	public void renderSelf(int rw, int rh){
		this.width = rw;
		this.renderQuad(0, 0, width, height, "ui/background");
	}

	@Override
	protected boolean processButtonClick(int x, int y, boolean left){
		return false;
	}

}
