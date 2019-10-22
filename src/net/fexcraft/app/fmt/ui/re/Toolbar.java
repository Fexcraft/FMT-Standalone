package net.fexcraft.app.fmt.ui.re;

import java.awt.Desktop;
import java.io.File;

import net.fexcraft.app.fmt.FMTB;
import net.fexcraft.app.fmt.porters.PorterManager;
import net.fexcraft.app.fmt.ui.NewElement;
import net.fexcraft.app.fmt.ui.UserInterface;
import net.fexcraft.app.fmt.ui.editor.Editor;
import net.fexcraft.app.fmt.ui.general.DialogBox;
import net.fexcraft.app.fmt.ui.general.NFC.AfterTask;
import net.fexcraft.app.fmt.ui.general.NFC.ChooserMode;
import net.fexcraft.app.fmt.ui.tree.RightTree;
import net.fexcraft.app.fmt.utils.GGR;
import net.fexcraft.app.fmt.utils.HelperCollector;
import net.fexcraft.app.fmt.utils.ImageHelper;
import net.fexcraft.app.fmt.utils.SaveLoad;
import net.fexcraft.app.fmt.utils.Settings;
import net.fexcraft.app.fmt.utils.TextureManager;
import net.fexcraft.app.fmt.utils.TextureManager.Texture;
import net.fexcraft.app.fmt.utils.TextureUpdate;
import net.fexcraft.app.fmt.wrappers.BoxWrapper;
import net.fexcraft.app.fmt.wrappers.CollisionGridWrapper;
import net.fexcraft.app.fmt.wrappers.CylinderWrapper;
import net.fexcraft.app.fmt.wrappers.MarkerWrapper;
import net.fexcraft.app.fmt.wrappers.PolygonWrapper;
import net.fexcraft.app.fmt.wrappers.ShapeboxWrapper;
import net.fexcraft.app.fmt.wrappers.TexrectWrapperA;
import net.fexcraft.app.fmt.wrappers.TexrectWrapperB;
import net.fexcraft.app.fmt.wrappers.TurboList;
import net.fexcraft.lib.common.math.RGB;
import net.fexcraft.lib.common.utils.Print;

public class Toolbar extends NewElement {
	
	private UserInterface ui;

