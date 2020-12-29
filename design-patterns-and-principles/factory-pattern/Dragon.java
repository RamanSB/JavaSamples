/**
 * An example AerialVehicle object
 */
public class Dragon implements AirTransport {

    @Override
    public void fly(){
        flapWings();
    }

    @Override
    public void makeSound(){
        System.out.println("Raaawwwwrr!");
    }

    @Override
    public String getAerialVehicle(){
        return "Dragon";
    }

    public void flapWings(){
        System.out.println("Dragon ascends and flaps wings");
    }
}
