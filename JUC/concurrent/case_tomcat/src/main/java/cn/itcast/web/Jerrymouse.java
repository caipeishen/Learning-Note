package cn.itcast.web;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

@Slf4j(topic = "c.Jerrymouse")
public class Jerrymouse {
    private final ExecutorService executorService = new ThreadPoolExecutor(
            8,
            16,
            60,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(10)
    );
    final static String UTF8 = "utf-8";
    final static byte[] LINE_BYTES = System.getProperty("line.separator").getBytes();

    public void start() {
        try (ServerSocket ss = new ServerSocket(80,50, InetAddress.getByName("127.0.0.1"))) {
            while (!executorService.isShutdown()) {
                try {
                    Socket socket = ss.accept();
                    executorService.execute(() -> {
                        handle(socket);
                    });
                } catch (RejectedExecutionException e) {
                    if (!executorService.isShutdown()) {

                    }
                }
            }
        } catch (IOException e) {
            log.error("server socket error : {}", e);
        }
    }

    private void handle(Socket socket) {
        try {
            doDispatch(socket);
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void doDispatch(Socket socket) throws IOException, ServletException {
        RequestImpl request = new RequestImpl(socket);
        ResponseImpl response = new ResponseImpl(socket);
        String requestURI = request.getRequestURI();
        if(requestURI == null) {
            return;
        }
        if(requestURI.equals("/")) {
            index(response.getOutputStream());
        } else {
            _404(response.getOutputStream());
        }
    }

    private void _404(OutputStream out) throws IOException {
        out.write("HTTP/1.1 404 NOT FOUND".getBytes());
        out.write(LINE_BYTES);
        String html = "{\"error\":\"NOT FOUND\"}";
        byte[] bytes = html.getBytes(UTF8);
        out.write("Content-Type: application/json;charset=utf-8".getBytes());
        out.write(LINE_BYTES);
        out.write(("Content-Length: " + bytes.length).getBytes());
        out.write(LINE_BYTES);
        out.flush();
        out.write(LINE_BYTES);
        out.write(bytes);
        out.flush();
    }

    private void index(OutputStream out) throws IOException {
        out.write("HTTP/1.1 200 OK".getBytes());
        out.write(LINE_BYTES);
        String html = "<html><body><h1>hello, Jerrymouse</h1></body></html>";
        byte[] bytes = html.getBytes(UTF8);
        out.write("Content-Type: text/html;charset=utf-8".getBytes());
        out.write(LINE_BYTES);
        out.write(("Content-Length: " + bytes.length).getBytes());
        out.write(LINE_BYTES);
        out.flush();
        out.write(LINE_BYTES);
        out.write(bytes);
        out.flush();
    }

    public static void main(String[] args) {
        new Jerrymouse().start();
    }
}
