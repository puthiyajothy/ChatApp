import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class ThreadHandler extends Thread {
	Socket socket;

	ThreadHandler(Socket socket) {
		this.socket = socket;

	}

	@Override
	public void run() {
		try {
			DataInputStream InputStream = new DataInputStream(socket.getInputStream());
			PrintStream outPutStream = new PrintStream(socket.getOutputStream());

			outPutStream.println("connect with server");
			boolean data = true;
			while (data) {

				String line = InputStream.readLine();

				if (line.equals("list")) {
					String list[] = { "" };
					Server.map.keySet().forEach(e -> {
						String you = "";
						System.out.println(socket.getRemoteSocketAddress());
						if (socket.getRemoteSocketAddress().equals(((Socket) Server.map.get(e)).getRemoteSocketAddress())) {
							you = "you";
						}
						list[0] += e + you + ",";
					});
					outPutStream.println(list[0]);
				}
				System.out.println(line);

				outPutStream.println("From Server: " + line);
			}
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
