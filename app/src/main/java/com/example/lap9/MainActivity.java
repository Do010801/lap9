package com.example.lap9;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private HashMap<String, student> hashmap = new HashMap <String, student>();
    private ArrayList<String> list = new ArrayList<String>();
    private ArrayList<String> major = new ArrayList<String>();
    Spinner spin;
    ListView listview;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spin = (Spinner) findViewById(R.id.spinerMajor);
        button = (Button) findViewById(R.id.button);
        listview = (ListView) findViewById(R.id.listview);
        AddStudentFromFileCSV();
        //Tạo spinner để chọn ngành
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_item,major);
        //Thiết lập cách hiển thị dữ liệu trong spinner

        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice)
        ;
        //Thiết lập adapter cho spinner
        spin.setAdapter(adapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Print(spin.getSelectedItem().toString());
            }
        });
    }
    public void AddStudentFromFileCSV(){
        try {
            String splitBy = ",";
            FileInputStream in = this.openFileInput("Data.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            //Lưu chuỗi "All" vào đầu danh sách ngành
            major.add("All");
            while (br!=null){
                String line = br.readLine();
                String[] value = line.split(splitBy);
                //Lưu tất cả danh sách SV vào Hashmap
                hashmap.put(value[0],new student(value[0], value[1],value[2]));
                //Kiểm tra nếu chưa có tên ngành trong danh sách thì thêm vào major list
                if (!major.contains(value[2])){
                    major.add(value[2]);
                }
            }
            br.close();
        }
        catch (Exception e) {
            System.out.println("" + e.getMessage());
        }
    }
    public void Print(String s)
    {
        list.clear(); //Mỗi lần hiển thị mới phải xóa list trước
        if (s.equals("All")) {
            for (student x: hashmap.values()) {
                list.add(x.toString());
            }
        }
        else {
            for (student x : hashmap.values()) {
                if (s.equals(x.getMajor())) {
                    list.add(x.toString());
                }
            }
        }
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter1);
    }
}