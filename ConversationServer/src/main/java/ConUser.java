import java.io.*;
import java.net.Socket;

public class ConUser {
    private String nickName;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public ConUser(Socket socket) {
        this.socket = socket;
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (Exception ex) {
            throw new RuntimeException("User 생성 오류");
        }
    }

    public void close() {
        try {
            in.close();
        } catch (Exception ignore) {
        }
        try {
            out.close();
        } catch (Exception ignore) {
        }
        try {
            socket.close();
        } catch (Exception ignore) {
        }
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Socket getSocket() {
        return socket;
    }

    public DataInputStream getIn() {
        return in;
    }

    public void setIn(DataInputStream in) {
        this.in = in;
    }

    public DataOutputStream getOut() {
        return out;
    }

    public void setOut(DataOutputStream out) {
        this.out = out;
    }

    public void write(String msg) {
        try {
            out.writeUTF(msg);
            out.flush();
        } catch (Exception ex) {
            throw new RuntimeException("메시지 전송오류");
        }
    }

    public String read() {
        try {
            return in.readUTF();
        } catch (Exception ex) {
            throw new RuntimeException("메시지 읽기 실패");
        }
    }
}
