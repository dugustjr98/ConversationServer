import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConHouse {
    private List<ConRoom> rooms;
    private List<ConUser> lobby;

    public ConHouse(){
        rooms = Collections.synchronizedList(new ArrayList<>());
        lobby = Collections.synchronizedList(new ArrayList<>());
    }

    public void createRoom(ConUser conUser,String title){
        ConRoom conRoom = new ConRoom(conUser,title);
        rooms.add(conRoom);
    }
    public void createPasswordRoom(ConUser conUser,String title,String password){
        ConRoom conRoom = new ConRoom(conUser,title,password);
        rooms.add(conRoom);
    }

    public void addLobbyUser(ConUser conUser){
        lobby.add(conUser);
    }

    public void exit(ConUser conUser){
        lobby.remove(conUser);
    }

    public List<ConUser> getUser(ConUser conUser){
        for(ConRoom room:rooms){
            if(room.ExistConUser(conUser)){
                return room.getRoom();
            }
        }
        return new ArrayList<>();
    }

    public List<ConRoom> getRooms() {
        return rooms;
    }

    public void joinRoom(int roomNum,ConUser conUser){
        ConRoom conRoom = rooms.get(roomNum);
        conRoom.addConUser(conUser);
    }
    public void joinRoom(int roomNum,ConUser conUser,String password){
        ConRoom conRoom = rooms.get(roomNum);
        conRoom.addConUser(conUser);
    }
}
