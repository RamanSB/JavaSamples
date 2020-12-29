/**
 * An example AerialVehicle object
 */
public class Aeroplane implements AirTransport {

    @Override
    public void fly(){
        turnOnEngine();
    }

    @Override
    public void makeSound(){
        System.out.println("GrgGRggrgRGGRGR Engine turning on");
    }

    @Override
    public String getAerialVehicle(){
        return "Aeroplane";
    }

    public void turnOnEngine(){
        System.out.println("Aeroplane engine revs and begins to ascend");
    }


}
