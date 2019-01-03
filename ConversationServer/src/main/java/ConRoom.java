import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConRoom {
    private List<ConUser> room;
    private String title;
    private String password;



    public ConRoom(ConUser conUser, String title)
    {
        this.title = title;
        room = Collections.synchronizedList(new ArrayList<>());
        room.add(conUser);
    }
    public ConRoom(ConUser conUser, String title,String password){
        this.title= title;
        this.password = password;
        room = Collections.synchronizedList(new ArrayList<>());
        room.add(conUser);
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void addConUser(ConUser conUser){
        room.add(conUser);
    }
    public boolean ExistConUser(ConUser conUser){
        return room.contains(conUser);
    }

    public List<ConUser> getRoom() {
        return room;
    }
    public ConUser getUser(int index){
       return room.get(index);
    }

    public void setRoom(List<ConUser> room) {
        this.room = room;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
