public class Person{

    private String username;
    private int seeds = 2;
    private int food = 0;
    
    public Person(String username) {
        this.username = username;
    }

    public String GetUsername() {
        return username;
    }

    public int getSeeds() {
        return seeds;
    }
    public void updateSeeds(int newAmount) {
        this.seeds = newAmount;
    }
    public int getFood() {
        return food;
    }
    public void updateFood(int newAmount) {
        this.food = newAmount;
    }
}