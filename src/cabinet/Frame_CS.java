package cabinet;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.*;

//import db.Member.Menu;
//import db.Flight.SeatDAO;
//import db.Flight.SeatDTO;
//import db.Flight.Frame_C;

public class Frame_CS extends JFrame {


    JTabbedPane jtp = new JTabbedPane();//탭을 생성

    JPanel contentPane2 = new JPanel();//panel생성

    //텍스트가 적힌 라벨과 내용을 적을 필드 생성
    private final JLabel lblNewLabel = new JLabel("ROOM_NUMBER");
    private final JTextField tfRno = new JTextField();
    private final JLabel label = new JLabel("SEAT_NUMBER");
    private final JTextField tfSno = new JTextField();
    private final JLabel label_1 = new JLabel("STATE");
    private final JTextField tfState = new JTextField();
    private final JLabel label_2 = new JLabel("ID");
    private final JTextField tfId = new JTextField();
    private final JLabel label_3 = new JLabel("PERIOD");
    private final JTextField tfPeriod = new JTextField();
    //스크롤 생성
    private final JScrollPane scrollPane = new JScrollPane();
    //테이블(표) 생성
    private final JTable table = new JTable();
    //버튼 생성
    private final JButton btAdd = new JButton("ADD");
    private final JButton btFind = new JButton("FIND");
    private final JButton btAll = new JButton("All");
    private final JButton btDel = new JButton("DELETE");
    private final JButton btCancel = new JButton("CANCEL");
    //방 이름이 적힌 라벨 생성
    private final JLabel lblRoom = new JLabel("ROOM 410");
    private final JLabel lblRoom_1 = new JLabel("ROOM 420");
    //pannel 생성
    private final JPanel panel_1 = new JPanel();
    private final JPanel panel_2 = new JPanel();
    private final JPanel panel_3 = new JPanel();
    private final JPanel panel_4 = new JPanel();
    private final JPanel panel_5 = new JPanel();
    private final JPanel panel_6 = new JPanel();
    private final JPanel panel_7 = new JPanel();
    private final JPanel panel_8 = new JPanel();
    //버튼을 배열로 생성
    private final JButton[] buttons_410_1 = new JButton[10];
    private final JButton[] buttons_410_2 = new JButton[10];
    private final JButton[] buttons_410_3 = new JButton[10];
    private final JButton[] buttons_410_4 = new JButton[10];
    private final JButton[] buttons_420 = new JButton[10];


    SeatDTO dto=new SeatDTO();//좌석 정보를 생성
    SeatDAO dao=new SeatDAO();//좌석 db
    DefaultTableModel model
            =new DefaultTableModel();

    public static final int NONE=0;
    public static final int ADD=1;
    public static final int DEL=2;
    public static final int FIND=3;
    public static final int ALL=4;

    int cmd=NONE;
    private final JLabel lblBG = new JLabel("");



