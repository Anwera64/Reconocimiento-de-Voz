package com.example.anton.voicerecognition

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var recognizedTexts = ArrayList<ArrayList<String>>()
    var speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), 527)
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request. (In this example I just punched in
                // the value 527)
            }
        }

        rvPastTexts.layoutManager = LinearLayoutManager(this)
        rvPastTexts.adapter = RecognizedAdapter(recognizedTexts, this)

        prepareVoiceRecognition()

        btRecord.setOnClickListener({
            recognize()
        })
    }

    fun recognize() {
        print("Started")
        var intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        speechRecognizer.startListening(intent)
    }

    fun prepareVoiceRecognition() {
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                Log.v("Main", "Ready")
                btRecord.text = getString(R.string.stop)
                btRecord.setOnClickListener({
                    speechRecognizer.stopListening()
                })
            }

            override fun onRmsChanged(rmsdB: Float) {

            }

            override fun onBufferReceived(buffer: ByteArray?) {

            }

            override fun onPartialResults(partialResults: Bundle?) {

            }

            override fun onEvent(eventType: Int, params: Bundle?) {

            }

            override fun onBeginningOfSpeech() {
                Log.v("Main", "Started")
                textOutput.setText(getString(R.string.listening))
            }

            override fun onEndOfSpeech() {
                Log.v("Main", "Ended")
                btRecord.text = getString(R.string.start_talking)
                btRecord.setOnClickListener({
                    recognize()
                })
            }

            override fun onError(error: Int) {
                Log.v("Main", "Error: ".plus(error))
                when (error) {
                    7 -> {
                        textOutput.setText(getString(R.string.couldnt_recognize))
                    }
                    9 -> {
                        textOutput.setText(getString(R.string.need_permissions))
                    }
                }
            }

            override fun onResults(results: Bundle?) {
                Log.v("Main", "Results")
                var text = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                val texts = text?.let { it } ?: return
                val normalTexts = ArrayList<String>()
                normalTexts.addAll(texts)
                textOutput.setText(text[0])
                recognizedTexts.add(normalTexts)
                rvPastTexts.adapter.notifyDataSetChanged()
            }
        })
    }
}
