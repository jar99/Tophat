public class TrainModel {


    private float velocity;
    private float temperature;
    private String trainID;

    private TrainControllerInterface trainInterface;

    public TrainModel(String trainID) {
        this.trainID = trainID;
    }

    public void update(long delaTime){
        float power = trainInterface.getPower();
        float brake = trainInterface.getBrake();
        if(brake > 0){
            velocity = 0.0f;
        }else if(power > 0){
            velocity = ((float) delaTime / 10000) * power;
        }
    }

    public float getVelocity() {
        return velocity;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public String toString(){
        return trainID;
    }
}
