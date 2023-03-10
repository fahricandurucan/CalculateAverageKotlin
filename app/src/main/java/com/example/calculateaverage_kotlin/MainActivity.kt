package com.example.calculateaverage_kotlin

import android.content.Context
import android.icu.number.IntegerWidth
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.view.get
import androidx.core.view.isEmpty
import androidx.core.view.isNotEmpty
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.new_course_layout.*
import kotlinx.android.synthetic.main.new_course_layout.view.*

class MainActivity : AppCompatActivity() {

    private val COURSES = arrayOf("Calculus","General Physics","Data Structure","Matematik","Kimya",
    "Algorithm","Operating System")
    private var coursesArray = ArrayList<Courses>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var adapter = ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,COURSES)
        etCourseName.setAdapter(adapter)

        if(rootLayout.childCount==0){
            btnCalculate.visibility = View.INVISIBLE
        }
        else{
            btnCalculate.visibility = View.VISIBLE
        }
        btnAddCourse.setOnClickListener {

            if(!etCourseName.text.isNullOrEmpty()){
                var inflater = LayoutInflater.from(this)
                //var inflater2 = layoutInflater
                /*var inflater3 = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                inflater3.inflate()*/

                //XML den javaya dönüştürme
                // yani new_course_layout u alıp view a dönüştürüyoruz
                var newCourseView = inflater.inflate(R.layout.new_course_layout,null)

                var courseName = etCourseName.text.toString()
                var kredi = spinnerKredi.selectedItem.toString()
                var note = spinnerNote.selectedItem.toString()

                newCourseView.etNewCourseName.setText(courseName)
                newCourseView.spinnerNewKredi.setSelection(getSpinnerPosition(spinnerKredi,kredi))
                newCourseView.spinnerNewNote.setSelection(getSpinnerPosition(spinnerNote,note))


                //silme butonu oluşturuluyor
                newCourseView.buttonDelete.setOnClickListener {
                    rootLayout.removeView(newCourseView)
                    if(rootLayout.childCount==0){
                        btnCalculate.visibility = View.INVISIBLE
                    }
                    else{
                        btnCalculate.visibility = View.VISIBLE
                    }
                }
                rootLayout.addView(newCourseView)
                btnCalculate.visibility = View.VISIBLE
                reset()



            }
            else{
                Toast.makeText(this,"Lütfen Ders Adını Giriniz",Toast.LENGTH_LONG).show()
            }


        }


    }

    fun reset(){
        etCourseName.setText("")
        spinnerKredi.setSelection(0)
        spinnerNote.setSelection(0)
    }

    fun getSpinnerPosition(spinner: Spinner, value:String) : Int {
        var index = 0
        for( i in 0..spinner.count){
            if (spinner.getItemAtPosition(index).toString().equals(value)){
                break
            }
            else{
                index++
            }
        }
        return index
    }

    fun calculateAverage(view: View){    // view a hangi butona tıkladıysam onun bilgisi geliyor diyebilirim

        var sumOfKredi=0.0
        var sumOfCourse=0.0

        for(i in 0..rootLayout.childCount-1){
            var line = rootLayout.getChildAt(i)
            var course = Courses(line.etNewCourseName.text.toString(),
                (line.spinnerNewNote.selectedItem.toString()),
                (line.spinnerNewKredi.selectedItemPosition+1).toString())
            coursesArray.add(course)
        }


        for(currentCourse in coursesArray){
            sumOfKredi += currentCourse.kredi.toDouble()
            sumOfCourse += currentCourse.kredi.toDouble()*convertNoteToKredi(currentCourse.note)
        }

        var result = sumOfCourse / sumOfKredi
        Toast.makeText(this,"ORTALAMA: "+result,Toast.LENGTH_LONG).show()
        coursesArray.clear() //butona tekrar tekrar basıldığında hesaplama yapmasın diye yazıldı



    }

    fun convertNoteToKredi(note:String):Double{
        var value = 0.0
        when(note){
            "AA" -> value = 4.0
            "BA" -> value = 3.5
            "BB" -> value = 3.0
            "CB" -> value = 2.5
            "CC" -> value = 2.0
            "DC" -> value = 1.5
            "DD" -> value = 1.0
            "FF" -> value = 0.0
        }
        return  value
    }

}