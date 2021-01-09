-pattern;
/**
 * This class is the Factory class that is capable of creating an Aerial Vehicle (object which implements AirTransport).
 *
 * At runtime we can select which flying object we want. Also promotes Loose-coupling - Object required is created else where (In Factory)
 * The object creation (occurs in factory) is abstracted away.
 *
 * As described on journal dev:
 * The factory design pattern is used when we have a superclass with multiple sub-classes and based on input, we need to return one of the sub-class.
 * This pattern takes out the responsibility of the instantiation of a class from the client program to the factory class.
 */
public class AirTransportVehicleFactory {

    public static AirTransport createAerialVehicle(String vehicleType){
        AirTransport aerialVehicle =  switch (vehicleType) { //Introduced in Java 13 (Switch - yield returns the result from switch statement)
            case "Glider": yield new Glider();
            case "Dragon": yield new Dragon();
            case "Aeroplane": yield new Aeroplane();
            default: throw new IllegalArgumentException("Vehicle type does not match existing aerial vehicle types.");
        };
        aerialVehicle.fly(); //checking how our vehicle fly's before we return the instance
        return aerialVehicle;
    }

    public static void main(String[] args){
        AirTransportVehicleFactory.createAerialVehicle("Glider");
        AirTransportVehicleFactory.createAerialVehicle("Aeroplane");
        AirTransportVehicleFactory.createAerialVehicle("Dragon");
        AirTransportVehicleFactory.createAerialVehicle("Bird");
    }
}
