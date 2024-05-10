package com.goormthon_univ.russianroulette;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayDeque;
import java.util.Deque;

public class MainActivity extends AppCompatActivity {
    static int hpA=100;
    static int hpB=100;
    static int attackDamage=20;
    int roundNumber=1;

    //턴
    //0은 A, 1은 B
    int turn=0;

    //텍스트뷰
    TextView roundTextView;
    ProgressBar hpBarA;
    ProgressBar hpBarB;
    ImageView indicateA;
    ImageView indicateB;

    //아이템 인벤토리 배열
    /*
    1번: 데미지 2배
    2번: 공포탄<->실탄
    3번: 탄 알아내기
     */
    int[] itemA;
    int[] itemB;


    //총
    //실탄은 1, 공격탄은 0
    Deque<Integer> deque=new ArrayDeque<>();
    int[] bulletList;

    //총알 개수
    static int bulletNumber;

    void newRound(){
        //라운드 시작 전 준비
        itemPick();
        bulletNumber=(int)(Math.random()*3)+3; //총알 개수 랜덤
        int bullet0=0; //공포탄 개수
        int bullet1=0; //실탄 개수
        for(int i=0;i<bulletNumber;i++){
            int ran=(int)(Math.random()*2);
            if(ran==0){
                deque.offerFirst(0);
                bullet0++;
            }else{
                deque.offerLast(1);
                bullet1++;
            }
        }

        //만약 공포탄만 나오거나 실탄만 나왔을 경우
        if(bullet0==0||bullet1==0){
            if(deque.poll()==0){
                deque.addFirst(1);
                bullet0--;
                bullet1++;
            }else{
                deque.addFirst(0);
                bullet0++;
                bullet1--;
            }
        }

        roundTextView.setText((++roundNumber)+" 라운드 시작!\n"+"공포탄:"+bullet0+" 실탄:"+bullet1);
        Toast.makeText(getApplicationContext(),roundNumber+" 라운드가 시작되었습니다",Toast.LENGTH_SHORT).show();
    }

    void turnChange(int num){
        if(num==0){
            indicateA.setVisibility(View.VISIBLE);
            indicateB.setVisibility(View.GONE);
            turn=0;
            findViewById(R.id.gun_button).setRotation(180);
            findViewById(R.id.round_number).setRotation(180);
        }else{
            indicateA.setVisibility(View.GONE);
            indicateB.setVisibility(View.VISIBLE);
            turn=1;
            findViewById(R.id.gun_button).setRotation(0);
            findViewById(R.id.round_number).setRotation(0);
        }
    }

