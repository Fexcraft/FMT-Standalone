package net.fexcraft.app.fmt.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.lwjgl.opengl.GL11;

import net.fexcraft.app.fmt.FMT;
import net.fexcraft.app.fmt.polygon.Polygon;

public class Picker {
	
	public static PickType TYPE = PickType.NONE;
	private static ByteBuffer buffer, pickres = ByteBuffer.allocate(4);
	private static boolean filled, offcenter;
	private static Polygon polygon;

	public static void resetBuffer(boolean resize){
		if(resize){
			buffer = ByteBuffer.allocateDirect(FMT.WIDTH * FMT.HEIGHT * 4);
			buffer.order(ByteOrder.nativeOrder());
		}
		if(!filled) return;
		buffer.clear();
		filled = false;
	}
	
	public static enum PickType {
		
		NONE, POLYGON, FACE, COLOR;
		
		public boolean pick(){
			return this != NONE;
		}

		public boolean polygon(){
			return this == POLYGON || this == FACE;
		}

		public boolean face(){
			return this == FACE;
		}
		
		public boolean color(){
			return this == COLOR;
		}
		
	}

	public static void reset(){
		TYPE = PickType.NONE;
		Logging.log("ending pick");
	}

	public static void pick(PickType type, boolean off){
		TYPE = type;
		offcenter = off;
		polygon = null;
		Logging.log("starting pick");
	}

	public static void process(){
		if(!filled) fillBuffer();
		if(TYPE.color()){
			
		}
		else if(TYPE.face() && polygon != null){
			
		}
		else{
			byte[] pick = getPick();
			Logging.log(pick[0]);
			Logging.log(pick[1]);
			Logging.log(pick[2]);
			Logging.log(pick[3]);
			pickres.put(0, pick);
			int res = pickres.getInt(0);
			Logging.log(res);
		}
	}

	private static byte[] getPick(){
		int x, y;
		byte[] picked = new byte[4];
		if(offcenter){
			x = GGR.mousePosX();
			y = -(GGR.mousePosY() - FMT.HEIGHT);
		}
		else{
			x = FMT.WIDTH / 2;
			y = FMT.HEIGHT / 2;
		}
		buffer.get((x + y * FMT.WIDTH) * 4, picked);
		return picked;
	}

	private static void fillBuffer(){
		GL11.glReadPixels(0, 0, FMT.WIDTH, FMT.HEIGHT, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
	}

}
