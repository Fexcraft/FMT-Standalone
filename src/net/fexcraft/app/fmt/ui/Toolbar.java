package net.fexcraft.app.fmt.ui;

import java.io.File;

import org.liquidengine.legui.component.Panel;

import net.fexcraft.app.fmt.FMT;
import net.fexcraft.app.fmt.attributes.UpdateHandler;
import net.fexcraft.app.fmt.attributes.UpdateHandler.UpdateHolder;
import net.fexcraft.app.fmt.attributes.UpdateType;
import net.fexcraft.app.fmt.settings.Settings;
import net.fexcraft.app.fmt.ui.ToolbarMenu.MenuButton;
import net.fexcraft.app.fmt.ui.components.QuickAdd;
import net.fexcraft.app.fmt.utils.Logging;
import net.fexcraft.app.fmt.utils.SaveHandler;

public class Toolbar extends Panel {
	
	public static final Runnable NOTHING = () -> {};
	private UpdateHolder holder;

	public Toolbar(){
		super(0, 0, FMT.WIDTH, 30);
		this.setFocusable(false);
		Settings.applyBorderless(this);
		holder = new UpdateHolder();
		this.add(new Icon(0, "./resources/textures/icons/toolbar/info.png", () -> Logging.log("test")));
		this.add(new Icon(1, "./resources/textures/icons/toolbar/settings.png", NOTHING));
		this.add(new Icon(2, "./resources/textures/icons/toolbar/profile.png", NOTHING));
		this.add(new Icon(3, "./resources/textures/icons/toolbar/save.png", () -> SaveHandler.save(FMT.MODEL, null)));
		this.add(new Icon(4, "./resources/textures/icons/toolbar/open.png", () -> SaveHandler.openDialog()));
		this.add(new Icon(5, "./resources/textures/icons/toolbar/new.png", () -> SaveHandler.newDialog()));
		this.add(new ToolbarMenu(0, "file",
			new MenuButton(0, "file.new", () -> SaveHandler.newDialog()),
			new MenuButton(1, "file.open", () -> SaveHandler.openDialog()),
			new ToolbarMenu(-2, "file.recent",
				new MenuButton(0, "file.recent.none"),
				new MenuButton(1, "file.recent.none"),
				new MenuButton(2, "file.recent.none"),
				new MenuButton(3, "file.recent.none"),
				new MenuButton(4, "file.recent.none"),
				new MenuButton(5, "file.recent.none"),
				new MenuButton(6, "file.recent.none"),
				new MenuButton(7, "file.recent.none"),
				new MenuButton(8, "file.recent.none"),
				new MenuButton(9, "file.recent.none")
			).setLayerPreShow(layer -> {
				//TODO
			}),
			new MenuButton(3, "file.save", () -> SaveHandler.saveDialogByState(null)),
			new MenuButton(4, "file.save_as", () -> SaveHandler.saveAsDialog(null)),
			new MenuButton(5, "file.import"),
			new MenuButton(6, "file.export"),
			new MenuButton(7, "file.settings"),
			new MenuButton(8, "file.donate"),
			new MenuButton(9, "file.exit", () -> FMT.close())
		));
		this.add(new ToolbarMenu(1, "utils"));
		this.add(new ToolbarMenu(2, "editors",
			new MenuButton(0, "editors.new")
		));
		holder.add(UpdateType.EDITOR_CREATED, wrap -> {
			Editor editor = wrap.get(0);
			ToolbarMenu menu = ToolbarMenu.MENUS.get(editor.tree ? "trees" : "editors");
			MenuButton button = new MenuButton(menu.components.size(), editor.id, editor.name);
			button.addListener(() -> editor.toggle());
			menu.components.add(button);
			menu.layer.regComponent(button);
			menu.layer.refreshSize();
		});
		holder.add(UpdateType.EDITOR_REMOVED, wrap -> {
			Editor editor = wrap.get(0);
			if(editor.tree) return;
			//TODO
		});
		this.add(new ToolbarMenu(3, "trees"));
		this.add(new ToolbarMenu(4, "polygons",
			new MenuButton(0, "polygons.add_box", () -> QuickAdd.addBox()),
			new MenuButton(1, "polygons.add_shapebox", () -> QuickAdd.addShapebox()),
			new MenuButton(2, "polygons.add_cylinder", () -> QuickAdd.addCylinder()),
			new MenuButton(3, "polygons.add_boundingbox"),
			new MenuButton(4, "polygons.add_object"),
			new MenuButton(5, "polygons.add_marker"),
			new MenuButton(6, "polygons.add_group", () -> QuickAdd.addGroup()),
			new MenuButton(7, "polygons.add_voxel"),
			new ToolbarMenu(-8, "polygons.special",
				new MenuButton(0, "polygons.special.fvtm_rail"),
				new MenuButton(1, "polygons.special.curve_line")
			)
		));
		this.add(new ToolbarMenu(5, "texture"));
		this.add(new ToolbarMenu(6, "helpers"));
		this.add(new ToolbarMenu(7, "project",
			new MenuButton(0, "project.open"),
			new MenuButton(1, "project.settings"),
			//new MenuButton(2, "project.import"),
			//new MenuButton(3, "project.export"),
			new MenuButton(2, "project.close")
		));
		this.add(new ToolbarMenu(8, "exit", () -> FMT.close()));
		UpdateHandler.registerHolder(holder);
	}

	public static void addRecent(File file){
		// TODO Auto-generated method stub
	}

}
