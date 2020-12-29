/**
 * This class is the Factory class that is capable of creating an Aerial Vehicle (object which implements AirTransport).
 *
 * At runtime we can select which flying object we want. Also promotes Loose-coupling - Object required is created else where
 * The object creation is abstracted away from where it is needed.
 */
public class AirTransportVehicleFactory {

    public static AirTransport createAerialVehicle(String vehicleType){
        AirTransport aerialVehicle =  switch (vehicleType) {
            case "Glider": yield new Glider();
            case "Dragon": yield new Dragon();
            case "Aeroplane": yield new Aeroplane();
            default: throw new IllegalArgumentException("Vehicle type does not match existing aerial vehicle types.");
        };
        aerialVehicle.fly();
        return aerialVehicle;
    }

    public static void main(String[] args){
        AirTransportVehicleFactory.createAerialVehicle("Glider");
        AirTransportVehicleFactory.createAerialVehicle("Aeroplane");
        AirTransportVehicleFactory.createAerialVehicle("Dragon");
        AirTransportVehicleFactory.createAerialVehicle("Bird");
    }
}
