package com.mutsuddi_s.myflowpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    val channel = Channel<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* CoroutineScope(Dispatchers.Main).launch {
             getUserNames().forEach {
                 Log.d(TAG, "user  $it ")
             }
         }*/

        // producer()


        // consumer()
        /*GlobalScope.launch {


            getNotes()
                .map {
                    FormattedNote(it.isActive,it.title.uppercase(),it.description)
            }
                .filter {
                    it.isActive
                }
                .collect{
                    Log.d(TAG, "onCreate: $it")
                }
        }*/


        /*.onStart {
            emit(-1)
            Log.d(TAG, "onCreate: starting out")
        }
        .onCompletion {
            emit(6)
            Log.d(TAG, "onCreate: completed")
        }
        .onEach {
            Log.d(TAG, "onCreate: about to emit - $it")
        }*/
        /*  .collect{

          Log.d(TAG, "onCreate: 1 - $it")
      }*/


        /*GlobalScope.launch(Dispatchers.Main) {

            val time = measureTimeMillis {

                producer()
                    .buffer(3)
                    .collect {
                    delay(1500)

                    Log.d(TAG, "onCreate: 2 - $it")
                }
            }
            Log.d(TAG, "onCreate: $time")
        }*/

        GlobalScope.launch(Dispatchers.Main) {


            try {



                val result=producer()
                //delay(7000)


                    /*.map{
                        delay(1000)
                        it*2
                        Log.d(TAG, "map thread  - ${Thread.currentThread().name}")
                        
                    }
                    .filter{
                        delay(500)
                        Log.d(TAG, "Filter thread - ${Thread.currentThread().name}")
                        it<8

                    }
                    .flowOn(Dispatchers.IO)*/

                delay(7000)


                Log.d(TAG, "onCreate: ${result.value} ")

                    result.collect {

                        Log.d(TAG, "item - $it")
                    }
            }catch (e: Exception){
                Log.d(TAG, "onCreate: ${e.message} ")
            }
        }


    }

   /* private fun producer() :  StateFlow<Int> {

        val mutableStateFlow = MutableStateFlow(10)

        GlobalScope.launch {
            delay(2000)
            mutableStateFlow.emit(20)

            delay(2000)
            mutableStateFlow.emit(30)
        }

        return  mutableStateFlow
*/




    }

   private fun producer() :  Flow<Int> {

        val mutableSharedFlow= MutableSharedFlow<Int>(  )

        GlobalScope.launch {
        val list= listOf<Int>(1,2,3,4,5,6,7)

        list.forEach {
            mutableSharedFlow.emit(it)
            Log.d(TAG, " Emitting - $it ")
            delay(1000)
        }
        }
        return mutableSharedFlow


    }



    //producer
    /*fun producer() = flow<Int> {

        val list = listOf(1, 2, 3, 4, 5)
        list.forEach {
            delay(1000)

            Log.d(TAG, "Emitter thread - ${Thread.currentThread().name}")
            emit(it)
            throw Exception("Error in emitter")
        }
    }*/
    /* fun producer(){
        CoroutineScope(Dispatchers.Main).launch {

            channel.send(1)
            channel.send(2)
        }



    }
    fun consumer(){
        CoroutineScope(Dispatchers.Main).launch {
            Log.d(TAG, "channel : ${channel.receive().toString()}" )
            Log.d(TAG, "channel : ${channel.receive().toString()}")
            channel.receive()

        }*/

    private fun getNotes(): Flow<Note> {
        val list = listOf(
            Note(1, true, "First", "First Description"),
            Note(2, true, "second", "second Description"),
            Note(3, false, "third", "third Description"),
        )

        return list.asFlow()
    }

    data class Note(val id: Int, val isActive: Boolean, val title: String, val description: String)
    data class FormattedNote(val isActive: Boolean, val title: String, val description: String)


}


/*
private suspend  fun getUserNames():List<String>{
    val list = mutableListOf<String>()
    list.add(getUser(1))
    list.add(getUser(2))
    list.add(getUser(3))
    return list
}

private suspend fun getUser(id: Int): String{
    delay(1000)
    return "Usser$id"
}*/
