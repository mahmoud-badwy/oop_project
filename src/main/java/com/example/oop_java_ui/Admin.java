import java.util.Date;
import java.util.List;

public class Admin extends User {
    private String role;
    private float working_hours;
    private int nextId;
    RoomManager A = new RoomManager();

   
    public Admin(String role, float working_hours, int nextId, int id, String name, int age, String userName, String password, Date birthday, UserType userType , RoomManager m ) {
    super(id, name, age, userName, password, birthday, userType);
    this.role = role;
    this.working_hours = working_hours;
    A = m ;
    }

    

    // --- Getters and Setters ---
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public float getWorking_hours() {
        return working_hours;
    }

    public void setWorking_hours(float working_hours) {
        this.working_hours = working_hours;
    }

  

    public int getNextId() {
        return nextId;
    }

    public void setNextId(int nextId) {
        this.nextId = nextId;
    }

  public void getAllrooms(){System.out.println(A.getAllRooms());}
  public void Createroom(String name, int capacity){System.out.println(A.createRoom(name, capacity));}
  public void getroombyID(int id){System.out.println(A.getRoomById(id));}
  public void updateRoom(int id, String name, int capacity) {System.out.println(A.updateRoom(id, name, capacity));}
  public void deleteRoom(int id){System.out.println(A.deleteRoom(id));}
  public void searchRoomsByName(String name){System.out.println(A.searchRoomsByName(name));}
  
   

    public void showEvent( Event e)   {
        System.out.println(e.toString());
    }
    public void showattendes(Event e )   {
        System.out.println(e.getAttendees());
    }

    List<Event> getAllEvents() {
        return getAllEvents();
    }
 
}