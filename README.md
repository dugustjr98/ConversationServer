#채팅 프로그램 만들기

2019/01/04


1조 : 여현석 , 박찬종

자바 쓰레드 및 소켓 통신 학습 목적으로 채팅 프로그램을 만들었다.  

github와 sourcetree를 통한 버전 관리 및 협업을 실시함.


## 기능 리스트
(로비 명령어)
* /help : 명령어 확인  <br>
* /create : 방을 만들고 들어갑니다. <br> 
* /list : 방 목록을 방 번호와 함께 보여줍니다. <br> 
* /join : 해당 번호의 방으로 들어갑니다. <br>
* /delete : 방에 아무도 없을 때, 해당 번호의 방을 폭파합니다.<br> 
* /who : 전체 유저를 보여줍니다. <br> 
* /quit : 채팅 프로그램을 종료합니다. <br>

(채팅방 내 명령어)
* /who : 채팅방 안에 있는 사람들을 보여줍니다. <br> 
* /out : 채팅방에서 나옵니다. <br>

## 추가
1. 방장 기능, 강퇴, 투표 기능 등 기능 추가 예정
2. 쓰레드와 소켓을 이용한 채팅 프로그램의 한계가 존재함 <br> 
    -> 추후 nio 또는 netty를 이용한 확장 고려