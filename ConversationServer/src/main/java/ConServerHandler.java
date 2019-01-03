
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
                    conUser.write("로비 입니다.");
                    switch (message){
                        case "/create":{
                            String roomName="";
                            String flag ="";
                            String password;
                            conUser.write("비밀번호방을 만드시겠습니까 ? (y/n)");
                            flag = conUser.read().toUpperCase();
                            if(flag.equals("Y")){
                                conUser.write("방제목을 입력하세요 : ");
                                roomName = conUser.read();
                                conUser.write("비밀번호를 입력하시오 . :");
                                password = conUser.read();
                                conHouse.createPasswordRoom(conUser,roomName,password);

                            }else if(flag.equals("N")){
                                conUser.write("방제목을 입력하세요 : ");
                                roomName = conUser.read();
                                conHouse.createRoom(conUser,roomName);

                            }else{
                                conUser.write("방을 만들 수 없습니다.");
                                break;
                            }

                            inRoom =true;
                            break;
                        }
                        case "/join":{
                            List<ConRoom> rooms = conHouse.getRooms();
                            String strRoomNum="";
                            String password;
                            int roomNum=0;
                            conUser.write("입장할 방 번호를 입력하세요 : ");
                            strRoomNum = conUser.read();
                            roomNum = Integer.parseInt(strRoomNum);
                            if(rooms.get(roomNum).getPassword()!=null){
                                conUser.write("비밀번호를 입력하세요 . : ");
                                password = conUser.read();
                                if(rooms.get(roomNum).getPassword().equals(password)){
                                    conHouse.joinRoom(roomNum, conUser,password);
                                }else{
                                    conUser.write("비밀번호가 틀렸습니다. 로비로 돌아갑니다.");
                                    break;
                                }
                            }else {
                                conHouse.joinRoom(roomNum, conUser);
                            }
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
                        case "/delete":{
                            List<ConRoom> rooms = conHouse.getRooms();

                            int i=0;
                            for(ConRoom cr:rooms){
                                conUser.write(i+" : "+cr.getTitle());
                                i++;
                            }
                            conUser.write("폭파시킬 방 번호를 입력하세요 : ");
                            String strRoomNum="";
                            int roomNum = 0;
                            strRoomNum = conUser.read();
                            roomNum = Integer.parseInt(strRoomNum);

                            if(rooms.get(roomNum).getRoom().size() == 0){
                                rooms.remove(roomNum);
                            }else{
                                conUser.write("방에 사람이 있습니다.");
                            }

                            break;

                        }
                    }

                }else{//방안에 있을 때
                    List<ConUser> conUsers = conHouse.getUser(conUser);
                    for(ConUser cu:conUsers){
                        if(message.equals("/out")){
                            conUsers.remove(conUser);
                            inRoom = false;
                            break;
                        }
                        cu.write(conUser.getNickName()+" : "+message);
                    }
                }
            }

        }catch(Exception ex){
        conHouse.exit(conUser);
        }
    }
}
