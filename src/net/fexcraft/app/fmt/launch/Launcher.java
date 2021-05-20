package net.fexcraft.app.fmt.launch;

import java.awt.Button;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import net.fexcraft.app.json.JsonHandler;
import net.fexcraft.app.json.JsonMap;

/**
 * 
 * @author Ferdinand Calo' (FEX___96)
 *
 */
public class Launcher extends Frame {

	private static final int W = 480, H = 300;
	private static String log = new String();
	private static TextArea area;
	private static Button start, update, reload, exit;

	public static void main(String[] args){
		new Launcher();
		log("Starting launcher...");
		File laufile = new File("./launch.fmt");
		if(!laufile.exists()) log("No launch.fmt file found... Welcome to FMT Launcher!");
		File setfile = new File("./settings.json");
		JsonMap lau = JsonHandler.parse(laufile);
		JsonMap set = JsonHandler.parse(setfile);
		if(set.getBoolean("update_queued", false)){
			log("Update marked as queued, fetching catalog...");
			Catalog.fetch();
		}
		boolean loaded = Catalog.load();
		boolean check = false;
		if(loaded){
			check = !Catalog.check();
		}
		reload.setEnabled(true);
		update.setEnabled(loaded);
		start.setEnabled(check);
		set.get("update_queued").value(false);
		JsonHandler.print(setfile, set, false, false);
		JsonHandler.print(laufile, lau, false, false);
	}

	public Launcher(){
		setTitle("FMT Launcher");
		area = new TextArea();
		area.setBounds(10, 35, W - 20, H - 80);
		//area.addTextListener(lis -> area.setText(log));
		add(area);
		//
		start = new Button("Start FMT");
		start.setBounds(10, H - 40, 100, 30);
		start.addActionListener(event -> {
			try{
				log("Launching FMT...");
				Process pro = Runtime.getRuntime().exec(" java -jar " + (new File("./FMT.jar").getPath()));
				setVisible(false);
				int code = pro.waitFor();
				setVisible(true);
				log("FMT has closed with exit code '" + code + "'.");
			}
			catch(IOException | InterruptedException e){
				e.printStackTrace();
				log("ERROR: " + e.getMessage());
				for(StackTraceElement trace : e.getStackTrace()){
					log(trace.toString());
				}
			}
		});
		start.setEnabled(false);
		add(start);
		//
		update = new Button("Update FMT");
		update.setBounds(120, H - 40, 100, 30);
		update.addActionListener(event -> {
			Catalog.update();
			start.setEnabled(!Catalog.check());
		});
		update.setEnabled(false);
		add(update);
		//
		reload = new Button("Reload Catalog");
		reload.setBounds(230, H - 40, 100, 30);
		reload.addActionListener(event -> {
			Catalog.fetch();
			boolean loaded = Catalog.load();
			boolean check = false;
			if(loaded){
				check = !Catalog.check();
			}
			update.setEnabled(loaded);
			start.setEnabled(check);
		});
		reload.setEnabled(false);
		add(reload);
		//
		exit = new Button("Exit");
		exit.setBounds(340, H - 40, 100, 30);
		exit.addActionListener(event -> {
			log("Exiting the Launcher...");
			System.exit(0);
		});
		add(exit);
		//
		setLocationRelativeTo(null);
		setSize(480, 300);
		setLayout(null);
		setVisible(true);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				System.exit(0);
			}
		});
	}

	public static void log(Object obj){
		log += obj + "\n";
		area.setText(log);
		System.out.println(obj);
	}

}
