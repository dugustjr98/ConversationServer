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
        String roomName="";
        conUser.setNickName(nickname);
        System.out.println("message : " + nickname);

        conHouse.addLobbyUser(conUser);
        try{
            while(true){
                String message = conUser.read();

                if(!inRoom){

                    switch (message){
                        case "/help":{
                            conUser.write(" --- 로비 명령어 --- \n /help 명령어 확인 \n /create : 방을 만들고 들어갑니다. " +
                                    "\n /list : 방 목록을 방 번호와 함께 보여줍니다. \n /join 해당 번호의 방으로 들어갑니다. " +
                                    "\n /delete 방에 아무도 없을 때, 해당 번호의 방을 폭파합니다. \n /who 전체 유저를 보여줍니다. \n /quit 채팅 프로그램을 종료합니다. " +
                                    "\n --- 채팅방 내 명령어 --- \n /who 채팅방 안에 있는 사람들을 보여줍니다. \n /out 채팅방에서 나옵니다. \n ");
                            break;
                        }
                        case "/create":{
                            String flag ="";
                            String password;
                            conUser.write("비밀방을 만드시겠습니까 ? (y/n)");
                            flag = conUser.read().toUpperCase();
                            if(flag.equals("Y")){
                                conUser.write("방 제목을 입력하세요 : ");
                                roomName = conUser.read();
                                conUser.write(roomName + " 방의 비밀번호를 설정하세요 . :");
                                password = conUser.read();
                                conHouse.createPasswordRoom(conUser,roomName,password);

                            }else if(flag.equals("N")){
                                conUser.write("방 제목을 입력하세요 : ");
                                roomName = conUser.read();
                                conHouse.createRoom(conUser,roomName);

                            }else{
                                conUser.write("방을 만들 수 없습니다.");
                                break;
                            }
                            conUser.write(roomName+" 에 입장 하셨습니다.");
                            inRoom =true;
                            break;
                        }
                        case "/join":{
                            List<ConRoom> rooms = conHouse.getRooms();
                            String strRoomNum="";
                            String password;
                            int roomNum=0;
                            int i = 0;
                            conUser.write(" --- 현재 열려있는 방 목록 --- ");
                            for(ConRoom cr:rooms){
                                conUser.write(i+" : "+cr.getTitle());
                                i++;
                            }
                            conUser.write("입장할 방 번호를 입력하세요. : ");
                            strRoomNum = conUser.read();
                            roomNum = Integer.parseInt(strRoomNum);
                            if(rooms.get(roomNum).getPassword()!=null){
                                conUser.write("비밀번호를 입력하세요. : ");
                                password = conUser.read();
                                if(rooms.get(roomNum).getPassword().equals(password)){
                                    conHouse.joinRoom(roomNum, conUser,password);
                                }else{
                                    conUser.write("비밀번호가 틀렸습니다. 로비로 돌아갑니다. \n /help 로 명령어를 확인하실 수 있습니다.");
                                    break;
                                }
                            }else {
                                conHouse.joinRoom(roomNum, conUser);
                            }
                            conUser.write(rooms.get(roomNum).getTitle()+" 방에 입장 하셨습니다.");
                            inRoom =true;
                            break;
                        }
                        case "/list":{
                            List<ConRoom> rooms = conHouse.getRooms();
                            int i=0;
                            conUser.write(" --- 현재 열려있는 방 목록 --- ");
                            for(ConRoom cr:rooms){
                                conUser.write(i+" : "+cr.getTitle());
                                i++;
                            }
                            break;
                        }
                        case "/delete":{
                            List<ConRoom> rooms = conHouse.getRooms();

                            int i=0;
                            int j=0;
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
                                conUser.write(roomNum+" 번 방이 폭파되었습니다.");
                                conUser.write(" --- 남아있는 방 목록 --- ");
                                for(ConRoom cr:rooms){
                                    conUser.write(j+" : "+cr.getTitle());
                                    j++;
                                }
                            }else{
                                conUser.write("방에 사람이 있습니다.");
                            }

                            break;

                        }

                        case "/who": {
                            List<ConUser> lobby = conHouse.getLobby();
                            conUser.write(" --- 전체 채팅 유저 목록 --- ");
                            for (ConUser cu : lobby) {
                                    conUser.write(cu.getNickName());
                            }
                            conUser.write("총 " + lobby.size() + " 명의 유저가 채팅중입니다.");
                        }
                    }
                }else{//방안에 있을 때
                    List<ConUser> conUsers = conHouse.getUser(conUser);
                    for(ConUser cu:conUsers){
                        if(message.equals("/out")){
                            conUsers.remove(conUser);
                            inRoom = false;
                            conUser.write( "채팅방에서 나왔습니다. 로비로 돌아갑니다. \n/help 로 명령어를 확인할 수 있습니다.");
                            break;
                        }else if(message.equals("/who")){
                            conUser.write(" --- '" + roomName + "' 방 유저 목록 --- ");
                            for (ConUser ru : conUsers) {
                                conUser.write(ru.getNickName());
                            }
                            conUser.write("총 " + conUsers.size() + " 명의 유저가 " +roomName + " 방에 있습니다.");
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