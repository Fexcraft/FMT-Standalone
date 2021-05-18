package net.fexcraft.app.fmt.polygon;

import org.joml.Vector3f;

import net.fexcraft.app.fmt.attributes.PolyVal.PolygonValue;
import net.fexcraft.app.json.JsonArray;
import net.fexcraft.app.json.JsonMap;
import net.fexcraft.lib.common.math.Vec3f;
import net.fexcraft.lib.tmt.CylinderBuilder;
import net.fexcraft.lib.tmt.ModelRendererTurbo;

public class Cylinder extends Polygon {
	
	public float radius = 2, radius2, length = 2, base = 1, top = 1;
	public int segments = 8, seglimit, direction = ModelRendererTurbo.MR_TOP;
	public Vector3f topoff = new Vector3f(), toprot = new Vector3f();
	public boolean[] bools = new boolean[6];
	public boolean radial;
	public float seg_width, seg_height;
	
	public Cylinder(Model model){
		super(model);
	}

	protected Cylinder(Model model, JsonMap obj){
		super(model, obj);
		radius = obj.get("radius", radius);
		radius2 = obj.get("radius2", radius2);
		length = obj.get("length", length);
		segments = obj.get("segments", segments);
		seglimit = obj.get("seglimit", seglimit);
		direction = obj.get("direction", direction);
		base = obj.get("basescale", base);
		top = obj.get("topscale", top);
		topoff.x = obj.get("top_offset_x", topoff.x);
		topoff.y = obj.get("top_offset_y", topoff.y);
		topoff.z = obj.get("top_offset_z", topoff.z);
		toprot.x = obj.get("top_rotation_x", toprot.x);
		toprot.y = obj.get("top_rotation_y", toprot.y);
		toprot.z = obj.get("top_rotation_z", toprot.z);
		if(obj.has("faces_off")){
			JsonArray array = obj.getArray("faces_off");
			for(int i = 0; i < bools.length; i++){
				if(i >= array.size()) break;
				bools[i] = array.get(i).value();
			}
		}
		radial = obj.get("radialtex", radial);
		seg_width = obj.get("seg_width", seg_width);
		seg_height = obj.get("seg_height", seg_height);
	}

	@Override
	public Shape getShape(){
		return Shape.CYLINDER;
	}

	@Override
	protected void buildMRT(){
		if(radius != 0f || radial || usesTopRotation() /*|| cuv.anyCustom()*/){
			CylinderBuilder builder = turbo.newCylinderBuilder().setPosition(off.x, off.y, off.z)
				.setRadius(radius, radius2).setLength(length).setSegments(segments, seglimit).setScale(base, top)
				.setDirection(direction).setTopOffset(topoff.x, topoff.y, topoff.z).removePolygons(bools);
			//TODO custom uv
			if(radial) builder.setRadialTexture(seg_width, seg_height);
			else builder.setTopRotation(toprot.x, toprot.y, toprot.z);
			builder.build();
		}
		else{
			turbo.addCylinder(off.x, off.y, off.z, radius, length, segments, base, top, direction, getTopOff());
		}
	}

	@Override
	public float[] getFaceColor(int i){
		return turbo.getColor(i).toFloatArray();
	}
	
	public float getValue(PolygonValue polyval){
		switch(polyval.val()){
			case RADIUS: return radius;
			case RADIUS2: return radius2;
			case LENGTH: return length;
			case SEGMENTS: return segments;
			case SEG_LIMIT: return seglimit;
			case DIRECTION: return direction;
			case BASE_SCALE: return base;
			case TOP_SCALE: return top;
			case TOP_OFF: return getVectorValue(topoff, polyval.axe());
			case TOP_ROT: return getVectorValue(toprot, polyval.axe());
			case RADIAL: return getBooleanAsIntValue(radial);
			case SEG_WIDTH: return seg_width;
			case SEG_HEIGHT: return seg_height;
			case SIDES: return getIndexValue(bools, polyval.axe().ordinal());
			default: return super.getValue(polyval);
		}
	}

	public void setValue(PolygonValue polyval, float value){
		switch(polyval.val()){
			case RADIUS: radius = value; break;
			case RADIUS2: radius2 = value; break;
			case LENGTH: length = value; break;
			case SEGMENTS: segments = (int)value; break;
			case SEG_LIMIT: seglimit = (int)value; break;
			case DIRECTION: direction = (int)value; break;
			case BASE_SCALE: base = value; break;
			case TOP_SCALE: top = value; break;
			case TOP_OFF: setVectorValue(topoff, polyval.axe(), value); break;
			case TOP_ROT: setVectorValue(toprot, polyval.axe(), value); break;
			case RADIAL: radial = parseBooleanValue(value); break;
			case SEG_WIDTH: seg_width = value; break;
			case SEG_HEIGHT: seg_height = value; break;
			case SIDES: setIndexValue(bools, polyval.axe().ordinal(), value); break;
			default: super.setValue(polyval, value);
		}
		this.recompile();
	}

	public boolean usesTopRotation(){
		return toprot.x != 0f || toprot.y != 0f || toprot.z != 0f;
	}

	private Vec3f getTopOff(){
		return topoff.x == 0f && topoff.y == 0f && topoff.z == 0f ? null : new Vec3f(topoff.x, topoff.y, topoff.z);
	}

}
