import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConRoom {
    private List<ConUser> room;
    private String title;

    public ConRoom(ConUser conUser,String title)
    {
        this.title = title;
        room = Collections.synchronizedList(new ArrayList<>());
        room.add(conUser);
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
