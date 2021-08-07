package org.techtown.mission9;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.proto.ProtoOutputStream;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CustoFragment extends Fragment {

    EditText editText1;
    EditText editText2;
    Button btn;
    Button btn2;

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");

    Date selectedDate; //처음에 현재 날짜 및 선택한 날짜를 담는 변수 (근데 이거 필요 없는것같은데....)

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_custo, container, false);

        btn = rootView.findViewById(R.id.button);
        btn2 = rootView.findViewById(R.id.button2);
        editText1 = rootView.findViewById(R.id.editName);
        editText2 = rootView.findViewById(R.id.editAge);

        setSelectedDate(new Date()); //시작시 자동으로 현재 날짜 표시!

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = editText1.getText().toString();
                String age = editText2.getText().toString();
                String birth = btn.getText().toString();

                Toast.makeText(getContext(), "이름 : " + name + ", 나이 : " + age + ", 생년월일 : " + birth, Toast.LENGTH_SHORT).show();

            }
        });

        return rootView;
    }

    private void showDateDialog() {

        //지금 생년월일 버튼에 있는 날짜문자열을 Date객체에 넣기(by dataFormat)!
        String birthDateStr = btn.getText().toString();
        Date curBirthDate = new Date();

        try {
            curBirthDate = dateFormat.parse(birthDateStr);
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        //생년월일버튼의 생년월일을 캘린더에 넣어야지! 캘린더에서는 예를들어 2021년 7월 22일이 108974 이렇게 되니까
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(curBirthDate);

        int curYear = calendar.get(Calendar.YEAR);
        int curMonth = calendar.get(Calendar.MONTH);
        int curDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(getContext(),  birthDateSetListener,  curYear, curMonth, curDay);
        dialog.show();
    }

//88줄부터 105줄에서 보듯이 DatePickDialog는 캘린더객체를 매개로 날짜를 다루기때문에 내가 날짜를 넣거나 빼올때 캘린더 객체를 써야함!!!!!
    //날짜 --> 캘린터 --> DatePickerDiaglog --> 캘린더 --> 날짜

    private DatePickerDialog.OnDateSetListener birthDateSetListener = new DatePickerDialog.OnDateSetListener() {//날짜선택 대화상자의 이벤트처리 리스터
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            //내가 선택한 날짜를 캘린더 객체를 만들어 입력한다.
            Calendar selectedCalendar = Calendar.getInstance();
            selectedCalendar.set(Calendar.YEAR, year);
            selectedCalendar.set(Calendar.MONTH, monthOfYear);
            selectedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            //캘린터의 날짜를 추출해서 생년월일 버튼을 갱신!
            Date curDate = selectedCalendar.getTime();
            setSelectedDate(curDate);
        }
    };

    private void setSelectedDate(Date curDate) {
        selectedDate = curDate;

        String selectedDateStr = dateFormat.format(curDate);
        btn.setText(selectedDateStr);
    }

}