	public Toolbar(UserInterface ui){
		super(null, "toolbar", "toolbar"); this.ui = ui; hovercolor = RGB.WHITE;
		this.setPosition(0, 0, -20)/*.setTexture("ui/background_dark")*/.setSize(100, 30);
		TextureManager.loadTexture("icons/toolbar/info", null);
		TextureManager.loadTexture("icons/toolbar/new", null);
		TextureManager.loadTexture("icons/toolbar/open", null);
		TextureManager.loadTexture("icons/toolbar/save", null);
		TextureManager.loadTexture("icons/toolbar/profile", null);
		TextureManager.loadTexture("icons/toolbar/settings", null);
		this.setColor(0xff484848).setBorder(0xff32a852, 0xffeb4034, 3, false, true);
		//
		this.elements.add(new Icon(this, "info", "icons/toolbar/info", 32, 0, 0){
			@Override
			protected boolean processButtonClick(int x, int y, boolean left){
				DialogBox.notAvailableYet(); return true;
			}
		});
		this.elements.add(new Icon(this, "new_file", "icons/toolbar/new", 32, 0, 0){
			@Override
			protected boolean processButtonClick(int x, int y, boolean left){
				SaveLoad.openNewModel(); return true;
			}
		});
		this.elements.add(new Icon(this, "open_file", "icons/toolbar/open", 32, 0, 0){
			@Override
			protected boolean processButtonClick(int x, int y, boolean left){
				SaveLoad.openModel(); return true;
			}
		});
		this.elements.add(new Icon(this, "save_file", "icons/toolbar/save", 32, 0, 0){
			@Override
			protected boolean processButtonClick(int x, int y, boolean left){
				SaveLoad.saveModel(false, false); return true;
			}
		});
		this.elements.add(new Icon(this, "profile", "icons/toolbar/profile", 32, 0, 0){
			@Override
			protected boolean processButtonClick(int x, int y, boolean left){
				DialogBox.notAvailableYet(); return true;
			}
		});
		this.elements.add(new Icon(this, "settings", "icons/toolbar/settings", 32, 0, 0){
			@Override
			protected boolean processButtonClick(int x, int y, boolean left){
				UserInterface.SETTINGSBOX.show(); return true;
			}
		});
		//
		int btsz = 96, bthg = 28;
		this.elements.add(new Button(this, "file", null, btsz, bthg, 0, 0){
			@Override
			public void setupSubmenu(){
				HoverMenu menu = new HoverMenu(this, "menu", 100){
					@Override
					public void addButtons(){
						this.elements.add(new Button(this, "new_model", null, 20, 26, 0, 0){
							@Override
							protected boolean processButtonClick(int x, int y, boolean left){
								SaveLoad.openNewModel(); return true;
							}
						}.setText("New Model", false));
						this.elements.add(new Button(this, "open", null, 20, 26, 0, 0){
							@Override
							protected boolean processButtonClick(int x, int y, boolean left){
								SaveLoad.openModel(); return true;
							}
						}.setText("Open Model", false));
						this.elements.add(new Button(this, "save", null, 20, 26, 0, 0){
							@Override
							protected boolean processButtonClick(int x, int y, boolean left){
								SaveLoad.saveModel(false, false); return true;
							}
						}.setText("Save Model", false));
						this.elements.add(new Button(this, "save_as", null, 20, 26, 0, 0){
							@Override
							protected boolean processButtonClick(int x, int y, boolean left){
								SaveLoad.saveModel(true, false); return true;
							}
						}.setText("Save as...", false));
						this.elements.add(new Button(this, "import", null, 20, 26, 0, 0){
							@Override
							protected boolean processButtonClick(int x, int y, boolean left){
								PorterManager.handleImport(); return true;
							}
						}.setText("Import <<", false));
						this.elements.add(new Button(this, "export", null, 20, 26, 0, 0){
							@Override
							protected boolean processButtonClick(int x, int y, boolean left){
								PorterManager.handleExport(); return true;
							}
						}.setText("Export >>", false));
						this.elements.add(new Button(this, "exit", null, 20, 26, 0, 0){
							@Override
							protected boolean processButtonClick(int x, int y, boolean left){
								SaveLoad.checkIfShouldSave(true, false); return true;
							}
						}.setText("Exit", false));
					}
				};
				this.elements.add(menu);
			}
		}.setText("File", true));
		this.elements.add(new Button(this, "utils", null, btsz, bthg, 0, 0){
			@Override
			public void setupSubmenu(){
				HoverMenu menu = new HoverMenu(this, "menu", 100){
					@Override
					public void addButtons(){
						this.elements.add(new Button(this, "undo", null, 20, 26, 0, 0){
							@Override
							protected boolean processButtonClick(int x, int y, boolean left){
								DialogBox.notAvailableYet(); return true;
							}
						}.setText("Undo", false));
						this.elements.add(new Button(this, "redo", null, 20, 26, 0, 0){
							@Override
							protected boolean processButtonClick(int x, int y, boolean left){
								DialogBox.notAvailableYet(); return true;
							}
						}.setText("Redo", false));
						this.elements.add(new Button(this, "create_gif", null, 20, 26, 0, 0){
							@Override
							protected boolean processButtonClick(int x, int y, boolean left){
								ImageHelper.createGif(false); return true;
							}
						}.setText("Create Gif", false));
						this.elements.add(new Button(this, "screenshot", null, 20, 26, 0, 0){
							@Override
							protected boolean processButtonClick(int x, int y, boolean left){
								ImageHelper.takeScreenshot(true); return true;
							}
						}.setText("Screenshot", false));
						this.elements.add(new Button(this, "reset", null, 20, 26, 0, 0){
							@Override
							protected boolean processButtonClick(int x, int y, boolean left){
								FMTB.ggr = new GGR(FMTB.get(), 0, 4, 4); FMTB.ggr.rotation.xCoord = 45;
								return true;
							}
						}.setText("Reset Camera", false));
						this.elements.add(new Button(this, "calc_size", null, 20, 26, 0, 0){
							@Override
							protected boolean processButtonClick(int x, int y, boolean left){
								long l = 0, ll = 0;
								for(TurboList list : FMTB.MODEL.getCompound().values()){
									for(PolygonWrapper wrapper : list){
										l += jdk.nashorn.internal.ir.debug.ObjectSizeCalculator.getObjectSize(wrapper.getTurboObject(0));
									}
								}
								ll = jdk.nashorn.internal.ir.debug.ObjectSizeCalculator.getObjectSize(FMTB.MODEL);
								Print.console("MainClass: " + Settings.byteCountToString(jdk.nashorn.internal.ir.debug.ObjectSizeCalculator.getObjectSize(FMTB.get()), true));
								FMTB.showDialogbox("Size (MC / Editor): \n" + Settings.byteCountToString(l, true) + " // " + Settings.byteCountToString(ll, true), "ok", null, DialogBox.NOTHING, null);
								//
								long uis = jdk.nashorn.internal.ir.debug.ObjectSizeCalculator.getObjectSize(FMTB.get().UI);
								Print.console("UI: " + Settings.byteCountToString(uis, true));
								return true;
							}
						}.setText("Calc. Size", false));
					}
				};
				this.elements.add(menu);
			}
		}.setText("Utils", true));
		this.elements.add(new Button(this, "editor", null, btsz, bthg, 0, 0){
			@Override
			public void setupSubmenu(){
				HoverMenu menu = new HoverMenu(this, "menu", 100){
					@Override
					public void addButtons(){
						this.elements.add(new Button(this, "copy_selection", null, 20, 26, 0, 0){
							@Override
							protected boolean processButtonClick(int x, int y, boolean left){
								FMTB.MODEL.copyAndSelect(); return true;
							}
						}.setText("Copy Selected", false));
						for(int i = 0; i < 3; i++){
							String str = i == 0 ? "x" : i == 1 ? "y" : "z"; int j = i; String[] arr = new String[]{ "[L/R]", "[U/D]", "[F/B]"};
							this.elements.add(new Button(this, "flip_" + str, null, 20, 26, 0, 0){
								@Override protected boolean processButtonClick(int x, int y, boolean left){ FMTB.MODEL.flipShapeboxes(j); return true; }
							}.setText("Flip (" + str.toUpperCase() + ") " + arr[j], false));
						}
						this.elements.add(new Button(this, "controls", null, 20, 26, 0, 0){
							@Override
							protected boolean processButtonClick(int x, int y, boolean left){
								UserInterface.CONTROLS.show(); return true;
							}
						}.setText("Controls", false));
					}
				};
				this.elements.add(menu);
			}
		}.setText("Editor", true));
		this.elements.add(new Button(this, "shapeditor", null, btsz, bthg, 0, 0){
			@Override
			public void setupSubmenu(){
				HoverMenu menu = new HoverMenu(this, "menu", 100){
					@Override
					public void addButtons(){
						this.elements.add(new Button(this, "toggle", null, 20, 26, 0, 0){
							@Override
							protected boolean processButtonClick(int x, int y, boolean left){
								Editor.toggleAll(); return true;
							}
						}.setText("Toggle Visibility", false));
						this.elements.add(new Button(this, "general", null, 20, 26, 0, 0){
							@Override
							protected boolean processButtonClick(int x, int y, boolean left){
								Editor.show("general_editor"); return true;
							}
						}.setText("General Editor", false));
						this.elements.add(new Button(this, "texture", null, 20, 26, 0, 0){
							@Override
							protected boolean processButtonClick(int x, int y, boolean left){
								Editor.show("texture_editor"); return true;
							}
						}.setText("Texture Editor (beta)", false));
						this.elements.add(new Button(this, "model_group", null, 20, 26, 0, 0){
							@Override
							protected boolean processButtonClick(int x, int y, boolean left){
								Editor.show("model_group_editor"); return true;
							}
						}.setText("Model/Group Editor", false));
					}
				};
				this.elements.add(menu);
			}
		}.setText("Shapeditor", true));
		this.elements.add(new Button(this, "shapelist", null, btsz, bthg, 0, 0){
			@Override
			protected boolean processButtonClick(int x, int y, boolean left){
				RightTree.toggle("modeltree"); return true;
			}
			@Override
			public void setupSubmenu(){
				HoverMenu menu = new HoverMenu(this, "menu", 100){
					@Override
					public void addButtons(){
						this.elements.add(new Button(this, "add_box", null, 20, 26, 0, 0){
							@Override
							protected boolean processButtonClick(int x, int y, boolean left){
								FMTB.MODEL.add(new BoxWrapper(FMTB.MODEL), null, true);
								this.root.setVisible(false); return true;
							}
						}.setText("Add Generic Box", false));
						this.elements.add(new Button(this, "add_shapebox", null, 20, 26, 0, 0){
							@Override
							protected boolean processButtonClick(int x, int y, boolean left){
								FMTB.MODEL.add(new ShapeboxWrapper(FMTB.MODEL), null, true);
								this.root.setVisible(false); return true;
							}
						}.setText("Add Shapebox", false));
						this.elements.add(new Button(this, "add_texrectb", null, 20, 26, 0, 0){
							@Override
							protected boolean processButtonClick(int x, int y, boolean left){
								FMTB.MODEL.add(new TexrectWrapperB(FMTB.MODEL), null, true);
								this.root.setVisible(false); return true;
							}
						}.setText("Add TexRect [Basic]", false));
						this.elements.add(new Button(this, "add_texrecta", null, 20, 26, 0, 0){
							@Override
							protected boolean processButtonClick(int x, int y, boolean left){
								FMTB.MODEL.add(new TexrectWrapperA(FMTB.MODEL), null, true);
								this.root.setVisible(false); return true;
							}
						}.setText("Add TexRect [Adv.]", false));
						this.elements.add(new Button(this, "add_cylinder", null, 20, 26, 0, 0){
							@Override
							protected boolean processButtonClick(int x, int y, boolean left){
								FMTB.MODEL.add(new CylinderWrapper(FMTB.MODEL), null, true);
								this.root.setVisible(false); return true;
							}
						}.setText("Add Cylinder", false));
						this.elements.add(new Button(this, "add_group", null, 20, 26, 0, 0){
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
								this.root.setVisible(false); return true;
							}
						}.setText("Add Group", false));
						this.elements.add(new Button(this, "add_marker", null, 20, 26, 0, 0){
							@Override
							protected boolean processButtonClick(int x, int y, boolean left){
								FMTB.MODEL.add(new MarkerWrapper(FMTB.MODEL), "markers", true);
								this.root.setVisible(false); return true;
							}
						}.setText("Add Marker", false));
						this.elements.add(new Button(this, "add_collisiongrid", null, 20, 26, 0, 0){
							@Override
							protected boolean processButtonClick(int x, int y, boolean left){
								FMTB.MODEL.add(new CollisionGridWrapper(FMTB.MODEL), null, true);
								this.root.setVisible(false); return true;
							}
						}.setText("Add CollisionGrid", false));
					}
				};
				this.elements.add(menu);
			}
		}.setText("Shapelist", true));
		this.elements.add(new Button(this, "textures", null, btsz, bthg, 0, 0){
			@Override
			public void setupSubmenu(){
				HoverMenu menu = new HoverMenu(this, "menu", 100){
					@Override
					public void addButtons(){
						this.elements.add(new Button(this, "select", null, 20, 26, 0, 0){
							@Override
							protected boolean processButtonClick(int x, int y, boolean left){
								UserInterface.FILECHOOSER.show(new String[]{ "Select a texture file." }, new File("./resources/textures"), new AfterTask(){
									@Override
									public void run(){
										String name = file.getPath(); TextureManager.loadTextureFromFile(name, file); FMTB.MODEL.setTexture(name);
										//
										/*Texture tex = TextureManager.getTexture(name, true); if(tex == null) return;
										if(tex.getWidth() > FMTB.MODEL.textureX) FMTB.MODEL.textureX = tex.getWidth();
										if(tex.getHeight() > FMTB.MODEL.textureY) FMTB.MODEL.textureY = tex.getHeight();*/
									}
								}, ChooserMode.PNG); return true;
							}
						}.setText("Select Texture", false));
						this.elements.add(new Button(this, "edit", null, 20, 26, 0, 0){
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
								} catch(Exception e){ e.printStackTrace(); } return true;
							}
						}.setText("Edit (External)", false));
						this.elements.add(new Button(this, "remove", null, 20, 26, 0, 0){
							@Override
							protected boolean processButtonClick(int x, int y, boolean left){
								if(FMTB.MODEL.texture != null && TextureManager.getTexture(FMTB.MODEL.texture, true) != null){
									FMTB.MODEL.setTexture(null); TextureManager.removeTexture(FMTB.MODEL.texture);
								} return true;
							}
						}.setText("Remove/Unload", false));
						this.elements.add(new Button(this, "texreset", null, 20, 26, 0, 0){
							@Override
							protected boolean processButtonClick(int x, int y, boolean left){
								FMTB.MODEL.getCompound().values().forEach(list -> list.forEach(turbo -> {
									turbo.textureX = 0; turbo.textureY = 0; turbo.recompile();
								}));
								FMTB.showDialogbox("Texture Positions Reset.", "ok",  null, DialogBox.NOTHING, null); return true;
							}
						}.setText("Reset TexPos.", false));
						this.elements.add(new Button(this, "autopos", null, 20, 26, 0, 0){
							@Override
							protected boolean processButtonClick(int x, int y, boolean left){
								TextureUpdate.tryAutoPos(null); return true;
							}
						}.setText("Auto Position", false));
						this.elements.add(new Button(this, "generate", null, 20, 26, 0, 0){
							@Override
							protected boolean processButtonClick(int x, int y, boolean left){
								String texname = "temp/" + FMTB.MODEL.name;
								FMTB.MODEL.setTexture(texname);
		                    	TextureManager.newBlankTexture(texname, null);
		                    	Texture tex = TextureManager.getTexture(texname, true);
		                    	FMTB.MODEL.textureScale = 1; FMTB.MODEL.updateFields();
		                    	FMTB.MODEL.getCompound().values().forEach(elm -> elm.forEach(poly -> poly.burnToTexture(tex.getImage(), null)));
		                    	TextureManager.saveTexture(texname); tex.reload(); FMTB.MODEL.recompile();
								return true;
							}
						}.setText("Generate New", false));
					}
				};
				this.elements.add(menu);
			}
		}.setText("Texture", true));
		this.elements.add(new Button(this, "helpers", null, btsz, bthg, 0, 0){
			@Override
			protected boolean processButtonClick(int x, int y, boolean left){
				RightTree.toggle("helpertree"); return true;
			}
			@Override
			public void setupSubmenu(){
				HoverMenu menu = new HoverMenu(this, "menu", 100){
					@Override
					public void addButtons(){
						this.elements.add(new Button(this, "view", null, 20, 26, 0, 0){
							@Override
							protected boolean processButtonClick(int x, int y, boolean left){
								RightTree.show("helpertree"); return true;
							}
						}.setText("View Loaded", false));
						this.elements.add(new Button(this, "fmtb", null, 20, 26, 0, 0){
							@Override
							protected boolean processButtonClick(int x, int y, boolean left){
								UserInterface.FILECHOOSER.show(new String[]{ "Select a Preview/Helper file." }, new File("./helpers"), new AfterTask(){
									@Override public void run(){ HelperCollector.loadFMTB(file); }
								}, ChooserMode.SAVEFILE_LOAD); return true;
							}
						}.setText("Load FMTB", false));
						this.elements.add(new Button(this, "frame", null, 20, 26, 0, 0){
							@Override
							protected boolean processButtonClick(int x, int y, boolean left){
								UserInterface.FILECHOOSER.show(new String[]{ "Select an Image file." }, new File("./helpers"), new AfterTask(){
									@Override public void run(){ HelperCollector.loadFrame(file); }
								}, ChooserMode.HELPFRAMEIMG); return true;
							}
						}.setText("Load Frame", false));
						this.elements.add(new Button(this, "import", null, 20, 26, 0, 0){
							@Override
							protected boolean processButtonClick(int x, int y, boolean left){
								UserInterface.FILECHOOSER.show(new String[]{ "Select a Preview/Helper file." }, new File("./helpers"), new AfterTask(){
									@Override public void run(){ HelperCollector.load(file, porter, mapped_settings); }
								}, ChooserMode.IMPORT); return true;
							}
						}.setText("Load Imported", false));
						this.elements.add(new Button(this, "clear", null, 20, 26, 0, 0){
							@Override
							protected boolean processButtonClick(int x, int y, boolean left){
								HelperCollector.LOADED.clear(); return true;
							}
						}.setText("Clear All / Unload", false));
					}
				};
				this.elements.add(menu);
			}
		}.setText("Helpers", true));
		this.elements.add(new Button(this, "exit", null, btsz, bthg, 0, 0){
			@Override
			protected boolean processButtonClick(int x, int y, boolean left){
				SaveLoad.checkIfShouldSave(true, false); return true;
			}
		}.setText("Exit", true));
		//
		realign();
	}

	private void realign(){
		int start = 4, high = 3;
		for(NewElement elm : elements){
			if(start + elm.width >= width - 4){ start = 4; high += 34; }
			elm.setPosition(start, elm instanceof Icon ? high - 2 : high, null); start += elm.width + 2;
		}
		this.height = high + 31; for(NewElement elm : elements) elm.repos();
	}
	
	@Override
	public NewElement repos(){
		width = ui.width; x = xrel; y = yrel; realign(); return this.clearVertexes();
	}

}