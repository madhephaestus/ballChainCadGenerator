import eu.mihosoft.vrl.v3d.parametrics.*;
import java.util.stream.Collectors;
import com.neuronrobotics.bowlerstudio.vitamins.Vitamins;
import eu.mihosoft.vrl.v3d.CSG;
import eu.mihosoft.vrl.v3d.Cube;
import eu.mihosoft.vrl.v3d.Cylinder
import eu.mihosoft.vrl.v3d.Sphere
CSG generate(){
	String type= "ballChain"
	if(args==null)
		args=["4point5mm"]
	// The variable that stores the current size of this vitamin
	StringParameter size = new StringParameter(	type+" Default",args.get(0),Vitamins.listVitaminSizes(type))
	HashMap<String,Object> measurments = Vitamins.getConfiguration( type,size.getStrValue())

	def ballDiameterValue = measurments.ballDiameter
	def centerToCenterValue = measurments.centerToCenter
	def cordDiameterValue = measurments.cordDiameter
	def massCentroidXValue = measurments.massCentroidX
	def massCentroidYValue = measurments.massCentroidY
	def massCentroidZValue = measurments.massCentroidZ
	def massKgValue = measurments.massKg
	def priceValue = measurments.price
	def sourceValue = measurments.source
	for(String key:measurments.keySet().stream().sorted().collect(Collectors.toList())){
		println "ballChain value "+key+" "+measurments.get(key);
	}
	CSG ball = new Sphere(ballDiameterValue/2).toCSG()
	CSG cord = new Cylinder(cordDiameterValue/2, centerToCenterValue).toCSG()
					.roty(-90)
	// Stub of a CAD object
	CSG part = ball
				.union(ball.movex(centerToCenterValue))
				.union(cord)
				.union(cord.toXMax())
				.union(ball.movex(-centerToCenterValue))

	return part
			.setParameter(size)
			.setRegenerate({generate()})
}
return generate()