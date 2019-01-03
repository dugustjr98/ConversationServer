import javax.print.DocFlavor;
import java.net.Socket;
import java.util.List;

public class ConServerHandler extends Thread {
    private ConHouse conHouse;
    private Socket socket;
    private boolean inRoom;

    public ConServerHandler(Socket socket, ConHouse conHouse){
        this.conHouse = conHouse;
        this.socket=socket;
        inRoom = false;
    }

    @Override
    public void run() {
        ConUser conUser = new ConUser(socket);
        String nickname = conUser.read();
        conUser.setNickName(nickname);
        System.out.println("message : " + nickname);

        conHouse.addLobbyUser(conUser);
        try{
            while(true){
               String message = conUser.read();
                if(!inRoom){
                    switch (message){
                        case "/create":{
                            String roomName="";
                            conUser.write("방제목을 입력하세요 : ");
                            roomName = conUser.read();
                            conHouse.createRoom(conUser,roomName);

                            inRoom =true;
                            break;
                        }
                        case "/join":{
                            String strRoomNum="";
                            int roomNum=0;
                            conUser.write("입장할 방 번호를 입력하세요 : ");
                            strRoomNum = conUser.read();
                            roomNum = Integer.parseInt(strRoomNum);
                            conHouse.joinRoom(roomNum,conUser);
                            inRoom =true;
                            break;
                        }
                        case "/list":{
                            List<ConRoom> rooms = conHouse.getRooms();
                            int i=0;
                            for(ConRoom cr:rooms){
                                conUser.write(i+" : "+cr.getTitle());
                                i++;
                            }
                            break;
                        }
                    }

                }else{//방안에 있을 때
                    List<ConUser> conUsers = conHouse.getUser(conUser);
                    for(ConUser cu:conUsers){
                        cu.write(conUser.getNickName()+" : "+message);
                    }
                }
            }

        }catch(Exception ex){
        conHouse.exit(conUser);
        }
    }
}
