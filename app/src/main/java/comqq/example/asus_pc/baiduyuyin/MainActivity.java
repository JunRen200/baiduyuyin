package comqq.example.asus_pc.baiduyuyin;


import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;

public class MainActivity extends AppCompatActivity implements SpeechSynthesizerListener {
    private static final String TEXT_FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "bd_etts_text.dat";
    private static final String FILE_PATH2 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "bd_etts_speech_male.dat";
    private static final String FILE_PATH1= Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "bd_etts_speech_female.dat";
    private SpeechSynthesizer mSpeechSynthesizer;
    private static final String TAG = "MainActivity";
    private EditText edt;
    Button btn_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_1 = (Button) findViewById(R.id.btn_1);
        edt = (EditText) findViewById(R.id.edt);
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = edt.getText().toString();
                if (!str.equals("")) {
                    startTTS1(str);
                    mSpeechSynthesizer.freeCustomResource();
                } else {
                    Toast.makeText(MainActivity.this, "请输入文字", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void startTTS1(String str) {
        mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        // 设置context
        mSpeechSynthesizer.setContext(this);
        // 设置语音合成状态监听器
        mSpeechSynthesizer.setSpeechSynthesizerListener(this);

        // 设置在线语音合成授权，需要填入从百度语音官网申请的api_key和secret_key
        mSpeechSynthesizer.setApiKey("R5KDnqMQ7wcatd6YgAhQITUC", "8e43218ac5e9cdcbda060b9504e114bb");
        // 设置离线语音合成授权，需要填入从百度语音官网申请的app_id
        mSpeechSynthesizer.setAppId("9460701");

        // 设置语音合成文本模型文件
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, TEXT_FILE_PATH);
        // 设置语音合成声音模型文件
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, FILE_PATH1);
        // 获取语音合成授权信息
        AuthInfo authInfo = mSpeechSynthesizer.auth(TtsMode.MIX);
        // 判断授权信息是否正确，如果正确则初始化语音合成器并开始语音合成，如果失败则做错误处理
        if (authInfo.isSuccess()) {
            Toast.makeText(this, "授权成功", Toast.LENGTH_LONG).show();
            mSpeechSynthesizer.initTts(TtsMode.MIX);
            mSpeechSynthesizer.speak(str);
        } else {
            // 授权失败
            Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onSynthesizeStart(String s) {
        Log.i(TAG, "onSynthesizeStart");
    }

    @Override
    public void onSynthesizeDataArrived(String s, byte[] bytes, int i) {
        Log.i(TAG, "onSynthesizeDataArrived: ");
    }

    @Override
    public void onSynthesizeFinish(String s) {
        Log.i(TAG, "onSynthesizeFinish: ");
    }

    @Override
    public void onSpeechStart(String s) {
        Log.i(TAG, "onSpeechStart: ");
    }

    @Override
    public void onSpeechProgressChanged(String s, int i) {
        Log.i(TAG, "onSpeechProgressChanged: ");
    }

    @Override
    public void onSpeechFinish(String s) {
        Log.i(TAG, "onSpeechFinish: ");
    }

    @Override
    public void onError(String s, SpeechError speechError) {
        Log.i(TAG, "onError: ");
    }
}