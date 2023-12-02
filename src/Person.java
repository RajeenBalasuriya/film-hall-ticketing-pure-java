import java.io.Serializable;

public class Person implements Serializable {

    private final String name;
    private final String surname;
    private final String email;

    public Person(String name,String surname,String email){
        this.name=name;
        this.surname=surname;
        this.email=email;
}
   //getters of the person class
    public String getName(){
        return name;
    }

    public String getSurname(){
        return surname;
    }


    public String getEmail(){
        return email;
    }



}
