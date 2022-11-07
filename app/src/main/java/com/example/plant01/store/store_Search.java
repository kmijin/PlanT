package com.example.plant01.store;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plant01.R;

import java.util.ArrayList;
import java.util.List;

public class store_Search extends AppCompatActivity {

    private List<String> list;          // 데이터를 넣은 리스트변수
    private AutoCompleteTextView search;
    private ImageButton store_searchback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_search);

        store_searchback = (ImageButton) findViewById(R.id.store_SearchBack);
        store_searchback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 리스트를 생성한다.
        list = new ArrayList<String>();

        // 리스트에 검색될 데이터(단어)를 추가한다.
        settingList();

        final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.store_SearchBar);

        // AutoCompleteTextView 에 아답터를 연결한다.
        autoCompleteTextView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,  list ));

        search = findViewById(R.id.store_SearchBar);
        search.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event){
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    Intent intent = new Intent(getApplicationContext(), store_SearchResult.class);
                    intent.putExtra("contact_search", search.getText().toString());
                    startActivity(intent);//액티비티 띄우기
                    return true;
                }
                return false;
            }
        });
    }
    // 검색에 사용될 데이터를 리스트에 추가한다.
    private void settingList(){
        list.add("선인장");
        list.add("꽃");
        list.add("화환");
        list.add("난");
        list.add("다육식물");
        list.add("초보");
        list.add("전문가");
        list.add("선물");
        list.add("조화");
        list.add("관리도구");
        list.add("자동급수기");
        list.add("화분");
        list.add("물뿌리개");
        list.add("삽");
        list.add("분재");
        list.add("이벤트");
        list.add("자동완성 테스트 1");
        list.add("자동완성 테스트 2");
        list.add("자동완성 테스트 3");
        list.add("자동완성 테스트 4");
        list.add("자동완성 테스트 5");
        list.add("자동완성 테스트 6");
        list.add("test 1");
        list.add("test 2");
        list.add("test 3");
        list.add("test 4");
        list.add("test 5");
        list.add("초보집사");
        list.add("옥잠화");

    }
}

