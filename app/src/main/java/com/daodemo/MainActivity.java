package com.daodemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.daodemo.data.Person;
import org.dao.DaoFactory;
import org.dao.IDaoSupport;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    IDaoSupport<Person> daoSupport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        daoSupport = DaoFactory.get().getDaoSupportAndCreateTable(Person.class);

    }

    public void insert(View view) {
        List<Person> datas = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            datas.add(new Person("张三" + i, i,"男"));
        }
        daoSupport.insert(datas);
        Log.e("TAG", "insert ok ");
    }

    public void query(View view) {
        StringBuffer sb = new StringBuffer();
        for (Person person : daoSupport.querySupport().query()) {
            sb.append(person.toString()).append("\n");
        }
        Log.e("TAG", "query: " + sb.toString());
    }
}
