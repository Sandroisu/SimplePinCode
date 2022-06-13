package com.slatinin.pincode

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val codeFragment = CodeFragment()
        supportFragmentManager.beginTransaction().add(R.id.main_container, codeFragment).commit()
        val arr = arrayOf(9, 3, 7, 6, 12, 8, 18, 90, 22)
        quickSort(arr, 0, arr.size - 1)
        var str = ""
        for (num in arr){
            str += num.toString()
        }
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
    }


    fun quickSort(arr: Array<Int>, lowIndex: Int, highIndex: Int) {
        if (lowIndex >= highIndex) {
            return
        }
        val pivot = arr[highIndex]
        var leftPointer = lowIndex
        var rightPointer = highIndex
        while (leftPointer < rightPointer) {
            while (arr[leftPointer] <= pivot && leftPointer < rightPointer) {
                leftPointer++
            }

            while (arr[rightPointer] >= pivot && leftPointer < rightPointer) {
                rightPointer--
            }
            swap(arr, leftPointer, rightPointer)
        }
        swap(arr, leftPointer, highIndex)
        quickSort(arr, lowIndex, leftPointer - 1)
        quickSort(arr, leftPointer + 1, highIndex)
    }

    companion object {
        fun swap(arr: Array<Int>, leftPointer: Int, rightPointer: Int) {
            val temp = arr[leftPointer]
            arr[leftPointer] = arr[rightPointer]
            arr[rightPointer] = temp
        }
    }
}