import java.net.*;
import java.io.*;
import java.util.Base64;
import java.util.concurrent.*;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

public class Main {
    static final int BUFF_SIZE = 65536;
    static final String HOST = "127.0.0.1";
    static final int PORT = 65432;
    static final String DEVICE_ID = "ABCDEFGH";
    static final int CHUNK = 1024;
    static final AudioFormat FORMAT = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
    static final int CHANNELS = 2;
    static final int RATE = 44100;
    static final int FRAMES_PER_BUFFER = 1000;
    static final int WIDTH = 400;
    static final int v_port = 9998;
    static final int a_port = 9999;
static DatagramSocket v_sock;
static DatagramSocket a_sock;


public static void main(String[] args) {
        try {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        v_sock = new DatagramSocket(v_port);
        a_sock = new DatagramSocket(a_port);
        v_sock.setReceiveBufferSize(BUFF_SIZE);
        v_sock.setReuseAddress(true);
        v_sock.bind(new InetSocketAddress(HOST, v_port));
        a_sock.setReuseAddress(true);
        a_sock.bind(new InetSocketAddress(HOST, a_port));
        Socket s = new Socket(HOST, PORT);
        String message = "R" + DEVICE_ID;
        OutputStream os = s.getOutputStream();
        os.write(message.getBytes());
        os.flush();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(video_receiving_thread);
        executor.execute(audio_receiving_thread);
        executor.shutdown();
        s.close();
        } catch (Exception e) {
        e.printStackTrace();
        }
        }

static Runnable video_receiving_thread = new Runnable() {
public void run() {
    Mat frame = new Mat();
    String title = "RECEIVING VIDEO " + String.valueOf(ProcessHandle.current().pid());
    int fps = 0, frames_to_count = 20, cnt = 0;
    double st = 0;
    try {
        while (true) {
            byte[] buffer = new byte[BUFF_SIZE];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            v_sock.receive(packet);
            byte[] data = Base64.getDecoder().decode(packet.getData());
            Mat decoded = Imgcodecs.imdecode(new Mat(1, data.length, CvType.CV_8UC1, new org.opencv.core.Scalar(0)), Imgcodecs.IMREAD_COLOR);
            decoded.convertTo(frame, CvType.CV_8UC3);
            Size sz = new Size(WIDTH, (int) (WIDTH * frame.height() / frame.width()));
            Imgcodecs.imwrite("temp.jpg", frame);
            frame = Imgcodecs.imread("temp.jpg");
            Imgcodecs.resize(frame, frame, sz);
            Core.putText(frame, "FPS: " + String.valueOf(fps), new org.opencv.core.Point(10, 30),
                    Core.FONT_HERSHEY_SIMPLEX, 0.7, new org.opencv.core.Scalar(0, 0, 255), 2);
            org.opencv.videoio.VideoWriter writer
        }
    }
}