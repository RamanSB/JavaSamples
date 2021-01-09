/**
 * An example AerialVehicle object
 */
public class Glider implements AirTransport{

    @Override
    public void fly() {
        glide();
    }

    @Override
    public void makeSound(){
        System.out.println("Ssssssssshhhhhhhhh");
    }

    @Override
    public String getAerialVehicle(){
        return "Glider";
    }

    public void glide(){
        System.out.println("Gliding through the air with my glider");
    }

}