    void itemPick(){
        //아이템
        itemA=new int[4];
        itemB=new int[4];
        int[] itemList={R.drawable.item_double,R.drawable.item_transfer,R.drawable.item_search};

        //아이템 랜덤 부여
        for(int i=0;i<itemA.length;i++){
            if(i<=1){
                itemA[i]=(int)(Math.random()*3);
            }else{
                itemA[i]=(int)(Math.random()*20);
            }
            if(i==0){
                if(itemA[i]<=2) findViewById(R.id.A1).setBackgroundResource(itemList[itemA[i]]);
                else findViewById(R.id.A1).setBackgroundColor(Color.parseColor("#9A8080"));
            }else if(i==1){
                if(itemA[i]<=2) findViewById(R.id.A2).setBackgroundResource(itemList[itemA[i]]);
                else findViewById(R.id.A2).setBackgroundColor(Color.parseColor("#9A8080"));
            }else if(i==2){
                if(itemA[i]<=2) findViewById(R.id.A3).setBackgroundResource(itemList[itemA[i]]);
                else findViewById(R.id.A3).setBackgroundColor(Color.parseColor("#9A8080"));
            }else if(i==3){
                if(itemA[i]<=2) findViewById(R.id.A4).setBackgroundResource(itemList[itemA[i]]);
                else findViewById(R.id.A4).setBackgroundColor(Color.parseColor("#9A8080"));
            }
        }

        for(int i=0;i<itemB.length;i++){
            if(i<=1){
                itemB[i]=(int)(Math.random()*3);
            }else{
                itemB[i]=(int)(Math.random()*20);
            }
            if(i==0){
                if(itemB[i]<=2) findViewById(R.id.B1).setBackgroundResource(itemList[itemB[i]]);
                else findViewById(R.id.B1).setBackgroundColor(Color.parseColor("#9A8080"));
            }else if(i==1){
                if(itemB[i]<=2) findViewById(R.id.B2).setBackgroundResource(itemList[itemB[i]]);
                else findViewById(R.id.B2).setBackgroundColor(Color.parseColor("#9A8080"));
            }else if(i==2){
                if(itemB[i]<=2) findViewById(R.id.B3).setBackgroundResource(itemList[itemB[i]]);
                else findViewById(R.id.B3).setBackgroundColor(Color.parseColor("#9A8080"));
            }else if(i==3){
                if(itemB[i]<=2) findViewById(R.id.B4).setBackgroundResource(itemList[itemB[i]]);
                else findViewById(R.id.B4).setBackgroundColor(Color.parseColor("#9A8080"));
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //A 인벤토리 아이템 리스너
        findViewById(R.id.A1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(turn==0){
                    if(itemA[0]==0){
                        Toast.makeText(getApplicationContext(),"2배 아이템 사용",Toast.LENGTH_SHORT).show();
                        attackDamage*=2;
                    }
                    else if(itemA[0]==1){
                        Toast.makeText(getApplicationContext(),"변경 아이템 사용",Toast.LENGTH_SHORT).show();
                        if(deque.peek()==0){
                            //Toast.makeText(getApplicationContext(),"다음 아이템은 공포탄입니다",Toast.LENGTH_SHORT).show();
                            deque.poll();
                            deque.addFirst(1);
                        }else{
                            //Toast.makeText(getApplicationContext(),"다음 아이템은 실탄입니다",Toast.LENGTH_SHORT).show();
                            deque.poll();
                            deque.addFirst(0);
                        }
                    }
                    else if(itemA[0]==2){
                        if(deque.peek()==0){
                            Toast.makeText(getApplicationContext(),"다음 아이템은 공포탄입니다",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"다음 아이템은 실탄입니다",Toast.LENGTH_SHORT).show();
                        }
                    }
                    findViewById(R.id.A1).setBackgroundColor(Color.parseColor("#9A8080"));
                }
            }
        });
        findViewById(R.id.A2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(turn==0){
                    if(itemA[1]==0){
                        Toast.makeText(getApplicationContext(),"2배 아이템 사용",Toast.LENGTH_SHORT).show();
                        attackDamage*=2;
                    }
                    else if(itemA[1]==1){
                        Toast.makeText(getApplicationContext(),"변경 아이템 사용",Toast.LENGTH_SHORT).show();
                        if(deque.peek()==0){
                            //Toast.makeText(getApplicationContext(),"다음 아이템은 공포탄입니다",Toast.LENGTH_SHORT).show();
                            deque.poll();
                            deque.addFirst(1);
                        }else{
                            //Toast.makeText(getApplicationContext(),"다음 아이템은 실탄입니다",Toast.LENGTH_SHORT).show();
                            deque.poll();
                            deque.addFirst(0);
                        }
                    }
                    else if(itemA[1]==2){
                        if(deque.peek()==0){
                            Toast.makeText(getApplicationContext(),"다음 아이템은 공포탄입니다",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"다음 아이템은 실탄입니다",Toast.LENGTH_SHORT).show();
                        }
                    }
                    findViewById(R.id.A2).setBackgroundColor(Color.parseColor("#9A8080"));
                }
            }
        });
        findViewById(R.id.A3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(turn==0){
                    if(itemA[2]==0){
                        Toast.makeText(getApplicationContext(),"2배 아이템 사용",Toast.LENGTH_SHORT).show();
                        attackDamage*=2;
                    }
                    else if(itemA[2]==1){
                        Toast.makeText(getApplicationContext(),"변경 아이템 사용",Toast.LENGTH_SHORT).show();
                        if(deque.peek()==0){
                            //Toast.makeText(getApplicationContext(),"다음 아이템은 공포탄입니다",Toast.LENGTH_SHORT).show();
                            deque.poll();
                            deque.addFirst(1);
                        }else{
                            //Toast.makeText(getApplicationContext(),"다음 아이템은 실탄입니다",Toast.LENGTH_SHORT).show();
                            deque.poll();
                            deque.addFirst(0);
                        }
                    }
                    else if(itemA[2]==2){
                        if(deque.peek()==0){
                            Toast.makeText(getApplicationContext(),"다음 아이템은 공포탄입니다",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"다음 아이템은 실탄입니다",Toast.LENGTH_SHORT).show();
                        }
                    }
                    findViewById(R.id.A3).setBackgroundColor(Color.parseColor("#9A8080"));
                }
            }
        });
        findViewById(R.id.A4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(turn==0){
                    if(itemA[3]==0){
                        Toast.makeText(getApplicationContext(),"2배 아이템 사용",Toast.LENGTH_SHORT).show();
                        attackDamage*=2;
                    }
                    else if(itemA[3]==1){
                        Toast.makeText(getApplicationContext(),"변경 아이템 사용",Toast.LENGTH_SHORT).show();
                        if(deque.peek()==0){
                            //Toast.makeText(getApplicationContext(),"다음 아이템은 공포탄입니다",Toast.LENGTH_SHORT).show();
                            deque.poll();
                            deque.addFirst(1);
                        }else{
                            //Toast.makeText(getApplicationContext(),"다음 아이템은 실탄입니다",Toast.LENGTH_SHORT).show();
                            deque.poll();
                            deque.addFirst(0);
                        }
                    }
                    else if(itemA[3]==2){
                        if(deque.peek()==0){
                            Toast.makeText(getApplicationContext(),"다음 아이템은 공포탄입니다",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"다음 아이템은 실탄입니다",Toast.LENGTH_SHORT).show();
                        }
                    }
                    findViewById(R.id.A4).setBackgroundColor(Color.parseColor("#9A8080"));
                }
            }
        });

        //B 인벤토리 아이템 리스너
        findViewById(R.id.B1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(turn==1){
                    if(itemB[0]==0){
                        Toast.makeText(getApplicationContext(),"2배 아이템 사용",Toast.LENGTH_SHORT).show();
                        attackDamage*=2;
                    }
                    else if(itemB[0]==1){
                        Toast.makeText(getApplicationContext(),"변경 아이템 사용",Toast.LENGTH_SHORT).show();
                        if(deque.peek()==0){
                            //Toast.makeText(getApplicationContext(),"다음 아이템은 공포탄입니다",Toast.LENGTH_SHORT).show();
                            deque.poll();
                            deque.addFirst(1);
                        }else{
                            //Toast.makeText(getApplicationContext(),"다음 아이템은 실탄입니다",Toast.LENGTH_SHORT).show();
                            deque.poll();
                            deque.addFirst(0);
                        }
                    }
                    else if(itemB[0]==2){
                        if(deque.peek()==0){
                            Toast.makeText(getApplicationContext(),"다음 아이템은 공포탄입니다",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"다음 아이템은 실탄입니다",Toast.LENGTH_SHORT).show();
                        }
                    }
                    findViewById(R.id.B1).setBackgroundColor(Color.parseColor("#9A8080"));
                }
            }
        });
        findViewById(R.id.B2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(turn==1){
                    if(itemB[1]==0){
                        Toast.makeText(getApplicationContext(),"2배 아이템 사용",Toast.LENGTH_SHORT).show();
                        attackDamage*=2;
                    }
                    else if(itemB[1]==1){
                        Toast.makeText(getApplicationContext(),"변경 아이템 사용",Toast.LENGTH_SHORT).show();
                        if(deque.peek()==0){
                            //Toast.makeText(getApplicationContext(),"다음 아이템은 공포탄입니다",Toast.LENGTH_SHORT).show();
                            deque.poll();
                            deque.addFirst(1);
                        }else{
                            //Toast.makeText(getApplicationContext(),"다음 아이템은 실탄입니다",Toast.LENGTH_SHORT).show();
                            deque.poll();
                            deque.addFirst(0);
                        }
                    }
                    else if(itemB[1]==2){
                        if(deque.peek()==0){
                            Toast.makeText(getApplicationContext(),"다음 아이템은 공포탄입니다",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"다음 아이템은 실탄입니다",Toast.LENGTH_SHORT).show();
                        }
                    }
                    findViewById(R.id.B2).setBackgroundColor(Color.parseColor("#9A8080"));
                }
            }
        });
        findViewById(R.id.B3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(turn==1){
                    if(itemB[2]==0){
                        Toast.makeText(getApplicationContext(),"2배 아이템 사용",Toast.LENGTH_SHORT).show();
                        attackDamage*=2;
                    }
                    else if(itemB[2]==1){
                        Toast.makeText(getApplicationContext(),"변경 아이템 사용",Toast.LENGTH_SHORT).show();
                        if(deque.peek()==0){
                            //Toast.makeText(getApplicationContext(),"다음 아이템은 공포탄입니다",Toast.LENGTH_SHORT).show();
                            deque.poll();
                            deque.addFirst(1);
                        }else{
                            //Toast.makeText(getApplicationContext(),"다음 아이템은 실탄입니다",Toast.LENGTH_SHORT).show();
                            deque.poll();
                            deque.addFirst(0);
                        }
                    }
                    else if(itemB[2]==2){
                        if(deque.peek()==0){
                            Toast.makeText(getApplicationContext(),"다음 아이템은 공포탄입니다",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"다음 아이템은 실탄입니다",Toast.LENGTH_SHORT).show();
                        }
                    }
                    findViewById(R.id.B3).setBackgroundColor(Color.parseColor("#9A8080"));
                }
            }
        });
        findViewById(R.id.B4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(turn==1){
                    if(itemB[3]==0){
                        Toast.makeText(getApplicationContext(),"2배 아이템 사용",Toast.LENGTH_SHORT).show();
                        attackDamage*=2;
                    }
                    else if(itemB[3]==1){
                        Toast.makeText(getApplicationContext(),"변경 아이템 사용",Toast.LENGTH_SHORT).show();
                        if(deque.peek()==0){
                            //Toast.makeText(getApplicationContext(),"다음 아이템은 공포탄입니다",Toast.LENGTH_SHORT).show();
                            deque.poll();
                            deque.addFirst(1);
                        }else{
                            //Toast.makeText(getApplicationContext(),"다음 아이템은 실탄입니다",Toast.LENGTH_SHORT).show();
                            deque.poll();
                            deque.addFirst(0);
                        }
                    }
                    else if(itemB[3]==2){
                        if(deque.peek()==0){
                            Toast.makeText(getApplicationContext(),"다음 아이템은 공포탄입니다",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"다음 아이템은 실탄입니다",Toast.LENGTH_SHORT).show();
                        }
                    }
                    findViewById(R.id.B4).setBackgroundColor(Color.parseColor("#9A8080"));
                }
            }
        });

        ImageButton gunButton=findViewById(R.id.gun_button);
        roundTextView=findViewById(R.id.round_number);
        hpBarA=findViewById(R.id.hpBarA);
        hpBarB=findViewById(R.id.hpBarB);

        indicateA=findViewById(R.id.indicateA);
        indicateB=findViewById(R.id.indicateB);

        indicateA.setVisibility(View.GONE);
        indicateB.setVisibility(View.GONE);

        //라운드 번호 표시
        gunButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"총알 개수: "+bulletNumber,Toast.LENGTH_SHORT).show();
            }
        });

        //라운드 시작 전 준비
        turnChange(0);
        hpA=100; //체력 초기화
        hpB=100;
        bulletNumber=(int)(Math.random()*3)+3; //총알 개수 랜덤
        int bullet0=0; //공포탄 개수
        int bullet1=0; //실탄 개수
        for(int i=0;i<bulletNumber;i++){
            int ran=(int)(Math.random()*2);
            if(ran==0){
                deque.offerFirst(0);
                bullet0++;
            }else{
                deque.offerLast(1);
                bullet1++;
            }
        }

        //만약 공포탄만 나오거나 실탄만 나왔을 경우
        if(bullet0==0||bullet1==0){
            if(deque.poll()==0){
                deque.addFirst(1);
                bullet0--;
                bullet1++;
            }else{
                deque.addFirst(0);
                bullet0++;
                bullet1--;
            }
        }

        roundTextView.setText((roundNumber)+" 라운드 시작!\n"+"공포탄:"+bullet0+" 실탄:"+bullet1);

        //플레이어 이미지 뷰
        ImageButton playerAButton=findViewById(R.id.playerA);
        ImageButton playerBButton=findViewById(R.id.playerB);

        //플레이어 텍스트 뷰
        TextView playerAText=findViewById(R.id.playerA_text);
        TextView playerBText=findViewById(R.id.playerB_text);

        playerAText.setVisibility(View.GONE);
        playerBText.setVisibility(View.GONE);

        playerAButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler=new Handler();
                playerAText.setVisibility(View.VISIBLE);

                if(deque.poll()==0){
                    if(turn==0){ //A 차례인 경우
                        //턴 계속 함
                    }else{ //B 차례인 경우
                        turnChange(0);
                    }
                    playerAText.setText("공포탄");
                }else{
                    if(turn==0){ //A 차례인 경우
                        turnChange(1);
                    }else{ //B 차례인 경우
                        turnChange(0);
                    }
                    playerAText.setText("실탄");
                    hpA-=attackDamage;
                    attackDamage=20; //2배 아이템 초기화
                    hpBarA.setProgress(hpA);
                }
                //총알 개수 갱신
                roundTextView.setText((roundNumber)+" 라운드 (남은 총알:"+deque.size()+")");

                //라운드가 끝났는지 체크
                if(!deque.isEmpty()){
                }else{
                    //새로운 라운드 준비
                    newRound();
                }

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playerAText.setVisibility(View.GONE);
                    }
                },1000);
                if(hpA<=0){
                    Toast.makeText(getApplicationContext(),"B가 이겼습니다",Toast.LENGTH_SHORT).show();
                    finish();
                }else if(hpB<=0){
                    Toast.makeText(getApplicationContext(),"A가 이겼습니다",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        playerBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler=new Handler();
                playerBText.setVisibility(View.VISIBLE);

                if(deque.poll()==0){
                    if(turn==1){ //B 차례인 경우
                        //턴 계속함
                    }else{ //A 차례인 경우
                        turnChange(1);
                    }
                    playerBText.setText("공포탄");
                }else{
                    if(turn==1){ //B 차례인 경우
                        turnChange(0);
                    }else{ //A 차례인 경우
                        turnChange(1);
                    }
                    playerBText.setText("실탄");
                    hpB-=attackDamage;
                    attackDamage=20; //2배 아이템 초기화
                    hpBarB.setProgress(hpB);
                }
                //총알 개수 갱신
                roundTextView.setText((roundNumber)+" 라운드 (남은 총알:"+deque.size()+")");

                //라운드가 끝났는지 체크
                if(!deque.isEmpty()){
                }else{
                    //새로운 라운드 준비
                    newRound();
                }

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playerBText.setVisibility(View.GONE);
                    }
                },1000);
                if(hpA<=0){
                    Toast.makeText(getApplicationContext(),"B가 이겼습니다",Toast.LENGTH_SHORT).show();
                    finish();
                }else if(hpB<=0){
                    Toast.makeText(getApplicationContext(),"A가 이겼습니다",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}