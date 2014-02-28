package com.alibaba.sprite;

import java.nio.ByteBuffer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import android.app.Application;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;

import com.alibaba.sprite.util.BufferPool;

/**
 * @author xianmao.hexm
 */
public class SpriteClient extends Application {

    public static final int TOTAL_BUFFER_SIZE = 1024 * 1024;

    // ��Ƶ��ȡԴ 
    public static int AUDIO_SOURCE = MediaRecorder.AudioSource.MIC;

    // ������Ƶ�����ʣ�44100��Ŀǰ�ı�׼������ĳЩ�豸��Ȼ֧��22050��16000��11025  
    public static int SAMPLE_RATE = 44100;

    // ������Ƶ��¼������CHANNEL_IN_STEREOΪ˫������CHANNEL_CONFIGURATION_MONOΪ������  
    public static int CHANNEL_IN_CONFIG = AudioFormat.CHANNEL_IN_STEREO;

    // ������Ƶ�Ĳ�������CHANNEL_OUT_STEREOΪ˫������CHANNEL_OUT_MONOΪ������  
    public static int CHANNEL_OUT_CONFIG = AudioFormat.CHANNEL_OUT_STEREO;

    // ��Ƶ���ݸ�ʽ:PCM 16λÿ����������֤�豸֧�֡�PCM 8λÿ����������һ���ܵõ��豸֧�֡�  
    public static int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;

    private final int bufferSize;
    private final BufferPool bufferPool;
    private final BlockingQueue<ByteBuffer> bufferQueue;

    public SpriteClient() {
        int s1 = AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNEL_IN_CONFIG, AUDIO_FORMAT);
        int s2 = AudioTrack.getMinBufferSize(SAMPLE_RATE, CHANNEL_OUT_CONFIG, AUDIO_FORMAT);
        this.bufferSize = s1 > s2 ? s1 : s2;
        this.bufferPool = new BufferPool(TOTAL_BUFFER_SIZE, bufferSize);
        this.bufferQueue = new LinkedBlockingQueue<ByteBuffer>();
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public BufferPool getBufferPool() {
        return bufferPool;
    }

    public BlockingQueue<ByteBuffer> getBufferQueue() {
        return bufferQueue;
    }

    public void free() {
        bufferQueue.clear();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
