# Bitgaram

Created By: 성희 배
Last Edited: Jan 02, 2020 3:02 PM

# 기술 스택

- Node.js
- Android Studio
- Mongo db
- 개발 기간 : 5일
- 개발 인원 : 2명 
    - 백엔드 @forbid403
    - 프론트엔드 @KangHyunSub


# 전체 앱 구성

![](gitImage/_Basic_Flowchart.png)

# 기능

### 회원 가입

- 회원 가입

    ![](gitImage/1.png)

- 로컬에서 사진 선택 가능
- 회원 가입 후에만 어플리케이션 접근
- 필수 정보 입력 시에만 회원 가입 가능
- SharedPreference로 세션 유지
- 회원가입 시 회원 정보, 로컬에 있는 갤러리와 연락처를 DB 전송

### TAB1

- 연락처 리스트

    ![](gitImage/address.jpg)

- DB에 있는 연락처 목록을 불러온다.
- 아래 버튼 클릭 시 update

### TAB2

- 갤러리

    ![](gitImage/gallery.jpg)

- DB의 사진들 불러오기
- 사진 클릭 시 확대
- 클릭 한 사진은 opacity 조절로 체크

### TAB3

- 채팅기능
    - 소켓통신을 이용한 채팅 기능
    ![](gitImage/chatting.jpg)
    ![](gitImage/chatting2.jpg)
    - 채팅 방은 DB에 저장된 ID로 구별
    - 채팅 방 입장 시 DB에서 지금까지 대화한 기록들 불러오기
    - socket.join을 사용해 emit시 구별해서 전송

# 트러블슈팅

- emit 시 모든 소켓에게 데이터를 전송하게 되어 다른 채팅방에 있어도 채팅이 업데이트 되는 문제가 생겼다.
    - io.emit이라는 메소드가 현재 소켓 풀에 있는 모든 소켓에게 메세지를 보내기 때문에 발생했다. 검색을 해 보니 join이라는 메서드가 있었다. 소켓을 채팅방 처럼 join으로 방을 만들어, 그 방에 있는 소켓들에게만 메세지를 보낼 수 있었다.
    - 채팅 방을 입장할 시에, 소켓을 연결한다. 서버에 채팅방의 ID를 소켓에 토큰으로 보낸다. 서버에서 io.on으로 connection을 감지 할 때에 소켓에서 보낸 토큰(roomID)로 join을 시킨다.
    - 클라이언트가 채팅 메세지를 보내면 io.to(roomId).emit으로 특정 room 안에만 있는 소켓에게만 메세지를 보낸다!
- AsyncTask의 request가 너무 많아 서버에 부하가 걸려 서버가 다운이 되었다.
    - 뷰페이저를 사용했기 때문에, MainActivity가 생성 될 때에 뷰페이저 내부의 fragment가 모두 생성이 된다. Oncreate 메소드에 AsyncTask로 서버와의 통신을 했기 때문에, 서버에 부하가 걸렸다.
    - 통신 부분을 따로 모듈화 하여서 Oncreate가 아닌 다른 trigger를 두어 버튼이 클릭 될 시에만 통신을 하도록 하였다. 그래도 빈번한 request를 받으면 서버가 정지되어서 좀 더 안정적인 방법을 찾아야겠다.

# 배운점/느낀점

- Socket 통신에 대해서 자세히 알 수 있었다. Socket 안에 파라미터를 보낼 수 있다는 것도 알았고, 여러 메소드들을 새롭게 알게 되었고 조절할 수 있었다.
- 프로젝트 진행을 백엔드 / 프론트엔드를 확실히 나누어서 분배하였다. 조금 더 전문적으로 개발하고 싶은 욕심 때문에 그렇게 했는데, 만약 웹 개발에 대해서 풀스택으로 자세히 알고, 연륜이 있다면 분배하여 진행했을 것 같은데 그정도 실력이 되지 않았기 때문에 개발 속도가 더뎌졌다. 프론트는 완성 됐는데 백엔드는 작업중이고, 백엔드는 완성 됐는데 프론트는 작업중이고, 이런 경우에는 한 명이 기다려야 했기 때문에 비효율적이었던 것 같다. 다음부터는 페어 프로그래밍 등 함께 협업하여 프로젝트를 진행 해야겠다.

# 공부한 내용

[https://forbid403.github.io/til/0102-TIL/](https://forbid403.github.io/til/0102-TIL/)

[https://forbid403.github.io/til/0103-TIL/](https://forbid403.github.io/til/0103-TIL/)

[https://forbid403.github.io/til/0104-TIL/](https://forbid403.github.io/til/0104-TIL/)