    public Frame_CS() {
        Frame_C c= new Frame_C();
        //탭 이름 설정
        jtp.addTab("Customer", Frame_C.contentPane1);
        jtp.addTab("Seat", contentPane2);
        getContentPane().add(jtp);



        start();

        try {
            (dao).dbConnect();
        } catch (Exception e) {
            System.out.println("DB연결 실패"+e.getMessage());
        }//db와 커넥션
        //////////////////
        model.addColumn("방 번호");
        model.addColumn("자리번호");
        model.addColumn("사용유무");
        model.addColumn("아이디");
        model.addColumn("남은기간");

        //model을 view와 연결---------
        table.setModel(model);
        table.getTableHeader().setBackground(
                Color.PINK);
        table.getTableHeader().setForeground(
                Color.DARK_GRAY);
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(20);
        initialTf();

//		setVisible(true);

        //윈도우 창 가운데 생성
        int xpos, ypos;
        Dimension dimen1, dimen2;
        this.setResizable(true);
        dimen1 = Toolkit.getDefaultToolkit().getScreenSize(); //화면해상도 불러오기
        dimen2 = this.getSize();  //프레임의크기
        xpos = (int)(dimen1.getWidth()/2-dimen2.getWidth()/2);
        ypos = (int)(dimen1.getHeight()/2-dimen2.getHeight()/2);
        this.setLocation(xpos,ypos);
        this.setTitle("StudyRoom");
        setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\images\\Book.jpg"));
        this.setVisible(true);

        initGUI();


    }
    private void start() {
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                dao.close();
                //db와 연결된 자원 반납
                System.exit(0);
            }
        });

        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {//테이블에 마우스를 감지하는 기능 추가
                System.out.println("mousePressed()");//출력
                int row=table.getSelectedRow();//테이블의 행을 누르면 row에 저장
                setTitle(row+"행");
                for(int i=0;i<4;i++){
                    Object obj
                            =table.getValueAt(row, i);//테이블 행의 열마다 obj에 저장
                    String objStr=obj.toString();//문자열로 형변환
                    switch(i){
                        case 0:
                            tfRno.setText(objStr);
                            break;
                        case 1:
                            tfSno.setText(objStr);
                            break;
                        case 2:
                            tfState.setText(objStr);
                            break;
                        case 3:
                            tfId.setText(objStr);
                            break;
                        case 4:
                            tfPeriod.setText(objStr);
                            break;

                    }//switch---------

                }//for----------
            }
        });

        btAdd.setBackground(Color.PINK);//배경색 분홍
        btAdd.addActionListener(new ActionListener() {//동작을 감지하는 기능 추가
            public void actionPerformed(ActionEvent e) {//동작을 감지
                System.out.println("Add actionPerformed()");//출력
                if(cmd!=ADD){  //모든 메뉴중에 ADD를 마우스로 클릭했을때 cmd = 0 add = 1
                    setEnabled(ADD);//버튼을 활성화
                    tfSno.requestFocus();//커서

                }else{//cmd = 1 add = 1
                    add();//자리 추가
                    setEnabled(NONE);//버튼 활성화
                    cmd=NONE;//cmd = 0
                    initialTf();//입력칸 비활성화
                    clearTf(); //입력하는 칸을 빈칸으로 만들어줌
                }
            }
        });
        btFind.setBackground(Color.PINK);//배경색 분홍색
        btFind.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Find actionPerformed()");
                if(cmd!=FIND){// cmd = 0 cmd = 3
                    setEnabled(FIND);//버튼활성화
                    tfId.requestFocus();
                }else{
                    showData(FIND);//테이블과 좌석 표시
                    cmd=NONE;//cmd = 0
                    setEnabled(cmd);//버튼 활성화
                    initialTf();//입력칸 비활성화
                    clearTf();//입력칸 빈칸

                }
            }
        });
        btAll.setBackground(Color.PINK);//배경색 분홍
        btAll.setText("ALL");//텍스트 추가
        btAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("All actionPerformed()");
                cmd=ALL;//cmd = 4
                setEnabled(cmd);//버튼 활성화
                initialTf();//입력칸 비활성화
                showData(ALL);//테이블에 데이터 표시
            }
        });
        btDel.setBackground(Color.PINK);//배경색 분홍
        btDel.setText("DELETE");//텍스트 추가
        btDel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Delete actionPerformed()");
                if(cmd!=DEL){//cmd = 4 del = 2
                    setEnabled(DEL);//버튼 활성화
                    tfSno.requestFocus(); //커서
                }else{
                    delete();//id로 삭제
                    setEnabled(NONE);//버튼 활성화
                    cmd=NONE;//cmd = 0
                    initialTf();//입력칸 비활성화
                    clearTf();//입력칸 빈칸
                }
            }
        });
        btCancel.setBackground(Color.PINK);//배경색 분홍색
        btCancel.setText("CANCEL");//텍스트 추가
        btCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("actionPerformed()");
                cmd=NONE;//cmd = 0
                setEnabled(cmd);//버튼 활성화
                initialTf();//입력칸 빈칸
            }
        });

    }
    private void initGUI() {                           //jpanel

        contentPane2.setLayout(null);

        this.setResizable(true); //frame 크기 임의 설정(false)시 불가
        this.setSize(1100,700); //frame 사이즈 설정 method
        this.setLocationRelativeTo(null);// 창 가운데 생성코드


        this.lblNewLabel.setBounds(14, 10, 100, 28);
        contentPane2.add(this.lblNewLabel);
        this.tfRno.setBounds(12, 38, 128, 28);
        this.tfRno.setColumns(10);
        contentPane2.add(this.tfRno);               //Rno

        this.label.setBounds(14, 66, 100, 28);
        contentPane2.add(this.label);
        this.tfSno.setColumns(10);
        this.tfSno.setBounds(12, 94, 128, 28);
        contentPane2.add(this.tfSno);                //Sno

        this.label_1.setBounds(12, 122, 73, 28);
        contentPane2.add(this.label_1);
        this.tfState.setColumns(10);
        this.tfState.setBounds(12, 150, 128, 28);
        contentPane2.add(this.tfState);              //setState

        this.label_2.setBounds(12, 178, 73, 28);
        contentPane2.add(this.label_2);
        this.tfId.setColumns(10);
        this.tfId.setBounds(12, 206, 128, 28);
        contentPane2.add(this.tfId);              //Id

        this.label_3.setBounds(12, 234, 73, 28);
        contentPane2.add(this.label_3);
        this.tfPeriod.setColumns(10);
        this.tfPeriod.setBounds(12, 262, 128, 28);
        contentPane2.add(this.tfPeriod);                //Period



        //안쪽 자료 나오는 결과 테이블
        this.scrollPane.setBounds(239, 66, 500, 226);
        contentPane2.add(this.scrollPane);

        this.scrollPane.setViewportView(this.table);

        this.btAdd.setBounds(239, 34, 85, 33);
        contentPane2.add(this.btAdd);

        this.btFind.setBounds(322, 34, 85, 33);
        contentPane2.add(this.btFind);

        this.btAll.setBounds(405, 34, 85, 33);
        contentPane2.add(this.btAll);

        this.btDel.setBounds(488, 34, 85, 33);
        contentPane2.add(this.btDel);

        this.btCancel.setBounds(571, 34, 85, 33);
        contentPane2.add(this.btCancel);

        //410글자필드라벨
        lblRoom.setBounds(239, 312, 73, 28);
        contentPane2.add(lblRoom);
        //420글자필드라벨
        lblRoom_1.setBounds(774, 312, 73, 28);
        contentPane2.add(lblRoom_1);


        //room 410

        //1~10번째 좌석묶음
        panel_2.setBackground(SystemColor.controlHighlight);
        panel_2.setBounds(239, 341, 95, 242);
        contentPane2.add(panel_2);
        panel_2.setLayout(new GridLayout(5, 2, 2, 2));
        for (int i = 0; i < buttons_410_1.length; i++) {
            buttons_410_1[i] = new JButton();
            buttons_410_1[i].setText(""+(i+1));
            panel_2.add(buttons_410_1[i]);
            buttons_410_1[i].setBackground(Color.PINK);
            buttons_410_1[i].setFont(new Font("굴림", Font.BOLD, 9));
        }
        panel_3.setBackground(SystemColor.controlHighlight);
        panel_3.setBounds(334, 341, 40, 242);
        contentPane2.add(panel_3);
        //11~20번째 좌석묶음
        panel_4.setBackground(SystemColor.controlHighlight);
        panel_4.setBounds(374, 341, 95, 242);
        contentPane2.add(panel_4);
        panel_4.setLayout(new GridLayout(5, 2, 2, 2));
        for (int i = 0; i < buttons_410_2.length; i++) {
            buttons_410_2[i] = new JButton();
            buttons_410_2[i].setText(""+(i+11));
            panel_4.add(buttons_410_2[i]);
            buttons_410_2[i].setBackground(Color.PINK);
            buttons_410_2[i].setFont(new Font("굴림", Font.BOLD, 9));
        }
        panel_5.setBackground(SystemColor.controlHighlight);
        panel_5.setBounds(469, 341, 40, 242);
        contentPane2.add(panel_5);
        //21~30번째 좌석묶음
        panel_6.setBackground(SystemColor.controlHighlight);
        panel_6.setBounds(509, 341, 95, 242);
        contentPane2.add(panel_6);
        panel_6.setLayout(new GridLayout(5, 2, 2, 2));
        for (int i = 0; i < buttons_410_3.length; i++) {
            buttons_410_3[i] = new JButton();
            buttons_410_3[i].setText(""+(i+21));
            panel_6.add(buttons_410_3[i]);
            buttons_410_3[i].setBackground(Color.PINK);
            buttons_410_3[i].setFont(new Font("굴림", Font.BOLD, 9));
        }
        panel_7.setBackground(SystemColor.controlHighlight);
        panel_7.setBounds(604, 341, 40, 242);
        contentPane2.add(panel_7);
        //31~40번째 좌석묶음
        panel_8.setBackground(SystemColor.controlHighlight);
        panel_8.setBounds(644, 341, 95, 242);
        contentPane2.add(panel_8);
        panel_8.setLayout(new GridLayout(5, 2, 2, 2));
        for (int i = 0; i < buttons_410_4.length; i++) {
            buttons_410_4[i] = new JButton();
            buttons_410_4[i].setText(""+(i+31));
            panel_8.add(buttons_410_4[i]);
            buttons_410_4[i].setBackground(Color.PINK);
            buttons_410_4[i].setFont(new Font("굴림", Font.BOLD, 9));
        }
        //room420
        contentPane2.add(panel_1);
        panel_1.setBackground(SystemColor.controlHighlight);
        panel_1.setBounds(774, 341, 253, 242);
        panel_1.setLayout(new GridLayout(2, 5, 2, 90));
        for (int i = 0; i < buttons_420.length; i++) {
            buttons_420[i] = new JButton();
            buttons_420[i].setText(""+(i+41));
            panel_1.add(buttons_420[i]);
            buttons_420[i].setBackground(Color.PINK);
        }

        lblBG.setIcon(new ImageIcon("D:\\images\\study6.jpg"));
        lblBG.setBounds(0, -46, 1100, 700);
        contentPane2.add(lblBG);
    }

    public void changeSeatColor(){//버튼의 색을 바꿔주는 메소드

        SeatDTO[] Sdao = null;//좌석정보 null
        Sdao = dao.selectAll();//좌석 전체 데이터를 sdao에 저장
        String[][] data = new String[Sdao.length][5];
         for(int i=0; i< data.length; i++) {//sdao의 데이터를 data 문자열 배열에 저장
            data[i][0] = Sdao[i].getRno();  //+"" 로 문자열로 만들어줌
            data[i][1] = Sdao[i].getSno();
            data[i][2] = Sdao[i].getState();
            data[i][3] = Sdao[i].getId();
            data[i][4] = Sdao[i].getPeriod()+"";
        }

        for(int i=0; i< data.length; i++) {
            int Sno = Integer.parseInt(data[i][1]);//좌석번호
            int period = Integer.parseInt(data[i][4]);//남은기간

            if(data[i][2] != null) {//state가 null이 아니면
                //사용중이면 초록색 남은기간이 6미만이면 빨간색으로 배경색 변경
                if(1 <=Sno && Sno <= 10) {
                    buttons_410_1[Sno-1].setBackground(Color.CYAN);
                    if(period<6) { buttons_410_1[Sno-1].setBackground(Color.RED);}
                }else if(11 <=Sno && Sno <= 20){
                    buttons_410_2[Sno-11].setBackground(Color.CYAN);
                    if(period<6) { buttons_410_2[Sno-11].setBackground(Color.RED);}
                }else if(21 <=Sno && Sno <= 30) {
                    buttons_410_3[Sno-21].setBackground(Color.CYAN);
                    if(period<6) { buttons_410_3[Sno-21].setBackground(Color.RED);}
                }else if(31 <=Sno && Sno <= 40) {
                    buttons_410_4[Sno-31].setBackground(Color.CYAN);
                    if(period<6) { buttons_410_4[Sno-31].setBackground(Color.RED);}
                }else if(41 <=Sno &&Sno <= 50) {
                    buttons_420[Sno-41].setBackground(Color.CYAN);
                    if(period<6) { buttons_420[Sno-41].setBackground(Color.RED);}
                }else{
                    buttons_410_1[Sno-1].setBackground(Color.PINK);
                    buttons_410_2[Sno-11].setBackground(Color.PINK);
                    buttons_410_3[Sno-21].setBackground(Color.PINK);
                    buttons_410_4[Sno-31].setBackground(Color.PINK);
                    buttons_420[Sno-41].setBackground(Color.PINK);
                }
            }//if
        }//for
    }

    public void initialTf(){
        //tf들을 비활성화
        boolean b=false;
        tfRno.setEditable(b);
        tfSno.setEditable(b);
        tfState.setEditable(b);
        tfId.setEditable(b);
        tfPeriod.setEditable(b);

    }//initialTf()--------
    /*tf의 편집 가능 여부를 결정하는 메소드*/
    public void setEditable(int n){
        boolean b=false;
        switch(n){
            case ADD:
                tfRno.setEditable(b);
                tfSno.setEditable(!b);
                tfId.setEditable(!b);
                tfPeriod.setEditable(!b);
                break;
            case FIND://좌석번호 검색
                tfId.setEditable(!b);
                break;
            case DEL:// 좌석번호로 삭제
                tfSno.setEditable(!b);
                break;
            case NONE:
            case ALL:
                initialTf();
                break;
        }

    }//setEditable()---------
    /*버튼의 활성화 여부를 결정하는 메소드*/
    public void setEnabled(int n){
        boolean b=false;
        this.intialBt(b);
        switch(n){
            case ADD:
                btAdd.setEnabled(!b);
                btCancel.setEnabled(!b);
                cmd=ADD;
                break;
            case DEL:
                btDel.setEnabled(!b);
                btCancel.setEnabled(!b);
                cmd=DEL;
                break;
            case FIND:
                btFind.setEnabled(!b);
                btCancel.setEnabled(!b);
                cmd=FIND;
                break;
            case ALL:
                btAll.setEnabled(!b);
                btCancel.setEnabled(!b);
                cmd=ALL;
                break;

            case NONE:
                this.intialBt(!b);//모든 버튼 활성화
                break;
        }
        this.setEditable(cmd);
        //tf의 활성화 여부 결정..

    }
    /*버튼 비활성화 메소드*/
    public void intialBt(boolean b){
        btAdd.setEnabled(b);
        btDel.setEnabled(b);
        btAll.setEnabled(b);
        btFind.setEnabled(b);
        btCancel.setEnabled(b);
    }
    /*tf를 비워주는 메소드*/
    public void clearTf(){
        tfRno.setText("");
        tfSno.setText("");
        tfState.setText("");
        tfId.setText("");
        tfPeriod.setText("");
    }

    public void add() {

        String msg = "";
        //입력창에서 데이터를 받고 저장
        dto.setSno(tfSno.getText());
        dto.setId(tfId.getText());
        dto.setPeriod(tfPeriod.getText());

        //유효성체크
        if(dto.getId()==null||dto.getSno() ==null
                ||dto.getId().trim().equals("")
                ||dto.getSno().trim().equals("")){
            msg = "ID와 좌석번호를 입력하세요";
            JOptionPane.showMessageDialog(this, msg);
            return;
        }
        //중복성체크
        if(dao.duplicateCheck(dto)){//null이 아니면
            msg = "중복된 좌석번호입니다.";
            JOptionPane.showMessageDialog(this, msg);
            return;
        }
        if(dao.duplicateSeatCheck(dto)) {//id가 있으면 true
            msg = "사용좌석이 추가됩니다.";
            JOptionPane.showMessageDialog(this, msg);

        }



        int n = dao.updateSeatInfo(dto);//데이터가 있으면
        if(n>0) { msg = "좌석지정 성공";
        }else { msg = "좌석지정 실패";
        }
        JOptionPane.showMessageDialog(this, msg); //성공 메세지 팝업창 생성
        showData(ALL); //창에 전체데이터 보여줌
    }

    public void showData(int n) {
        SeatDTO[] arr = null;  //자료가 몇개가 될 지 모르므로 null 설정
        if(n==ALL) { //모두보기
            arr=dao.selectAll();//arr에 모든 데이터 추가
        }else if(n==FIND) { //ID 검색
            String id = tfId.getText();//텍스트 필드에 있는 데이터 저장
            arr = dao.selectById(id);//arr에 찾은 id데이터 추가
        }
        if(arr== null ) {  /*****|| arr.length == 0 넣으면 안됌!!!!!!(rs.next()때문)*******/
            JOptionPane.showMessageDialog(this, "현재 등록된 회원 없음.");
            return;
        }
        String[] colNames = {"방 번호", "자리번호", "사용유무", "아이디",
                "남은기간"};
        String[][] data = new String[arr.length][5];
        //insert, delet 등등으로 길이가 항상 변하므로 길이가 [arr.length]가 됨
        for(int i=0; i< data.length; i++) {
            data[i][0] = arr[i].getRno();  //+"" 로 문자열로 만들어줌
            data[i][1] = arr[i].getSno();
            data[i][2] = arr[i].getState();
            data[i][3] = arr[i].getId();
            data[i][4] = arr[i].getPeriod()+"";

        }
        model.setDataVector(data, colNames);
        table.setModel(model);

        changeSeatColor();//좌석 색 변경
    }

    public void delete() {
        //삭제할 ID입력하지 않으면 경고창 생성    -> JOptionPane.showMessageDialog() 사용
        //정말 ??님의 정보를 삭제하시겠습니까? -> JOptionPane.showConfirmDialog() 사용

        String Sno = tfSno.getText(); //화면에 입력한 아이디값 가져오기
        String msg = "";

        //유효성체크
        if(Sno ==null||Sno.trim().equals("")){  //trim = 공백제거
            msg = "삭제할 좌석번호를 입력하세요";
            tfId.requestFocus();
            JOptionPane.showMessageDialog(this, msg);
            return; //여기서 끊어주기위해 반드시 입력해야함
        }
        msg = "정말 " + Sno + "좌석의 정보를 삭제하시겠습니까?";
        int yn = JOptionPane.showConfirmDialog(this, msg);
        // MessageDialog와 차이점 : 팝업 창에 예,아니오,취소 세가지 버튼이 추가됨
        if(yn == JOptionPane.YES_OPTION) {
            //삭제성공 혹은 삭제실패 확인
            int isDel = dao.deleteSeatInfo(Sno.trim()); //호출 후 삭제 성공 혹은 삭제 실패 메세지 생성
            if(isDel>0) {//리턴 삭제 1
                msg = "좌석정보 삭제성공";
            } else {
                msg = "좌석정보 삭제실패";}
            JOptionPane.showMessageDialog(this, msg); //회원삭제성공 메세지 팝업창 생성
            showData(ALL); //창에 회원삭제 후 전체데이터 보여줌
        }
    }
//public static void main(String[] args) {
//	Frame_CS cs = new Frame_CS();
//}

}
